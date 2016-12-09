package com.wfe.scenes;

import org.lwjgl.opengl.GL11;

import com.wfe.core.stateMachine.IState;
import com.wfe.core.stateMachine.StateMachine;

public class MenuState implements IState {

	@Override
	public void loadResources() {
		
	}

	@Override
	public void onEnter() throws Exception {
	}
	
	@Override
	public void changeState(StateMachine gGameMode) throws Exception {
		//if(Keyboard.isKeyDown(Keys.KEY_F)) {
			gGameMode.change("game");
		//}
	}
	
	@Override
	public void update(float dt) throws Exception {
	}
	
	@Override
	public void render() throws Exception {
		GL11.glClearColor(0.25f, 0.5f, 1.0f, 1.0f);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}

	@Override
	public void onExit() {
	}

}
