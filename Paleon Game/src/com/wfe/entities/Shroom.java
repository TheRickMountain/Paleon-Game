package com.wfe.entities;

import com.wfe.behaviours.BoundingBoxBh;
import com.wfe.behaviours.GatherableBh;
import com.wfe.components.Material;
import com.wfe.components.Model;
import com.wfe.core.ResourceManager;
import com.wfe.graph.transform.Transform3D;
import com.wfe.gui.ItemDatabase;
import com.wfe.math.Vector3f;
import com.wfe.scenegraph.Entity;
import com.wfe.scenegraph.World;

public class Shroom extends Entity {

	public Shroom(World world) {
		super(world, "shroom");
		
		addComponent(new Model(ResourceManager.getMesh("shroom")));
		addComponent(new Material(ResourceManager.getTexture("shroom")));
		
		setTransform(new Transform3D());
		
		addBehaviour(new BoundingBoxBh(new Vector3f(-1, 0, -1), new Vector3f(1, 1, 1)));
		addBehaviour(new GatherableBh());
		
		scale.scale(0.4f);
		guiID = ItemDatabase.SHROOM;
	}

}
