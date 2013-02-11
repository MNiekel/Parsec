package com.example.parsec;


import org.andengine.engine.options.EngineOptions;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import android.util.DisplayMetrics;
import android.util.Log;

public class Parsec extends BaseGameActivity {

	private static int CAMERA_WIDTH;
	private static int CAMERA_HEIGHT;

	private Camera camera;
	private Scene scene;

	@Override
	public EngineOptions onCreateEngineOptions() {
	    final DisplayMetrics metrics = new DisplayMetrics();
	    getWindowManager().getDefaultDisplay().getMetrics(metrics);
	    CAMERA_WIDTH = metrics.widthPixels;
	    CAMERA_HEIGHT = metrics.heightPixels;
		this.camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		Log.v("onCreateEngineOptions", "W:H "+CAMERA_WIDTH+":"+CAMERA_HEIGHT);

		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.camera);
	}
	
	@Override
	public void onCreateGame() {
		super.onCreateGame();
		if (this.mEngine == null) {
			Log.v("AutoScroll", "engine == null");
		}
	}
	
	@Override
	public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws Exception {
		ResourceManager.getInstance().setGameGlobals(this.getEngine(), this.camera, this.getApplicationContext());
		ResourceManager.getInstance().loadTextures();
		ResourceManager.getInstance().loadTempTextures();
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws Exception {
		pOnCreateSceneCallback.onCreateSceneFinished(scene = new Scene());
	}
	
	@Override
	public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		mEngine.registerUpdateHandler(new TimerHandler(1f, new ITimerCallback() {
			public void onTimePassed(final TimerHandler pTimerHandler) {
				mEngine.unregisterUpdateHandler(pTimerHandler);
				
				scene.attachChild(new BackgroundLayer());
				
				final PlayerObject player = ObjectFactory.getInstance().createPlayer();
				player.setPosition(100, 100);
				scene.attachChild(player);
				
				scene.attachChild(new EnemyLayer());
				
				Controller controller = ObjectFactory.getInstance().createController(player);
				scene.setChildScene(controller);
			}
		}));
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}
}
