package com.wfe.gui;

import com.wfe.behaviours.PlayerBh;
import com.wfe.core.Paleon;
import com.wfe.core.ResourceManager;
import com.wfe.entities.Axe;
import com.wfe.entities.Helmet;
import com.wfe.entities.Player;
import com.wfe.graph.Texture;
import com.wfe.scenegraph.World;

public class Equipment {
	
	private PlayerBh player;
	
	private World world;
	
	private Slot handSlot, helmetSlot, armorSlot;
	
	public Equipment(World world, Player player) {
		this.world = world;
		this.player = player.getBehaviour(PlayerBh.class);
		
		Texture texture = ResourceManager.getTexture("ui_slot");
		handSlot = new Slot(texture, 0, 0, 50, 50);
		helmetSlot = new Slot(texture, 0, 0, 50, 50);
		armorSlot = new Slot(texture, 0, 0, 50, 50);
		
		updatePosition();
	}

	public void update(float dt) {
		
	}
	
	public void render() {
		handSlot.render();
		helmetSlot.render();
		armorSlot.render();
		
		
		if(Paleon.display.wasResized()) {
			updatePosition();
		}
	}
	
	public void addItem(Item item) {
		switch(item.type) {
		case HELMET:
			helmetSlot.addItem(item);
			player.addHelmet(new Helmet(world));
			break;
		case ARMOR:
			armorSlot.addItem(item);
			break;
		case WEAPON:
		case TOOL:
			handSlot.addItem(item);
			player.addWeapon(new Axe(world));
			break;
		default:
			break;
		}
	}
	
	private void updatePosition() {
		armorSlot.setPosition(Paleon.display.getWidth() - 60, Paleon.display.getHeight() - 50);
		helmetSlot.setPosition(Paleon.display.getWidth() - 120, Paleon.display.getHeight() - 50);
		handSlot.setPosition(Paleon.display.getWidth() - 180, Paleon.display.getHeight() - 50);
	}
	
}
