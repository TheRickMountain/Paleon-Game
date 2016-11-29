package com.wfe.graph.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.wfe.core.Display;
import com.wfe.core.ResourceManager;
import com.wfe.graph.Camera;
import com.wfe.graph.Mesh;
import com.wfe.graph.TextureLoader;
import com.wfe.graph.shaders.ShaderProgram;
import com.wfe.math.Matrix4f;
import com.wfe.utils.Color;
import com.wfe.utils.GameTime;
import com.wfe.utils.MathUtils;

/**
 * Created by Rick on 11.10.2016.
 */
public class SkyboxRenderer {

    private Mesh mesh;

    private static final float SIZE = 500.0f;

    private static final float[] VERTICES = {
            -SIZE, SIZE, -SIZE,
            -SIZE, -SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,
            SIZE, SIZE, -SIZE,
            -SIZE, SIZE, -SIZE,

            -SIZE, -SIZE, SIZE,
            -SIZE, -SIZE, -SIZE,
            -SIZE, SIZE, -SIZE,
            -SIZE, SIZE, -SIZE,
            -SIZE, SIZE, SIZE,
            -SIZE, -SIZE, SIZE,

            SIZE, -SIZE, -SIZE,
            SIZE, -SIZE, SIZE,
            SIZE, SIZE, SIZE,
            SIZE, SIZE, SIZE,
            SIZE, SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,

            -SIZE, -SIZE, SIZE,
            -SIZE, SIZE, SIZE,
            SIZE, SIZE, SIZE,
            SIZE, SIZE, SIZE,
            SIZE, -SIZE, SIZE,
            -SIZE, -SIZE, SIZE,
            
            -SIZE,  SIZE, -SIZE,
   	     	SIZE,  SIZE, -SIZE,
   	     	SIZE,  SIZE,  SIZE,
   	     	SIZE,  SIZE,  SIZE,
   	     	-SIZE,  SIZE,  SIZE,
   	     	-SIZE,  SIZE, -SIZE,
    };

    private ShaderProgram shader;

    private Camera camera;
    
    private Matrix4f viewMatrix;

    int nightSky;
    int sunsetSky;
    int sunnySky;
    
    private int texture0;
	private int texture1;
	
	private float blendFactor = 0;
	
	private float time = 0;
    
    private static final float ROTATE_SPEED = 0.5f;
	private float rotation = 0;

    public SkyboxRenderer(Camera camera) throws Exception {
        this.camera = camera;

        mesh = new Mesh(VERTICES, 3);
        
        nightSky = TextureLoader.loadCubemap("night");
        sunsetSky = TextureLoader.loadCubemap("sunset");
        sunnySky = TextureLoader.loadCubemap("sunny");

        shader = ResourceManager.loadShader("skybox");

        shader.createUniform("projection");
        shader.createUniform("view");

        shader.createUniform("cubeMap0");
        shader.createUniform("cubeMap1");
        
        shader.createUniform("blendFactor");
        
        shader.createUniform("fogColor");
        
        shader.bind();
        shader.setUniform("cubeMap0", 0);
        shader.setUniform("cubeMap1", 1);
        shader.setUniform("projection", this.camera.getProjectionMatrix());
        shader.unbind();
        
        viewMatrix = new Matrix4f();
    }
    
    public void update(float dt) {
    	rotation += ROTATE_SPEED * dt;
    	
		time = GameTime.getATime();
		if(time >= 0 && time < 2500){
			texture0 = nightSky;
			texture1 = nightSky;
			blendFactor = (time - 0)/(2500 - 0);
		} else if(time >= 2500 && time < 3500){
			texture0 = nightSky;
			texture1 = sunsetSky;
			blendFactor = (time - 2500)/(3500 - 2500);
		} else if(time >= 3500 && time < 7500){
			texture0 = sunsetSky;
			texture1 = sunnySky;
			blendFactor = (time - 3500)/(7500 - 3500);
		} else if(time >= 7500 && time < 12000){
			texture0 = sunnySky;
			texture1 = sunnySky;
			blendFactor = (time - 7500)/(12000 - 7500);
		} else if(time >= 12000 && time < 16500){
			texture0 = sunnySky;
			texture1 = sunnySky;
			blendFactor = (time - 12000)/(16500 - 12000);
		} else if(time >= 16500 && time < 21000){
			texture0 = sunnySky;
			texture1 = sunsetSky;
			blendFactor = (time - 16500)/(21000 - 16500);
		} else if(time >= 21000 && time < 23500){
			texture0 = sunsetSky;
			texture1 = nightSky;
			blendFactor = (time - 21000)/(23500 - 21000);
		} else {
			texture0 = nightSky;
			texture1 = nightSky;
			blendFactor = (time - 23500)/(24001 - 23500);
		} 
	}
    
    public void render(Camera camera, Color fogColor) {
        if(Display.wasResized()) {
            shader.setUniform("projection", this.camera.getProjectionMatrix(), true);
        }

        shader.bind();
        shader.setUniform("fogColor", fogColor);
        Matrix4f.load(camera.getViewMatrix(), viewMatrix);
        viewMatrix.m30 = 0;
        viewMatrix.m31 = 0;
        viewMatrix.m32 = 0;
        Matrix4f.rotate((float)Math.toRadians(rotation), MathUtils.AXIS_Y, viewMatrix, viewMatrix);
        shader.setUniform("view", viewMatrix);
        shader.setUniform("blendFactor", blendFactor);
        
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, texture0);
        GL13.glActiveTexture(GL13.GL_TEXTURE1);
        GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, texture1);
        
        GL30.glBindVertexArray(mesh.getVAO());
        GL20.glEnableVertexAttribArray(0);
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, mesh.getVertexCount());
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        
        shader.unbind();
    }

    public void cleanup() {
        mesh.cleanup();
        shader.cleanup();
    }

}
