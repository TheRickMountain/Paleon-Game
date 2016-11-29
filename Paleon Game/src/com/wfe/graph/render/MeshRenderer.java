package com.wfe.graph.render;

import java.util.List;
import java.util.Map;

import com.wfe.components.Material;
import com.wfe.core.Display;
import com.wfe.core.ResourceManager;
import com.wfe.graph.Camera;
import com.wfe.graph.DirectionalLight;
import com.wfe.graph.Mesh;
import com.wfe.graph.Texture;
import com.wfe.graph.shaders.ShaderProgram;
import com.wfe.math.Vector4f;
import com.wfe.scenegraph.Entity;
import com.wfe.utils.Color;
import com.wfe.utils.OpenglUtils;

/**
 * Created by Rick on 06.10.2016.
 */
public class MeshRenderer {

    private ShaderProgram shader;

    private Camera camera;

    public MeshRenderer(Camera camera) {
        this.camera = camera;

        shader = ResourceManager.loadShader("entity");

        shader.createUniform("projection");
        shader.createUniform("view");
        shader.createUniform("model");

        shader.createUniform("image");
        
        shader.createUniform("color");

        shader.createUniform("lightPosition");
        shader.createUniform("lightColor");

        shader.createUniform("viewPosition");
        
        shader.createUniform("transparency");

        shader.createUniform("numberOfRows");
        shader.createUniform("offset");
        shader.createUniform("useFakeLighting");
        shader.createUniform("useSpecular");
        
        shader.createUniform("plane");
        
        shader.createUniform("fogColor");
        
        shader.bind();
        shader.setUniform("projection", this.camera.getProjectionMatrix());
        shader.setUniform("image", 0);
        shader.unbind();
    }

    public void render(Map<Mesh, List<Entity>> entities, DirectionalLight light, Camera camera, Color fogColor, Vector4f plane) {
        if(Display.wasResized()) {
            shader.setUniform("projection", this.camera.getProjectionMatrix(), true);
        }
        
        shader.bind();
        
        shader.setUniform("fogColor", fogColor);

        shader.setUniform("plane", plane);
        
        shader.setUniform("view", camera.getViewMatrix());
        shader.setUniform("viewPosition", camera.getPosition());

        shader.setUniform("lightPosition", light.position);
        shader.setUniform("lightColor", light.color);

        for(Mesh mesh :entities.keySet()) {
            mesh.startRender();

            List<Entity> batch = entities.get(mesh);
            for(Entity entity : batch) {
            	if(entity.isActive()) {
            		if(camera.testEntityInView(entity)) {
		                shader.setUniform("model", entity.getTransform().getModelMatrix());
		
		                Material material = entity.getComponent(Material.class);
		
		                shader.setUniform("color", material.color);
		                int numberOfRows = material.getNumberOfRows();
		                shader.setUniform("numberOfRows", numberOfRows);
		                if(numberOfRows != 1) {
		                    shader.setUniform("offset", entity.getTextureXOffset(), entity.getTextureYOffset());
		                }
		                shader.setUniform("useFakeLighting", material.useFakeLighting);
		                shader.setUniform("useSpecular", material.useSpecular);
		
		                if(material.transparency) {
		                    OpenglUtils.cullFace(false);
		                    shader.setUniform("transparency", true);
		                } else {
		                	shader.setUniform("transparency", false);
		                }
		
		                Texture texture = material.texture;
		
		                texture.bind(0);
		
		                mesh.render();
		
		                texture.unbind();
		
		                OpenglUtils.cullFace(true);        	
            		}
            	}
            }

            mesh.endRender();
        }
    }

    public void cleanup() {
        shader.cleanup();
    }

}
