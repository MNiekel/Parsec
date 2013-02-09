package com.example.parsec;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class GameObject extends Sprite {
	
	public GameObject(final float pX, final float pY, final ITextureRegion pTextureRegion) {
		super(pX, pY, pTextureRegion, ResourceManager.getInstance().engine.getVertexBufferObjectManager());
	}
	
	public GameObject(final float pX, final float pY, final float pWidth, final float pHeight, final ITextureRegion pTextureRegion) {
		super(pX, pY, pWidth, pHeight, pTextureRegion, ResourceManager.getInstance().engine.getVertexBufferObjectManager());
	}

	public GameObject(final float pX, final float pY, final ITextureRegion pTextureRegion, final VertexBufferObjectManager pVBOM) {
		super(pX, pY, pTextureRegion, pVBOM);
	}
}
