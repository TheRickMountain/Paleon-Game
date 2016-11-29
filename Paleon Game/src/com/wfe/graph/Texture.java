package com.wfe.graph;

import java.awt.image.BufferedImage;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public class Texture {

	private int id;
	private int width, height;
	private BufferedImage image;
	
	public Texture(int id) {
		this(id, 0, 0, null);
	}
	
	public Texture(int id, int width, int height, BufferedImage buffer) {
		this.id = id;
		this.width = width;
		this.height = height;
		this.image = buffer;
	}
	
	public Texture(int id, int width, int height) {
		this(id, width, height, null);
	}
	
	public int getID() {
		return id;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public BufferedImage getBufferedImage() {
		return image;
	}
	
	public void bind(int unit) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + unit);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
	}

	public void unbind() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}
	
	public void cleanup() {
		GL11.glDeleteTextures(id);
	}
	
}
