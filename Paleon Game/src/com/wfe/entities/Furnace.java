package com.wfe.entities;

import com.wfe.behaviours.BoundingBoxBh;
import com.wfe.behaviours.FurnaceBh;
import com.wfe.components.Collider;
import com.wfe.components.Material;
import com.wfe.components.Model;
import com.wfe.core.ResourceManager;
import com.wfe.graph.transform.Transform3D;
import com.wfe.math.Vector3f;
import com.wfe.scenegraph.Entity;
import com.wfe.scenegraph.World;

public class Furnace extends Entity {

	public Furnace(World world, Vector3f pos) {
		super(world, "furnace");
		
		addComponent(new Model(ResourceManager.getMesh("furnace")));
		addComponent(new Material(ResourceManager.getTexture("furnace")));
		addComponent(new Collider(ResourceManager.getColliderMesh("box"), 
				new Vector3f(pos), new Vector3f(0, 0, 0), new Vector3f(2, 2, 2)));
		addBehaviour(new BoundingBoxBh(new Vector3f(-2, 0, -2), new Vector3f(2, 3, 2)));
		addBehaviour(new FurnaceBh());
		
		this.position.set(pos);
		
		setTransform(new Transform3D());
		
		scale.set(2);
	}

}
