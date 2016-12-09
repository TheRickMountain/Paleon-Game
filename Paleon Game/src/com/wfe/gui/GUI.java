package com.wfe.gui;

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
	
	
	public GUI(World world, Player player) {
		ItemDatabase.init();
		hud = new HUD(world);
		equipment = new Equipment(world, player);
		inventory = new Inventory(world);
		
		/*** *** ***/
		inventory.addItem("apple");
		inventory.addItem("pineapple");
		inventory.addItem("banana");
		inventory.addItem("cap");
		inventory.addItem("axe");
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
	}
	
	public void render() {
		equipment.render();
		inventory.render();
		
		if(draggedItem != null) {
			GUIRenderer.render(Mouse.getX() - 25, Mouse.getY() - 25, 50, 50, draggedItem.icon);
			if(draggedItemCount >= 2) {
				inventory.countText.setText("x" + draggedItemCount);
				GUIRenderer.render(Mouse.getX() - 25, Mouse.getY() - 25, inventory.countText);
			}
		}
	}
	
}
