package com.wfe.entities;

import com.wfe.components.Material;
import com.wfe.components.Model;
import com.wfe.core.ResourceManager;
import com.wfe.graph.transform.Transform3D;
import com.wfe.scenegraph.Entity;
import com.wfe.scenegraph.World;

public class Shroom extends Entity {

	public Shroom(World world) {
		super(world, "Shroom");
		
		addComponent(new Model(ResourceManager.getMesh("shroom")));
		addComponent(new Material(ResourceManager.getTexture("shroom")));
		
		setTransform(new Transform3D());
		
		scale.scale(0.4f);
	}

}
