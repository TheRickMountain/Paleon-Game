package com.wfe.gui;

import java.util.ArrayList;
import java.util.List;

import com.wfe.behaviours.PlayerBh;
import com.wfe.core.Display;
import com.wfe.core.ResourceManager;
import com.wfe.entities.Axe;
import com.wfe.entities.Hummer;
import com.wfe.input.Mouse;
import com.wfe.scenegraph.World;
import com.wfe.scenes.Game;

public class Equipment {

	private List<Slot> slots;
	
	public boolean opened = false;
	
	private PlayerBh player;
	
	private World world;
	
	public Equipment(World world) {
		this.world = world;
		
		player = world.getEntityByName("Player").getBehaviour(PlayerBh.class);
		
		slots = new ArrayList<Slot>(8);
			
		slots.add(new Slot(ResourceManager.getTexture("ui_slot"), 
				Display.getWidth() - 170, Display.getHeight() / 2 - 120, 50, 50, Slot.SlotType.CAP));
		
		slots.add(new Slot(ResourceManager.getTexture("ui_slot"), 
				Display.getWidth() - 170, Display.getHeight() / 2 - 60, 50, 50, Slot.SlotType.TUNIC));
		
		slots.add(new Slot(ResourceManager.getTexture("ui_slot"), 
				Display.getWidth() - 170, Display.getHeight() / 2, 50, 50, Slot.SlotType.PANTS));
		
		slots.add(new Slot(ResourceManager.getTexture("ui_slot"), 
				Display.getWidth() - 170, Display.getHeight() / 2 + 60, 50, 50, Slot.SlotType.BOOTS));
		
		slots.add(new Slot(ResourceManager.getTexture("ui_slot"), 
				Display.getWidth() - 110, Display.getHeight() / 2 - 85, 50, 50, Slot.SlotType.BACKPACK));
		
		slots.add(new Slot(ResourceManager.getTexture("ui_slot"), 
				Display.getWidth() - 230, Display.getHeight() / 2 - 85, 50, 50, Slot.SlotType.WEAPON));
		
		slots.add(new Slot(ResourceManager.getTexture("ui_slot"), 
				Display.getWidth() - 110, Display.getHeight() / 2 - 25, 50, 50, Slot.SlotType.AMULET));
		
		slots.add(new Slot(ResourceManager.getTexture("ui_slot"), 
				Display.getWidth() - 230, Display.getHeight() / 2 - 25, 50, 50, Slot.SlotType.AMULET));
	}

	public void update(float dt) {
		if(opened) {
			if(Mouse.isButtonDown(0)) {
				for(Slot slot : slots) {
					if(slot.overMouse()) {
						
						if(Game.gui.draggedItem != null) {
							
							if(slot.getSlotItem() != null) {
								Item temp = slot.getSlotItem();
								Game.gui.draggedItemCount = slot.getItemsCount();
								slot.removeItem();
								slot.addItem(Game.gui.draggedItem);
								Game.gui.draggedItem = temp;
								
								player.removeWeapon();
								
								if(slot.getSlotItem().itemName.equals("axe")) {
									player.addWeapon(new Axe(world));
								} else if(slot.getSlotItem().itemName.equals("hummer")) {
									player.addWeapon(new Hummer(world));
								}
							} else {
								addItem(Game.gui.draggedItem);
								Game.gui.draggedItem = null;
							}
						} else {
							if(slot.getSlotItem() != null) {
								if(slot.getSlotItem().itemType.equals(Item.ItemType.WEAPON)) {
									player.removeWeapon();
								}
								
								Game.gui.draggedItem = slot.getSlotItem();
								Game.gui.draggedItemCount = 1;
								slot.removeItem();
							}
						}
					}
				}
			}	
		}
		
		if(Display.wasResized()) {
			slots.get(0).setPosition(Display.getWidth() - 170, Display.getHeight() / 2 - 120);
			slots.get(1).setPosition(Display.getWidth() - 170, Display.getHeight() / 2 - 60);
			slots.get(2).setPosition(Display.getWidth() - 170, Display.getHeight() / 2);
			slots.get(3).setPosition(Display.getWidth() - 170, Display.getHeight() / 2 + 60);
			slots.get(4).setPosition(Display.getWidth() - 110, Display.getHeight() / 2 - 85);
			slots.get(5).setPosition(Display.getWidth() - 230, Display.getHeight() / 2 - 85);
			slots.get(6).setPosition(Display.getWidth() - 110, Display.getHeight() / 2 - 25);
			slots.get(7).setPosition(Display.getWidth() - 230, Display.getHeight() / 2 - 25);
		}
		
	}
	
	public void addItem(Item item) {
		boolean added = false;
		for(Slot slot : slots) {
			if(slot.slotType.ordinal() == item.itemType.ordinal()) {
				if(slot.getSlotItem() == null) {
					slot.addItem(item);
					added = true;
				} else {
					Game.gui.inventory.addItem(slot.getSlotItem().itemID);
					slot.removeItem();
					slot.addItem(item);
					added = true;
				}
				
				if(added) {
					if(item.itemName.equals("axe")) {
						player.addWeapon(new Axe(world));
					} else if(item.itemName.equals("hummer")) {
						player.addWeapon(new Hummer(world));
					}
				}
				
			}
		}
	}
	
	public void render() {
		if(opened) {
			for(Slot slot : slots)
				slot.render();
		}
	}
	
}
