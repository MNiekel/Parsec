package com.example.parsec;

import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.EntityBackground;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class GameScene extends Scene {

	public GameScene(VertexBufferObjectManager pVBOM, float pWidth, float pHeight) {
		final Entity bgEntity = new Entity();
		final EntityBackground background = new EntityBackground(bgEntity);
		
		this.setBackground(background);
		this.setBackgroundEnabled(true);
	}

}
