package com.wfe.core.stateMachine;

/**
 * Created by Rick on 06.10.2016.
 */
public class EmptyState implements IState {

    @Override
    public void loadResources() {

    }
    
    @Override
    public void changeState(StateMachine gGameMode)  throws Exception {
    	
    }

    @Override
    public void onEnter() throws Exception {

    }
    
    @Override
    public void update(float dt) {

    }

    @Override
	public void render() throws Exception {
		
	}
    
    @Override
    public void onExit() {

    }
    
}
