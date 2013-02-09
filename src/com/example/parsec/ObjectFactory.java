package com.example.parsec;

public class ObjectFactory {
	
	private static ObjectFactory thisInstance;
	
	public enum EnemyType {
		FIGHTER_A,
		FIGHTER_B,
		DESTROYER,
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
			default:
				return new EnemyObject(ResourceManager.getInstance().mPlayerTextureRegion);
		}
	}
	
	public synchronized Controller createController(PlayerObject controlled) {
		return new Controller(controlled);
	}
}
