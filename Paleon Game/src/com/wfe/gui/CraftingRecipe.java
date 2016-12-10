package com.wfe.gui;

public class CraftingRecipe {
	
	public CraftingElement[] craftingElements; 
	
	public CraftingRecipe(CraftingElement ...craftingElements){
		if(craftingElements.length > 5)
			throw new IllegalArgumentException("Too many crafting elements");
		
		this.craftingElements = craftingElements;
	}

}
