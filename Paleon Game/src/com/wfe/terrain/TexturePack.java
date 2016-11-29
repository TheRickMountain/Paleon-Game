package com.wfe.terrain;

import java.awt.image.BufferedImage;

import com.wfe.graph.Texture;

/**
 * Created by Rick on 08.10.2016.
 */
public class TexturePack {

	private final BufferedImage heightMap;
    public Texture blendMap;
    public Texture redTexture;
    public Texture greenTexture;
    public Texture blueTexture;
    public Texture alphaTexture;

    public TexturePack(BufferedImage heightMap, Texture blendMap, Texture redTexture, 
    		Texture greenTexture, Texture blueTexture, Texture alphaTexture) {
    	this.heightMap = heightMap;
        this.blendMap = blendMap;
        this.redTexture = redTexture;
        this.greenTexture = greenTexture;
        this.blueTexture = blueTexture;
        this.alphaTexture = alphaTexture;
    }

	public BufferedImage getHeightMap() {
		return heightMap;
	}
    
}
