package com.example.parsec;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

import android.content.Context;
import android.util.Log;

public class ResourceManager {
	
	private static ResourceManager thisInstance;
	
	public Camera camera;
	public Engine engine;
	public Context context;

	public ITextureRegion mPlayerTextureRegion;
	public ITextureRegion mBulletTextureRegion;
	
	public ITextureRegion mBackgroundRegion;
	public ITextureRegion mMoonTextureRegion;
	
	public ITextureRegion mKnobRegion;
	public ITextureRegion mBaseRegion;
	
	public ITextureRegion mFaceTextureRegion;
	
	public static ResourceManager getInstance() {
		if (thisInstance == null) {
			Log.v("ResourceManager", "Creating new ResourceManager");
			thisInstance = new ResourceManager();
		}
		return thisInstance;
	}	
	
	public ResourceManager() {
	}
	
	public synchronized void setGameGlobals(Engine pEngine, Camera pCamera, Context pContext) {
		this.engine = pEngine;
		this.camera = pCamera;
		this.context = pContext;
	}
	
	public void loadTextures() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
		BitmapTextureAtlas mSpaceshipTextureAtlas = new BitmapTextureAtlas(engine.getTextureManager(), 64, 64, TextureOptions.BILINEAR);
		mPlayerTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mSpaceshipTextureAtlas, context, "spaceship.png", 0, 0);
		mSpaceshipTextureAtlas.load();
		
		BitmapTextureAtlas mBulletTextureAtlas = new BitmapTextureAtlas(engine.getTextureManager(), 10, 10);
		mBulletTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBulletTextureAtlas, context, "bullet.png", 0, 0);
		mBulletTextureAtlas.load();

		BitmapTextureAtlas mBackgroundTextureAtlas = new BitmapTextureAtlas(engine.getTextureManager(), 640, 368, TextureOptions.BILINEAR);
		mBackgroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBackgroundTextureAtlas, context, "backgroundstars.png", 0, 0);
		mBackgroundTextureAtlas.load();

		BitmapTextureAtlas mMoonTextureAtlas = new BitmapTextureAtlas(engine.getTextureManager(), 512, 512);
		mMoonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mMoonTextureAtlas, context, "moon.png", 0, 0);
		mMoonTextureAtlas.load();
		
		BitmapTextureAtlas mKnobTextureAtlas = new BitmapTextureAtlas(engine.getTextureManager(), 64, 64);
		mKnobRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mKnobTextureAtlas, context, "onscreen_control_knob.png", 0, 0);
		mKnobTextureAtlas.load();

		BitmapTextureAtlas mBaseTextureAtlas = new BitmapTextureAtlas(engine.getTextureManager(), 128, 128);
		mBaseRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBaseTextureAtlas, context, "onscreen_control_base.png", 0, 0);
		mBaseTextureAtlas.load();
	}	
	
	public void loadTempTextures() {
		BitmapTextureAtlas mBitmapTextureAtlas = new BitmapTextureAtlas(engine.getTextureManager(), 32, 32, TextureOptions.BILINEAR);
		mFaceTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, context, "face_box.png", 0, 0);
		mBitmapTextureAtlas.load();
	}
}
