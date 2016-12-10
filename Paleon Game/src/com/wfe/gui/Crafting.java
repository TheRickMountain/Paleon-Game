package com.wfe.gui;

import java.util.ArrayList;
import java.util.List;

import com.wfe.core.Paleon;
import com.wfe.core.ResourceManager;
import com.wfe.core.input.Mouse;
import com.wfe.graph.Texture;
import com.wfe.graph.render.GUIRenderer;
import com.wfe.scenegraph.World;
import com.wfe.utils.Rect;

public class Crafting {
	
	private Inventory inventory;
	
	private Rect rect;
	private Texture texture;
	
	private int slotsX = 4,  slotsY = 5;
	
	private List<Slot> slots = new ArrayList<Slot>();
	
	public boolean show = false;
	
	public Crafting(World world, Inventory inventory) {
		this.inventory = inventory;
		
		rect = new Rect(0, 0, 0, 0);
		texture = ResourceManager.getTexture("ui_slot");
		
		for(int x = 0; x < slotsX; x++) {
			for(int y = 0; y < slotsY; y++) {
				slots.add(new Slot(texture, 0, 0, 50, 50));
			}
		}
		
		updatePosition();
		
		for(Item item : ItemDatabase.items) {
			if(item.hasCrafting) {
				addRecipe(item.name);
			}
		}
	}
	
	public void update() {
		if(show) {
			if(Mouse.isButtonDown(0)) {
				for(Slot slot : slots) {
					if(slot.overMouse()) {
						Item item = slot.getItem();
						if(!slot.ghostItem) {
							CraftingElement[] ces = item.craftingElements;
							for(int i = 0; i < ces.length; i++) {
								CraftingElement ce = ces[i];
								inventory.removeItem(ce.getId(), ce.getAmount());
							}
							
							inventory.addItem(item.ID, 1);
						}
					}
				}
				
				updateRecipes();
			}
		}
		
		if(Paleon.display.wasResized()) {
			updatePosition();
		}
	}
	
	public void updateRecipes() {
		for(Slot slot : slots) {
			Item item = slot.getItem();
			boolean hasElements = true;
			if(item != null) {
				CraftingElement[] ces = item.craftingElements;
				for(int i = 0; i < ces.length; i++) {
					if(inventory.containsItemAmount(ces[i].getId()) < ces[i].getAmount()) {
						hasElements = false;
					}
				}
				
				slot.ghostItem = !hasElements;
			}
		}
	}
	
	public void render() {
		if(show) {
			GUIRenderer.render(rect, texture);
		
			for(Slot slot : slots) {
				slot.render();
			}
		}
	}
	
	private void addRecipe(String name) {
		for(Slot slot : slots) {
			if(slot.isEmpty()) {
				slot.addItem(ItemDatabase.getItemByName(name));
				slot.setItemsCount(0);
				return;
			}
		}
	}
	
	private void updatePosition() {
		rect.x = Paleon.display.getWidth() / 2 - 250;
		rect.y = Paleon.display.getHeight() / 2 - 145;
		rect.width = 500;
		rect.height = 290;
		
		int count = 0;
		for(int y = 0; y < slotsY; y++) {
			for(int x = 0; x < slotsX; x++) {
				Slot slot = slots.get(count);
				slot.setPosition(x * 55 + rect.x + 10, y * 55 + rect.y + 10);
				count++;
			}
		}
	}

}
