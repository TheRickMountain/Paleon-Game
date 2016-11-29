package com.wfe.behaviours;

import com.wfe.scenegraph.Entity;

public abstract class Behaviour {

    public boolean active = true;
	
	public Entity parent;
	
	public void setParent(Entity parent) {
		this.parent = parent;
	}
	
	public abstract void start();
	
	public abstract void update(float deltaTime);
	
	public abstract void onGUI();
	
}