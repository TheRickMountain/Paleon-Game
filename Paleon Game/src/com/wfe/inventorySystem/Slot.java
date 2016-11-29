package com.wfe.inventorySystem;

import com.wfe.graph.Texture;
import com.wfe.graph.render.GUIRenderer;
import com.wfe.input.Mouse;
import com.wfe.utils.MathUtils;

public class Slot {
	
	private Item item;
	
	private Texture slotTexture;
	
	public float xPos, yPos, xScale, yScale;
	
	public Slot(Texture slotTexture, int xPos, int yPos, int xScale, int yScale) {
		this.slotTexture = slotTexture;
		this.xPos = xPos;
		this.yPos = yPos;
		this.xScale = xScale;
		this.yScale = yScale;
	}
	
	public void addItem(Item item) {
		this.item = item;
	}
	
	public void removeItem() {
		item = null;
	}
	
	public Item getItem() {
		return item;
	}
	
	public void render() {
		GUIRenderer.render(xPos, yPos, xScale, yScale, slotTexture);
		if(item != null)
			GUIRenderer.render(xPos, yPos, xScale, yScale, item.itemIcon);
	}
	
	public boolean overMouse() {
		return MathUtils.point2DBoxIntersection(Mouse.getX(), Mouse.getY(), xPos, yPos, xScale, yScale);
	}

}
