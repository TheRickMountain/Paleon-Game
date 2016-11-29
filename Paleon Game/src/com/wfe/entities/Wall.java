package com.wfe.entities;

import com.wfe.components.Material;
import com.wfe.components.Model;
import com.wfe.core.ResourceManager;
import com.wfe.graph.transform.Transform3D;
import com.wfe.scenegraph.Entity;
import com.wfe.scenegraph.World;

public class Wall extends Entity {

	public Wall(World world) {
		super(world, "Wall");
		addComponent(new Model(ResourceManager.getMesh("wall")));
		addComponent(new Material(ResourceManager.getTexture("clay")));
		setTransform(new Transform3D());
		
		scale.set(1.5f);
	}

	
	
}
