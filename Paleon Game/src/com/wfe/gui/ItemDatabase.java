package com.wfe.gui;

import java.util.HashMap;
import java.util.Map;

import com.wfe.test.ItemsImporter;

public class ItemDatabase {
	
	private static Map<String, Item> items = new HashMap<String, Item>();
	
	public static void init() {
		ItemsImporter importer = new ItemsImporter();
		try {
			importer.importItems(items);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Item getItemByName(String name) {
		return items.get(name);
	}

}
