package com.wfe.graph.transform;

import com.wfe.math.Matrix4f;
import com.wfe.math.Vector3f;
import com.wfe.scenegraph.Entity;

/**
 * Created by Rick on 13.10.2016.
 */
public abstract class Transform {

    public Entity parent;
    
    public boolean active = true;

    protected final Matrix4f modelMatrix;

    protected Vector3f position;
    protected Vector3f rotation;
    protected Vector3f scale;
    
    protected Vector3f localPosition;
    protected Vector3f localRotation;
    protected Vector3f localScale;

    public void setParent(Entity parent) {
        this.parent = parent;

        this.position = parent.position;
        this.rotation = parent.rotation;
        this.scale = parent.scale;
        
        this.localPosition = parent.localPosition;
        this.localRotation = parent.localRotation;
        this.localScale = parent.localScale;
    }

    public Transform() {
        modelMatrix = new Matrix4f();
    }

    public abstract void update();

    public final Matrix4f getModelMatrix() {
        return modelMatrix;
    }


}
