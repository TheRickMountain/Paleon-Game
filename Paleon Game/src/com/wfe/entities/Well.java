package com.wfe.entities;

import com.wfe.components.Material;
import com.wfe.components.Model;
import com.wfe.core.ResourceManager;
import com.wfe.graph.transform.Transform3D;
import com.wfe.scenegraph.Entity;
import com.wfe.scenegraph.World;

public class Well extends Entity {

	public Well(World world) {
		super(world, "Well");
		
        setTransform(new Transform3D());
        addComponent(new Model(ResourceManager.getMesh("well")));
        addComponent(new Material(ResourceManager.getTexture("well")));
        scale.set(2.5f);
	}

}
