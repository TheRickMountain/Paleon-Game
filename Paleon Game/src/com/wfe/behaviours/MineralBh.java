package com.wfe.behaviours;

import com.wfe.core.input.Mouse;
import com.wfe.scenegraph.Entity;
import com.wfe.utils.MathUtils;

public class MineralBh extends Behaviour {

	BoundingBoxBh bb;
	Entity player;
	PlayerBh playerBh;
	
	int count;
	
	@Override
	public void start() {
		bb = parent.getBehaviour(BoundingBoxBh.class);
		player = parent.getWorld().getEntityByName("Player");
		playerBh = player.getBehaviour(PlayerBh.class);
		
		count = MathUtils.random(2, 5);
	}

	@Override
	public void update(float deltaTime) {
		if(Mouse.isButtonDown(1)) {
			if(MathUtils.getDistanceBetweenPoints(player.position.x, player.position.z, 
					parent.position.x, parent.position.z) <= 10) {
				if(bb.intersect()) {
					playerBh.addMiningEntity(parent, 10);
				}
			}
		}
	}
	
	public int getCount() {
		return count;
	}
	
	public void decrease() {
		count--;
		if(count == 0) {
			parent.remove();
		}
	}

	@Override
	public void onGUI() {
		
	}

}
