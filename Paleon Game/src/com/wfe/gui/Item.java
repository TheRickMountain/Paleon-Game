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
	public final CraftingElement[] craftingElements;
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
	
	public Item(int ID, Texture icon, String name, String desc, int starvation, 
			int dehydration, Type type, boolean hasCrafting, CraftingElement[] craftingElements, int maxStackSize) {
		this.ID = ID;
		this.name = name;
		this.desc = desc;
		this.icon = icon;
		this.starvation = starvation;
		this.dehydration = dehydration;
		this.type = type;
		this.hasCrafting = hasCrafting;
		this.craftingElements = craftingElements;
		this.maxStackSize = maxStackSize;
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
