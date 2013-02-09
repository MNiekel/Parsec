package com.example.parsec;

import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.IEntity;
import org.andengine.opengl.texture.region.ITextureRegion;

public class PlayerObject extends GameObject {
	
	private final PhysicsHandler physics = new PhysicsHandler(this);
	
	public PlayerObject(ITextureRegion pTexture) {
		super(0, 0, pTexture);
		this.registerPhysicsHandler();
	}
	
	private void registerPhysicsHandler() {
		this.registerUpdateHandler(physics);
	}
	
	public void setVelocity(final float pVx, final float pVy) {
		this.physics.setVelocity(pVx, pVy);
	}
	
	public void fireBullet() {
		final BulletObject bullet = ObjectFactory.getInstance().createBullet();
		bullet.setPosition(this);
		bullet.setY(bullet.getY() - bullet.getHeight() / 2 + this.getHeight() / 2);
		bullet.setX(bullet.getX() + this.getWidth());
		IEntity parent = this.getParent();
		parent.attachChild(bullet);
	}
}
