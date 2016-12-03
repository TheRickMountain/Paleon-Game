package com.wfe.gui;

import com.wfe.behaviours.ButtonBh;
import com.wfe.core.Display;
import com.wfe.core.ResourceManager;
import com.wfe.graph.render.GUIRenderer;
import com.wfe.input.Mouse;
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
		equipmentButton.position.x = Display.getWidth() - 50;
		equipmentButton.position.y = Display.getHeight() / 2;
		equipmentButtonBh = equipmentButton.getBehaviour(ButtonBh.class);
		
		ItemDatabase.init();
		hud = new HUD(world);
		equipment = new Equipment(world);
		inventory = new Inventory(world);
		
		/*** *** ***/
		inventory.addItem(ItemDatabase.APPLE);
		inventory.addItem(ItemDatabase.APPLE);
		inventory.addItem(ItemDatabase.CAP);
		inventory.addItem(ItemDatabase.PANTS);
		inventory.addItem(ItemDatabase.TUNIC);
		inventory.addItem(ItemDatabase.BOOTS);
		
		for(int i = 0; i < 21; i++)
			inventory.addItem(ItemDatabase.LOG_WALL);
	
		inventory.addItem(ItemDatabase.HUMMER);
		inventory.addItem(ItemDatabase.AXE);
		/*** *** ***/
	}
	
	public void update(float dt) {
		hud.update(dt);
		equipment.update(dt);
		inventory.update(dt);
		
		if(equipmentButtonBh.isPressedDown(0)) {
			equipment.opened = !equipment.opened;
		}
		
		if(Display.wasResized()) {
			equipmentButton.position.x =  Display.getWidth() - 50;
			equipmentButton.position.y =  Display.getHeight() / 2;
		}
	}
	
	public void render() {
		equipment.render();
		inventory.render();
		
		if(draggedItem != null) {
			GUIRenderer.render(Mouse.getX() - 25, Mouse.getY() - 25, 50, 50, draggedItem.itemIcon);
			if(draggedItemCount >= 2) {
				inventory.countText.setText("x" + draggedItemCount);
				GUIRenderer.render(Mouse.getX() - 25, Mouse.getY() - 25, inventory.countText);
			}
		}
	}
	
}
