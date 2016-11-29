package com.wfe.inventorySystem;

import com.wfe.core.ResourceManager;
import com.wfe.graph.Texture;

public class Item {
	
	public String itemName;
	public int itemID;
	public String itemDesc;
	public Texture itemIcon;
	public int itemFood;
	public float itemWeight;
	public ItemType itemType;
	public int stack;
	
	public enum ItemType {
		WEAPON,
		CONSUMABLE,
		ITEM
	}
	
	public Item() {
		
	}

	public Item(String itemName, int itemId, String itemDesc, int itemFood, float itemWeight,
			ItemType itemType, int stackability) {
		this.itemName = itemName;
		this.itemID = itemId;
		this.itemDesc = itemDesc;
		if(!itemName.isEmpty()) {
			this.itemIcon = ResourceManager.getTexture("ui_" + itemName);
		}
		this.itemFood = itemFood;
		this.itemWeight = itemWeight;
		this.itemType = itemType;
		this.stack = stackability;
	}

}
