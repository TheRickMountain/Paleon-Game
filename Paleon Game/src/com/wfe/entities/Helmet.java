package com.wfe.entities;

import com.wfe.components.Material;
import com.wfe.components.Model;
import com.wfe.core.ResourceManager;
import com.wfe.graph.transform.Transform3D;
import com.wfe.scenegraph.Entity;
import com.wfe.scenegraph.World;

public class Helmet extends Entity {

	public Helmet(World world) {
		super(world, "helmet");
		
		addComponent(new Model(ResourceManager.getMesh("helmet")));
		addComponent(new Material(ResourceManager.getTexture("helmet")));
		setTransform(new Transform3D());
	}

}
