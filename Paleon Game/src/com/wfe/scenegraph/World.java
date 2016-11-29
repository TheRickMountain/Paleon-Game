package com.wfe.scenegraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import com.wfe.behaviours.Behaviour;
import com.wfe.components.Collider;
import com.wfe.components.Component;
import com.wfe.components.Image;
import com.wfe.components.Model;
import com.wfe.components.Text;
import com.wfe.core.ResourceManager;
import com.wfe.graph.Camera;
import com.wfe.graph.Mesh;
import com.wfe.graph.render.GUIRenderer;
import com.wfe.graph.render.MeshRenderer;
import com.wfe.graph.render.SkyboxRenderer;
import com.wfe.graph.render.TerrainRenderer;
import com.wfe.graph.render.WaterRenderer;
import com.wfe.graph.transform.Transform;
import com.wfe.graph.water.WaterFrameBuffers;
import com.wfe.graph.water.WaterTile;
import com.wfe.input.Key;
import com.wfe.input.Keyboard;
import com.wfe.math.Vector4f;
import com.wfe.physics.CollisionPacket;
import com.wfe.terrain.Terrain;
import com.wfe.terrain.TerrainBlock;
import com.wfe.utils.CellInfo;
import com.wfe.utils.Color;
import com.wfe.utils.GameTime;
import com.wfe.utils.MathUtils;
import com.wfe.utils.MousePicker;
import com.wfe.utils.OpenglUtils;
import com.wfe.weather.Weather;

/**
 * Created by Rick on 06.10.2016.
 */
public class World {

	public final WaterFrameBuffers fbos;
    private final MeshRenderer meshRenderer;
    private final TerrainRenderer terrainRenderer;
    private final SkyboxRenderer skyboxRenderer;
    private final WaterRenderer waterRenderer;
    private final GUIRenderer guiRenderer;

    public Map<Terrain, List<TerrainBlock>> terrains = new HashMap<Terrain, List<TerrainBlock>>();

    private static final int WORLD_TERRAIN_WIDTH = 3;
    private static final int WORLD_TILE_WIDTH = WORLD_TERRAIN_WIDTH * Terrain.TERRAIN_WIDTH_BLOCKS;

    private final TerrainBlock[][] terrainGrid;

    public final List<Entity> entities = new ArrayList<>();
    private final List<Component> guis = new ArrayList<>();
    private final Map<Mesh, List<Entity>> meshes = new HashMap<>();
    private final List<WaterTile> waters = new ArrayList<>();

    private final List<Behaviour> behaviours = new ArrayList<>();
    private final List<Behaviour> behavioursToAdd = new ArrayList<>();
    private final List<Behaviour> behavioursToRemove = new ArrayList<>();

    private final List<Transform> transforms = new ArrayList<>();
    private final List<Transform> transformsToAdd = new ArrayList<>();
    private final List<Transform> transformsToRemove = new ArrayList<>();
    
    private final List<Collider> colliders = new ArrayList<>();
    
    private final Weather weather;

    public final Map<String, CellInfo> cells = new HashMap<>();

    private Camera camera;

    private boolean wireframeMode = false;
    public boolean onGuiLayer = false;
    
    private Vector4f reflectionClipPlane = new Vector4f(0, 1, 0, -WaterTile.HEIGHT + 1f);
    private Vector4f refractionClipPlane = new Vector4f(0, -1, 0, WaterTile.HEIGHT);
    private Vector4f normalClipPlane = new Vector4f(0, -1, 0, 15);
    
    private Color fogColor;

    public World(Camera camera) throws Exception {
    	OpenglUtils.depthTest(true);
        OpenglUtils.cullFace(true);
    	
        this.camera = camera;

        fbos = new WaterFrameBuffers();
        meshRenderer = new MeshRenderer(camera);
        terrainRenderer = new TerrainRenderer(camera);
        skyboxRenderer = new SkyboxRenderer(camera);
        waterRenderer = new WaterRenderer(camera, fbos);
        guiRenderer = new GUIRenderer();      
        
        terrainGrid = new TerrainBlock[WORLD_TILE_WIDTH][WORLD_TILE_WIDTH];

        MousePicker.setUpMousePicker(this, camera);
    
        fogColor = new Color(1.0f, 1.0f, 1.0f, 1.0f);
        
        weather = new Weather();
        
        GameTime.init();
    }

    public void update(float dt) {
    	GameTime.update();
    	weather.updateWeather(GameTime.getATime());
    	fogColor = weather.getFogColor();
    	
    	MousePicker.update();
    	
        camera.update();
        camera.rotate(dt);
        
        skyboxRenderer.update(dt);
        waterRenderer.update(dt);

        for(Behaviour bh : behaviours) {
        	if(bh.active)
        		bh.update(dt);
        }

        if(!behavioursToAdd.isEmpty()) {
            for(Behaviour bh : behavioursToAdd) {
                behaviours.add(bh);
            }
            behavioursToAdd.clear();
        }

        if(!behavioursToRemove.isEmpty()) {
            for(Behaviour bh : behavioursToRemove) {
                behaviours.remove(bh);
            }
            behavioursToRemove.clear();
        }

        for(Transform tr : transforms) {
        	if(tr.active)
        		tr.update();
        }

        if(!transformsToAdd.isEmpty()) {
            for(Transform tr : transformsToAdd) {
                transforms.add(tr);
            }
            transformsToAdd.clear();
        }

        if(!transformsToRemove.isEmpty()) {
            for(Transform tr : transformsToRemove) {
                transforms.remove(tr);
            }
            transformsToRemove.clear();
        }
        
        if(Keyboard.isKeyDown(Key.F5)) {
            wireframeMode = !wireframeMode;
            OpenglUtils.wireframeMode(wireframeMode);
        }
    }
    
    public void clear() {
    	GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    public void render() {
    
    	GL11.glEnable(GL30.GL_CLIP_DISTANCE0);
    	
        fbos.bindReflectionFrameBuffer();
        float distance = 2 * (camera.getPosition().y - WaterTile.HEIGHT);
        camera.getPosition().y -= distance;
        camera.invertPitch();
        clear();
        camera.updateViewMatrix();
        meshRenderer.render(meshes, weather.sun, camera, fogColor, reflectionClipPlane);
        terrainRenderer.render(terrains, weather.sun, camera, fogColor, reflectionClipPlane);
        skyboxRenderer.render(camera, fogColor);
        camera.getPosition().y += distance;
        camera.invertPitch();
        
        fbos.bindRefractionFrameBuffer();
        clear();
        camera.updateViewMatrix();
        meshRenderer.render(meshes, weather.sun, camera, fogColor, refractionClipPlane);
        terrainRenderer.render(terrains, weather.sun, camera, fogColor, refractionClipPlane);
        skyboxRenderer.render(camera, fogColor);
        
        GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
        fbos.unbindCurrentFrameBuffer();
        
        clear();
        meshRenderer.render(meshes, weather.sun, camera, fogColor, normalClipPlane);
        terrainRenderer.render(terrains, weather.sun, camera, fogColor, normalClipPlane);
        skyboxRenderer.render(camera, fogColor);
        waterRenderer.render(waters, weather.sun, fogColor);
        guiRenderer.render(guis);
        
        GUIRenderer.startRender();
        
        for(Behaviour bh : behaviours)
        	if(bh.active)
        		bh.onGUI();
        
        GUIRenderer.endRender();
        
    }

    public void addTerrain(Terrain terrain){
        if(terrain.getGridX() > WORLD_TERRAIN_WIDTH || terrain.getGridZ() > WORLD_TERRAIN_WIDTH) {
            System.err.println("World not large enough to add terrain at " + terrain.getGridX());
            return;
        }

        List<TerrainBlock> terrainBlocks = new ArrayList<>();
        for(TerrainBlock terrainBlock : terrain.getTerrainBlocks()){
            int index = terrainBlock.getIndex();
            int gridX = (index % Terrain.TERRAIN_WIDTH_BLOCKS)
                    + (terrain.getGridX() * Terrain.TERRAIN_WIDTH_BLOCKS);
            int gridZ = (int) (Math.floor(index / Terrain.TERRAIN_WIDTH_BLOCKS) + (terrain
                    .getGridZ() * Terrain.TERRAIN_WIDTH_BLOCKS));
            terrainGrid[gridX][gridZ] = terrainBlock;
            terrainBlocks.add(terrainBlock);
        }
        terrains.put(terrain, terrainBlocks);
    }
    
    public void addWaterTile(WaterTile tile) {
    	waters.add(tile);
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    public Entity getEntityByName(String name) {
        for (Entity e:
                entities) {
            if(e.name.equals(name)) return e;
        }
        return null;
    }

    protected void addComponent(Component component) {
        if(component.type.equals(Component.Type.MODEL)) {
            Model model = (Model) component;
            Mesh mesh = model.mesh;
            List<Entity> batch = meshes.get(mesh);
            if(batch != null) {
                batch.add(model.parent);
            } else {
                List<Entity> newBatch = new ArrayList<Entity>();
                newBatch.add(model.parent);
                meshes.put(mesh, newBatch);
            }
        } else if(component.type.equals(Component.Type.IMAGE)) {
            Image image = (Image) component;
            guis.add(image);
        } else if(component.type.equals(Component.Type.TEXT)) {
            Text text = (Text) component;
            guis.add(text);
        } else if(component.type.equals(Component.Type.COLLIDER)) {
        	Collider collider = (Collider) component;
        	colliders.add(collider);
        }
    }

    protected void removeComponent(Component component) {
        if(component.type.equals(Component.Type.MODEL)) {
            Model model = (Model) component;
            Mesh mesh = model.mesh;
            List<Entity> batch = meshes.get(mesh);
            batch.remove(model.parent);
            if(batch.isEmpty()) {
                meshes.remove(mesh);
            }
        } else if(component.type.equals(Component.Type.IMAGE)) {
            Image image = (Image) component;
            guis.remove(image);
        } else if(component.type.equals(Component.Type.TEXT)) {
            Text text = (Text) component;
            guis.remove(text);
        } else if(component.type.equals(Component.Type.COLLIDER)) {
        	Collider collider = (Collider) component;
        	colliders.remove(collider);
        }
    }

    protected void addBehaviour(Behaviour behaviour) {
        behaviour.start();
        behavioursToAdd.add(behaviour);
    }

    protected void removeBehaviour(Behaviour behaviour) {
        behavioursToRemove.add(behaviour);
    }

    protected void addTransform(Transform transform) {
        transformsToAdd.add(transform);
    }

    protected void removeTransform(Transform transform) {
        transformsToRemove.add(transform);
    }

    public void cleanup() {
        meshRenderer.cleanup();
        terrainRenderer.cleanup();
        skyboxRenderer.cleanup();
        fbos.cleanup();
        waterRenderer.cleanup();
        guiRenderer.cleanup();
        ResourceManager.cleanup();
    }

    public float getTerrainHeight(float x, float z){
        TerrainBlock block = getTerrainForPosition(x, z);
        if(block == null){
            return 0;
        }
        float terrainX = x - (block.getX() - Terrain.BLOCK_SIZE / 2);
        float terrainZ = z - (block.getZ() - Terrain.BLOCK_SIZE / 2);
        return block.calcHeight(terrainX, terrainZ);
    }

    public TerrainBlock getTerrainForPosition(float x, float z) {
        int terrain_i = (int) Math.floor(x / Terrain.BLOCK_SIZE);
        int terrain_j = (int) Math.floor(z / Terrain.BLOCK_SIZE);
        if (terrain_i < 0 || terrain_j < 0) {
            return null;
        }
        if (terrain_i >= terrainGrid.length) {
            return null;
        } else if (terrain_j >= terrainGrid[terrain_i].length) {
            return null;
        }
        return terrainGrid[terrain_i][terrain_j];
    }
    
    public void checkCollision(CollisionPacket colPackage) {
    	for(Collider collider : colliders) {
    		if(MathUtils.getDistanceBetweenPoints(
    				collider.parent.position.x, collider.parent.position.z, 
    				colPackage.getR3Position().x, colPackage.getR3Position().z) < 10) {
    			collider.checkCollision(colPackage);
    		}
    	}
    }

}
