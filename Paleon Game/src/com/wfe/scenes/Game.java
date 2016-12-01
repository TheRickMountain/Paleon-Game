package com.wfe.scenes;

import com.wfe.components.Text;
import com.wfe.core.IScene;
import com.wfe.core.ResourceManager;
import com.wfe.entities.Birch;
import com.wfe.entities.Flint;
import com.wfe.entities.Grass;
import com.wfe.entities.Player;
import com.wfe.entities.Shroom;
import com.wfe.graph.Camera;
import com.wfe.graph.render.GUIRenderer;
import com.wfe.graph.transform.Transform2D;
import com.wfe.graph.water.WaterTile;
import com.wfe.inventorySystem.InventoryBh;
import com.wfe.math.Vector3f;
import com.wfe.scenegraph.Entity;
import com.wfe.scenegraph.World;
import com.wfe.terrain.Terrain;
import com.wfe.terrain.TerrainGenerator;
import com.wfe.terrain.TexturePack;
import com.wfe.utils.CellInfo;
import com.wfe.utils.Color;
import com.wfe.utils.GameTime;

public class Game implements IScene {
	
	private World world;
	
	public static State state = Game.State.GAME;
	
	public enum State {
		GAME,
		GUI
	}
	
	@Override
	public void loadResources() {
		ResourceManager.loadTexture("gui/slot", "ui_slot");
		
		ResourceManager.loadTexture("gui/icons/flint", "ui_flint");
		ResourceManager.loadTexture("gui/icons/log wall", "ui_log wall");
		ResourceManager.loadTexture("gui/icons/apple", "ui_apple");
		ResourceManager.loadTexture("gui/icons/shroom", "ui_shroom");
		
		ResourceManager.loadColliderMesh("box", "box");
		ResourceManager.loadColliderMesh("wall", "wall");
		
		ResourceManager.loadTexture("rock", "rock");
		
		ResourceManager.loadTexture("clay", "clay");
		ResourceManager.loadMesh("wall", "wall");
		ResourceManager.loadMesh("corner_wall", "corner_wall");
		
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

		ResourceManager.loadTexture("models/settler/eyes", "eyes");
		ResourceManager.loadMesh("models/settler/eyes", "eyes");
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
        
        /*** Rock ***/
        ResourceManager.loadMesh("models/rock/rock_1", "rock"); 
        /*** *** ***/
        
        /*** Grass ***/
        ResourceManager.loadTexture("models/grass/diffuse", "diffuse");
        ResourceManager.loadMesh("models/grass/grass", "grass");
        /*** *** ***/
        
        /*** Desert House ***/
        ResourceManager.loadTexture("models/desertHouse/desertHouse", "desertHouse");
        ResourceManager.loadMesh("models/desertHouse/desertHouse", "desertHouse");
        /*** *** ***/
        
        /*** Palm ***/
        ResourceManager.loadTexture("models/palm/palm", "palm");
        ResourceManager.loadMesh("models/palm/palm", "palm");
        /*** *** ***/
        
        /*** Well ***/
        ResourceManager.loadTexture("models/well/well", "well");
        ResourceManager.loadMesh("models/well/well", "well");
        /*** *** ***/
        
        /*** Flint ***/
        ResourceManager.loadMesh("models/flint/flint", "flint");
        ResourceManager.loadTexture("models/flint/flint", "flint");
        /*** *** ***/
        
        /*** Flint ***/
        ResourceManager.loadMesh("models/shroom/shroom_1", "shroom");
        ResourceManager.loadTexture("models/shroom/shroom", "shroom");
        /*** *** ***/
	}

	@Override
	public void init() throws Exception {
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
                float height = world.getTerrainHeight((x * 3) + 1.5f, (z * 3) + 1.5f);

                int cellState = 0;

                if(height < 3.9f)
                    cellState = 1;
                
                world.cells.put(x + " " + z, 
                		new CellInfo(new Vector3f(x * 3 + 1.5f, height, z * 3 + 1.5f), cellState));
            }
        }
        /*** *** ***/
		
		for(int i = 60; i < 840; i+= 120) {
			for(int j = 60; j < 840; j+= 120) {
				world.addWaterTile(new WaterTile(j, i));
			}
		}
		
		Entity inventory = new Entity(world, "Inventory");
	    inventory.addBehaviour(new InventoryBh());
		
        Entity text = new Entity(world, "Text");
        text.addComponent(new Text("Winter Fox Engine alpha 0.3 Unstable", GUIRenderer.primitiveFont, 1f, Color.RED));
        text.setTransform(new Transform2D());
        
        Grass grass = new Grass(world);
        grass.position.set(400, world.getTerrainHeight(400, 400), 400);
        grass.textureIndex = 2;
        
        Grass grass2 = new Grass(world);
        grass2.position.set(400, world.getTerrainHeight(400, 400), 400);
        grass2.textureIndex = 0;
        grass2.rotation.y = 45;   
        
        Player player = new Player(world, camera);
	    player.rotation.y = 180; 
        
        //Birch birch1 = new Birch(world, new Vector3f(384, world.getTerrainHeight(384, 384) - 1f, 384));
        Birch birch2 = new Birch(world, new Vector3f(400, world.getTerrainHeight(400, 384), 384));
        //Birch birch3 = new Birch(world, new Vector3f(384, world.getTerrainHeight(384, 400) - 1f, 400));
        
        Flint flint = new Flint(world);
        flint.position.set(384, world.getTerrainHeight(384, 384), 384);
        
        Shroom shroom = new Shroom(world);
        shroom.position.set(384, world.getTerrainHeight(384, 400), 400);
        
        GameTime.setTime(12, 00);
        
        world.init();
	}

	@Override
	public void update(float dt) throws Exception {
		world.update(dt);
		world.render();
	}

	@Override
	public void cleanup() {
		world.cleanup();
	}

}
