package com.wfe.behaviours;

import com.wfe.core.input.Keyboard;
import com.wfe.core.input.Keys;
import com.wfe.core.input.Mouse;
import com.wfe.graph.Camera;
import com.wfe.math.Vector3f;
import com.wfe.physics.CollisionPacket;
import com.wfe.physics.FPlane;
import com.wfe.scenegraph.Entity;
import com.wfe.scenegraph.World;
import com.wfe.scenes.GameState;
import com.wfe.utils.MathUtils;
import com.wfe.utils.TimeUtil;

public class PlayerBh extends Behaviour {

	public float speed = 15.0f;
	
	private AnimBh anim;
	
	private boolean move = false;
	
	private Camera camera;
	
	private World world;
	
	private CollisionPacket colPackage;
	
	private Entity helmet;
	private Entity weapon;
	
	private Entity head;
	private Entity rightForearm;
	
	private TimeUtil time;
	private Entity miningEntity;
	private int miningTime = 0;
	private boolean mining = false;
	
	public PlayerBh(Camera camera) {
		this.camera = camera;
	}
	
	@Override
	public void start() {
		this.anim = parent.getBehaviour(AnimBh.class);
		this.world = parent.getWorld();
		
		this.time = new TimeUtil();

		Vector3f pos = new Vector3f(world.cells.get("129 129").position);
		pos.y += 2.2f;
		colPackage = new CollisionPacket(new Vector3f(1, 2, 1), pos);
	
		head = parent.getChildByName("Head");
		rightForearm = parent.getChildByName("Right Arm").getChildByName("Right Forearm");
	}

	@Override
	public void update(float dt) {	
		if(Mouse.isButton(1)) {
			if(mining) {
				float currTime = (float) time.getTime();
				if(currTime <= miningTime) {
					anim.miningAnim(dt);
					GameState.gui.hud.miningProgress.setCurrentValue((currTime * 100) / miningTime);
				} else {
					mining = false;
					time.reset();
					GameState.gui.hud.miningProgress.setCurrentValue(0);
					
					switch(miningEntity.name) {
					case "stone":
						GameState.gui.inventory.addItem("flint");
						miningEntity.getBehaviour(MineralBh.class).decrease();
						if(miningEntity.getBehaviour(MineralBh.class).getCount() == 0) {
							miningEntity = null;
						}
						break;
					case "tree":
						miningEntity.remove();
						miningEntity = null;
						
						for(int i = 0; i < 10; i++)
							GameState.gui.inventory.addItem("log");
						break;
					}
				}
			}
		}
		
		if(Mouse.isButtonUp(1)) {
			mining = false;
			time.reset();
			miningEntity = null;
			GameState.gui.hud.miningProgress.setCurrentValue(0);
		}
		
		if(!mining)
			moving(dt);
	}
	
	public void addHelmet(Entity helmet) {
		if(this.helmet != null)
			removeHelmet();
		
		this.helmet = helmet;
		head.addChild(helmet);
	}
	
	public void removeHelmet() {
		if(helmet != null) {
			head.removeChild(helmet);
			this.helmet = null;
		}
	}
	
	public void addWeapon(Entity weapon) {
		if(this.weapon != null)
			removeWeapon();
		
		this.weapon = weapon;
		rightForearm.addChild(weapon);
	}
	
	public void removeWeapon() {
		if(weapon != null) {
			rightForearm.removeChild(weapon);
			this.weapon = null;
		}
	}
	
	public void addMiningEntity(Entity entity, int miningTime) {
		if(weapon != null) {
			if(weapon.name.equals("axe")) {
				parent.rotation.y = MathUtils.getRotationBetweenPoints(entity.position, parent.position) + 90;
				this.miningEntity = entity;
				this.miningTime = miningTime;
				this.mining = true;
			} else {
				System.out.println("I don't have necessary tools");
			}
		} else {
			System.out.println("I don't have necessary tools");
		}
	}

	public void moving(float dt) {
		move = false;
		
		float yaw = camera.getYaw();
		colPackage.setR3toESpaceVelocity(0, 0, 0);
		
		if(Keyboard.isKey(Keys.KEY_W) && Keyboard.isKey(Keys.KEY_A)) {
			colPackage.setR3toESpaceVelocity((float)Math.sin(Math.toRadians(yaw + 135)) * -1.0f * speed * dt, 
					0, (float)Math.cos(Math.toRadians(yaw + 135)) * speed * dt);
			parent.rotation.y = -yaw + 45;
			move = true;
		} else if(Keyboard.isKey(Keys.KEY_W) && Keyboard.isKey(Keys.KEY_D)) {
			colPackage.setR3toESpaceVelocity((float)Math.sin(Math.toRadians(yaw - 135)) * -1.0f * speed * dt, 
					0, (float)Math.cos(Math.toRadians(yaw - 135)) * speed * dt);
			parent.rotation.y = -yaw - 45;
			move = true;
		} else if(Keyboard.isKey(Keys.KEY_S) && Keyboard.isKey(Keys.KEY_D)) {
			colPackage.setR3toESpaceVelocity((float)Math.sin(Math.toRadians(yaw - 45)) * -1.0f * speed * dt, 
					0, (float)Math.cos(Math.toRadians(yaw -45)) * speed * dt);
			parent.rotation.y = -yaw - 135;
			move = true;
		} else if(Keyboard.isKey(Keys.KEY_S) && Keyboard.isKey(Keys.KEY_A)) {
			colPackage.setR3toESpaceVelocity((float)Math.sin(Math.toRadians(yaw + 45)) * -1.0f * speed * dt, 
					0, (float)Math.cos(Math.toRadians(yaw + 45)) * speed * dt);
			parent.rotation.y = -yaw + 135;
			move = true;
		} else if(Keyboard.isKey(Keys.KEY_W)) {
			colPackage.setR3toESpaceVelocity((float)Math.sin(Math.toRadians(yaw)) * -1.0f * -speed * dt, 
					0, (float)Math.cos(Math.toRadians(yaw)) * -speed * dt);
			parent.rotation.y = -yaw;
			move = true;
		} else if(Keyboard.isKey(Keys.KEY_S)) {
			colPackage.setR3toESpaceVelocity((float)Math.sin(Math.toRadians(yaw)) * -1.0f * speed * dt, 
					0, (float)Math.cos(Math.toRadians(yaw)) * speed * dt);
			parent.rotation.y = -yaw + 180;
			move = true;
		} else if(Keyboard.isKey(Keys.KEY_A)) {
			colPackage.setR3toESpaceVelocity((float)Math.sin(Math.toRadians(yaw + 90)) * -1.0f * speed * dt, 
					0, (float)Math.cos(Math.toRadians(yaw + 90)) * speed * dt);
			parent.rotation.y = -yaw + 90;
			move = true;
		} else if(Keyboard.isKey(Keys.KEY_D)) {
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
