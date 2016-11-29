package com.wfe.entities;

import com.wfe.behaviours.BoundingBoxBh;
import com.wfe.behaviours.CollectableBh;
import com.wfe.components.Material;
import com.wfe.components.Model;
import com.wfe.core.ResourceManager;
import com.wfe.graph.transform.Transform3D;
import com.wfe.math.Vector3f;
import com.wfe.scenegraph.Entity;
import com.wfe.scenegraph.World;

public class Flint extends Entity {

	public Flint(World world) {
		super(world, "Flint");
		
		addComponent(new Model(ResourceManager.getMesh("flint")));
		addComponent(new Material(ResourceManager.getTexture("flint")));
		
		setTransform(new Transform3D());
		
		addBehaviour(new BoundingBoxBh(new Vector3f(-1, 0, -1), new Vector3f(1, 1, 1)));
		addBehaviour(new CollectableBh());
		
		scale.scale(0.4f);
	}

}
