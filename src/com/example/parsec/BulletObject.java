package com.example.parsec;

import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.opengl.texture.region.ITextureRegion;

public class BulletObject extends GameObject {
	
	private final String LOG = "BulletObject";
	
	private final int VELOCITY = 400;
	private PhysicsHandler physics = new PhysicsHandler(this);

	public BulletObject(ITextureRegion pTextureRegion) {
		super(0, 0, pTextureRegion);
		this.registerUpdateHandler(physics);
		this.physics.setVelocity(VELOCITY, 0);
	}
}
