package com.wfe.graph.render;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.wfe.core.Display;
import com.wfe.core.ResourceManager;
import com.wfe.graph.Camera;
import com.wfe.graph.DirectionalLight;
import com.wfe.graph.Mesh;
import com.wfe.graph.Texture;
import com.wfe.graph.shaders.ShaderProgram;
import com.wfe.graph.water.WaterFrameBuffers;
import com.wfe.graph.water.WaterTile;
import com.wfe.math.Matrix4f;
import com.wfe.math.Vector3f;
import com.wfe.utils.Color;
import com.wfe.utils.MathUtils;
import com.wfe.utils.OpenglUtils;

public class WaterRenderer {

	private static final float WAVE_SPEED = 0.03f;
	
	private Camera camera;
	
	private Mesh quad;
	private ShaderProgram shader;
	private Matrix4f modelMatrix;
	
	private WaterFrameBuffers fbos;
	
	private Texture dudvMap;
	private Texture normalMap;
	
	private float moveFactor = 0;

	public WaterRenderer(Camera camera, WaterFrameBuffers fbos) {
		this.fbos = fbos;
		
		this.camera = camera;
		
		this.modelMatrix = new Matrix4f();
		
		dudvMap = ResourceManager.getTexture("dudvMap");
		normalMap = ResourceManager.getTexture("normalMap");
		
		this.shader = ResourceManager.loadShader("water");
		
		shader.createUniform("projectionMatrix");
		shader.createUniform("modelMatrix");
		shader.createUniform("viewMatrix");
		
		shader.createUniform("reflectionTexture");
		shader.createUniform("refractionTexture");
		shader.createUniform("dudvMap");
		shader.createUniform("normalMap");
		shader.createUniform("depthMap");
		
		shader.createUniform("moveFactor");
		shader.createUniform("cameraPosition");
		shader.createUniform("lightPosition");
		shader.createUniform("lightColor");
		
		shader.createUniform("fogColor");
		
		shader.bind();
		shader.setUniform("reflectionTexture", 0);
		shader.setUniform("refractionTexture", 1);
		shader.setUniform("dudvMap", 2);
		shader.setUniform("normalMap", 3);
		shader.setUniform("depthMap", 4);
		shader.unbind();
		
		setUpVAO();
		
		shader.setUniform("projectionMatrix", camera.getProjectionMatrix(), true);
	}
	
	public void update(float dt) {
		moveFactor += WAVE_SPEED * dt;
		moveFactor %= 1;
	}

	public void render(List<WaterTile> water, DirectionalLight sun, Color fogColor) {
		prepareRender(sun, fogColor);	
		for (WaterTile tile : water) {
			if(camera.testWaterInView(tile)) {
				MathUtils.getEulerModelMatrix(modelMatrix,
						new Vector3f(tile.getX(), WaterTile.HEIGHT, tile.getZ()), new Vector3f(0, 0, 0),
						new Vector3f(WaterTile.TILE_SIZE, WaterTile.TILE_SIZE, WaterTile.TILE_SIZE));
				shader.setUniform("modelMatrix", modelMatrix);
				GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, quad.getVertexCount());
			}
		}
		unbind();
	}
	
	private void prepareRender(DirectionalLight sun, Color fogColor){
		shader.bind();
		
		if(Display.wasResized()) {
			shader.setUniform("projectionMatrix", camera.getProjectionMatrix());
		}
		
		shader.setUniform("fogColor", fogColor);
		
		shader.setUniform("cameraPosition", camera.getPosition());
		
		shader.setUniform("lightPosition", sun.position);
		shader.setUniform("lightColor", sun.color.getRf(), sun.color.getGf(), sun.color.getBf());
		
		shader.setUniform("viewMatrix", camera.getViewMatrix());
		shader.setUniform("moveFactor", moveFactor);
		GL30.glBindVertexArray(quad.getVAO());
		GL20.glEnableVertexAttribArray(0);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, fbos.getReflectionTexture());
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, fbos.getRefractionTexture());
		dudvMap.bind(2);
		normalMap.bind(3);
		GL13.glActiveTexture(GL13.GL_TEXTURE4);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, fbos.getRefractionDepthTexture());
		
		OpenglUtils.alphaBlending(true);
	}
	
	private void unbind(){
		OpenglUtils.alphaBlending(false);
		
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		shader.unbind();
	}

	private void setUpVAO() {
		float[] vertices = { -1, -1, -1, 1, 1, -1, 1, -1, -1, 1, 1, 1 };
		quad = new Mesh(vertices, 2);
	}
	
	public void cleanup() {
		quad.cleanup();
		shader.cleanup();
	}

}
