package com.wfe.entities;

import com.wfe.components.Material;
import com.wfe.components.Model;
import com.wfe.core.ResourceManager;
import com.wfe.graph.transform.Transform3D;
import com.wfe.scenegraph.Entity;
import com.wfe.scenegraph.World;

public class Palm extends Entity {

	public Palm(World world) {
		super(world, "Palm");
		
		addComponent(new Model(ResourceManager.getMesh("palm")));
		Material palmMat = new Material(ResourceManager.getTexture("palm"));
		palmMat.transparency = true;
		addComponent(palmMat);
		setTransform(new Transform3D());
		scale.set(3);
		
	}
	
	

}
