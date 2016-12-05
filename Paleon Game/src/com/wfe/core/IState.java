package com.wfe.core;

public interface IState {

    void loadResources();

    void onEnter() throws Exception;

    void update(float dt) throws Exception;

    void onExit();

}
