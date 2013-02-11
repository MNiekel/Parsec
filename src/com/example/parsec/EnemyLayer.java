package com.example.parsec;

import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.CubicBezierCurveMoveModifier;
import org.andengine.entity.modifier.LoopEntityModifier;

import android.util.Log;

import com.example.parsec.ObjectFactory.EnemyType;

public class EnemyLayer extends Entity {
	
	private final float WAVE_INTERVAL = 5;
	private final float ENEMY_INTERVAL = 2;
	private final int WAVE_SIZE = 10;
	private float timeElapsed = 0;
	private float enemyTimeElapsed = 0;
	private final float width = ResourceManager.getInstance().camera.getWidth();
	private final float height = ResourceManager.getInstance().camera.getHeight();
	private int numEnemiesCreated = 0;
	private int numEnemies = 0;

	public EnemyLayer() {
		super();
	}
	
	@Override
    protected void onManagedUpdate(float pSecondsElapsed) {
        super.onManagedUpdate(pSecondsElapsed);
        timeElapsed += pSecondsElapsed;
        if (timeElapsed > WAVE_INTERVAL) {
        	if ((numEnemiesCreated < WAVE_SIZE) && (enemyTimeElapsed > ENEMY_INTERVAL)) {
        		final EnemyObject enemy = ObjectFactory.getInstance().createEnemy(EnemyType.FIGHTER_A);
        		enemy.setPosition(width, 300);
        		CubicBezierCurveMoveModifier move = new CubicBezierCurveMoveModifier(10, width, 0, width / 2, height, width, 0, 0, height / 2)
        		{
        	        @Override
        	        protected void onModifierStarted(IEntity pItem)
        	        {
        	        	super.onModifierStarted(pItem);
        	        	numEnemies += 1;
        	        }
        	       
        	        @Override
        	        protected void onModifierFinished(IEntity pItem)
        	        {
        	        	super.onModifierFinished(pItem);
        	        	pItem.setVisible(false);
        	        	pItem.dispose();
        	        	numEnemies -= 1;
        	        	if (numEnemies <= 0) {
        	        		numEnemies = 0;
        	        		timeElapsed = 0;
        	        		enemyTimeElapsed = 0;
        	        		numEnemiesCreated = 0;
        	        	}
        	        }
        		};
        			
        		enemy.registerEntityModifier(move);

        		this.attachChild(enemy);
            	numEnemiesCreated += 1;
            	enemyTimeElapsed = 0;
            } else {
            	enemyTimeElapsed += pSecondsElapsed;
            }
        }
    }
}
