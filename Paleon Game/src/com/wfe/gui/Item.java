package com.wfe.gui;

import com.wfe.graph.Texture;

public class Item {
	
	public Texture icon;
	public String name;
	public int ID;
	public String desc;
	public int starvation;
	public int dehydration;
	public Type type;
	public int maxStackSize;
	
	public enum Type {
		HELMET,
		ARMOR,
		WEAPON,
		TOOL,
		CONSUMABLE,
		MINERAL,
		MATERIAL,
		BUILDING,
	}
	
	public Item(Texture itemIcon, String itemName, int itemId, String itemDesc, int itemStarvation, int itemDehydration,
			Type itemType, int stackability) {
		this.name = itemName;
		this.ID = itemId;
		this.desc = itemDesc;
		this.icon = itemIcon;
		this.starvation = itemStarvation;
		this.dehydration = itemDehydration;
		this.type = itemType;
		this.maxStackSize = stackability;
	}
	
	public String toString() {
		return "name: " + name + "\n" +
				"id: " + ID + "\n" +
				"description: " + desc + "\n" +
				"starvation: " + starvation + "\n" +
				"dehydration: " + dehydration + "\n" +
				"type: " + type + "\n" +
				"stack: " + maxStackSize;
	}

}
