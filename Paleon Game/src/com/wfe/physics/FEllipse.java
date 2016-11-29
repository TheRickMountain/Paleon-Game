package com.wfe.physics;

import com.wfe.math.Vector3f;

public class FEllipse {

	public Vector3f position;
	public Vector3f radius;
	
	public Vector3f velocity;
	
	public FEllipse(Vector3f position, Vector3f radius) {
		this.position = position;
		this.radius = radius;
		
		this.velocity = new Vector3f();
	}
	
}
