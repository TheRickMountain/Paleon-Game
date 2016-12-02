package com.wfe.entities;

import com.wfe.behaviours.AnimBh;
import com.wfe.behaviours.PlayerBh;
import com.wfe.components.Material;
import com.wfe.components.Model;
import com.wfe.core.ResourceManager;
import com.wfe.graph.Camera;
import com.wfe.graph.transform.Transform3D;
import com.wfe.scenegraph.Entity;
import com.wfe.scenegraph.World;

public class Player extends Entity {

	public Player(World world, Camera camera) {
		super(world, "Player");
		
		Material skinMat = new Material(ResourceManager.getTexture("skin"));
		
		Material eyesMat = new Material(ResourceManager.getTexture("eyes"));
		eyesMat.useSpecular = true;
		
		addComponent(new Model(ResourceManager.getMesh("body")));
        addComponent(skinMat);
        setTransform(new Transform3D());

        Entity head = new Entity(world, "Head");
        head.addComponent(new Model(ResourceManager.getMesh("head")));
        head.addComponent(skinMat);
        head.setTransform(new Transform3D());
        head.localPosition.set(0, 0.85f, 0);
        addChild(head);

        Entity eyes = new Entity(world, "Eyes");
        eyes.addComponent(new Model(ResourceManager.getMesh("eyes")));
        eyes.addComponent(eyesMat);
        eyes.setTransform(new Transform3D());
        eyes.localPosition.set(0, -0.1f, 0);
        eyes.localRotation.set(0, 180, 0);
        addChild(eyes);

        Entity rightArm = new Entity(world, "Right Arm");
        rightArm.addComponent(new Model(ResourceManager.getMesh("rightArm")));
        rightArm.addComponent(skinMat);
        rightArm.setTransform(new Transform3D());
        rightArm.localPosition.set(0.65f, 0.6f, 0);
        addChild(rightArm);

        {
            Entity rightForearm = new Entity(world, "Right Forearm");
            rightForearm.addComponent(new Model(ResourceManager.getMesh("rightForearm")));
            rightForearm.addComponent(skinMat);
            rightForearm.setTransform(new Transform3D());
            rightForearm.localPosition.set(1.08f, -0.7f, 0);
            rightArm.addChild(rightForearm);
        }

        Entity leftArm = new Entity(world, "Left Arm");
        leftArm.addComponent(new Model(ResourceManager.getMesh("leftArm")));
        leftArm.addComponent(skinMat);
        leftArm.setTransform(new Transform3D());
        leftArm.localPosition.set(-0.65f, 0.6f, 0);
        addChild(leftArm);

        {
            Entity leftForearm = new Entity(world, "Left Forearm");
            leftForearm.addComponent(new Model(ResourceManager.getMesh("leftForearm")));
            leftForearm.addComponent(skinMat);
            leftForearm.setTransform(new Transform3D());
            leftForearm.localPosition.set(-1.08f, -0.7f, 0);
            leftArm.addChild(leftForearm);
        }

        Entity leftHip = new Entity(world, "Left Hip");
        leftHip.addComponent(new Model(ResourceManager.getMesh("hip")));
        leftHip.addComponent(skinMat);
        leftHip.setTransform(new Transform3D());
        leftHip.localPosition.set(-0.4f, -0.45f, 0);
        addChild(leftHip);

        {
            Entity leftShin = new Entity(world, "Left Shin");
            leftShin.addComponent(new Model(ResourceManager.getMesh("shin")));
            leftShin.addComponent(skinMat);
            leftShin.setTransform(new Transform3D());
            leftShin.localPosition.set(0, -0.8f, 0);
            leftHip.addChild(leftShin);
        }

        Entity rightHip = new Entity(world, "Right Hip");
        rightHip.addComponent(new Model(ResourceManager.getMesh("hip")));
        rightHip.addComponent(skinMat);
        rightHip.setTransform(new Transform3D());
        rightHip.localPosition.set(0.4f, -0.45f, 0);
        addChild(rightHip);

        {
            Entity rightShin = new Entity(world, "Right Shin");
            rightShin.addComponent(new Model(ResourceManager.getMesh("shin")));
            rightShin.addComponent(skinMat);
            rightShin.setTransform(new Transform3D());
            rightShin.localPosition.set(0, -0.8f, 0);
            rightHip.addChild(rightShin);
        }
        
        addBehaviour(new AnimBh());
        addBehaviour(new PlayerBh(camera));
	}

	
	
}
