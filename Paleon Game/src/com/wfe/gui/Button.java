package com.wfe.gui;

import com.wfe.behaviours.ButtonBh;
import com.wfe.components.Image;
import com.wfe.graph.Texture;
import com.wfe.graph.transform.Transform2D;
import com.wfe.scenegraph.Entity;
import com.wfe.scenegraph.World;

public class Button extends Entity {

	
	public Button(World world, String name, Texture texture) {
		super(world, name);
		
		addComponent(new Image(texture));
		
		setTransform(new Transform2D());
		
		addBehaviour(new ButtonBh());
	}
	
}
