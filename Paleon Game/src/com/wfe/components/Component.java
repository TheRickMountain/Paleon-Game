package com.wfe.components;

import com.wfe.scenegraph.Entity;

/**
 * Created by Rick on 06.10.2016.
 */

public abstract class Component {

    public boolean active = true;

    public Type type;

    public Entity parent;

    public void setParent(Entity parent) {
        this.parent = parent;
    }

    public enum Type {
        MODEL,
        MATERIAL,
        TEXT,
        IMAGE,
        COLLIDER
    }

}

