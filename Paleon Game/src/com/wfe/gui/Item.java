package com.wfe.gui;

import com.wfe.core.ResourceManager;
import com.wfe.graph.Texture;

public class Item {
	
	public String itemName;
	public int itemID;
	public String itemDesc;
	public Texture itemIcon;
	public int itemStarvation;
	public int itemDehydration;
	public ItemType itemType;
	public int stack;
	
	public enum ItemType {
		WEAPON,
		CONSUMABLE,
		ITEM,
		BUILDING,
		CAP,
		TUNIC,
		PANTS, 
		BOOTS
	}
	
	public Item() {
		
	}

	public Item(String itemName, int itemId, String itemDesc, int itemStarvation, int itemDehydration,
			ItemType itemType, int stackability) {
		this.itemName = itemName;
		this.itemID = itemId;
		this.itemDesc = itemDesc;
		if(!itemName.isEmpty()) {
			this.itemIcon = ResourceManager.getTexture("ui_" + itemName);
		}
		this.itemStarvation = itemStarvation;
		this.itemDehydration = itemDehydration;
		this.itemType = itemType;
		this.stack = stackability;
	}

}
