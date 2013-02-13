package com.example.parsec;

import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.IEntity;
import org.andengine.opengl.texture.region.ITextureRegion;

public class PlayerObject extends GameObject {
	
	private final PhysicsHandler physics = new PhysicsHandler(this);
	
	public PlayerObject(ITextureRegion pTexture) {
		super(0, 0, pTexture);
		this.registerUpdateHandler(physics);
	}
	
	public void setVelocity(final float pVx, final float pVy) {
		this.physics.setVelocity(pVx, pVy);
	}
}
