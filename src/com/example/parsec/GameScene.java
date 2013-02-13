package com.example.parsec;

import java.util.LinkedList;

import org.andengine.entity.scene.Scene;
import android.util.Log;

public class GameScene extends Scene {
	
	private final String LOG = "GameScene";
	
	private GameLoop gameLoop;
	
	public PlayerObject player;
	public EnemyLayer enemies = null;
	
	public BulletPool bulletPool;
	public LinkedList<BulletObject> bullets;
	
	public GameScene() {
	}
	
	public void populateScene() {
		this.attachChild(new BackgroundLayer());
		bulletPool = new BulletPool();
		bullets = new LinkedList<BulletObject>();
		
		player = ObjectFactory.getInstance().createPlayer();
		player.setPosition(100, 100);
		this.attachChild(player);
		
		Controller controller = ObjectFactory.getInstance().createController(player, this);
		this.setChildScene(controller);
		
		this.gameLoop = new GameLoop(this);
		this.registerUpdateHandler(this.gameLoop);
	}
	
	public void fireBullet() {
		if (gameLoop.bulletCoolDown < gameLoop.BULLET_COOLDOWN) {
			return;
		}
		Log.v(LOG, "FIRE! ");
		BulletObject bullet = bulletPool.obtainPoolItem();
		bullet.resetBullet();
		bullet.setY(player.getY() - bullet.getHeight() / 2 + player.getHeight() / 2);
		bullet.setX(player.getX() + player.getWidth());
		this.attachChild(bullet);
		bullets.add(bullet);
		gameLoop.bulletCoolDown = 0;
	}
}
