package com.wfe.physics;

import com.wfe.math.Matrix3f;
import com.wfe.math.Vector3f;

public class CollisionPacket {

	public final Vector3f eRadius;
	
	private final Vector3f R3Velocity;
	private final Vector3f R3Position;
	
	private final Vector3f velocity;
	private final Vector3f normalizedVelocity;
	private final Vector3f basePoint;
	
	public boolean foundCollision;
	public double nearestDistance;
	public final Vector3f intersectionPoint;
	
	private Matrix3f eSpace;
	private Matrix3f R3;
	
	public CollisionPacket(Vector3f eRadius, Vector3f bP) {
		this.eRadius = eRadius;
		
		eSpace = new Matrix3f();
		eSpace.setIdentity();
        eSpace.m00 /= eRadius.x;
        eSpace.m11 /= eRadius.y;
        eSpace.m22 /= eRadius.z;
        
        R3 = new Matrix3f();
        Matrix3f.invert(eSpace, R3);
		
		R3Velocity = new Vector3f();
		R3Position = new Vector3f();
		
		velocity = new Vector3f();
		normalizedVelocity = new Vector3f();
		basePoint = Matrix3f.transform(eSpace, bP, bP);
		
		intersectionPoint = new Vector3f();
	}
	
	public void moveTo(Vector3f velocity) {
		Vector3f.add(basePoint, velocity, basePoint);
	}
	
	public void setR3toESpaceVelocity(float x, float y, float z) {
		this.velocity.set(x, y, z);
		Matrix3f.transform(eSpace, velocity, velocity);
	}
	
	public void setESpaceVelocity(float x, float y, float z) {
		this.velocity.set(x, y, z);
	}
	
	public Vector3f getNormalizedVelocity() {
		return velocity.normalise(normalizedVelocity);
	}
	
	public void setR3toESpacePosition(float x, float y, float z) {
		this.basePoint.set(x, y, z);
		Matrix3f.transform(eSpace, basePoint, basePoint);
	}
	
	public void setESpacePosition(float x, float y, float z) {
		this.basePoint.set(x, y, z);
	}

	public Vector3f getR3Velocity() {
		return Matrix3f.transform(R3, velocity, R3Velocity);
	}

	public Vector3f getR3Position() {
		return Matrix3f.transform(R3, basePoint, R3Position);
	}

	public Vector3f getVelocity() {
		return velocity;
	}

	public Vector3f getBasePoint() {
		return basePoint;
	}
	
}
