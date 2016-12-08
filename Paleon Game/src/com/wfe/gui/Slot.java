package com.wfe.gui;

import com.wfe.components.Text;
import com.wfe.core.input.Mouse;
import com.wfe.graph.Texture;
import com.wfe.graph.render.GUIRenderer;
import com.wfe.utils.MathUtils;

public class Slot {
	
	private Item item;
	
	private Texture slotTexture;
	
	public float xPos, yPos, xScale, yScale;
	
	private int itemCount;
	
	public SlotType type = SlotType.ALL;
	
	public enum SlotType {
		CAP,
		TUNIC,
		PANTS, 
		BOOTS,
		WEAPON,
		AMULET,
		BACKPACK,
		ALL
	}
	
	public Slot(Texture slotTexture, int xPos, int yPos, int xScale, int yScale) {
		this(slotTexture, xPos, yPos, xScale, yScale, SlotType.ALL);
	}
	
	public Slot(Texture slotTexture, int xPos, int yPos, int xScale, int yScale, SlotType type) {
		this.slotTexture = slotTexture;
		this.xPos = xPos;
		this.yPos = yPos;
		this.xScale = xScale;
		this.yScale = yScale;
		this.itemCount = 0;
		this.type = type;
	}
	
	public boolean addItem(Item item) {	
		if(this.item != null) {
			if(this.item.itemID == item.itemID) {
				itemCount++;
				return true;
			}
		} else {
			this.item = item;
			itemCount++;
			return true;
		} 
		
		return false;
	}
	
	public void removeItem() {
		item = null;
		itemCount = 0;
	}
	
	public void removeItem(int count) {
		if(count > itemCount)
			itemCount = 0;
		else
			itemCount -= count;
	}
	
	public Item getSlotItem() {
		return item;
	}
	
	public void setPosition(float x, float y) {
		this.xPos = x;
		this.yPos = y;
	}
	
	public void render(Text text) {
		GUIRenderer.render(xPos, yPos, xScale, yScale, slotTexture);
		if(item != null) {
			GUIRenderer.render(xPos, yPos, xScale, yScale, item.itemIcon);
			if(itemCount >= 2) {
				text.setText("x" + itemCount);
				GUIRenderer.render(xPos, yPos, text);
			}
		}
	}
	
	public void render() {
		GUIRenderer.render(xPos, yPos, xScale, yScale, slotTexture);
		if(item != null) {
			GUIRenderer.render(xPos, yPos, xScale, yScale, item.itemIcon);
		}
	}
	
	public boolean overMouse() {
		return MathUtils.point2DBoxIntersection(Mouse.getX(), Mouse.getY(), xPos, yPos, xScale, yScale);
	}
	
	public int getItemsCount() {
		return itemCount;
	}
	
	public void setItemsCount(int itemsCount) {
		this.itemCount = itemsCount;
	}
	
	public boolean isEmpty() {
		return item == null;
	}
	
	public void decreaseItem() {
		itemCount--;
		
		if(itemCount == 0) {
			item = null;
		}
	}

}
