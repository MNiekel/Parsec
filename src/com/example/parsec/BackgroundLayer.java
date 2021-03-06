package com.example.parsec;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.Entity;

public class BackgroundLayer extends Entity {
	
	public BackgroundLayer() {
		super();
		
		final Camera camera = ResourceManager.getInstance().camera;
		final float width = camera.getWidth();
		final float height = camera.getHeight();
		
		final float moonX = 2*width/3;
		final float moonY = height/2;

		final GameObject moon = new GameObject(moonX, moonY, ResourceManager.getInstance().mMoonTextureRegion);
		
		this.attachChild(moon);
	}
}