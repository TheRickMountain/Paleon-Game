package com.wfe.gui;

import java.util.ArrayList;
import java.util.List;

import com.wfe.test.ItemsImporter;

public class ItemDatabase {
	
	public static List<Item> items = new ArrayList<Item>();
	
	public static void init() {
		ItemsImporter importer = new ItemsImporter();
		try {
			importer.importItems(items);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Item getItemByName(String name) {
		for(Item item : items) {
			if(item.name.equals(name)) {
				return item;
			}
		}
		return null;
	}
	
	public static Item getItemByID(int id) {
		for(Item item : items) {
			if(item.ID == id) {
				return item;
			}
		}
		return null;
	}

}
