package com.wfe.behaviours;

import com.wfe.core.input.Mouse;
import com.wfe.scenegraph.Entity;
import com.wfe.scenes.GameState;
import com.wfe.utils.MathUtils;

public class GatherableBh extends Behaviour {

	BoundingBoxBh bb;
	Entity player;
	
	@Override
	public void start() {
		bb = parent.getBehaviour(BoundingBoxBh.class);
		player = parent.getWorld().getEntityByName("Player");
	}

	@Override
	public void update(float deltaTime) {
		if(Mouse.isButtonDown(1)) {
			if(MathUtils.getDistanceBetweenPoints(player.position.x, player.position.z, 
					parent.position.x, parent.position.z) <= 10) {
				if(bb.intersect()) {
					if(GameState.gui.inventory.addItem(parent.guiID)) {
						parent.remove();
					}
				}
			}
		}
	}

	@Override
	public void onGUI() {
		
	}

}
