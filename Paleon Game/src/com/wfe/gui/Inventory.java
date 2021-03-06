package com.wfe.gui;

import java.util.ArrayList;
import java.util.List;

import com.wfe.components.Collider;
import com.wfe.components.Text;
import com.wfe.core.Paleon;
import com.wfe.core.ResourceManager;
import com.wfe.core.input.Keys;
import com.wfe.core.input.Keyboard;
import com.wfe.core.input.Mouse;
import com.wfe.entities.Wall;
import com.wfe.graph.render.GUIRenderer;
import com.wfe.math.Vector2f;
import com.wfe.math.Vector3f;
import com.wfe.scenegraph.Entity;
import com.wfe.scenegraph.World;
import com.wfe.scenes.GameState;
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
					(Paleon.display.getWidth() / 2) - 235 + (i * 60), Paleon.display.getHeight() - 50, 50, 50));
			
		}
		
		Slot slot1 = slots.get(0);
		Slot slot2 = slots.get(slots.size() - 1);
		rect = new Rect(slot1.xPos, slot1.yPos,
				slot2.xPos + slot2.xScale, slot2.yPos + slot2.yScale);
	}

	
	public void update(float deltaTime) {
		building();
		
		if(MathUtils.point2DBoxIntersection(Mouse.getX(), Mouse.getY(), rect)) {
			GameState.state = GameState.State.GUI;
		} else {
			GameState.state = GameState.State.GAME;
		}
		
		if(Mouse.isButtonDown(0)) {
			for(Slot slot : slots) {
				if(slot.overMouse()) {
					if(GameState.gui.draggedItem != null) {
						if(slot.getItem() == null) {
							slot.addItem(GameState.gui.draggedItem);
							slot.setItemsCount(GameState.gui.draggedItemCount);
							GameState.gui.draggedItem = null;
						} else {
							if(slot.getItem().ID == GameState.gui.draggedItem.ID) {
								slot.addItem(GameState.gui.draggedItem,
										GameState.gui.draggedItemCount);
								GameState.gui.draggedItem = null;
								GameState.gui.draggedItemCount = 0;
							} else {
								Item tempItem = slot.getItem();
								int tempItemCount = slot.getItemCount();
								slot.removeItem();
								slot.addItem(GameState.gui.draggedItem);
								slot.setItemsCount(GameState.gui.draggedItemCount);
								GameState.gui.draggedItem = tempItem;
								GameState.gui.draggedItemCount = tempItemCount;
							}
						}
					} else if(slot.getItem() != null) {
						GameState.gui.draggedItemCount = slot.getItemCount();
						GameState.gui.draggedItem = slot.getItem();
						slot.removeItem();
					}
				}
			}
		}
			
		if(Mouse.isButtonDown(1)) {
			for(Slot slot : slots) {
				if(slot.overMouse()) {
					if(slot.getItem() != null) {
						switch(slot.getItem().type) {
						case CONSUMABLE:
							if(slot.getItemCount() > 1) {
								GameState.gui.hud.hunger.increase(slot.getItem().starvation);
								slot.removeItem(1);
							} else {
								GameState.gui.hud.hunger.increase(slot.getItem().starvation);
								slot.removeItem();
							}
							break;
						case HELMET:
						case ARMOR:
						case WEAPON:
						case TOOL:
							GameState.gui.equipment.addItem(slot.getItem());
							slot.removeItem();
							break;
						default:
							break;
						}
					}
				}
			}
		}
		
		if(Paleon.display.wasResized()) {
			for(int i = 0; i < 8; i++) {
				slots.get(i).xPos = (Paleon.display.getWidth() / 2) - 235 + (i * 60);
				slots.get(i).yPos = Paleon.display.getHeight() - 50;
			}
			
			Slot slot1 = slots.get(0);
			Slot slot2 = slots.get(slots.size() - 1);
			rect = new Rect(slot1.xPos, slot1.yPos,
					slot2.xPos + slot2.xScale, slot2.yPos + slot2.yScale);
		}
	}
	
	public void building() {
		if(GameState.gui.draggedItemCount == 0)
			GameState.gui.draggedItem = null;
		
		if(GameState.gui.draggedItem != null) {			
			if(GameState.gui.draggedItem.type.equals(Item.Type.BUILDING)) {
				if(building != null) {
					Vector2f point = MousePicker.getGridPoint();
					
					if(point != null) {
						building.position.set(point.x, world.getTerrainHeight(point.x, point.y), point.y);
					}
					
					if(Mouse.isButtonDown(0)) {
						if(GameState.state.equals(GameState.State.GUI)) {
							building.remove();
							building = null;
						}
					}
					
					if(Mouse.isButtonDown(1)) {
						if(MathUtils.getDistanceBetweenPoints(player.position.x, player.position.z, 
								building.position.x, building.position.z) <= 10) {
							
							building.addComponent(new Collider(ResourceManager.getColliderMesh("box"),
									new Vector3f(building.position.x, building.position.y, building.position.z),
									new Vector3f(0, building.rotation.y, 0), new Vector3f(2f, 2f, 2f)));
							building = null;
							
							GameState.gui.draggedItemCount--;
						}
					}
					
					if(Keyboard.isKeyDown(Keys.KEY_R)) {
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

	
	public boolean addItem(int id, int amount) {
		
		// Trying to find the same items in the inventory
		for(Slot slot : slots) {
			if(slot.getItem() != null) {
				if(slot.getItem().ID == id) {
					if(slot.addItem(ItemDatabase.getItemByID(id), amount)) {
						return true;	
					}
				}
			}
			
		}
		
		// If the same item isn't exist, adds item to the first empty slot
		for(Slot slot : slots) {
			if(slot.addItem(ItemDatabase.getItemByID(id), amount))
				return true;
		}
		
		return false;
	}
	
	public int containsItemAmount(String itemName) {
		Item item = ItemDatabase.getItemByName(itemName);
		
		int temp = 0;
		
		for(Slot slot : slots) {
			Item slotItem = slot.getItem();
			if(slotItem != null) {
				if(item.ID == slotItem.ID) {
					temp += slot.getItemCount();
				}
			}
		}
		
		return temp;
	}
	
	public int containsItemAmount(int id) {
		Item item = ItemDatabase.getItemByID(id);
		int temp = 0;
		for(Slot slot : slots) {
			Item slotItem = slot.getItem();
			if(slotItem != null) {
				if(item.ID == slotItem.ID) {
					temp += slot.getItemCount();
				}
			}
		}
		
		return temp;
	}
	
	public void removeItem(int id, int amount) {
		for(Slot slot : slots) {
			Item item = slot.getItem();
			if(item != null) {
				if(item.ID == id) {
					slot.decreaseItem(amount);
				}
			}
		}
	}
	
	public void render() {
		for(Slot slot : slots)
			slot.render(countText);
	}

	
	
}
