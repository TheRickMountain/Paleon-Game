package com.wfe.gui;

import java.util.ArrayList;
import java.util.List;

import com.wfe.components.Collider;
import com.wfe.components.Text;
import com.wfe.core.Display;
import com.wfe.core.ResourceManager;
import com.wfe.entities.Wall;
import com.wfe.graph.render.GUIRenderer;
import com.wfe.input.Key;
import com.wfe.input.Keyboard;
import com.wfe.input.Mouse;
import com.wfe.math.Vector2f;
import com.wfe.math.Vector3f;
import com.wfe.scenegraph.Entity;
import com.wfe.scenegraph.World;
import com.wfe.scenes.Game;
import com.wfe.utils.Color;
import com.wfe.utils.MathUtils;
import com.wfe.utils.MousePicker;
import com.wfe.utils.Rect;

public class Inventory {

	private List<Slot> slots = new ArrayList<Slot>();
	
	private Entity building;
	private float currentEntityRotation;
	
	private Entity player;
	
	private Rect rect;
	
	public Text countText;
	
	private World world;
	
	public Inventory(World world) {	
		this.world = world;
		
		countText = new Text("test", GUIRenderer.primitiveFont, 1.1f, Color.WHITE);			
		
		player = world.getEntityByName("Player");
		
		for(int i = 0; i < 8; i++) {
			slots.add(new Slot(ResourceManager.getTexture("ui_slot"), 
					(Display.getWidth() / 2) - 235 + (i * 60), Display.getHeight() - 50, 50, 50));
			
		}
		
		Slot slot1 = slots.get(0);
		Slot slot2 = slots.get(slots.size() - 1);
		rect = new Rect(slot1.xPos, slot1.yPos,
				slot2.xPos + slot2.xScale, slot2.yPos + slot2.yScale);
	}

	
	public void update(float deltaTime) {
		building();
		
		if(MathUtils.point2DBoxIntersection(Mouse.getX(), Mouse.getY(), rect)) {
			Game.state = Game.State.GUI;
		} else {
			Game.state = Game.State.GAME;
		}
		
		if(Mouse.isButtonDown(0)) {
			for(Slot slot : slots) {
				if(slot.overMouse()) {
					if(Game.gui.draggedItem != null) {
						if(slot.getSlotItem() == null) {
							slot.addItem(Game.gui.draggedItem);
							slot.setItemsCount(Game.gui.draggedItemCount);
							Game.gui.draggedItem = null;
						} else {
							if(slot.getSlotItem().itemID == Game.gui.draggedItem.itemID) {
								slot.addItem(Game.gui.draggedItem);
								Game.gui.draggedItem = null;
							} else {
								Item temp = slot.getSlotItem();
								Game.gui.draggedItemCount = slot.getItemsCount();
								slot.removeItem();
								slot.addItem(Game.gui.draggedItem);
								Game.gui.draggedItem = temp;
							}
						}
					} else if(slot.getSlotItem() != null) {
						Game.gui.draggedItemCount = slot.getItemsCount();
						Game.gui.draggedItem = slot.getSlotItem();
						slot.removeItem();
					}
				}
			}
		}
			
		if(Mouse.isButtonDown(1)) {
			for(Slot slot : slots) {
				if(slot.overMouse()) {
					if(slot.getSlotItem() != null) {
						if(slot.getSlotItem().itemType.equals(Item.ItemType.CONSUMABLE)) {
							if(slot.getItemsCount() > 1) {
								Game.gui.hud.hunger.increase(slot.getSlotItem().itemStarvation);
								slot.removeItem(1);
							} else {
								Game.gui.hud.hunger.increase(slot.getSlotItem().itemStarvation);
								slot.removeItem();
							}
						} else if(slot.getSlotItem().itemType.equals(Item.ItemType.CAP)) {
							Game.gui.equipment.addItem(slot.getSlotItem());
							slot.removeItem();
						} else if(slot.getSlotItem().itemType.equals(Item.ItemType.TUNIC)) {
							Game.gui.equipment.addItem(slot.getSlotItem());
							slot.removeItem();
						} else if(slot.getSlotItem().itemType.equals(Item.ItemType.PANTS)) {
							Game.gui.equipment.addItem(slot.getSlotItem());
							slot.removeItem();
						} else if(slot.getSlotItem().itemType.equals(Item.ItemType.BOOTS)) {
							Game.gui.equipment.addItem(slot.getSlotItem());
							slot.removeItem();
						} else if(slot.getSlotItem().itemType.equals(Item.ItemType.WEAPON)) {
							Game.gui.equipment.addItem(slot.getSlotItem());
							slot.removeItem();
						}
					}
				}
			}
		}
		
		if(Display.wasResized()) {
			for(int i = 0; i < 8; i++) {
				slots.get(i).xPos = (Display.getWidth() / 2) - 235 + (i * 60);
				slots.get(i).yPos = Display.getHeight() - 50;
			}
			
			Slot slot1 = slots.get(0);
			Slot slot2 = slots.get(slots.size() - 1);
			rect = new Rect(slot1.xPos, slot1.yPos,
					slot2.xPos + slot2.xScale, slot2.yPos + slot2.yScale);
		}
	}
	
	public void building() {
		if(Game.gui.draggedItemCount == 0)
			Game.gui.draggedItem = null;
		
		if(Game.gui.draggedItem != null) {			
			if(Game.gui.draggedItem.itemType.equals(Item.ItemType.BUILDING)) {
				if(building != null) {
					Vector2f point = MousePicker.getGridPoint();
					
					if(point != null) {
						building.position.set(point.x, world.getTerrainHeight(point.x, point.y), point.y);
					}
					
					if(Mouse.isButtonDown(0)) {
						if(Game.state.equals(Game.State.GUI)) {
							building.remove();
							building = null;
						}
					}
					
					if(Mouse.isButtonDown(1)) {
						if(MathUtils.getDistanceBetweenPoints(player.position.x, player.position.z, 
								building.position.x, building.position.z) <= 10) {
							
							building.addComponent(new Collider(ResourceManager.getColliderMesh("box"),
									new Vector3f(building.position.x, building.position.y, building.position.z),
									new Vector3f(0, building.rotation.y, 0), new Vector3f(1.5f, 1.5f, 1.5f)));
							building = null;
							
							Game.gui.draggedItemCount--;
						}
					}
					
					if(Keyboard.isKeyDown(Key.R)) {
						currentEntityRotation += 90;
						if(currentEntityRotation == 360)
							currentEntityRotation = 0;
						
						building.rotation.y = currentEntityRotation;
					}
				} else {
					building = new Wall(world);
					building.rotation.y = currentEntityRotation;
				}
			}
		}
	}

	
	public boolean addItem(int id) {
		
		// Trying to find the same items in the inventory
		for(Slot slot : slots) {
			if(slot.getSlotItem() != null) {
				if(slot.getSlotItem().itemID == id) {
					if(slot.addItem(ItemDatabase.getItem(id))) {
						return true;	
					}
				}
			}
			
		}
		
		// If the same item isn't exist, adds item to the first empty slot
		for(Slot slot : slots) {
			if(slot.addItem(ItemDatabase.getItem(id)))
				return true;
		}
		
		return false;
	}
	
	public void render() {
		for(Slot slot : slots)
			slot.render(countText);
	}

	
	
}
