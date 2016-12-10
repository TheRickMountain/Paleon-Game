package com.wfe.gui;

public class CraftingElement {
	
	private int id;
	private int amount;
	
	public CraftingElement(int id, int amount) {
		super();
		this.id = id;
		this.amount = amount;
	}

	public int getId() {
		return id;
	}

	public int getAmount() {
		return amount;
	}

}
