package com.wfe.behaviours;

import com.wfe.core.input.Mouse;

public class ButtonBh extends Behaviour {

	@Override
	public void start() {
		
	}

	@Override
	public void update(float deltaTime) {
		
	}

	@Override
	public void onGUI() {
		
	}
	
	public boolean isPressedDown(int button) {
		if(isOverMouse()) {
			if(Mouse.isButtonDown(button)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isPressedUp(int button) {
		if(isOverMouse()) {
			if(Mouse.isButtonUp(button)) {
				return true;
			}
		}
		
		return false;
	}

	public boolean isPressed(int button) {
		if(isOverMouse()) {
			if(Mouse.isButton(button)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isOverMouse() {
		float x = parent.position.x;
		float y = parent.position.y;
		float width = parent.scale.x;
		float height = parent.scale.y;
		
		return (Mouse.getX() >= x && Mouse.getX() <= x + width &&
				Mouse.getY() >= y && Mouse.getY() <= y + height);
	}
	

}
