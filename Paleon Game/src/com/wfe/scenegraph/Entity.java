package com.wfe.scenegraph;

import java.util.ArrayList;
import java.util.List;

import com.wfe.behaviours.Behaviour;
import com.wfe.components.Component;
import com.wfe.components.Material;
import com.wfe.components.Model;
import com.wfe.graph.transform.Transform;
import com.wfe.math.Vector3f;
import com.wfe.utils.ReflectionUtils;

/**
 * Created by Rick on 06.10.2016.
 */
public class Entity {

    public Vector3f position;
    public final Vector3f rotation;
    public final Vector3f scale;
    
    public final Vector3f localPosition;
    public final Vector3f localRotation;
    public final Vector3f localScale;

    private final World world;

    private Transform transform;

    private Entity parent;

    public String name;

    private boolean active = true;

    private final List<Component> components;
    private final List<Behaviour> behaviours;
    private final List<Entity> children;

    public int textureIndex = 0;
    
    private float furthestPoint = 0;

    public Entity(World world) {
        this(world, "");
    }
    
    public Entity(World world, String name) {
        this.world = world;
        this.world.addEntity(this);

        this.name = name;

        components = new ArrayList<>();
        behaviours = new ArrayList<>();
        children = new ArrayList<>();

        position = new Vector3f();
        rotation = new Vector3f();
        scale = new Vector3f(1, 1, 1);
        
        localPosition = new Vector3f();
        localRotation = new Vector3f();
        localScale = new Vector3f(1, 1, 1);
    }

    public World getWorld() {
        return world;
    }

    public void setTransform(Transform transform) {
        this.transform = transform;
        this.transform.setParent(this);
        this.world.addTransform(transform);
    }

    public Transform getTransform() {
        return transform;
    }

    public Entity getParent() {
        return parent;
    }

    private void setParent(Entity parent) {
        this.parent = parent;
    }

    public boolean hasParent() {
        return parent != null ? true : false;
    }

    @SuppressWarnings("unchecked")
    public <T extends Component> T getComponent(Class<T> type)
    {
        for (Component component : components)
            if (ReflectionUtils.isInstanceOf(type, component))
                return (T) component;

        return null;
    }

    public void addComponent(Component component) {
    	if(component.type.equals(Component.Type.MODEL)) {
    		Model model = (Model) component;
    		furthestPoint = model.mesh.getFurthestPoint();
    	}
    	
        component.setParent(this);
        this.components.add(component);
        this.world.addComponent(component);
    }

    public void removeComponent(Component component) {
        this.components.remove(component);
        this.world.removeComponent(component);
    }

    public List<Component> getComponents() {
        return components;
    }

    @SuppressWarnings("unchecked")
    public <T extends Behaviour> T getBehaviour(Class<T> type)
    {
        for (Behaviour behaviour : behaviours)
            if (ReflectionUtils.isInstanceOf(type, behaviour))
                return (T) behaviour;

        return null;
    }

    public void addBehaviour(Behaviour behaviour) {
        behaviour.setParent(this);
        this.behaviours.add(behaviour);
        this.world.addBehaviour(behaviour);
    }

    public void removeBehaviour(Behaviour behaviour) {
        this.behaviours.remove(behaviour);
        this.world.removeBehaviour(behaviour);
    }

    public List<Behaviour> getBehaviours() {
        return behaviours;
    }

    public void remove() {
        for(Component component : components) {
            this.world.removeComponent(component);
        }

        if(transform != null)
            this.world.removeTransform(transform);

        for(Behaviour behaviour : behaviours) {
            this.world.removeBehaviour(behaviour);
        }

        for(Entity child : children) {
            child.remove();
        }

        this.world.removeEntity(this);
    }

    public void addChild(Entity child) {
        child.setParent(this);
        this.children.add(child);
    }

    public void removeChild(Entity child) {
        child.remove();
        this.children.remove(child);
        this.world.removeEntity(child);
    }

    public Entity getChildByName(String name) {
        for(Entity child : children) {
            if(child.name.equals(name)) {
                return child;
            }
        }
        return null;
    }

    public float getTextureXOffset(){
        Material material = getComponent(Material.class);

        int column = textureIndex % material.getNumberOfRows();
        return (float) column / (float) material.getNumberOfRows();
    }

    public float getTextureYOffset(){
        Material material = getComponent(Material.class);

        int row = textureIndex / material.getNumberOfRows();
        return (float) row / (float)material.getNumberOfRows();
    }

    public boolean isActive() {
    	return active;
    }
    
    public void activate() {
    	this.active = true;
    	
    	for(Component component : components) {
    		component.active = true;
    	}
    	
    	for(Behaviour behaviour : behaviours) {
    		behaviour.active = true;
    	}
    	
    	transform.active = true;
    }

    public void deactivate() {
    	this.active = false;
    	
    	for(Component component : components) {
    		component.active = false;
    	}
    	
    	for(Behaviour behaviour : behaviours) {
    		behaviour.active = false;
    	}
    	
    	transform.active = false;
    }
    
    public float getFurthestPoint() {
		return furthestPoint * Math.max(Math.max(scale.x, scale.y), scale.z);
	}
    
}
