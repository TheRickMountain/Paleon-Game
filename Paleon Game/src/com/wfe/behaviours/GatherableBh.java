package com.wfe.behaviours;

import com.wfe.core.input.Keyboard;
import com.wfe.core.input.Keys;
import com.wfe.gui.ItemDatabase;
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
		if(Keyboard.isKeyDown(Keys.KEY_F)) {
			if(MathUtils.getDistanceBetweenPoints(player.position.x, player.position.z, 
					parent.position.x, parent.position.z) <= 10) {
				if(GameState.gui.inventory.addItem(ItemDatabase.getItemByName(parent.name).ID, 1)) {
					parent.remove();
				}
			}
		}
	}

	@Override
	public void onGUI() {
		
	}

}
