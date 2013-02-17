package com.example.parsec;

import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.entity.scene.Scene;
import org.andengine.util.color.Color;

import android.opengl.GLES20;
import android.util.Log;

public class Controller extends AnalogOnScreenControl {
	
	private final static String LOG = "Controller";
	
	private static final float SPEED_MULTIPLIER = 200;
	
	public Controller(final GameObject object, final Scene parent) {
		super(0, 0, ResourceManager.getInstance().camera, ResourceManager.getInstance().mBaseRegion, ResourceManager.getInstance().mKnobRegion, 0.1f, 100, ResourceManager.getInstance().engine.getVertexBufferObjectManager(), new IAnalogOnScreenControlListener() {
			@Override
			public void onControlChange(final BaseOnScreenControl pBOSC, final float pX, final float pY) {
				((PlayerObject) object).setVelocity(pX*SPEED_MULTIPLIER, pY*SPEED_MULTIPLIER);
			}

			@Override
			public void onControlClick(final AnalogOnScreenControl pAnalogOnScreenControl) {
				//do nothing
			}
		});
		
		final float posY = ResourceManager.getInstance().camera.getHeight() - ResourceManager.getInstance().mBaseRegion.getHeight();

		getControlBase().setPosition(0, posY);
		getControlKnob().setColor(Color.GREEN);
		getControlBase().setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		getControlBase().setAlpha(0.5f);
		getControlBase().setScaleCenter(0, 128);
		getControlBase().setScale(2f);
		getControlKnob().setScale(2f);
		refreshControlKnobPosition();
	}
}
