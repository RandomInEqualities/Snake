
package snake.model;

public class Score {
	
	private int score;
	
	public Score(){
		this.score = 0;
	}
	
	public int getScore(){
		return score;
	}
	
	public void increment() {
		score++;
	}
	
	public void reset() {
		score = 0;
	}
	
}
