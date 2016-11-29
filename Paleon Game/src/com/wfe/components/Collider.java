package com.wfe.components;

import java.util.ArrayList;
import java.util.List;

import com.wfe.math.Matrix3f;
import com.wfe.math.Matrix4f;
import com.wfe.math.Vector3f;
import com.wfe.physics.ColliderMesh;
import com.wfe.physics.CollisionPacket;
import com.wfe.physics.Triangle;
import com.wfe.utils.MathUtils;

public class Collider extends Component {
	
	private List<Triangle> tris = new ArrayList<Triangle>();

	public Collider(ColliderMesh mesh, Vector3f position, Vector3f rotation, Vector3f scale) {	
		type = Type.COLLIDER;
		
		Triangle triangles[] = null;
		try {
			triangles = mesh.extractTriangles();
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        Vector3f eRadius = new Vector3f(1.0f, 2.0f, 1.0f);
        
        Matrix3f eSpace = new Matrix3f();
        eSpace.setIdentity();
        eSpace.m00 /= eRadius.x;
        eSpace.m11 /= eRadius.y;
        eSpace.m22 /= eRadius.z;
        
        Matrix3f R3 = new Matrix3f();
        Matrix3f.invert(eSpace, R3);

        Matrix4f modelMatrix = new Matrix4f();
        MathUtils.getModelMatrix(modelMatrix, position, rotation, scale);       
        
        for(int i = 0; i < triangles.length; i++) {
        	Triangle transformedTriangle = triangles[i].createInstance(modelMatrix);
        	Triangle triangleInESpace = transformedTriangle.getTransformedCopy(eSpace);
        	tris.add(triangleInESpace);
        }
	}
	
	public void checkCollision(CollisionPacket colPackage) {
		for(Triangle triangle : tris) {
    		MathUtils.checkTriangle(colPackage, 
    				triangle);
    	}
	}
	
}
