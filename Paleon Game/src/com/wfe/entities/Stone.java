package com.wfe.entities;

import com.wfe.behaviours.BoundingBoxBh;
import com.wfe.behaviours.MineralBh;
import com.wfe.components.Material;
import com.wfe.components.Model;
import com.wfe.core.ResourceManager;
import com.wfe.graph.transform.Transform3D;
import com.wfe.math.Vector3f;
import com.wfe.scenegraph.Entity;
import com.wfe.scenegraph.World;

public class Stone extends Entity {

	public Stone(World world) {
		super(world, "stone");
		
		addComponent(new Model(ResourceManager.getMesh("rock")));
		addComponent(new Material(ResourceManager.getTexture("rock")));
		addBehaviour(new BoundingBoxBh(new Vector3f(-2, 0, -2), new Vector3f(2, 1, 2)));
		setTransform(new Transform3D());
		
		addBehaviour(new MineralBh());
		
		scale.set(2.0f);
		scale.y = 0.5f;
	}

}
