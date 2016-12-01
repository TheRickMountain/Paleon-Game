package com.wfe.behaviours;

import com.wfe.components.Text;
import com.wfe.graph.Texture;
import com.wfe.graph.render.GUIRenderer;
import com.wfe.utils.Color;
import com.wfe.utils.Rect;

public class BarBh extends Behaviour {

	private float maxValue = 100;
	private float currentValue = 0;
	
	private float spriteSizeOnePercent;
	
	private Texture icon;
	private Color color;
	private Text text;
	
	private Rect rect;
	
	public BarBh(Texture icon, Color color) {
		this.icon = icon;
		this.color = color;
		this.text = new Text("text", GUIRenderer.primitiveFont, 1.1f, Color.WHITE);
	}
	
	@Override
	public void start() {
		this.rect = new Rect(0, 0, 150, 15);
		
		this.currentValue = maxValue;
		this.spriteSizeOnePercent = rect.width / maxValue;
	}

	@Override
	public void update(float deltaTime) {
		
	}

	@Override
	public void onGUI() {
		GUIRenderer.render(rect.x + parent.position.x, rect.y + parent.position.y,
				rect.width + rect.x, rect.height + rect.y, color);
		
		GUIRenderer.render(rect.x + parent.position.x - 12.5f, rect.y + parent.position.y - 6.25f,
				25 + rect.x, 25 + rect.y, icon);
		text.setText("" + (int)currentValue);
		GUIRenderer.render(rect.x + parent.position.x + 75, rect.y + parent.position.y - 2,
				text);
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
	
	public float getCurrentValue() {
		return currentValue;
	}
	
	public float getMaxValue() {
		return maxValue;
	}

}
