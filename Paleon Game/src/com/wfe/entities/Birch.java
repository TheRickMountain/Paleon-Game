package com.wfe.entities;

import com.wfe.components.Collider;
import com.wfe.components.Material;
import com.wfe.components.Model;
import com.wfe.core.ResourceManager;
import com.wfe.graph.transform.Transform3D;
import com.wfe.math.Vector3f;
import com.wfe.scenegraph.Entity;
import com.wfe.scenegraph.World;

public class Birch extends Entity {

	public Birch(World world, Vector3f position) {
		super(world, "Birch");
		
		addComponent(new Model(ResourceManager.getMesh("birch_trunk")));
		addComponent(new Material(ResourceManager.getTexture("birch_trunk")));
		addComponent(new Collider(ResourceManager.getColliderMesh("box"), 
				new Vector3f(position), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1)));
		setTransform(new Transform3D());
		scale.set(2.5f);
		
		Entity leaves = new Entity(world, "");
		leaves.addComponent(new Model(ResourceManager.getMesh("birch_leaves")));
		Material leavesMat = new Material(ResourceManager.getTexture("birch_leaves"));
		leavesMat.color.set(0.8f, 1.0f, 0.8f);
		leavesMat.transparency = true;
		leaves.addComponent(leavesMat);
		leaves.setTransform(new Transform3D());
		
		addChild(leaves);
		
		this.position.set(position);
	}

}
