package com.wfe.gui;

import java.util.ArrayList;
import java.util.List;

public class ItemDatabase {

	public static final int FLINT = 1;
	public static final int APPLE = 2;
	public static final int SHROOM = 3;
	public static final int LOG_WALL = 4;
	public static final int CAP = 5;
	public static final int TUNIC = 6;
	public static final int PANTS = 7;
	public static final int BOOTS = 8;
	public static final int AXE = 9;
	public static final int HUMMER = 10;
	
	private static List<Item> items = new ArrayList<Item>();
	
	public static void init() {
		items.add(new Item("", 0, "", 0, 0, Item.ItemType.ITEM, 0));
		items.add(new Item("flint", FLINT, "", 0, 0, Item.ItemType.ITEM, 1));
		items.add(new Item("apple", APPLE, "", 7, 0, Item.ItemType.CONSUMABLE, 1));
		items.add(new Item("shroom", SHROOM, "", 12, 0, Item.ItemType.CONSUMABLE, 1));
		items.add(new Item("log wall", LOG_WALL, "", 0, 0, Item.ItemType.BUILDING, 1));
		items.add(new Item("cap", CAP, "", 0, 0, Item.ItemType.CAP, 1));
		items.add(new Item("tunic", TUNIC, "", 0, 0, Item.ItemType.TUNIC, 1));
		items.add(new Item("pants", PANTS, "", 0, 0, Item.ItemType.PANTS, 1));
		items.add(new Item("boots", BOOTS, "", 0, 0, Item.ItemType.BOOTS, 1));
		items.add(new Item("axe", AXE, "", 0, 0, Item.ItemType.WEAPON, 1));
		items.add(new Item("hummer", HUMMER, "", 0, 0, Item.ItemType.WEAPON, 1));
	}
	
	public static Item getItem(int id) {
		return items.get(id);
	}

}
