package com.wfe.core.stateMachine;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.glfw.GLFW;

import com.wfe.core.Paleon;

/**
 * Created by Rick on 06.10.2016.
 */
public class StateMachine implements IState {

    private Map<String, IState> mStates;

    private IState currentState;

    public StateMachine() {
        mStates = new HashMap<String, IState>();
        currentState = new EmptyState();
        mStates.put(null, currentState);
    }

    public void add(String name, IState scene) {
        mStates.put(name, scene);
    }

    public void change(String name) throws Exception{
        currentState.onExit();
        currentState = mStates.get(name);
        currentState.loadResources();
        currentState.onEnter();
        Paleon.lastTime = GLFW.glfwGetTime();
    }

    @Override
    public void loadResources() {
        currentState.loadResources();
    }

    @Override
    public void onEnter() throws Exception {
        currentState.onEnter();
    }
    
    @Override
	public void changeState(StateMachine gGameMode) throws Exception {
    	currentState.changeState(gGameMode);
	}
    
    @Override
    public void update(float dt) throws Exception {
        currentState.update(dt);
    }
    
    @Override
	public void render() throws Exception {
		currentState.render();
	}

    @Override
    public void onExit() {
        currentState.onExit();
    }

}
