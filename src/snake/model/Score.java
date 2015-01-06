package snake.model;

import java.util.Observable;

public class Score extends Observable{
	public static int score;
	
	public Score(){
		this.score = 0;
	}
	
	public int getScore(){
		return score;
	}
}
