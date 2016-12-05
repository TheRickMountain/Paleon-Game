package com.wfe.core;

import org.lwjgl.opengl.GL11;

import com.wfe.core.input.Keyboard;
import com.wfe.core.input.Mouse;
import com.wfe.core.stateMachine.StateMachine;
import com.wfe.scenes.GameState;
import com.wfe.scenes.MenuState;

public class Paleon implements Runnable {

	private final Thread gameLoopThread;
	
    public static Display display;
    
    private final StateMachine gGameMode;

    public Paleon() {
        gameLoopThread = new Thread(this, "GAME_LOOP_THREAD");
        display = new Display("Winter Fox Engine", 1152, 648, false);
        /*Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        display = new Display("Winter Fox Engine", (int)screenSize.getWidth(), (int)screenSize.getHeight(), true);*/
        gGameMode = new StateMachine();
    }

    public void start() {
        String osName = System.getProperty("os.name");
        if (osName.contains("Mac")) {
            gameLoopThread.run();
        } else {
            gameLoopThread.start();
        }
    }

    @Override
    public void run() {
        try {
            init();
            gameLoop();
        } catch (Exception excp) {
            excp.printStackTrace();
        } finally {
            dispose();
        }
    }

    protected void init() throws Exception {
        display.init();
        initScenes();
    }

    public void initScenes() throws Exception {
        gGameMode.add("menu", new MenuState(gGameMode));
        gGameMode.add("game", new GameState(gGameMode));
        gGameMode.change("menu");
    }

    public void gameLoop() throws Exception {
        while (!display.isCloseRequested()) {
            Keyboard.startEventFrame();
            Mouse.startEventFrame();

            float deltaTime = Timer.getDeltaTime();

            if (deltaTime >= 1) {
                deltaTime = 0;
            }

            update(deltaTime);

            Keyboard.clearEventFrame();
            Mouse.clearEventFrame();

            if (display.wasResized()) {
                GL11.glViewport(0, 0, display.getWidth(), display.getHeight());
                display.setResized(false);
            }
        }
    }

    protected void update(float deltaTime) throws Exception {
        display.pollEvents();
        gGameMode.update(deltaTime);
        display.swapBuffers();
        Timer.update();
    }

    public void dispose() {
        try {
            gGameMode.change(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        display.destroy();
    }
    
    public static void main(String[] args) {
		new Paleon().start();
	}
	
}
