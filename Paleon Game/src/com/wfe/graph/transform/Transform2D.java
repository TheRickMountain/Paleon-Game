package com.wfe.graph.transform;

import com.wfe.math.Matrix4f;
import com.wfe.scenegraph.Entity;
import com.wfe.utils.MathUtils;

/**
 * Created by Rick on 12.10.2016.
 */
public class Transform2D extends Transform {

    public Transform2D() {
        super();
    }

    public void update() {
        MathUtils.getModelMatrix(modelMatrix, position.x, position.y, rotation.z, scale.x, scale.y);

        Entity pp = parent.getParent();
        if (pp != null) {
        	Matrix4f.mul(pp.getTransform().getModelMatrix(), modelMatrix, modelMatrix);
        }
    }

}
