package com.wfe.gui;

import com.wfe.behaviours.BarBh;
import com.wfe.graph.Texture;
import com.wfe.graph.transform.Transform2D;
import com.wfe.scenegraph.Entity;
import com.wfe.scenegraph.World;
import com.wfe.utils.Color;

public class Bar extends Entity {

	public Bar(World world, String name, Texture icon, Color color, boolean showText, int length) {
		super(world, name);
        setTransform(new Transform2D());
        addBehaviour(new BarBh(icon, color, showText, length));
	}

}
