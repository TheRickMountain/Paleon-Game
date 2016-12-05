package com.wfe.gui;

import com.wfe.components.Text;
import com.wfe.core.input.Mouse;
import com.wfe.graph.Texture;
import com.wfe.graph.render.GUIRenderer;
import com.wfe.utils.MathUtils;

public class Slot {
	
	private Item slotItem;
	
	private Texture slotTexture;
	
	public float xPos, yPos, xScale, yScale;
	
	private int itemsCount;
	
	public SlotType slotType = SlotType.ALL;
	
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
		this.itemsCount = 0;
		this.slotType = type;
	}
	
	public boolean addItem(Item item) {	
		if(this.slotItem != null) {
			if(this.slotItem.itemID == item.itemID) {
				itemsCount++;
				return true;
			}
		} else {
			this.slotItem = item;
			itemsCount++;
			return true;
		} 
		
		return false;
	}
	
	public void removeItem() {
		slotItem = null;
		itemsCount = 0;
	}
	
	public void removeItem(int count) {
		if(count > itemsCount)
			itemsCount = 0;
		else
			itemsCount -= count;
	}
	
	public Item getSlotItem() {
		return slotItem;
	}
	
	public void setPosition(float x, float y) {
		this.xPos = x;
		this.yPos = y;
	}
	
	public void render(Text text) {
		GUIRenderer.render(xPos, yPos, xScale, yScale, slotTexture);
		if(slotItem != null) {
			GUIRenderer.render(xPos, yPos, xScale, yScale, slotItem.itemIcon);
			if(itemsCount >= 2) {
				text.setText("x" + itemsCount);
				GUIRenderer.render(xPos, yPos, text);
			}
		}
	}
	
	public void render() {
		GUIRenderer.render(xPos, yPos, xScale, yScale, slotTexture);
		if(slotItem != null) {
			GUIRenderer.render(xPos, yPos, xScale, yScale, slotItem.itemIcon);
		}
	}
	
	public boolean overMouse() {
		return MathUtils.point2DBoxIntersection(Mouse.getX(), Mouse.getY(), xPos, yPos, xScale, yScale);
	}
	
	public int getItemsCount() {
		return itemsCount;
	}
	
	public void setItemsCount(int itemsCount) {
		this.itemsCount = itemsCount;
	}

}
