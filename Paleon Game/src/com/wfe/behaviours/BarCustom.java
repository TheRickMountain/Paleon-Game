package com.wfe.behaviours;

import com.wfe.graph.render.GUIRenderer;
import com.wfe.utils.Color;
import com.wfe.utils.Rect;

public class BarCustom {
	
	public int xPos, yPos;
	public Color color;
	
	private float maxValue = 100;
	private float currentValue = 0;
	
	private float spriteSizeOnePercent;
	
	private Rect rect;
	
	public BarCustom(Color color, int xPos, int yPos, int width, int height) {
		this.color = color;
		this.xPos = xPos;
		this.yPos = yPos;
		
		this.rect = new Rect(0, 0, width, height);
		
		this.currentValue = maxValue;
		this.spriteSizeOnePercent = rect.width / maxValue;
	}

	public void render() {
		GUIRenderer.render(rect.x + xPos, rect.y + yPos,
				rect.width + rect.x, rect.height + rect.y, color);
	}
	
	public void decrease(float value) {	
		float temp = currentValue - value;
		if(temp < 0) {
			value = 0;
			this.currentValue = 0;
		} else {
			this.currentValue -= value;
		}
		
		rect.width = currentValue * spriteSizeOnePercent;
	}
	
	public void increase(float value) {	
		float temp = currentValue + value;
		if(temp > maxValue) {
			value = 0;
			this.currentValue = maxValue;
		} else {
			this.currentValue += value;
		}
		
		rect.width = currentValue * spriteSizeOnePercent;
	}
	
	public void setCurrentValue(float currentValue) {
		this.currentValue = currentValue;
		rect.width = this.currentValue * spriteSizeOnePercent;
	}
	
	public float getCurrentValue() {
		return currentValue;
	}
	
	public float getMaxValue() {
		return maxValue;
	}

}
