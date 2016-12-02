package com.wfe.behaviours;

import com.wfe.gui.ItemDatabase;
import com.wfe.input.Mouse;
import com.wfe.scenegraph.Entity;
import com.wfe.scenes.Game;
import com.wfe.utils.MathUtils;

public class CollectableBh extends Behaviour {

	BoundingBoxBh bb;
	Entity player;
	
	@Override
	public void start() {
		bb = parent.getBehaviour(BoundingBoxBh.class);
		player = parent.getWorld().getEntityByName("Player");
	}

	@Override
	public void update(float deltaTime) {
		if(Mouse.isButtonDown(0)) {
			if(MathUtils.getDistanceBetweenPoints(player.position.x, player.position.z, 
					parent.position.x, parent.position.z) <= 5) {
				if(bb.intersect()) {
					if(parent.name.equals("flint")) {
						if(Game.gui.inventory.addItem(ItemDatabase.FLINT)) {
							parent.remove();
						}
					} else if(parent.name.equals("shroom")) {
						if(Game.gui.inventory.addItem(ItemDatabase.SHROOM)) {
							parent.remove();
						}
					}
				}
			}
		}
	}

	@Override
	public void onGUI() {
		
	}

}
