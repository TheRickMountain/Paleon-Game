package com.wfe.graph.render;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL32;

import com.wfe.core.Display;
import com.wfe.core.ResourceManager;
import com.wfe.graph.Camera;
import com.wfe.graph.DirectionalLight;
import com.wfe.graph.processing.LodCalculator;
import com.wfe.graph.shaders.ShaderProgram;
import com.wfe.math.Matrix4f;
import com.wfe.math.Vector3f;
import com.wfe.math.Vector4f;
import com.wfe.terrain.Terrain;
import com.wfe.terrain.TerrainBlock;
import com.wfe.terrain.TexturePack;
import com.wfe.utils.Color;
import com.wfe.utils.MathUtils;
import com.wfe.utils.OpenglUtils;

/**
 * Created by Rick on 08.10.2016.
 */
public class TerrainRenderer {

    private ShaderProgram shader;

    private Matrix4f modelMatrix;

    private Camera camera;

    public TerrainRenderer(Camera camera) {
        this.camera = camera;

        shader = ResourceManager.loadShader("terrain");

        shader.createUniform("model");
        shader.createUniform("view");
        shader.createUniform("projection");

        shader.createUniform("blendMap");
        shader.createUniform("aTexture");
        shader.createUniform("rTexture");
        shader.createUniform("gTexture");
        shader.createUniform("bTexture");

        shader.createUniform("lightPosition");
        shader.createUniform("lightColor");
        
        shader.createUniform("fogColor");
        
        shader.createUniform("plane");

        shader.bind();
        shader.setUniform("blendMap", 0);
        shader.setUniform("aTexture", 1);
        shader.setUniform("rTexture", 2);
        shader.setUniform("gTexture", 3);
        shader.setUniform("bTexture", 4);
        shader.setUniform("projection", this.camera.getProjectionMatrix());
        shader.unbind();
        
        modelMatrix = new Matrix4f();
    }

    public void render(Map<Terrain, List<TerrainBlock>> terrainBatches, DirectionalLight light, Camera camera, Color fogColor,
    		Vector4f plane) {
        if(Display.wasResized()) {
            shader.setUniform("projection", this.camera.getProjectionMatrix(), true);
        }

        shader.bind();
        
        shader.setUniform("fogColor", fogColor);
        
        shader.setUniform("plane", plane);

        for (List<TerrainBlock> blocks : terrainBatches.values()) {
            for (TerrainBlock block : blocks) {
                block.setStitching();
                LodCalculator.calculateTerrainLOD(block, camera);
            }
        }

        OpenglUtils.cullFace(true);
        for(Terrain terrain : terrainBatches.keySet()) {
            prepareTerrainInstance(terrain, camera, light);
            List<TerrainBlock> batch = terrainBatches.get(terrain);
            for(TerrainBlock terrainBlock : batch) {
                if(camera.testTerrainInView(terrainBlock)) {
                    int[] indexInfo = terrainBlock.getIndicesVBOInfo();
                    OpenglUtils.bindEBO(indexInfo[0]);
                    render(terrainBlock.getIndex(), indexInfo[1]);
                    OpenglUtils.unbindEBO();
                }
            }
            OpenglUtils.unbindVAO();
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        }

        shader.unbind();
    }

    private void prepareTerrainInstance(Terrain terrain, Camera camera, DirectionalLight light) {
        shader.setUniform("view", camera.getViewMatrix());
        shader.setUniform("lightPosition", light.position);
        shader.setUniform("lightColor", light.color);

        shader.setUniform("model",
                MathUtils.getModelMatrix(modelMatrix, new Vector3f(terrain.getX(), 0, terrain.getZ()),
                        new Vector3f(0, 0, 0), new Vector3f(1, 1, 1)));

        TexturePack texturePack = terrain.getTexture();
        texturePack.blendMap.bind(0);
        texturePack.alphaTexture.bind(1);
        texturePack.redTexture.bind(2);
        texturePack.greenTexture.bind(3);
        texturePack.blueTexture.bind(4);

        OpenglUtils.bindVAO(terrain.getVaoId());
    }

    private void render(int blockIndex, int indicesLength) {
        int vertexOffset = blockIndex * Terrain.VERTICES_PER_NODE;
        GL32.glDrawElementsBaseVertex(GL11.GL_TRIANGLES, indicesLength, GL11.GL_UNSIGNED_INT, 0, vertexOffset);
    }

    public void cleanup() {
        shader.cleanup();
    }

}
