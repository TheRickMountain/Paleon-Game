package com.wfe.inventorySystem;

import java.util.ArrayList;
import java.util.List;

import com.wfe.behaviours.Behaviour;
import com.wfe.core.Display;
import com.wfe.core.ResourceManager;
import com.wfe.graph.render.GUIRenderer;
import com.wfe.input.Mouse;

public class InventoryBh extends Behaviour {

	List<Slot> downSlots = new ArrayList<Slot>();
	
	Item draggedItem;
	
	@Override
	public void start() {
		ItemDatabase.init();
		
		for(int i = 0; i < 8; i++) {
			downSlots.add(new Slot(ResourceManager.getTexture("ui_slot"), 
					(Display.getWidth() / 2) - 235 + (i * 60), Display.getHeight() - 50, 50, 50));
			
		}
		
		downSlots.get(0).addItem(ItemDatabase.getItem(ItemDatabase.APPLE));
		downSlots.get(5).addItem(ItemDatabase.getItem(ItemDatabase.FLINT));
		downSlots.get(2).addItem(ItemDatabase.getItem(ItemDatabase.SHROOM));
	}

	@Override
	public void update(float deltaTime) {
		for(Slot slot : downSlots) {
			if(Mouse.isButtonDown(0)) {
				if(slot.overMouse()) {
					if(draggedItem != null) {
						if(slot.getItem() == null) {
							slot.addItem(draggedItem);
							draggedItem = null;
						} else {
							Item temp = slot.getItem();
							slot.removeItem();
							slot.addItem(draggedItem);
							draggedItem = temp;
						}
					} else if(slot.getItem() != null) {
						draggedItem = slot.getItem();
						slot.removeItem();
					}
				}
			}
			
			if(Mouse.isButtonDown(1)) {
				if(slot.overMouse()) {
					if(slot.getItem() != null) {
						if(slot.getItem().itemType.equals(Item.ItemType.CONSUMABLE)) {
							slot.removeItem();
						}
					}
				}
			}
		}
		
		if(Display.wasResized()) {
			for(int i = 0; i < 8; i++) {
				downSlots.get(i).xPos = (Display.getWidth() / 2) - 235 + (i * 60);
				downSlots.get(i).yPos = Display.getHeight() - 50;
			}
		}
	}
	
	public boolean addItem(int id) {
		for(Slot slot : downSlots) {
			if(slot.getItem() == null) {
				slot.addItem(ItemDatabase.getItem(id));
				return true;
			}
		}
		return false;
	}

	@Override
	public void onGUI() {
		for(Slot slot : downSlots)
			slot.render();
		
		if(draggedItem != null)
			GUIRenderer.render(Mouse.getX() - 25, Mouse.getY() - 25, 50, 50, draggedItem.itemIcon);
	}

	
	
}
