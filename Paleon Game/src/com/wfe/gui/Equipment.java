package com.wfe.gui;

import java.util.ArrayList;
import java.util.List;

import com.wfe.core.Display;
import com.wfe.core.ResourceManager;
import com.wfe.input.Mouse;
import com.wfe.scenegraph.World;
import com.wfe.scenes.Game;

public class Equipment {

	private List<Slot> slots;
	
	private Slot capSlot, tunicSlot, pantsSlot, bootsSlot, 
		backpackSlot, weaponSlot, amulet1Slot, amulet2Slot;
	
	public boolean opened = false;
	
	public Equipment(World world) {
		slots = new ArrayList<Slot>(8);
		
		
		capSlot = new Slot(ResourceManager.getTexture("ui_slot"), 
				Display.getWidth() - 170, Display.getHeight() / 2 - 120, 50, 50);
		slots.add(capSlot);
		
		tunicSlot = new Slot(ResourceManager.getTexture("ui_slot"), 
				Display.getWidth() - 170, Display.getHeight() / 2 - 60, 50, 50);
		slots.add(tunicSlot);
		
		pantsSlot = new Slot(ResourceManager.getTexture("ui_slot"), 
				Display.getWidth() - 170, Display.getHeight() / 2, 50, 50);
		slots.add(pantsSlot);
		
		bootsSlot = new Slot(ResourceManager.getTexture("ui_slot"), 
				Display.getWidth() - 170, Display.getHeight() / 2 + 60, 50, 50);
		slots.add(bootsSlot);
		
		backpackSlot = new Slot(ResourceManager.getTexture("ui_slot"), 
				Display.getWidth() - 110, Display.getHeight() / 2 - 85, 50, 50);
		slots.add(backpackSlot);
		
		weaponSlot = new Slot(ResourceManager.getTexture("ui_slot"), 
				Display.getWidth() - 230, Display.getHeight() / 2 - 85, 50, 50);
		slots.add(weaponSlot);
		
		amulet1Slot = new Slot(ResourceManager.getTexture("ui_slot"), 
				Display.getWidth() - 110, Display.getHeight() / 2 - 25, 50, 50);
		slots.add(amulet1Slot);
		
		amulet2Slot = new Slot(ResourceManager.getTexture("ui_slot"), 
				Display.getWidth() - 230, Display.getHeight() / 2 - 25, 50, 50);
		slots.add(amulet2Slot);
	}

	public void update(float dt) {
		if(opened) {
			if(Mouse.isButtonDown(0)) {
				for(Slot slot : slots) {
					if(slot.overMouse()) {
						if(Game.gui.inventory.draggedItem != null) {
							addItem(Game.gui.inventory.draggedItem);
							Game.gui.inventory.draggedItem = null;
						}
					}
				}
			}
		}
	}
	
	public void addItem(Item item) {
		if(item.itemType.equals(Item.ItemType.CAP)) {
			capSlot.addItem(item);
		} else if(item.itemType.equals(Item.ItemType.TUNIC)) {
			tunicSlot.addItem(item);
		} else if(item.itemType.equals(Item.ItemType.PANTS)) {
			pantsSlot.addItem(item);
		} else if(item.itemType.equals(Item.ItemType.BOOTS)) {
			bootsSlot.addItem(item);
		}
	}
	
	public void render() {
		if(opened) {
			for(Slot slot : slots)
				slot.render();
		}
	}
	
}
