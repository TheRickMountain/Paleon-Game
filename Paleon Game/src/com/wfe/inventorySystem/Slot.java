package com.wfe.inventorySystem;

import com.wfe.components.Text;
import com.wfe.graph.Texture;
import com.wfe.graph.render.GUIRenderer;
import com.wfe.input.Mouse;
import com.wfe.utils.MathUtils;

public class Slot {
	
	private Item item;
	
	private Texture slotTexture;
	
	public float xPos, yPos, xScale, yScale;
	
	private int itemsCount;
	
	public Slot(Texture slotTexture, int xPos, int yPos, int xScale, int yScale) {
		this.slotTexture = slotTexture;
		this.xPos = xPos;
		this.yPos = yPos;
		this.xScale = xScale;
		this.yScale = yScale;
		this.itemsCount = 0;
	}
	
	public boolean addItem(Item item) {
		if(this.item == null) {
			this.item = item;
			itemsCount++;
			return true;
		} 
		
		if(this.item.itemID == item.itemID) {
			itemsCount++;
			return true;
		}
		
		return false;
	}
	
	public void removeItem() {
		item = null;
		itemsCount = 0;
	}
	
	public Item getItem() {
		return item;
	}
	
	public void render(Text text) {
		GUIRenderer.render(xPos, yPos, xScale, yScale, slotTexture);
		if(item != null) {
			GUIRenderer.render(xPos, yPos, xScale, yScale, item.itemIcon);
			text.setText("x" + itemsCount);
			GUIRenderer.render(xPos, yPos, text);
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
