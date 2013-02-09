package com.example.parsec;

import org.andengine.engine.options.EngineOptions;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.util.FPSLogger;

import com.example.parsec.ObjectFactory.EnemyType;

import android.util.DisplayMetrics;
import android.util.Log;


public class Parsec extends SimpleBaseGameActivity {

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
	public void onCreateResources() {
		ResourceManager.getInstance().setGameGlobals(this.getEngine(), this.camera, this.getApplicationContext());
		ResourceManager.getInstance().loadTextures();
		ResourceManager.getInstance().loadTempTextures();
		}

	@Override
	public Scene onCreateScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		
		scene = new Scene();
		
		final SpaceBackground background = new SpaceBackground(new Entity());
		scene.setBackground(background);
		scene.setBackgroundEnabled(true);
				
		final PlayerObject player = ObjectFactory.getInstance().createPlayer();
		player.setPosition(100, 100);
		scene.attachChild(player);
		
		final EnemyObject enemy = ObjectFactory.getInstance().createEnemy(EnemyType.FIGHTER_A);
		enemy.setPosition(300, 300);
		scene.attachChild(enemy);
		
		Controller controller = ObjectFactory.getInstance().createController(player);
		scene.setChildScene(controller);

		return scene;
	}

}

/*
		CubicBezierCurveMoveModifier move = new CubicBezierCurveMoveModifier(10, CAMERA_WIDTH, 0, CAMERA_WIDTH / 2, CAMERA_HEIGHT, CAMERA_WIDTH, 0, 0, CAMERA_HEIGHT / 2);
		//QuadraticBezierCurveMoveModifier move2 = new QuadraticBezierCurveMoveModifier(10, CAMERA_WIDTH, 0, CAMERA_WIDTH / 2, CAMERA_HEIGHT, 0, CAMERA_HEIGHT / 2);
		player.registerEntityModifier(new LoopEntityModifier(move));
*/
