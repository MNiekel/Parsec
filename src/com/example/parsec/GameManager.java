package com.example.parsec;

public class GameManager {
	
	private static GameManager thisInstance;
		
	private int gmScore;
	private int gmHighScore;
	
	public static GameManager getInstance() {
		if (thisInstance == null) {
			thisInstance = new GameManager();
		}
		return thisInstance;
	}

	public int getScore() {
		return gmScore;
	}
	
	public void setScore(final int score) {
		gmScore = score;
	}
	
	public void incrementScore(final int increment) {
		gmScore += increment;
	}
}
