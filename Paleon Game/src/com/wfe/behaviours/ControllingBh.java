package com.wfe.behaviours;

import com.wfe.components.Collider;
import com.wfe.core.ResourceManager;
import com.wfe.entities.Wall;
import com.wfe.graph.Camera;
import com.wfe.input.Key;
import com.wfe.input.Keyboard;
import com.wfe.input.Mouse;
import com.wfe.math.Vector2f;
import com.wfe.math.Vector3f;
import com.wfe.physics.CollisionPacket;
import com.wfe.physics.FPlane;
import com.wfe.scenegraph.Entity;
import com.wfe.scenegraph.World;
import com.wfe.utils.MousePicker;

public class ControllingBh extends Behaviour {

	public float speed = 15.0f;
	
	private AnimBh anim;
	
	private boolean move = false;
	
	private Camera camera;
	
	private World world;
	
	CollisionPacket colPackage;
	
	Entity buildingEntity;
	
	public ControllingBh(Camera camera) {
		this.camera = camera;
	}
	
	@Override
	public void start() {
		this.anim = parent.getBehaviour(AnimBh.class);
		this.world = parent.getWorld();
		
		colPackage = new CollisionPacket(new Vector3f(1, 2, 1), new Vector3f(400, world.getTerrainHeight(400, 400) + 2.2f, 400));
	}

	@Override
	public void update(float dt) {	
		if(Keyboard.isKeyDown(Key.NUM_1)) {
			buildingEntity = new Wall(world);
		}
		
		if(buildingEntity != null) {
			Vector2f point = MousePicker.getGridPoint();
			
			if(point != null) {
				buildingEntity.position.set(point.x, world.getTerrainHeight(point.x, point.y), point.y);
			}
			
			if(Mouse.isButtonDown(0)) {
				buildingEntity.addComponent(new Collider(ResourceManager.getColliderMesh("box"),
						new Vector3f(buildingEntity.position.x, buildingEntity.position.y, buildingEntity.position.z),
						new Vector3f(0, buildingEntity.rotation.y, 0), new Vector3f(1.5f, 1.5f, 1.5f)));
				buildingEntity = null;
			}
			
			if(Keyboard.isKeyDown(Key.R)) {
				buildingEntity.rotation.y += 90;
			}
		}
		
		moving(dt);
	}

	public void moving(float dt) {
		move = false;
		
		float yaw = camera.getYaw();
		colPackage.setR3toESpaceVelocity(0, 0, 0);
		
		if(Keyboard.isKey(Key.W) && Keyboard.isKey(Key.A)) {
			colPackage.setR3toESpaceVelocity((float)Math.sin(Math.toRadians(yaw + 135)) * -1.0f * speed * dt, 
					0, (float)Math.cos(Math.toRadians(yaw + 135)) * speed * dt);
			parent.rotation.y = -yaw + 45;
			move = true;
		} else if(Keyboard.isKey(Key.W) && Keyboard.isKey(Key.D)) {
			colPackage.setR3toESpaceVelocity((float)Math.sin(Math.toRadians(yaw - 135)) * -1.0f * speed * dt, 
					0, (float)Math.cos(Math.toRadians(yaw - 135)) * speed * dt);
			parent.rotation.y = -yaw - 45;
			move = true;
		} else if(Keyboard.isKey(Key.S) && Keyboard.isKey(Key.D)) {
			colPackage.setR3toESpaceVelocity((float)Math.sin(Math.toRadians(yaw - 45)) * -1.0f * speed * dt, 
					0, (float)Math.cos(Math.toRadians(yaw -45)) * speed * dt);
			parent.rotation.y = -yaw - 135;
			move = true;
		} else if(Keyboard.isKey(Key.S) && Keyboard.isKey(Key.A)) {
			colPackage.setR3toESpaceVelocity((float)Math.sin(Math.toRadians(yaw + 45)) * -1.0f * speed * dt, 
					0, (float)Math.cos(Math.toRadians(yaw + 45)) * speed * dt);
			parent.rotation.y = -yaw + 135;
			move = true;
		} else if(Keyboard.isKey(Key.W)) {
			colPackage.setR3toESpaceVelocity((float)Math.sin(Math.toRadians(yaw)) * -1.0f * -speed * dt, 
					0, (float)Math.cos(Math.toRadians(yaw)) * -speed * dt);
			parent.rotation.y = -yaw;
			move = true;
		} else if(Keyboard.isKey(Key.S)) {
			colPackage.setR3toESpaceVelocity((float)Math.sin(Math.toRadians(yaw)) * -1.0f * speed * dt, 
					0, (float)Math.cos(Math.toRadians(yaw)) * speed * dt);
			parent.rotation.y = -yaw + 180;
			move = true;
		} else if(Keyboard.isKey(Key.A)) {
			colPackage.setR3toESpaceVelocity((float)Math.sin(Math.toRadians(yaw + 90)) * -1.0f * speed * dt, 
					0, (float)Math.cos(Math.toRadians(yaw + 90)) * speed * dt);
			parent.rotation.y = -yaw + 90;
			move = true;
		} else if(Keyboard.isKey(Key.D)) {
			colPackage.setR3toESpaceVelocity((float)Math.sin(Math.toRadians(yaw - 90)) * -1.0f * speed * dt,
					0, (float)Math.cos(Math.toRadians(yaw - 90)) * speed * dt);
			parent.rotation.y = -yaw - 90;
			move = true;
		}
		
		collideAndSlide();
		
		parent.position.set(colPackage.getR3Position());
		camera.playerPosition.set(parent.position);
		camera.playerPosition.y += 1.1f;
		
		if(move) {
			anim.walkAnim(dt);	
		} else {
			anim.idleAnim(dt);
		}
	}
	
	@Override
	public void onGUI() {
		
	}
	
	private int collisionRecursionDepth = 0;
	public void collideAndSlide() {
		Vector3f eSpacePosition = new Vector3f();
		eSpacePosition.set(colPackage.getBasePoint());
			
		Vector3f eSpaceVelocity = new Vector3f();
		eSpaceVelocity.set(colPackage.getVelocity());
		
		collisionRecursionDepth = 0;
		
		Vector3f finalPosition = collideWithWorld(eSpacePosition, eSpaceVelocity);
		
		colPackage.setESpacePosition(finalPosition.x, 
				(world.getTerrainHeight(parent.position.x, parent.position.z) + 2.2f) * 0.5f, finalPosition.z);
	}
	
	private static final float unitsPerMeter = 100.0f;
	
	public Vector3f collideWithWorld(Vector3f pos, Vector3f vel) {
		float unitScale = unitsPerMeter / 100.0f;
		float veryCloseDistance = 0.005f * unitScale;
		
		if(collisionRecursionDepth > 5)
			return pos;
		
		colPackage.setESpaceVelocity(vel.x, vel.y, vel.z);
		colPackage.setESpacePosition(pos.x, pos.y, pos.z);
		colPackage.foundCollision = false;
		
		world.checkCollision(colPackage);
		
		if(colPackage.foundCollision == false) {
			return Vector3f.add(pos, vel, null);
		}
		
		Vector3f destinationPoint = Vector3f.add(pos, vel, null);
		Vector3f newBasePoint = new Vector3f(pos);
		
		if(colPackage.nearestDistance >= veryCloseDistance) {
			Vector3f V = new Vector3f(vel);
			V.normalise();
			V.scale((float) (colPackage.nearestDistance - veryCloseDistance));
			newBasePoint = Vector3f.add(colPackage.getBasePoint(), V, null);
			
			V.normalise();
			Vector3f vcdV = new Vector3f(V);
			vcdV.scale(veryCloseDistance);
			Vector3f.sub(colPackage.intersectionPoint, vcdV, 
					colPackage.intersectionPoint);
		}
		
		Vector3f slidePlaneOrigin = new Vector3f(colPackage.intersectionPoint);
		Vector3f slidePlaneNormal = new Vector3f(
				Vector3f.sub(newBasePoint, colPackage.intersectionPoint, null));
		slidePlaneNormal.normalise();
		FPlane slidingPlane = new FPlane(slidePlaneOrigin, slidePlaneNormal);
		
		Vector3f newSlidePlaneNormal = new Vector3f(slidePlaneNormal);
		newSlidePlaneNormal.scale((float) slidingPlane.signedDistanceTo(destinationPoint));
		
		Vector3f newDestinationPoint = new Vector3f(
				Vector3f.sub(destinationPoint, newSlidePlaneNormal, null));
		
		Vector3f newVelocityVector = Vector3f.sub(newDestinationPoint, 
				colPackage.intersectionPoint, null);
		
		if(newVelocityVector.length() < veryCloseDistance) {
			return newBasePoint;
		}
		
		collisionRecursionDepth++;
		return collideWithWorld(newBasePoint, newVelocityVector);
	}

}
