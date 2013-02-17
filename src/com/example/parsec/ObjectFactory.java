package com.example.parsec;

import org.andengine.entity.scene.Scene;

public class ObjectFactory {
	
	private final String LOG = "ObjectFactory";
	
	private static ObjectFactory thisInstance;
	
	public enum EnemyType {
		FIGHTER_A,
		FIGHTER_B,
		DESTROYER,
		ASTEROID,
		SAUCER,
	}
	
	public static ObjectFactory getInstance() {
		if (thisInstance == null) {
			thisInstance = new ObjectFactory();
		}
		return thisInstance;
	}
	
	public ObjectFactory() {
	}

	public synchronized PlayerObject createPlayer() {
		return new PlayerObject(ResourceManager.getInstance().mPlayerTextureRegion);
	}
	
	public synchronized BulletObject createBullet() {
		return new BulletObject(ResourceManager.getInstance().mBulletTextureRegion);
	}
	
	public synchronized EnemyObject createEnemy(EnemyType type) {
		switch (type) {
			case FIGHTER_A:
				return new EnemyObject(ResourceManager.getInstance().mFaceTextureRegion);
			case FIGHTER_B:
				return new EnemyObject(ResourceManager.getInstance().mPlayerTextureRegion);
			case DESTROYER:
				return new EnemyObject(ResourceManager.getInstance().mPlayerTextureRegion);
			case ASTEROID:
				return new EnemyObject(ResourceManager.getInstance().mPlayerTextureRegion);
			case SAUCER:
				return new EnemyObject(ResourceManager.getInstance().mPlayerTextureRegion);
			default:
				return new EnemyObject(ResourceManager.getInstance().mPlayerTextureRegion);
		}
	}
	
	public synchronized Controller createController(GameObject controlledObject, Scene parentScene) {
		return new Controller(controlledObject, parentScene);
	}
	
	public synchronized FireButton createFireButton(Scene parentScene) {
		return new FireButton(parentScene);
	}
}
