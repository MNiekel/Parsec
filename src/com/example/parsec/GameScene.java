package com.example.parsec;

import java.util.LinkedList;

import org.andengine.engine.Engine;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntityFactory;
import org.andengine.entity.particle.BatchedSpriteParticleSystem;
import org.andengine.entity.particle.ParticleSystem;
import org.andengine.entity.particle.emitter.PointParticleEmitter;
import org.andengine.entity.particle.initializer.AccelerationParticleInitializer;
import org.andengine.entity.particle.initializer.VelocityParticleInitializer;
import org.andengine.entity.particle.modifier.AlphaParticleModifier;
import org.andengine.entity.particle.modifier.ExpireParticleInitializer;
import org.andengine.entity.particle.modifier.RotationParticleModifier;
import org.andengine.entity.particle.modifier.ScaleParticleModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.UncoloredSprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.color.Color;

import android.util.Log;

public class GameScene extends Scene {
	
	private final String LOG = "GameScene";
	
	private GameLoop gameLoop;
	
	public PlayerObject player;
	public EnemyLayer enemies = null;
	
	public BulletPool bulletPool;
	public LinkedList<BulletObject> bullets;
	
	public GameScene() {
		this.registerTouchArea(new Rectangle(0, 0, 800, 600, ResourceManager.getInstance().engine.getVertexBufferObjectManager()));
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
		bullet.setY(player.getY() - bullet.getHeight() / 2 + player.getHeight() / 2);
		bullet.setX(player.getX() + player.getWidth());
		this.attachChild(bullet);
		bullets.add(bullet);
		gameLoop.bulletCoolDown = 0;
	}
	
	public void createExplosion(final GameObject object) {
		final int particleSpawnCenterX = (int) (object.getX() + object.getWidth() / 2);
		final int particleSpawnCenterY = (int) (object.getY() + object.getHeight() / 2);
		
		PointParticleEmitter particleEmitter = new PointParticleEmitter(particleSpawnCenterX, particleSpawnCenterY);
		final float minSpawnRate = 25;
		final float maxSpawnRate = 50;
		final int maxParticleCount = 32;
		final float maxTime = 2;
		final Engine engine = ResourceManager.getInstance().engine;
		
		IEntityFactory<Rectangle> recFact = new IEntityFactory<Rectangle>() {
            @Override
            public Rectangle create(float pX, float pY) {
            	Rectangle rect = new Rectangle(particleSpawnCenterX, particleSpawnCenterY, 16, 16, engine.getVertexBufferObjectManager());
                rect.setColor(Color.RED);
                return rect;
            }
		};
		
		final ParticleSystem<Rectangle> particleSystem = new ParticleSystem<Rectangle>(recFact, particleEmitter, minSpawnRate, maxSpawnRate, maxParticleCount);
		particleSystem.addParticleInitializer(new VelocityParticleInitializer<Rectangle>(-50, 50, -50, 50));
		particleSystem.addParticleModifier(new AlphaParticleModifier<Rectangle>(0, 0.6f * maxTime, 1, 0));
		particleSystem.addParticleModifier(new RotationParticleModifier<Rectangle>(0, maxTime, 0, 360));
				
		this.registerUpdateHandler(new TimerHandler(maxTime, new ITimerCallback() {
			@Override
			public void onTimePassed(final TimerHandler pTimerHandler) {
				particleSystem.detachSelf();
                sortChildren();
                unregisterUpdateHandler(pTimerHandler);
            }
		}));

		this.attachChild(particleSystem);
	}
}
