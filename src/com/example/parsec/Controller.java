package com.example.parsec;

import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import android.opengl.GLES20;

public class Controller extends AnalogOnScreenControl {
	
	public Controller(final PlayerObject player) {
		super(0, 0, ResourceManager.getInstance().camera, ResourceManager.getInstance().mBaseRegion, ResourceManager.getInstance().mKnobRegion, 0.1f, 100, ResourceManager.getInstance().engine.getVertexBufferObjectManager(), new IAnalogOnScreenControlListener() {
			@Override
			public void onControlChange(final BaseOnScreenControl pBOSC, final float pX, final float pY) {
				player.setVelocity(pX*200, pY*200);
			}

			@Override
			public void onControlClick(final AnalogOnScreenControl pAnalogOnScreenControl) {
				player.fireBullet();
			}
		});
		
		final float posY = ResourceManager.getInstance().camera.getHeight() - ResourceManager.getInstance().mBaseRegion.getHeight();

		getControlBase().setPosition(0, posY);		
		getControlBase().setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		getControlBase().setAlpha(0.5f);
		getControlBase().setScaleCenter(0, 128);
		getControlBase().setScale(1.25f);
		getControlKnob().setScale(1.25f);
		refreshControlKnobPosition();
	}
}
