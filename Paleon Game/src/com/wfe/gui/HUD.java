package com.wfe.gui;

import com.wfe.behaviours.BarBh;
import com.wfe.core.ResourceManager;
import com.wfe.scenegraph.World;
import com.wfe.utils.Color;
import com.wfe.utils.TimeUtil;

public class HUD {

	public BarBh health, hunger;
	
	private TimeUtil timeUtil;
	
	public HUD(World world) {
		Bar healthBar = new Bar(world, "Health Bar", ResourceManager.getTexture("ui_health"), new Color(1.0f, 0.2f, 0.1f));
        healthBar.position.set(20, 10);
        
        Bar hungerBar = new Bar(world, "Hunger Bar", ResourceManager.getTexture("ui_hunger"), new Color(1.0f, 0.5f, 0.1f));
        hungerBar.position.set(20, 40);
		
		health = healthBar.getBehaviour(BarBh.class);
		hunger = hungerBar.getBehaviour(BarBh.class);
		
		timeUtil = new TimeUtil();
	}
	
	public void update(float dt) {
		if((int)timeUtil.getTime() >= 10) {
			hunger.decrease(1);
			timeUtil.reset();
		}
		
		if(hunger.getCurrentValue() == 0) {
			if((int)timeUtil.getTime() >= 1) {
				health.decrease(2);
				timeUtil.reset();
			}
		}
	}
	
}
