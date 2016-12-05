package com.wfe.core.stateMachine;

import java.util.HashMap;
import java.util.Map;

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
    public void update(float dt) throws Exception {
        currentState.update(dt);
    }

    @Override
    public void onExit() {
        currentState.onExit();
    }

}
