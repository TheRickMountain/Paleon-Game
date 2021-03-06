package com.wfe.scenes;

import java.util.Random;

import com.wfe.core.ResourceManager;
import com.wfe.core.stateMachine.IState;
import com.wfe.core.stateMachine.StateMachine;
import com.wfe.entities.Birch;
import com.wfe.entities.Flint;
import com.wfe.entities.Furnace;
import com.wfe.entities.Grass;
import com.wfe.entities.Player;
import com.wfe.entities.Shroom;
import com.wfe.entities.Stone;
import com.wfe.graph.Camera;
import com.wfe.graph.water.WaterTile;
import com.wfe.gui.GUI;
import com.wfe.math.Vector3f;
import com.wfe.scenegraph.World;
import com.wfe.terrain.Terrain;
import com.wfe.terrain.TerrainGenerator;
import com.wfe.terrain.TexturePack;
import com.wfe.utils.CellInfo;
import com.wfe.utils.GameTime;

public class GameState implements IState {
	
	private World world;
	
	public static State state = GameState.State.GAME;
	
	public static GUI gui;
	
	public enum State {
		GAME,
		GUI
	}
	
	@Override
	public void loadResources() {
		ResourceManager.loadTexture("gui/slot", "ui_slot");
		
		ResourceManager.loadTexture("models/helmet/helmet", "helmet");
		ResourceManager.loadMesh("models/helmet/helmet", "helmet");
		
		ResourceManager.loadTexture("models/axe/axe", "axe");
		ResourceManager.loadMesh("models/axe/axe", "axe");
		ResourceManager.loadMesh("models/hummer/hummer", "hummer");
		
		ResourceManager.loadTexture("gui/bar/health", "ui_health");
		ResourceManager.loadTexture("gui/bar/hunger", "ui_hunger");
		
		ResourceManager.loadColliderMesh("box", "box");
		ResourceManager.loadColliderMesh("wall", "wall");
		
		ResourceManager.loadTexture("rock", "rock");
		ResourceManager.loadMesh("models/rock/stone", "rock");
		
		ResourceManager.loadTexture("clay", "clay");
		ResourceManager.loadMesh("wall", "wall");
		
		/*** Furnace ***/
		ResourceManager.loadMesh("models/furnace/furnace", "furnace");
		ResourceManager.loadTexture("models/furnace/furnace", "furnace");
		/*** *** ***/
		
		/*** Terrain Textures ***/
		ResourceManager.loadTexture("terrain/dry_grass", "dry_grass");
		ResourceManager.loadTexture("terrain/dry_ground", "dry_ground");
		ResourceManager.loadTexture("terrain/ground", "ground");
		ResourceManager.loadTexture("terrain/sand", "sand");
		ResourceManager.loadTexture("terrain/grass", "fresh_grass");
		/*** *** ***/
		
		/*** Water Textures ***/
		ResourceManager.loadTexture("water/dudvMap", "dudvMap");
		ResourceManager.loadTexture("water/normalMap", "normalMap");
		
		/*** Settler ***/
		ResourceManager.loadTexture("models/settler/skin", "skin");

		ResourceManager.loadMesh("models/settler/body", "body");
		ResourceManager.loadMesh("models/settler/head", "head");
		ResourceManager.loadMesh("models/settler/leftArm", "rightArm");
		ResourceManager.loadMesh("models/settler/rightArm", "leftArm");
		ResourceManager.loadMesh("models/settler/leftForearm", "rightForearm");
		ResourceManager.loadMesh("models/settler/rightForearm", "leftForearm");
		ResourceManager.loadMesh("models/settler/hip", "hip");
		ResourceManager.loadMesh("models/settler/shin", "shin");
		/*** *** ***/
		
		/*** Conifer ***/
    	ResourceManager.loadTexture("models/conifer/trunk", "conifer_trunk");
        ResourceManager.loadMesh("models/conifer/trunk", "conifer_trunk");
        
        ResourceManager.loadTexture("models/conifer/leaves", "conifer_leaves");
        ResourceManager.loadMesh("models/conifer/leaves", "conifer_leaves");
        /*** *** ***/
        
        /*** Birch ***/
    	ResourceManager.loadTexture("models/birch/trunk", "birch_trunk");
        ResourceManager.loadMesh("models/birch/trunk", "birch_trunk");
        
        ResourceManager.loadTexture("models/birch/leaves", "birch_leaves");
        ResourceManager.loadMesh("models/birch/leaves", "birch_leaves");
        /*** *** ***/
        
        /*** Grass ***/
        ResourceManager.loadTexture("models/grass/grass", "diffuse");
        ResourceManager.loadMesh("models/grass/grass", "grass");
        /*** *** ***/
        
        /*** Flint ***/
        ResourceManager.loadMesh("models/flint/flint", "flint");
        ResourceManager.loadTexture("models/flint/flint", "flint");
        /*** *** ***/
        
        /*** Shroom ***/
        ResourceManager.loadMesh("models/shroom/shroom_1", "shroom");
        ResourceManager.loadTexture("models/shroom/shroom", "shroom");
        /*** *** ***/
	}

	@Override
	public void onEnter() throws Exception {
		Camera camera = new Camera(new Vector3f(0, 0, 0));
		world = new World(camera);
		
		/*** Terrain ***/
		TerrainGenerator generator = new TerrainGenerator();
		TexturePack texturePack = new TexturePack(
        		generator.getHeightMap(),
                generator.getBlendMapTexture(),
                ResourceManager.getTexture("dry_grass"),
                ResourceManager.getTexture("fresh_grass"),
                ResourceManager.getTexture("sand"),
                ResourceManager.getTexture("sand"));

        Terrain terrain = new Terrain(0, 0, texturePack);
        world.addTerrain(terrain);
        
        for(int x = 0; x < 256; x++) {
            for(int z = 0; z < 256; z++) {
                float height = world.getTerrainHeight((x * 4), (z * 4));

                int cellState = 0;

                if(height < 3.9f)
                    cellState = 1;
                
                world.cells.put(x + " " + z, 
                		new CellInfo(new Vector3f(x * 4, height, z * 4), cellState));
            }
        }
        /*** *** ***/
		
		for(int i = (int) WaterTile.TILE_SIZE; i < (int)(WaterTile.TILE_SIZE * 14); i+= (WaterTile.TILE_SIZE * 2)) {
			for(int j = (int) WaterTile.TILE_SIZE; j < (int)(WaterTile.TILE_SIZE * 14); j+= (WaterTile.TILE_SIZE * 2)) {
				world.addWaterTile(new WaterTile(j, i));
			}
		}
        
        Player player = new Player(world, camera);
        
        gui = new GUI(world, player);
        
        Random rand = new Random();
        
        for(int i = 0; i < 100; i++) {
        	float x = rand.nextFloat() * 768;
        	float z = rand.nextFloat() * 768;
        	float y = world.getTerrainHeight(x, z);
        	if(y >= 3)
        		new Birch(world, new Vector3f(x, y, z));
        }
        
        for(int i = 0; i < 50; i++) {
        	float x = rand.nextFloat() * 768;
        	float z = rand.nextFloat() * 768;
        	float y = world.getTerrainHeight(x, z);
        	if(y >= 3) {
        		Grass grass = new Grass(world);
        		grass.position.set(x, y, z);
        		grass.textureIndex = rand.nextInt(3);
        		grass.rotation.y = rand.nextFloat() * 360;
        	}	
        }
        
        Flint flint = new Flint(world);
        flint.position.set(world.cells.get("128 128").position);
        
        Shroom shroom = new Shroom(world);
        shroom.position.set(world.cells.get("128 129").position);
       
        GameTime.setTime(21, 00);
        
        Stone stone1 = new Stone(world);
        stone1.position.set(world.cells.get("129 129").position);
        
        Stone stone2 = new Stone(world);
        stone2.position.set(world.cells.get("129 130").position);
        
        Stone stone3 = new Stone(world);
        stone3.position.set(world.cells.get("130 129").position);
        
        new Furnace(world, world.cells.get("130 130").position);
        
        world.init();
	}
	
	@Override
	public void changeState(StateMachine gameMode) throws Exception {
		
	}
	
	@Override
	public void update(float dt) throws Exception {
		world.update(dt);
		gui.update(dt);
	}
	
	@Override
	public void render() throws Exception {
		world.render(gui);
	}

	@Override
	public void onExit() {
		world.cleanup();
	}

}
