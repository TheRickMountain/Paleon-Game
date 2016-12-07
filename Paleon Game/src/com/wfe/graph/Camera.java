package com.wfe.graph;

import com.wfe.core.Paleon;
import com.wfe.core.input.Keys;
import com.wfe.core.input.Keyboard;
import com.wfe.core.input.Mouse;
import com.wfe.graph.processing.Frustum;
import com.wfe.graph.water.WaterTile;
import com.wfe.math.Matrix4f;
import com.wfe.math.Vector3f;
import com.wfe.scenegraph.Entity;
import com.wfe.scenes.GameState;
import com.wfe.terrain.TerrainBlock;
import com.wfe.utils.MathUtils;

/**
 * Created by Rick on 07.10.2016.
 */
public class Camera {

    public static final float FOV = 60.0f;
    public static final float Z_NEAR = 0.1f;
    public static final float Z_FAR = 1000.f;

    private static final float MIN_DISTANCE = 25;
    private static final float MAX_DISTANCE = 200;

    private float distanceFromPlayer = 60;
    private float angleAroundPlayer = 180;
    private float zoomSpeed = 200;

    private static final float MAX_PITCH = 85;
    private static final float MIN_PITCH = 45;
    
    private Frustum frustum;

    private float speed = 25;

    private final Vector3f position;

    private float pitch = 45;

    private float yaw = 0;

    public Vector3f playerPosition;

    private Matrix4f projectionMatrix;

    private Matrix4f viewMatrix;

    public Camera(Vector3f playerPosition) {
    	this.frustum = new Frustum(this);
    	
        this.playerPosition = playerPosition;
        position = new Vector3f(0, 0, 0);

        projectionMatrix = new Matrix4f();
        viewMatrix = new Matrix4f();

        MathUtils.getPerspProjectionMatrix(projectionMatrix, FOV, 
        		Paleon.display.getWidth(), Paleon.display.getHeight(), Z_NEAR, Z_FAR);
    }

    public Vector3f getPosition() {
        return position;
    }

    public void invertPitch() {
    	this.pitch = -pitch;
    }
    
    public void update() {
        if(Paleon.display.wasResized()) {
            MathUtils.getPerspProjectionMatrix(projectionMatrix, FOV, 
            		Paleon.display.getWidth(), Paleon.display.getHeight(), Z_NEAR, Z_FAR);
        }
        
        frustum.updatePlanes();
    }

    public void rotate(float dt) {
    	if(GameState.state.equals(GameState.State.GAME)) {
	        if(Mouse.isButton(2)) {
	            calculateAngleAroundPlayer(dt);
	            calculatePitch(dt);
	        }
	
	        if(Mouse.isButtonDown(2)) {
	            Mouse.hide();
	        }
	
	        if(Mouse.isButtonUp(2)) {
	            Mouse.show();
	        }
	        
	        calculateZoom(dt);
    	}

        float horizontalDistance = calculateHorizontalDistance();
        float verticalDistance = calculateVerticalDistance();
        calculateCameraPosition(horizontalDistance, verticalDistance);
        this.yaw = 180 - angleAroundPlayer;
    }

    public void move(float dt) {
        float offsetX = 0;
        float offsetY = 0;
        float offsetZ = 0;

        if(Keyboard.isKey(Keys.KEY_W)) {
            offsetZ = -speed * dt;
        } else if(Keyboard.isKey(Keys.KEY_S)) {
            offsetZ = speed * dt;
        }

        if(Keyboard.isKey(Keys.KEY_A)) {
            offsetX = -speed * dt;
        } else if(Keyboard.isKey(Keys.KEY_D)) {
            offsetX = speed * dt;
        }

        movePosition(offsetX, offsetY, offsetZ);
    }

    private void movePosition(float offsetX, float offsetY, float offsetZ) {
        if(offsetZ != 0) {
            playerPosition.x += (float)Math.sin(Math.toRadians(yaw)) * -1.0f * offsetZ;
            playerPosition.z += (float)Math.cos(Math.toRadians(yaw))* offsetZ;
        }
        if(offsetX != 0) {
            playerPosition.x += (float)Math.sin(Math.toRadians(yaw - 90)) * -1.0f * offsetX;
            playerPosition.z += (float)Math.cos(Math.toRadians(yaw - 90))* offsetX;
        }

        playerPosition.y += offsetY;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    private void calculateAngleAroundPlayer(float dt){
        float angleChange = Mouse.Cursor.getDX() * 20 * dt;
        angleAroundPlayer -= angleChange;
    }

    private void calculatePitch(float dt){
        float pitchChange = -Mouse.Cursor.getDY() * 10 * dt;
        pitch -= pitchChange;
        if(pitch >= MAX_PITCH){
            pitch = MAX_PITCH;
        } else if(pitch <= MIN_PITCH){
            pitch = MIN_PITCH;
        }
    }

    private void calculateCameraPosition(float horizDistance, float verticDistance){
        float theta = angleAroundPlayer;
        float offsetX = (float) (horizDistance * Math.sin(Math.toRadians(theta)));
        float offsetZ = (float) (horizDistance * Math.cos(Math.toRadians(theta)));
        position.x = playerPosition.x - offsetX;
        position.z = playerPosition.z - offsetZ;
        position.y = playerPosition.y + verticDistance;
    }

    private float calculateHorizontalDistance(){
        return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
    }

    private float calculateVerticalDistance(){
        return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
    }

    private void calculateZoom(float dt){
        float zoomLevel = Mouse.getScroll() * dt * zoomSpeed;
        float temp = distanceFromPlayer;
        temp -= zoomLevel;
        if(temp >= MAX_DISTANCE){
            temp = MAX_DISTANCE;
        } else if(temp <= MIN_DISTANCE){
            temp = MIN_DISTANCE;
        }
        distanceFromPlayer = temp;
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }
    
    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }
    
    public void updateViewMatrix(){
    	viewMatrix = MathUtils.getViewMatrix(viewMatrix, this);
    }
    
    public boolean testTerrainInView(TerrainBlock block) {
    	return frustum.testTerrainInView(block);
    }
    
    public boolean testEntityInView(Entity entity) {
    	return frustum.testEntityInView(entity);
    }
    
    public boolean testWaterInView(WaterTile water) {
    	return frustum.testWaterTileInView(water);
    }

}
