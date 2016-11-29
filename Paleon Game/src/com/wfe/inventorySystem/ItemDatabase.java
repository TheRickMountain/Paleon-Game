package com.wfe.inventorySystem;

import java.util.ArrayList;
import java.util.List;

public class ItemDatabase {

	public static final int FLINT = 1;
	public static final int APPLE = 2;
	public static final int SHROOM = 3;
	
	private static List<Item> items = new ArrayList<Item>();
	
	public static void init() {
		items.add(new Item("", 0, "", 0, 0, Item.ItemType.ITEM, 0));
		items.add(new Item("flint", FLINT, "heavy flint", 0, 0, Item.ItemType.ITEM, 1));
		items.add(new Item("apple", APPLE, "sweet apple is so sweet", 0, 0, Item.ItemType.CONSUMABLE, 1));
		items.add(new Item("shroom", SHROOM, "I have a mushroom", 0, 0, Item.ItemType.CONSUMABLE, 1));
	}
	
	public static Item getItem(int id) {
		return items.get(id);
	}

}
