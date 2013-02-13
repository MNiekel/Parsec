package com.example.parsec;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

public class SplashScene extends Scene {
		
	public SplashScene() {
		setBackground(new Background(0, 0, 0));
		Sprite logo = new Sprite(0, 0, ResourceManager.getInstance().mSplashRegion, ResourceManager.getInstance().engine.getVertexBufferObjectManager()) {
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		
		logo.setScaleCenter(0, 0);
		final float width = ResourceManager.getInstance().camera.getWidth();
		final float height = ResourceManager.getInstance().camera.getHeight();
		float scaleX = width / logo.getWidth();
		float scaleY = height / logo.getHeight();
		float scale = Math.min(scaleX, scaleY);
		logo.setScale(scale);

		logo.setPosition((width - logo.getWidthScaled()) / 2, (height - logo.getHeightScaled()) / 2);
		attachChild(logo);
	}
}
