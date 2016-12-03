package com.wfe.entities;

import com.wfe.components.Material;
import com.wfe.components.Model;
import com.wfe.core.ResourceManager;
import com.wfe.graph.transform.Transform3D;
import com.wfe.scenegraph.Entity;
import com.wfe.scenegraph.World;

public class Hummer extends Entity {

	public Hummer(World world) {
		super(world, "hummer");
		
		addComponent(new Model(ResourceManager.getMesh("hummer")));
		addComponent(new Material(ResourceManager.getTexture("axe")));
		
		localPosition.x -= 1.6f;
		localPosition.y += 0.75f;
		localScale.set(1.5f);
		
		setTransform(new Transform3D());
	}

}
