package com.wfe.gui;

import com.wfe.components.Text;
import com.wfe.core.input.Mouse;
import com.wfe.graph.Texture;
import com.wfe.graph.render.GUIRenderer;
import com.wfe.utils.MathUtils;

public class Slot {
	
	private Item item;
	
	private Texture texture;
	
	public float xPos, yPos, xScale, yScale;
	
	private int itemCount;
	
	public Slot(Texture texture, int xPos, int yPos, int xScale, int yScale) {
		this.texture = texture;
		this.xPos = xPos;
		this.yPos = yPos;
		this.xScale = xScale;
		this.yScale = yScale;
		this.itemCount = 0;
	}
	
	public boolean addItem(Item item) {	
		if(this.item != null) {
			if(this.item.ID == item.ID) {
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
	
	public boolean addItem(Item item, int count) {	
		if(this.item != null) {
			if(this.item.ID == item.ID) {
				itemCount += count;
				return true;
			}
		} else {
			this.item = item;
			itemCount = count;
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
	
	public Item getItem() {
		return item;
	}
	
	public void setPosition(float x, float y) {
		this.xPos = x;
		this.yPos = y;
	}
	
	public void render(Text text) {
		GUIRenderer.render(xPos, yPos, xScale, yScale, texture);
		if(item != null) {
			GUIRenderer.render(xPos, yPos, xScale, yScale, item.icon);
			if(itemCount >= 2) {
				text.setText("x" + itemCount);
				GUIRenderer.render(xPos, yPos, text);
			}
		}
	}
	
	public void render() {
		GUIRenderer.render(xPos, yPos, xScale, yScale, texture);
		if(item != null) {
			GUIRenderer.render(xPos, yPos, xScale, yScale, item.icon);
		}
	}
	
	public boolean overMouse() {
		return MathUtils.point2DBoxIntersection(Mouse.getX(), Mouse.getY(), xPos, yPos, xScale, yScale);
	}
	
	public int getItemCount() {
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
