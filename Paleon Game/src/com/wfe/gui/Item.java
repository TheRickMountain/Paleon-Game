package com.wfe.gui;

import com.wfe.graph.Texture;

public class Item {
	
	public final Texture icon;
	public final String name;
	public final int ID;
	public final String desc;
	public final int starvation;
	public final int dehydration;
	public final Type type;
	public final boolean hasCrafting;
	public final int maxStackSize;
	
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
			Type itemType, boolean hasCrafting, int stackability) {
		this.name = itemName;
		this.ID = itemId;
		this.desc = itemDesc;
		this.icon = itemIcon;
		this.starvation = itemStarvation;
		this.dehydration = itemDehydration;
		this.type = itemType;
		this.hasCrafting = hasCrafting;
		this.maxStackSize = stackability;
	}
	
	public String toString() {
		return "name: " + name + "\n" +
				"id: " + ID + "\n" +
				"description: " + desc + "\n" +
				"starvation: " + starvation + "\n" +
				"dehydration: " + dehydration + "\n" +
				"type: " + type + "\n" +
				"has crafting: " + hasCrafting + "\n" +
				"stack: " + maxStackSize;
	}

}
