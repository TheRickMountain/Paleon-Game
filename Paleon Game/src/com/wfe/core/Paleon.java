package com.wfe.core;

import org.lwjgl.glfw.GLFW;
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
    
    private boolean running;
    
    private float frameTime = 1.0f / 60.0f;
    
    public static double lastTime;

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
        gGameMode.add("menu", new MenuState());
        gGameMode.add("game", new GameState());
        gGameMode.change("menu");
    }
    
    public void stop() {
        if (running)
            running = false;
    }

    public void gameLoop() throws Exception {
    	running = true;
    	
    	lastTime = GLFW.glfwGetTime();
    	double unprocessedTime = 0;
    	
    	while(running) {
    		boolean render = false;
    		
    		double startTime = GLFW.glfwGetTime();
    		double passedTime = startTime - lastTime;
    		lastTime = startTime;
    		unprocessedTime += passedTime;
    		
    		while(unprocessedTime > frameTime) {
    			if(display.isCloseRequested())
    				stop();
    			
    			Keyboard.startEventFrame();
                Mouse.startEventFrame();
    			
                gGameMode.changeState(gGameMode);
    			gGameMode.update(frameTime);
    			
    			Keyboard.clearEventFrame();
    			Mouse.clearEventFrame();
    			
    			unprocessedTime -= frameTime;
    			
    			render = true;
    		}
    		
    		if(render)
    			render();
    	}
    	
        /*while (!display.isCloseRequested()) {
        	
        	Keyboard.startEventFrame();
            Mouse.startEventFrame();
            
            gGameMode.changeState(gGameMode);
            gGameMode.update(frameTime);
            
            Keyboard.clearEventFrame();
            Mouse.clearEventFrame();
            
            render();

            if (display.wasResized()) {
                GL11.glViewport(0, 0, display.getWidth(), display.getHeight());
                display.setResized(false);
            }
        }*/
    	
    	if (display.wasResized()) {
            GL11.glViewport(0, 0, display.getWidth(), display.getHeight());
            display.setResized(false);
        }
    }

    protected void update(float deltaTime) throws Exception {
        gGameMode.update(deltaTime);
    }

    protected void render() throws Exception {
    	gGameMode.render();
    	display.pollEvents();
        display.swapBuffers();
        //calculateDelta();
    }
    
    public void dispose() {
        try {
            gGameMode.change(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        display.destroy();
    }
    
    /*private double getTime() {
        return System.nanoTime() / 1_000_000.0;
    }
    
    private void calculateDelta() {
		double time = getTime();
		double difference = time - lastFrame;
		float value = ((float) difference) / DELTA_FACTOR;
		delta = updateRollingAverage(value);
		lastFrame = time;

	}
    
    private static float updateRollingAverage(float value) {
		previousTimes.add(0, value);
		if (previousTimes.size() > ROLLING_AVERAGE_LENGTH) {
			previousTimes.remove(ROLLING_AVERAGE_LENGTH);
		}
		if (previousTimes.size() < ROLLING_AVERAGE_LENGTH) {
			return value;
		}
		return Utils.getAverageOfList(previousTimes);
	}
    */
    public static void main(String[] args) {
		new Paleon().start();
	}
	
}
