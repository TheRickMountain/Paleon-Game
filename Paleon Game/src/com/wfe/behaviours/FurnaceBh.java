package com.wfe.behaviours;

import com.wfe.components.Text;
import com.wfe.core.Paleon;
import com.wfe.core.ResourceManager;
import com.wfe.core.input.Keyboard;
import com.wfe.core.input.Keys;
import com.wfe.core.input.Mouse;
import com.wfe.graph.Texture;
import com.wfe.graph.render.GUIRenderer;
import com.wfe.gui.Item;
import com.wfe.gui.ItemDatabase;
import com.wfe.gui.Slot;
import com.wfe.scenegraph.Entity;
import com.wfe.scenes.GameState;
import com.wfe.utils.Color;
import com.wfe.utils.MathUtils;
import com.wfe.utils.Rect;
import com.wfe.utils.TimeUtil;

public class FurnaceBh extends Behaviour {

	BoundingBoxBh bb;
	Entity player;
	PlayerBh playerBh;
	
	boolean showGUI;
	
	Texture slotTexture;
	Text slotText;
	TimeUtil time;
	BarCustom processBar;
	
	Rect rect;
	
	Slot fuelSlot, resourceSlot, resultSlot;
	
	boolean processStarted;
	int processTime = 5;
	
	@Override
	public void start() {
		bb = parent.getBehaviour(BoundingBoxBh.class);
		player = parent.getWorld().getEntityByName("Player");
		playerBh = player.getBehaviour(PlayerBh.class);
		
		slotTexture = ResourceManager.getTexture("ui_slot");
		slotText = new Text("e", GUIRenderer.primitiveFont, 1.1f, new Color(1.0f, 1.0f, 1.0f));
		time = new TimeUtil();
		processBar = new BarCustom(new Color(1.0f, 0.25f, 0.25f), 0, 0, 100, 15);
		
		rect = new Rect();
		
		resourceSlot = new Slot(slotTexture, 0, 0, 0, 0);
		fuelSlot = new Slot(slotTexture, 0, 0, 0, 0);
		resultSlot = new Slot(slotTexture, 0, 0, 0, 0);
		
		updatePosition();
	}

	@Override
	public void update(float deltaTime) {
		if(Mouse.isButtonDown(0)) {
			if(MathUtils.getDistanceBetweenPoints(player.position.x, player.position.z, 
					parent.position.x, parent.position.z) <= 10) {
				if(bb.intersect()) {
					showGUI = true;
				}
			}
		}
		
		if(Paleon.display.wasResized()) {
			updatePosition();
		}
		
		if(showGUI) {
			if(Keyboard.isKeyDown(Keys.KEY_TAB))
				showGUI = false;
			
			if(Mouse.isButtonDown(0)) {
				Item item = GameState.gui.draggedItem;
				if(item != null) {
					if(resourceSlot.overMouse()) {
						switch(item.name) {
						case "flint":
							resourceSlot.addItem(item, GameState.gui.draggedItemCount);
							GameState.gui.draggedItem = null;
							GameState.gui.draggedItemCount = 0;
							break;
						}
					} else if(fuelSlot.overMouse()) {
						switch(item.name) {
						case "log":
							fuelSlot.addItem(item);
							fuelSlot.setItemsCount(GameState.gui.draggedItemCount);
							GameState.gui.draggedItem = null;
							GameState.gui.draggedItemCount = 0;
							break;
						}
					}
				} else {
					if(resourceSlot.overMouse()) {
						GameState.gui.draggedItem = resourceSlot.getItem();
						GameState.gui.draggedItemCount = resourceSlot.getItemCount();
						resourceSlot.removeItem();
						
						processBar.setCurrentValue(0);
						processStarted = false;
						time.reset();
					} else if(resultSlot.overMouse()) {
						if(!resultSlot.isEmpty()) {
							GameState.gui.draggedItem = resultSlot.getItem();
							GameState.gui.draggedItemCount = resultSlot.getItemCount();
							resultSlot.removeItem();
						}
					}
 				}
			}
		}
		
		if(processStarted) {
			/* If time more than processTime than ready result is 
			 	going to the results slot */
			float currentProcessTime = (float) time.getTime();
			if(currentProcessTime >= processTime) {
				switch(resourceSlot.getItem().name) {
				case "flint":
					resultSlot.addItem(ItemDatabase.getItem("apple"));
					resourceSlot.decreaseItem();
					break;
				}
				
				processStarted = false;
				time.reset();
				processBar.setCurrentValue(0);
			} else {
				processBar.setCurrentValue((currentProcessTime * 100) / processTime);
			}
		} else {
			processBar.setCurrentValue(0);
			if(!resourceSlot.isEmpty()) {
				
				if(!fuelSlot.isEmpty()) {
					processStarted = true;
					fuelSlot.decreaseItem();
				}
			}
		}
		
	}

	@Override
	public void onGUI() {
		if(showGUI) {
			GUIRenderer.render(rect, slotTexture);
			resourceSlot.render(slotText);
			fuelSlot.render(slotText);
			resultSlot.render(slotText);
			processBar.render();
		}
	}
	
	private void updatePosition() {
		rect.x = Paleon.display.getWidth() / 2 - 120;
		rect.y = Paleon.display.getHeight() / 2 - 70;
		rect.width = 240; 
		rect.height = 140;
	
		resourceSlot.xPos = (int)rect.x + 10;
		resourceSlot.yPos = (int)rect.y + 10;
		resourceSlot.xScale = 50;
		resourceSlot.yScale = 50;
		
		fuelSlot.xPos = (int)rect.x + 10;
		fuelSlot.yPos = (int)(rect.y + rect.height) - 60;
		fuelSlot.xScale = 50;
		fuelSlot.yScale = 50;
		
		resultSlot.xPos = (int)(rect.x + rect.width) - 60;
		resultSlot.yPos = (int)rect.y + 10;
		resultSlot.xScale = 50;
		resultSlot.yScale = 50;
		
		processBar.xPos = (int)rect.x + 70;
		processBar.yPos = (int)(rect.y + 28);
	}

}
