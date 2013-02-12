package com.example.parsec;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.scene.Scene;
import android.util.Log;

public class GameScene extends Scene {
	
	private PlayerObject player;
	private EnemyLayer enemies = null;
	
	private final float WAVE_INTERVAL = 5;
	private float timeElapsed = 0;
	
	public GameScene() {
	}
	
	public void populateScene() {
		this.attachChild(new BackgroundLayer());
		
		player = ObjectFactory.getInstance().createPlayer();
		player.setPosition(100, 100);
		this.attachChild(player);
		
		Controller controller = ObjectFactory.getInstance().createController(player);
		this.setChildScene(controller);
		
		this.registerUpdateHandler(new IUpdateHandler() {
			@Override
			public void onUpdate(float pSecondsElapsed) {
				if (enemies == null) {
					timeElapsed += pSecondsElapsed;
					if (timeElapsed > WAVE_INTERVAL) {
						Log.i("GameScene", "Time for next wave");
						enemies = new EnemyLayer();
						attachChild(enemies);
					}
				} else {
					final int count = enemies.getChildCount();
					if (count == 0) {
						enemies.clearUpdateHandlers();
						enemies.dispose();
						enemies.detachSelf();
						enemies = null;
						timeElapsed = 0;
						Log.i("GameScene", "Disposed of EnemyLayer");
					} else {
						EnemyObject enemy = (EnemyObject) enemies.getChildByIndex(0);
						if (player.collidesWith(enemy)) {
							Log.i("GameScene", "Collision detected");
						}
					}
				}
			}
			
			public void reset() {
				//TODO
			}
		});
	}
}
