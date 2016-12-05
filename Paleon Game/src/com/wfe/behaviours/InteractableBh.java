package com.wfe.behaviours;

import com.wfe.gui.ItemDatabase;
import com.wfe.input.Mouse;
import com.wfe.scenegraph.Entity;
import com.wfe.scenes.GameState;
import com.wfe.utils.MathUtils;

public class InteractableBh extends Behaviour {

	BoundingBoxBh bb;
	Entity player;
	PlayerBh playerBh;
	
	@Override
	public void start() {
		bb = parent.getBehaviour(BoundingBoxBh.class);
		player = parent.getWorld().getEntityByName("Player");
		playerBh = player.getBehaviour(PlayerBh.class);
	}

	@Override
	public void update(float deltaTime) {
		if(Mouse.isButtonDown(0)) {
			if(MathUtils.getDistanceBetweenPoints(player.position.x, player.position.z, 
					parent.position.x, parent.position.z) <= 5) {
				if(bb.intersect()) {
					if(parent.name.equals("flint")) {
						if(GameState.gui.inventory.addItem(ItemDatabase.FLINT)) {
							parent.remove();
						}
					} else if(parent.name.equals("shroom")) {
						if(GameState.gui.inventory.addItem(ItemDatabase.SHROOM)) {
							parent.remove();
						}
					} else if(parent.name.equals("birch")) {
						playerBh.chop();
					}
				}
			}
		}
	}

	@Override
	public void onGUI() {
		
	}

}
