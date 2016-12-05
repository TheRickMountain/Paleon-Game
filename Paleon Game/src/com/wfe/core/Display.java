package com.wfe.core;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_FORWARD_COMPAT;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetScrollCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;

import com.wfe.core.input.Keyboard;
import com.wfe.core.input.Mouse;

/**
 * Created by Rick on 06.10.2016.
 */
public class Display {

    private long mWindow;
    private String mTitle = "";
    private boolean mFullscreen;
    private boolean mResized;
    private int mWidth;
    private int mHeight;

    public Display(String title, int width, int height, boolean fullscreen) {
        this.mTitle = title;
    	this.mWidth = width;
        this.mHeight = height;
        this.mFullscreen = fullscreen;
    }
        
    public void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if(!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        if(!mFullscreen) {
        	mWindow = glfwCreateWindow(mWidth, mHeight, mTitle, NULL, NULL);
        
	        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
	        glfwSetWindowPos(
	                mWindow,
	                (vidmode.width() - mWidth) / 2,
	                (vidmode.height() - mHeight) / 2);
        } else {
        	mWindow = glfwCreateWindow(mWidth, mHeight, mTitle, GLFW.glfwGetPrimaryMonitor(), NULL);
        }
        
        if ( mWindow == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        glfwMakeContextCurrent(mWindow);
        glfwSwapInterval(1);

        glfwShowWindow(mWindow);

        GL.createCapabilities();
        
        glfwSetWindowSizeCallback(mWindow, new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
                resize(width, height);
            }
        });

        glfwSetKeyCallback(mWindow, new Keyboard());
        glfwSetMouseButtonCallback(mWindow, new Mouse());
        glfwSetCursorPosCallback(mWindow, new Mouse.Cursor());
        glfwSetScrollCallback(mWindow, new Mouse.Scroll());
    }
    
    public void resize(int width, int height) {
    	this.mWidth = width;
    	this.mHeight = height;
    	setResized(true);
    }

    public boolean isCloseRequested() {
        return glfwWindowShouldClose(mWindow);
    }

    public void pollEvents() {
        glfwPollEvents();
    }

    public void swapBuffers() {
        glfwSwapBuffers(mWindow);
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public boolean wasResized() {
        return mResized;
    }

    public void setResized(boolean resized) {
        this.mResized = resized;
    }

    public long getWindow() {
        return mWindow;
    }

    public void destroy() {
        glfwFreeCallbacks(mWindow);
        glfwDestroyWindow(mWindow);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
}
