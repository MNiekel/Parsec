package com.example.parsec;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.CubicBezierCurveMoveModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveModifier;

import com.example.parsec.ObjectFactory.EnemyType;

public class EnemyLayer extends Entity {
	
	private final String LOG = "EnemyLayer";
	
	private final float ENEMY_INTERVAL = 2;
	private final int WAVE_SIZE = 5;
	private float timeElapsed = 0;
	private final float width = ResourceManager.getInstance().camera.getWidth();
	private final float height = ResourceManager.getInstance().camera.getHeight();
	private int enemiesCreated = 0;
	private LinkedList<EnemyObject> enemies = new LinkedList<EnemyObject>();

	public EnemyLayer() {
		super();
		this.createEnemy();
	}

	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
        super.onManagedUpdate(pSecondsElapsed);
		timeElapsed += pSecondsElapsed;
		if ((timeElapsed > ENEMY_INTERVAL) && (enemiesCreated < WAVE_SIZE)) {
			this.createEnemy();
			timeElapsed = 0;
		}
		Iterator<EnemyObject> iterator = enemies.iterator();
		while (iterator.hasNext()) {
			EnemyObject enemy = iterator.next();
			if (enemy.isDisposed()) {
				enemy.clearEntityModifiers();
				enemy.clearUpdateHandlers();
				enemy.detachSelf();
			}
		}
	}
	
	private void createEnemy() {
		final EnemyObject enemy = ObjectFactory.getInstance().createEnemy(EnemyType.FIGHTER_A);
		enemy.setX(width);
		Random randomGenerator = new Random();
		final float duration = randomGenerator.nextFloat()*4 + 4;
		final float y1 = (float) randomGenerator.nextInt((int) height);
		final float y2 = (float) height - randomGenerator.nextInt((int) height / 4);
		final float y3 = (float) randomGenerator.nextInt((int) height / 4);
		final float y4 = (float) randomGenerator.nextInt((int) height);
		final CubicBezierCurveMoveModifier move = new CubicBezierCurveMoveModifier(duration, width, y1, 3 * width / 4, y2, width / 2, y3, -enemy.getWidth(), y4)
		{
			protected void onModifierStarted(IEntity pItem) {
        		super.onModifierStarted(pItem);
			}
			
			protected void onModifierFinished(IEntity pItem) {
	        	super.onModifierFinished(pItem);
	        	LoopEntityModifier loopMove = new LoopEntityModifier(new MoveModifier(4, width, -((GameObject) pItem).getWidth(), pItem.getY(), pItem.getY()));
	        	pItem.registerEntityModifier(loopMove);
			}
		};
		enemy.registerEntityModifier(move);
		this.attachChild(enemy);
		enemies.add(enemy);
		enemiesCreated += 1;
	}
}