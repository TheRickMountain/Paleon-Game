package com.wfe.gui;

import com.wfe.core.input.Keyboard;
import com.wfe.core.input.Keys;
import com.wfe.core.input.Mouse;
import com.wfe.entities.Player;
import com.wfe.graph.render.GUIRenderer;
import com.wfe.scenegraph.World;

public class GUI {
	
	public Item draggedItem;
	public int draggedItemCount;
	
	public HUD hud;
	public Equipment equipment;
	public Inventory inventory;
	public Crafting crafting;
	
	
	public GUI(World world, Player player) {
		ItemDatabase.init();
		hud = new HUD(world);
		equipment = new Equipment(world, player);
		inventory = new Inventory(world);
		crafting = new Crafting(world, inventory);
		
		/*** *** ***/
		inventory.addItem("apple");
		inventory.addItem("pineapple");
		inventory.addItem("banana");
		inventory.addItem("cap");
		inventory.addItem("log");
		inventory.addItem("flint");
		inventory.addItem("log");
		inventory.addItem("flint");
		/*** *** ***/
	}
	
	public void update(float dt) {
		hud.update(dt);
		equipment.update(dt);
		inventory.update(dt);
		crafting.update();
		
		if(Keyboard.isKeyDown(Keys.KEY_TAB)) {
			crafting.show = !crafting.show;
			if(crafting.show) {
				crafting.updateRecipes();
			}
		}
	}
	
	public void render() {
		equipment.render();
		inventory.render();
		crafting.render();
		
		if(draggedItem != null) {
			GUIRenderer.render(Mouse.getX() - 25, Mouse.getY() - 25, 50, 50, draggedItem.icon);
			if(draggedItemCount >= 2) {
				inventory.countText.setText("x" + draggedItemCount);
				GUIRenderer.render(Mouse.getX() - 25, Mouse.getY() - 25, inventory.countText);
			}
		}
	}
	
}
