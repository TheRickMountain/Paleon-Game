package com.wfe.core;

public interface IScene {

    void loadResources();

    void init() throws Exception;

    void update(float dt) throws Exception;

    void cleanup();

}
