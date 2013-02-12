package com.example.parsec;

import java.util.Iterator;
import java.util.LinkedList;

import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.CubicBezierCurveMoveModifier;

import com.example.parsec.ObjectFactory.EnemyType;

public class EnemyLayer extends Entity {
	
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
		CubicBezierCurveMoveModifier move = new CubicBezierCurveMoveModifier(10, width, 0, width / 2, height, width, 0, 0, height / 2)
		{
			protected void onModifierStarted(IEntity pItem) {
        		super.onModifierStarted(pItem);
			}
			
			protected void onModifierFinished(IEntity pItem) {
	        	super.onModifierFinished(pItem);
	        	pItem.setVisible(false);
	        	pItem.dispose();
			}
		};
		enemy.registerEntityModifier(move);
		this.attachChild(enemy);
		enemies.add(enemy);
		enemiesCreated += 1;
	}
}