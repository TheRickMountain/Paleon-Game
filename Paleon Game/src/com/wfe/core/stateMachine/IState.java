package com.wfe.core.stateMachine;

public interface IState {

    void loadResources();

    void onEnter() throws Exception;

    void changeState(StateMachine gGameMode) throws Exception;
    
    void update(float dt) throws Exception;
    
    void render() throws Exception;

    void onExit();

}
