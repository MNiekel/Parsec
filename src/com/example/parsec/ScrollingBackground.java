package com.example.parsec;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.sprite.Sprite;

public class ScrollingBackground extends AutoParallaxBackground {
	public ScrollingBackground() {
		super(0, 0, 0, 16);
		
		final Camera camera = ResourceManager.getInstance().camera;
		final float height = camera.getHeight();
		final float width = camera.getWidth();
		
		final GameObject surface = new GameObject(0, 0, ResourceManager.getInstance().mSurfaceRegion);
		surface.setHeight(4*surface.getHeight());
		surface.setWidth(4*surface.getWidth());
		surface.setPosition(0, height - surface.getHeight());
		
		final GameObject stars = new GameObject(0, 0, width, height, ResourceManager.getInstance().mBackgroundRegion);
		this.attachParallaxEntity(new ParallaxEntity(0f, stars));
		this.attachParallaxEntity(new ParallaxEntity(-10f, surface));
	}
}
