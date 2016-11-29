package com.wfe.behaviours;

import com.wfe.input.Mouse;
import com.wfe.inventorySystem.InventoryBh;
import com.wfe.inventorySystem.ItemDatabase;
import com.wfe.scenegraph.Entity;
import com.wfe.utils.MathUtils;

public class CollectableBh extends Behaviour {

	BoundingBoxBh bb;
	InventoryBh inventory;
	Entity player;
	
	@Override
	public void start() {
		bb = parent.getBehaviour(BoundingBoxBh.class);
		inventory = parent.getWorld().getEntityByName("Inventory").getBehaviour(InventoryBh.class);
		player = parent.getWorld().getEntityByName("Player");
	}

	@Override
	public void update(float deltaTime) {
		if(Mouse.isButtonDown(0)) {
			if(MathUtils.getDistanceBetweenPoints(player.position.x, player.position.z, 
					parent.position.x, parent.position.z) <= 5) {
				if(bb.intersect()) {
					if(inventory.addItem(ItemDatabase.FLINT)) {
						parent.remove();
					} else {
						System.out.println("Inventory is full!");
					}
				}
			}
		}
	}

	@Override
	public void onGUI() {
		
	}

}
