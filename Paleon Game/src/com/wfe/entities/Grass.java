package com.wfe.entities;

import com.wfe.components.Material;
import com.wfe.components.Model;
import com.wfe.core.ResourceManager;
import com.wfe.graph.transform.Transform3D;
import com.wfe.scenegraph.Entity;
import com.wfe.scenegraph.World;

public class Grass extends Entity {

	public Grass(World world) {
		super(world, "Grass");
		
        addComponent(new Model(ResourceManager.getMesh("grass")));
        Material grassMaterial = new Material(ResourceManager.getTexture("diffuse"));
        grassMaterial.transparency = true;
        grassMaterial.useFakeLighting = true;
        grassMaterial.setNumberOfRows(2);
        addComponent(grassMaterial);
        setTransform(new Transform3D());
        scale.set(2);
	}

}
