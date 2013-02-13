package com.example.parsec;

import java.util.Iterator;
import org.andengine.engine.handler.IUpdateHandler;
import android.util.Log;

public class GameLoop implements IUpdateHandler {
	
	private final String LOG = "GameLoop";
	
	public final float BULLET_COOLDOWN = 1;
	public float bulletCoolDown = BULLET_COOLDOWN;
	
	private final float WAVE_INTERVAL = 5;
	public float timeElapsed = 0;

	private GameScene gameScene;
	private boolean gameLoop;
	
	public GameLoop(GameScene scene) {
		this.gameScene = scene;
		this.gameLoop = true;
	}

	@Override
	public void onUpdate(float pSecondsElapsed) {
		if (!gameLoop) {
			return;
		}
		bulletCoolDown += pSecondsElapsed;
		if (gameScene.enemies == null) {
			timeElapsed += pSecondsElapsed;
			if (timeElapsed > WAVE_INTERVAL) {
				Log.v(LOG, "Time for next wave");
				gameScene.enemies = new EnemyLayer();
				gameScene.attachChild(gameScene.enemies);
			}
		} else {
			final int count = gameScene.enemies.getChildCount();
			if (count == 0) {
				gameScene.enemies.clearUpdateHandlers();
				gameScene.enemies.dispose();
				gameScene.enemies.detachSelf();
				gameScene.enemies = null;
				timeElapsed = 0;
				Log.v(LOG, "Disposed of EnemyLayer");
			} else {
				for (int i = 0; i < gameScene.enemies.getChildCount(); i++) {
					final EnemyObject enemy = (EnemyObject) gameScene.enemies.getChildByIndex(i);
					
					if (gameScene.player.collidesWith(enemy)) {
						Log.i(LOG, "Collision detected, you die!");
						gameScene.player.setVisible(false);
						gameScene.createExplosion(gameScene.player);
						gameScene.createExplosion(enemy);
						enemy.dispose();
						enemy.setVisible(false);
						gameLoop = false;
						break;
					}
					
					Iterator<BulletObject> iterator = gameScene.bullets.iterator();
					while (iterator.hasNext()) {
						final BulletObject bullet = iterator.next();
						if (bullet.collidesWith(enemy)) {
							Log.v(LOG, "You hit an enemy object");
							gameScene.bulletPool.recyclePoolItem(bullet);
							iterator.remove();
							gameScene.createExplosion(enemy);
							enemy.setVisible(false);
							enemy.dispose();
							break;
						}
					}
				}
			}
		}
		
		Iterator<BulletObject> iterator = gameScene.bullets.iterator();
		while (iterator.hasNext()) {
			final BulletObject bullet = iterator.next();
			if (bullet.getX() > ResourceManager.getInstance().camera.getWidth()) {
				gameScene.bulletPool.recyclePoolItem(bullet);
				iterator.remove();
			}
		}
	}
	
	public void reset() {
		//TODO
	}

}
