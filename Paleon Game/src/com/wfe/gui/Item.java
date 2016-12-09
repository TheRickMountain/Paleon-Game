package com.wfe.gui;

import com.wfe.core.ResourceManager;
import com.wfe.graph.Texture;

public class Item {
	
	public Texture icon;
	public String name;
	public int ID;
	public String desc;
	public int starvation;
	public int dehydration;
	public ItemType type;
	public int stack;
	
	public enum ItemType {
		CAP,
		TUNIC,
		PANTS, 
		BOOTS,
		WEAPON,
		AMULET,
		BACKPACK,
		CONSUMABLE,
		ITEM,
		BUILDING,
	}
	
	public Item() {
		
	}

	public Item(String itemName, int itemId, String itemDesc, int itemStarvation, int itemDehydration,
			ItemType itemType, int stackability) {
		this.name = itemName;
		this.ID = itemId;
		this.desc = itemDesc;
		if(!itemName.isEmpty()) {
			this.icon = ResourceManager.getTexture("ui_" + itemName);
		}
		this.starvation = itemStarvation;
		this.dehydration = itemDehydration;
		this.type = itemType;
		this.stack = stackability;
	}
	
	public Item(Texture itemIcon, String itemName, int itemId, String itemDesc, int itemStarvation, int itemDehydration,
			ItemType itemType, int stackability) {
		this.name = itemName;
		this.ID = itemId;
		this.desc = itemDesc;
		this.icon = itemIcon;
		this.starvation = itemStarvation;
		this.dehydration = itemDehydration;
		this.type = itemType;
		this.stack = stackability;
	}
	
	public String toString() {
		return "name: " + name + "\n" +
				"id: " + ID + "\n" +
				"description: " + desc + "\n" +
				"starvation: " + starvation + "\n" +
				"dehydration: " + dehydration + "\n" +
				"type: " + type + "\n" +
				"stack: " + stack;
	}

}
