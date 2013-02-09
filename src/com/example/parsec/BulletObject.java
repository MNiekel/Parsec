package com.example.parsec;

import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.opengl.texture.region.ITextureRegion;

public class BulletObject extends GameObject {
	
	private final PhysicsHandler physics = new PhysicsHandler(this);

	public BulletObject(ITextureRegion pTextureRegion) {
		super(0, 0, pTextureRegion);
		this.registerUpdateHandler(physics);
		this.physics.setVelocity(100, 0);
	}

	public BulletObject(final float pX, final float pY, ITextureRegion pTextureRegion) {
		super(pX, pY, pTextureRegion);
		this.registerUpdateHandler(physics);
		this.physics.setVelocity(100, 0);
	}

}
