package snake.model;
import java.awt.Point;
import java.util.ArrayList;

public class SnakePlayer {
	private Field head;
	private Field tail;
	private SnakeGame game;
	private ArrayList<Field> snake;
	private boolean gameOver;
	private int score;

	public SnakePlayer(SnakeGame game){
		this.game = game;
		this.gameOver = false;
		this.score = 0;
		
		//Place snake
		this.head = new Field (game.level.height/2, game.level.length/2);
		this.tail = new Field (game.level.height/2, game.level.length/2+1);
		this.snake = new ArrayList<>();
		snake.add(head);
		snake.add(tail);
	}

	public void move(int dx, int dy) {
		// check for food
		if (game.getFood().equals(snake.get(0))) {
			snake.add(0, new Field(snake.get(0).getRow() + dx, snake.get(0).getColumn() + dy));
			game.food = null;
			score++;
		} else {
			for (int i = 0; i < snake.size(); i++) {
				if (new Field(snake.get(0).getRow() + dx, snake.get(0).getColumn() + dy).equals(snake.get(i))) {
					gameOver = true;
				}
			}
			if (!gameOver) {
				for (int i = snake.size() - 1; i > 0; i--) {
					snake.set(i, snake.get(i - 1));
				}
				snake.set(0, new Field(snake.get(0).getRow() + dx,
						snake.get(0).getColumn() + dy));
			}
		}
	}
	public ArrayList<Field> getSnake(){
		return snake;
	}
}
