
package snake.model;

import java.util.Observable;

public class Score extends Observable {
	
	private int score;
	
	public Score(){
		this.score = 0;
	}
	
	public int getValue() {
		return score;
	}
	
	void increment() {
		setScore(score++);
	}
	
	void reset() {
		setScore(0);
	}
	
	void setScore(int value) {
		score = value;
		setChanged();
		notifyObservers();
	}
	
}
