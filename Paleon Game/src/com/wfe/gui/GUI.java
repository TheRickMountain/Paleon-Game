package com.wfe.gui;

import com.wfe.behaviours.ButtonBh;
import com.wfe.core.Paleon;
import com.wfe.core.ResourceManager;
import com.wfe.core.input.Mouse;
import com.wfe.graph.render.GUIRenderer;
import com.wfe.scenegraph.World;

public class GUI {
	
	private Button equipmentButton;
	private ButtonBh equipmentButtonBh;
	
	public Item draggedItem;
	public int draggedItemCount;
	
	public HUD hud;
	public Equipment equipment;
	public Inventory inventory;
	
	
	public GUI(World world) {
		equipmentButton = new Button(world, "Equipment Button", ResourceManager.getTexture("ui_character"));
		equipmentButton.scale.set(50, 50);
		equipmentButton.position.x = Paleon.display.getWidth() - 50;
		equipmentButton.position.y = Paleon.display.getHeight() / 2;
		equipmentButtonBh = equipmentButton.getBehaviour(ButtonBh.class);
		
		ItemDatabase.init();
		hud = new HUD(world);
		equipment = new Equipment(world);
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
		
		if(equipmentButtonBh.isPressedDown(0)) {
			equipment.opened = !equipment.opened;
		}
		
		if(Paleon.display.wasResized()) {
			equipmentButton.position.x =  Paleon.display.getWidth() - 50;
			equipmentButton.position.y =  Paleon.display.getHeight() / 2;
		}
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
