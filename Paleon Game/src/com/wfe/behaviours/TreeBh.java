package com.wfe.behaviours;

import com.wfe.core.input.Mouse;
import com.wfe.scenegraph.Entity;
import com.wfe.utils.MathUtils;

public class TreeBh extends Behaviour {

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
		if(Mouse.isButtonDown(1)) {
			if(MathUtils.getDistanceBetweenPoints(player.position.x, player.position.z, 
					parent.position.x, parent.position.z) <= 10) {
				if(bb.intersect()) {
					playerBh.addMiningEntity(parent, 5);
				}
			}
		}
	}

	@Override
	public void onGUI() {
		
	}

}
