package com.wfe.graph.transform;

import com.wfe.math.Matrix4f;
import com.wfe.math.Vector3f;
import com.wfe.math.Vector4f;
import com.wfe.scenegraph.Entity;
import com.wfe.utils.MathUtils;

/**
 * Created by Rick on 12.10.2016.
 */
public class Transform3D extends Transform {

	private Vector4f temp;
	
    public Transform3D() {
        super();
        temp = new Vector4f();
    }

    public void update() {
        Entity pp = parent.getParent();
        if (pp != null) {
			Matrix4f.transform(pp.getTransform().getModelMatrix(), 
					new Vector4f(localPosition.x, localPosition.y, localPosition.z, 1.0f), temp);
			position.set(temp);
			
			Vector3f.add(localRotation,  pp.rotation, rotation);
			scale.x = localScale.x * pp.scale.x;
			scale.y = localScale.y * pp.scale.y;
			scale.z = localScale.z * pp.scale.z;
        }
        
        MathUtils.getEulerModelMatrix(modelMatrix, position, rotation, scale);
    }

}
