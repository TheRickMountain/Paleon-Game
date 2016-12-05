package com.wfe.scenes;

import com.wfe.core.IState;
import com.wfe.core.StateMachine;

public class MenuState implements IState {

	private StateMachine gGameMode;
	
	public MenuState(StateMachine gameMode) {
		this.gGameMode = gameMode;
	}
	
	@Override
	public void loadResources() {
		
	}

	@Override
	public void onEnter() throws Exception {
		
	}

	@Override
	public void update(float dt) throws Exception {
		gGameMode.change("game");
	}

	@Override
	public void onExit() {
		
	}

}
