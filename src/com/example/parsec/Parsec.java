package com.example.parsec;

import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;
import org.andengine.ui.activity.BaseGameActivity;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;

public class Parsec extends BaseGameActivity {
	
	private final String LOG = "Parsec";

	private static int CAMERA_WIDTH;
	private static int CAMERA_HEIGHT;

	private Camera camera;
	private GameScene scene;
	
	private boolean gamePaused = false;

	@Override
	public EngineOptions onCreateEngineOptions() {
	    final DisplayMetrics metrics = new DisplayMetrics();
	    getWindowManager().getDefaultDisplay().getMetrics(metrics);
	    CAMERA_WIDTH = metrics.widthPixels;
	    CAMERA_HEIGHT = metrics.heightPixels;
		this.camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		Log.i(LOG, "onCreateEngineOptions: widthxheight = "+CAMERA_WIDTH+"x"+CAMERA_HEIGHT);

		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.camera);
	}
	
	@Override
	public Engine onCreateEngine(EngineOptions pEngineOptions) {
		return new LimitedFPSEngine(pEngineOptions, 30);
	}
	
	@Override
	public void onCreateGame() {
		super.onCreateGame();
	}
	
	@Override
	public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws Exception {
		ResourceManager.getInstance().setGameGlobals(this.getEngine(), this.camera, this.getApplicationContext());
		ResourceManager.getInstance().loadSplashTextures();
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws Exception {
		pOnCreateSceneCallback.onCreateSceneFinished(new SplashScene());
	}
	
	@Override
	public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		mEngine.registerUpdateHandler(new TimerHandler(2f, new ITimerCallback() {
			public void onTimePassed(final TimerHandler pTimerHandler) {
				mEngine.unregisterUpdateHandler(pTimerHandler);
				ResourceManager.getInstance().loadTextures();
				ResourceManager.getInstance().loadTempTextures();
				
				Scene attachedScene = mEngine.getScene();
				if (attachedScene != null) {
					attachedScene.detachSelf();
				}
				
				scene = new GameScene();

				scene.populateScene();
				mEngine.setScene(scene);
			}
		}));
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}
	
	@Override
	public boolean onKeyDown(final int pKeyCode, final KeyEvent pEvent) {
		if (pKeyCode == KeyEvent.KEYCODE_BACK && pEvent.getAction() == KeyEvent.ACTION_DOWN) {
			if (!gamePaused) {
				onPauseGame();
				gamePaused = true;
				return true;
			}
		}
		return super.onKeyDown(pKeyCode, pEvent);
	}
}
