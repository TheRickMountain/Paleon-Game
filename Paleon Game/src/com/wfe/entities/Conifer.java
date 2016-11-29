package com.wfe.entities;

import com.wfe.components.Material;
import com.wfe.components.Model;
import com.wfe.core.ResourceManager;
import com.wfe.graph.transform.Transform3D;
import com.wfe.scenegraph.Entity;
import com.wfe.scenegraph.World;

public class Conifer extends Entity {

	public Conifer(World world) {
		super(world, "Conifer");
		
		addComponent(new Model(ResourceManager.getMesh("conifer_trunk")));
		addComponent(new Material(ResourceManager.getTexture("conifer_trunk")));
		setTransform(new Transform3D());
		scale.set(2f);
		
		Entity leaves = new Entity(world, "");
		leaves.addComponent(new Model(ResourceManager.getMesh("conifer_leaves")));
		Material leavesMat = new Material(ResourceManager.getTexture("conifer_leaves"));
		leavesMat.transparency = true;
		leaves.addComponent(leavesMat);
		leaves.setTransform(new Transform3D());
		
		addChild(leaves);
	}

}
