package com.wfe.terrain;

import java.awt.image.BufferedImage;

import com.wfe.graph.Texture;
import com.wfe.graph.TextureLoader;
import com.wfe.utils.Color;
import com.wfe.utils.ImageUtils;

public class TerrainGenerator {
	
	private static final float MAX_PIXEL_COLOR = 256 * 256 * 256;
	
	private BufferedImage heightMap;
	private BufferedImage blendMap;
	
	public TerrainGenerator() {
		heightMap = HeightMapGenerator.generatePerlinIsland(5, 0.1f);
		blendMap = HeightMapGenerator.generatePerlin(5, 0.1f);
		BufferedImage groundMap = HeightMapGenerator.generatePerlin(7, 0.8f);
		
		for(int x = 0; x < 256; x++) {
			for(int y = 0; y < 256; y++) {
				float height = groundMap.getRGB(x, y);
		    	height += MAX_PIXEL_COLOR/2f;
		    	height /= MAX_PIXEL_COLOR/2;
				if(height > 0.65f) {
					groundMap.setRGB(x, y, new Color(255, 0, 0).toHex());
				} else {
					groundMap.setRGB(x, y, 0);
				}
			}
		}
		
		for(int x = 0; x < 256; x++) {
			for(int y = 0; y < 256; y++) {
				int color = groundMap.getRGB(x, y);
				if(color == -65536) {
					blendMap.setRGB(x, y, new Color(255, 0, 0).toHex());
				} else {
					blendMap.setRGB(x, y, new Color(0, 255, 0).toHex());
				}
			}
		}
		
		for(int x = 0; x < 256; x++) {
			for(int y = 0; y < 256; y++) {
				float height = heightMap.getRGB(x, y);
		    	height += MAX_PIXEL_COLOR/2f;
		    	height /= MAX_PIXEL_COLOR/2;
				if(height < 0.06f) {
					blendMap.setRGB(x, y, 0);
				}
			}
		}
		
		blendMap = ImageUtils.blur(blendMap);
	}

	public BufferedImage getHeightMap() {
		return heightMap;
	}

	public BufferedImage getBlendMap() {
		return blendMap;
	}

	public Texture getBlendMapTexture() {
		return TextureLoader.load(blendMap);
	}
	
}
