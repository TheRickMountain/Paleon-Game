package com.wfe.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rick on 06.10.2016.
 */
public class SceneManager implements IScene {

    private static Map<String, IScene> scenes;

    private static IScene currentScene;

    public SceneManager() {
        scenes = new HashMap<String, IScene>();
        currentScene = new Scene();
        scenes.put(null, currentScene);
    }

    public static void add(String name, IScene scene) {
        scenes.put(name, scene);
    }

    public static void change(String name) throws Exception{
        currentScene.cleanup();
        currentScene = scenes.get(name);
        currentScene.loadResources();
        currentScene.init();
    }

    @Override
    public void loadResources() {
        currentScene.loadResources();
    }

    @Override
    public void init() throws Exception {
        currentScene.init();
    }

    @Override
    public void update(float dt) throws Exception {
        currentScene.update(dt);
    }

    @Override
    public void cleanup() {
        currentScene.cleanup();
    }

}
