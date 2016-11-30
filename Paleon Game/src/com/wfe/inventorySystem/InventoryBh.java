package com.wfe.inventorySystem;

import java.util.ArrayList;
import java.util.List;

import com.wfe.behaviours.Behaviour;
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
import com.wfe.scenes.Game;
import com.wfe.utils.Color;
import com.wfe.utils.MathUtils;
import com.wfe.utils.MousePicker;
import com.wfe.utils.Rect;

public class InventoryBh extends Behaviour {

	private List<Slot> downSlots = new ArrayList<Slot>();
	
	private Item draggedItem;
	private int draggedItemCount;
	
	private Entity placeableEntity;
	private float currentEntityRotation;
	
	private Rect rect;
	
	private Text countText;
	
	@Override
	public void start() {	
		ItemDatabase.init();
		
		countText = new Text("test", GUIRenderer.primitiveFont, 1.1f, Color.WHITE);			
		
		for(int i = 0; i < 8; i++) {
			downSlots.add(new Slot(ResourceManager.getTexture("ui_slot"), 
					(Display.getWidth() / 2) - 235 + (i * 60), Display.getHeight() - 50, 50, 50));
			
		}
		
		Slot slot1 = downSlots.get(0);
		Slot slot2 = downSlots.get(downSlots.size() - 1);
		rect = new Rect(slot1.xPos, slot1.yPos,
				slot2.xPos + slot2.xScale, slot2.yPos + slot2.yScale);
		
		downSlots.get(0).addItem(ItemDatabase.getItem(ItemDatabase.APPLE));
		downSlots.get(1).addItem(ItemDatabase.getItem(ItemDatabase.FLINT));
		downSlots.get(2).addItem(ItemDatabase.getItem(ItemDatabase.SHROOM));
		downSlots.get(3).addItem(ItemDatabase.getItem(ItemDatabase.LOG_WALL));
	}

	
	@Override
	public void update(float deltaTime) {
		building();
		
		if(MathUtils.point2DBoxIntersection(Mouse.getX(), Mouse.getY(), rect)) {
			Game.state = Game.State.GUI;
		} else {
			Game.state = Game.State.GAME;
		}
		
		for(Slot slot : downSlots) {
			if(Mouse.isButtonDown(0)) {
				if(slot.overMouse()) {
					if(draggedItem != null) {
						if(slot.getItem() == null) {
							slot.addItem(draggedItem);
							slot.setItemsCount(draggedItemCount);
							draggedItem = null;
						} else {
							Item temp = slot.getItem();
							draggedItemCount = slot.getItemsCount();
							slot.removeItem();
							slot.addItem(draggedItem);
							draggedItem = temp;
						}
					} else if(slot.getItem() != null) {
						draggedItemCount = slot.getItemsCount();
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
			
			Slot slot1 = downSlots.get(0);
			Slot slot2 = downSlots.get(downSlots.size() - 1);
			rect = new Rect(slot1.xPos, slot1.yPos,
					slot2.xPos + slot2.xScale, slot2.yPos + slot2.yScale);
		}
	}
	
	public void building() {
		if(draggedItem != null) {
			if(draggedItem.itemType.equals(Item.ItemType.BUILDING)) {
				if(placeableEntity != null) {
					Vector2f point = MousePicker.getGridPoint();
					
					if(point != null) {
						placeableEntity.position.set(point.x, parent.getWorld().getTerrainHeight(point.x, point.y), point.y);
					}
					
					if(Mouse.isButtonDown(0)) {
						if(Game.state.equals(Game.State.GUI)) {
							placeableEntity.remove();
							placeableEntity = null;
						}
					}
					
					if(Mouse.isButtonDown(1)) {
						placeableEntity.addComponent(new Collider(ResourceManager.getColliderMesh("box"),
								new Vector3f(placeableEntity.position.x, placeableEntity.position.y, placeableEntity.position.z),
								new Vector3f(0, placeableEntity.rotation.y, 0), new Vector3f(1.5f, 1.5f, 1.5f)));
						placeableEntity = null;
					}
					
					if(Keyboard.isKeyDown(Key.R)) {
						currentEntityRotation += 90;
						if(currentEntityRotation == 360)
							currentEntityRotation = 0;
						
						placeableEntity.rotation.y = currentEntityRotation;
					}
				} else {
					placeableEntity = new Wall(parent.getWorld());
					placeableEntity.rotation.y = currentEntityRotation;
				}
			}
		}
	}
	
	public boolean addItem(int id) {
		for(Slot slot : downSlots) {
			if(slot.addItem(ItemDatabase.getItem(id))) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void onGUI() {
		for(Slot slot : downSlots)
			slot.render(countText);
		
		if(draggedItem != null) {
			GUIRenderer.render(Mouse.getX() - 25, Mouse.getY() - 25, 50, 50, draggedItem.itemIcon);
			countText.setText("x" + draggedItemCount);
			GUIRenderer.render(Mouse.getX() - 25, Mouse.getY() - 25, countText);
		}
	}

	
	
}
