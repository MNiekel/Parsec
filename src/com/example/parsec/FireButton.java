package com.example.parsec;

import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;

public class FireButton extends GameObject {
	
	private Scene parentScene;

	public FireButton(Scene parentScene) {
		super(0, 0, ResourceManager.getInstance().mKnobRegion, ResourceManager.getInstance().engine.getVertexBufferObjectManager());
		
		final float width = ResourceManager.getInstance().camera.getWidth();
		final float height = ResourceManager.getInstance().camera.getHeight();

		this.setScale(2);
		this.setPosition(width - 2*getWidth(), height - 2*getHeight());
		this.parentScene = parentScene;
		parentScene.registerTouchArea(this);

	}

	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
			((GameScene) parentScene).fireBullet();
			return true;
		}
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
			return true;
		}
		return true;
	}
}
