package com.wfe.gui;

import com.wfe.behaviours.ButtonBh;
import com.wfe.core.Display;
import com.wfe.core.ResourceManager;
import com.wfe.graph.render.GUIRenderer;
import com.wfe.input.Mouse;
import com.wfe.scenegraph.World;
import com.wfe.utils.Color;

public class GUI {
	
	private ButtonBh equipmentButtonBh;
	
	public Item draggedItem;
	public int draggedItemCount;
	
	public Equipment equipment;
	public Inventory inventory;
	
	
	public GUI(World world) {
		/*** Health and Starvation bars ***/
		Bar healthBar = new Bar(world, "Health Bar", ResourceManager.getTexture("ui_health"), new Color(1.0f, 0.2f, 0.1f));
        healthBar.position.set(20, 10);
        
        Bar hungerBar = new Bar(world, "Hunger Bar", ResourceManager.getTexture("ui_hunger"), new Color(1.0f, 0.5f, 0.1f));
        hungerBar.position.set(20, 40);
        /*** *** ***/
		
		Button equipmentButton = new Button(world, "Equipment Button", ResourceManager.getTexture("ui_character"));
		equipmentButton.scale.set(50, 50);
		equipmentButton.position.x = Display.getWidth() - 50;
		equipmentButton.position.y = Display.getHeight() / 2;
		equipmentButtonBh = equipmentButton.getBehaviour(ButtonBh.class);
		
		equipment = new Equipment(world);
		inventory = new Inventory(world);
	}
	
	public void update(float dt) {
		equipment.update(dt);
		inventory.update(dt);
		
		if(equipmentButtonBh.isPressedDown(0)) {
			equipment.opened = !equipment.opened;
		}
	}
	
	public void render() {
		equipment.render();
		inventory.render();
		
		if(draggedItem != null) {
			GUIRenderer.render(Mouse.getX() - 25, Mouse.getY() - 25, 50, 50, draggedItem.itemIcon);
			inventory.countText.setText("x" + draggedItemCount);
			GUIRenderer.render(Mouse.getX() - 25, Mouse.getY() - 25, inventory.countText);
		}
	}
	
}
