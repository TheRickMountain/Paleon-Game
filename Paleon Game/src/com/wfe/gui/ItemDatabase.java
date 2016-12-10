package com.wfe.gui;

import java.util.ArrayList;
import java.util.List;

import com.wfe.graph.TextureLoader;

public class ItemDatabase {
	
	public static List<Item> items = new ArrayList<Item>();
	
	public static final int APPLE = 0;
	public static final int ARMOR = 1;
	public static final int AXE = 2;
	public static final int BANANA = 3;
	public static final int BREAD = 4;
	public static final int BROCOLLI = 5;
	public static final int COOKIE = 6;
	public static final int FLINT = 7;
	public static final int HELMET = 8;
	public static final int HUMMER = 9;
	public static final int LOG_WALL = 10;
	public static final int LOG = 11;
	public static final int PINEAPPLE = 12;
	public static final int POTATO = 13;
	public static final int SHROOM = 14;
	public static final int TROUT = 15;
	
	private static final String PATH = "/items/";
	
	public static void init() throws Exception {
		items.add(new Item(APPLE, TextureLoader.load(PATH + "apple.png"), "apple", "apple is apple", 
				10, 0, Item.Type.CONSUMABLE, false, null, 64));
		
		items.add(new Item(ARMOR, TextureLoader.load(PATH + "armor.png"), "armor", "armor is armor", 
				0, 0, Item.Type.ARMOR, true, 
				new CraftingElement[]{new CraftingElement(FLINT, 4)}, 64));
		
		items.add(new Item(AXE, TextureLoader.load(PATH + "axe.png"), "axe", "axe is axe", 
				0, 0, Item.Type.TOOL, true, 
				new CraftingElement[]{new CraftingElement(FLINT, 1), new CraftingElement(LOG, 1)}, 64));
		
		items.add(new Item(BANANA, TextureLoader.load(PATH + "banana.png"), "banana", "banana is banana", 
				10, 0, Item.Type.CONSUMABLE, false, null, 64));
		
		items.add(new Item(BREAD, TextureLoader.load(PATH + "bread.png"), "bread", "bread is bread", 
				10, 0, Item.Type.CONSUMABLE, false, null, 64));
		
		items.add(new Item(BROCOLLI, TextureLoader.load(PATH + "brocolli.png"), "brocolli", "brocolli is brocolli", 
				10, 0, Item.Type.CONSUMABLE, false, null, 64));
		
		items.add(new Item(COOKIE, TextureLoader.load(PATH + "cookie.png"), "cookie", "cookie is cookie", 
				10, 0, Item.Type.CONSUMABLE, false, null, 64));
		
		items.add(new Item(FLINT, TextureLoader.load(PATH + "flint.png"), "flint", "flint is flint", 
				0, 0, Item.Type.MATERIAL, false, null, 64));
		
		items.add(new Item(HELMET, TextureLoader.load(PATH + "helmet.png"), "helmet", "helmet is helmet", 
				0, 0, Item.Type.HELMET, true, 
				new CraftingElement[]{new CraftingElement(FLINT, 2)}, 64));
		
		items.add(new Item(HUMMER, TextureLoader.load(PATH + "hummer.png"), "hummer", "hummer is hummer", 
				0, 0, Item.Type.TOOL, true, new CraftingElement[]{new CraftingElement(FLINT, 2),
						new CraftingElement(LOG, 1)}, 64));
		
		items.add(new Item(LOG_WALL, TextureLoader.load(PATH + "log wall.png"), "log wall", "log wall is log wall", 
				0, 0, Item.Type.BUILDING, true, 
				new CraftingElement[]{new CraftingElement(LOG, 6)}, 64));
		
		items.add(new Item(LOG, TextureLoader.load(PATH + "log.png"), "log", "log is log", 
				0, 0, Item.Type.MATERIAL, false, null, 64));
		
		items.add(new Item(PINEAPPLE,TextureLoader.load(PATH + "pineapple.png"), "pineapple", "pineapple is pineapple", 
				10, 0, Item.Type.CONSUMABLE, false, null, 64));
		
		items.add(new Item(POTATO,TextureLoader.load(PATH + "potato.png"), "potato", "potato is potato", 
				10, 0, Item.Type.CONSUMABLE, false, null, 64));
		
		items.add(new Item(SHROOM,TextureLoader.load(PATH + "shroom.png"), "shroom", "shroom is shroom", 
				10, 0, Item.Type.CONSUMABLE, false, null, 64));
		
		items.add(new Item(TROUT,TextureLoader.load(PATH + "trout.png"), "trout", "trout is trout", 
				10, 0, Item.Type.CONSUMABLE, false, null, 64));
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
