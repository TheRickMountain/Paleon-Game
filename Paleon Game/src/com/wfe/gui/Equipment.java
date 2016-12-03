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
	
	private Slot capSlot, tunicSlot, pantsSlot, bootsSlot, 
		backpackSlot, weaponSlot, amulet1Slot, amulet2Slot;
	
	public boolean opened = false;
	
	private PlayerBh player;
	
	private World world;
	
	public Equipment(World world) {
		this.world = world;
		
		player = world.getEntityByName("Player").getBehaviour(PlayerBh.class);
		
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
			capSlot.xPos = Display.getWidth() - 170;
			capSlot.yPos = Display.getHeight() / 2 - 120;
			
			tunicSlot.xPos = Display.getWidth() - 170;
			tunicSlot.yPos = Display.getHeight() / 2 - 60;
			
			pantsSlot.xPos = Display.getWidth() - 170;
			pantsSlot.yPos = Display.getHeight() / 2;
			
			bootsSlot.xPos = Display.getWidth() - 170;
			bootsSlot.yPos = Display.getHeight() / 2 + 60;
			
			backpackSlot.xPos = Display.getWidth() - 110;
			backpackSlot.yPos = Display.getHeight() / 2 - 85;
			
			weaponSlot.xPos = Display.getWidth() - 230;
			weaponSlot.yPos = Display.getHeight() / 2 - 85;
			
			amulet1Slot.xPos = Display.getWidth() - 110;
			amulet1Slot.yPos = Display.getHeight() / 2 - 25;
			
			amulet2Slot.xPos = Display.getWidth() - 230;
			amulet2Slot.yPos = Display.getHeight() / 2 - 25;
		}
		
	}
	
	public void addItem(Item item) {		
		if(item.itemType.equals(Item.ItemType.CAP)) {
			if(capSlot.getSlotItem() != null) {
				Game.gui.inventory.addItem(capSlot.getSlotItem().itemID);
				capSlot.removeItem();
			}
				
			capSlot.addItem(item);
		} else if(item.itemType.equals(Item.ItemType.TUNIC)) {
			if(tunicSlot.getSlotItem() != null) {
				Game.gui.inventory.addItem(tunicSlot.getSlotItem().itemID);
				tunicSlot.removeItem();
			}
			
			tunicSlot.addItem(item);
		} else if(item.itemType.equals(Item.ItemType.PANTS)) {
			if(pantsSlot.getSlotItem() != null) {
				Game.gui.inventory.addItem(pantsSlot.getSlotItem().itemID);
				pantsSlot.removeItem();
			}
			
			pantsSlot.addItem(item);
		} else if(item.itemType.equals(Item.ItemType.BOOTS)) {
			if(bootsSlot.getSlotItem() != null) {
				Game.gui.inventory.addItem(bootsSlot.getSlotItem().itemID);
				bootsSlot.removeItem();
			}
			
			bootsSlot.addItem(item);
		} else if(item.itemType.equals(Item.ItemType.WEAPON)) {
			if(weaponSlot.getSlotItem() != null) {
				Game.gui.inventory.addItem(weaponSlot.getSlotItem().itemID);
				weaponSlot.removeItem();
				player.removeWeapon();
			}
			
			weaponSlot.addItem(item);
			if(item.itemName.equals("axe")) {
				player.addWeapon(new Axe(world));
			} else if(item.itemName.equals("hummer")) {
				player.addWeapon(new Hummer(world));
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
