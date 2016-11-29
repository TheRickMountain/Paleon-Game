package com.wfe.scenes;

import com.wfe.core.IScene;
import com.wfe.core.SceneManager;

public class Menu implements IScene {

	@Override
	public void loadResources() {
		
	}

	@Override
	public void init() throws Exception {
		
	}

	@Override
	public void update(float dt) throws Exception {
		SceneManager.change("Game");
	}

	@Override
	public void cleanup() {
		
	}

}
