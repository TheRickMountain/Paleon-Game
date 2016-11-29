package com.wfe.behaviours;

import com.wfe.math.Vector3f;
import com.wfe.utils.MousePicker;

public class BoundingBoxBh extends Behaviour {

	public Vector3f bounds[];
	
	private Vector3f min, max;
	
	public BoundingBoxBh(Vector3f min, Vector3f max) {
		bounds = new Vector3f[2];
		bounds[0] = new Vector3f(min);
		bounds[1] = new Vector3f(max);
		
		this.min = min;
		this.max = max;
	}
	
	
	@Override
	public void start() {
		
	}

	@Override
	public void update(float deltaTime) {
		bounds[0].x = min.x + parent.position.x;
		bounds[0].y = min.y + parent.position.y;
		bounds[0].z = min.z + parent.position.z;
		 		
		bounds[1].x = max.x + parent.position.x;
		bounds[1].y = max.y + parent.position.y;
		bounds[1].z = max.z + parent.position.z;
	}

	@Override
	public void onGUI() {
		
	}
	
	public boolean intersect() {
		float tmin, tmax, tymin, tymax, tzmin, tzmax;
		Vector3f rayOrigin = MousePicker.getRayOrigin();
		Vector3f rayDirection = MousePicker.getCurrentRay();
		
		if (rayDirection.x >= 0) {
			tmin = (bounds[0].x - rayOrigin.x) / rayDirection.x;
			tmax = (bounds[1].x - rayOrigin.x) / rayDirection.x;
		} else {
			tmin = (bounds[1].x - rayOrigin.x) / rayDirection.x;
			tmax = (bounds[0].x - rayOrigin.x) / rayDirection.x;
		}
		
		if (rayDirection.y >= 0) {
			tymin = (bounds[0].y - rayOrigin.y) / rayDirection.y;
			tymax = (bounds[1].y - rayOrigin.y) / rayDirection.y;
		} else {
			tymin = (bounds[1].y - rayOrigin.y) / rayDirection.y;
			tymax = (bounds[0].y - rayOrigin.y) / rayDirection.y;
		}
		
		if ( (tmin > tymax) || (tymin > tmax) )
			return false;
			
		if (tymin > tmin)
			tmin = tymin;
		
		if (tymax < tmax)
			tmax = tymax;
		
		if (rayDirection.z >= 0) {
			tzmin = (bounds[0].z - rayOrigin.z) / rayDirection.z;
			tzmax = (bounds[1].z - rayOrigin.z) / rayDirection.z;
		} else {
			tzmin = (bounds[1].z - rayOrigin.z) / rayDirection.z;
			tzmax = (bounds[0].z - rayOrigin.z) / rayDirection.z;
		}
			
		if ( (tmin > tzmax) || (tzmin > tmax) )
			return false;
			
		if (tzmin > tmin)
			tmin = tzmin;
			
		if (tzmax < tmax)
			tmax = tzmax;
			
		return ( (tmin < 1000) && (tmax > 1) );
	}

}
