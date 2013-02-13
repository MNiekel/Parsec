package com.example.parsec;

import org.andengine.util.adt.pool.GenericPool;

public class BulletPool extends GenericPool<BulletObject> {
	
	private final String LOG = "BulletPool";
	
	@Override
	protected BulletObject onAllocatePoolItem() {
		return ObjectFactory.getInstance().createBullet();
	}
	
	protected void onHandleRecycleItem(final BulletObject bullet) {
		bullet.setVisible(false);
		bullet.detachSelf();
	}
}