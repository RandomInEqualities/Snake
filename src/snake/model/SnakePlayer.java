package snake.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Observable;

public class SnakePlayer extends Observable {
	private Game game;
	private ArrayList<Field> snake;
	private boolean gameOver;
	private int score;

	public SnakePlayer(Game game) {
		this.game = game;
		this.gameOver = false;
		this.score = 0;

		// Place snake
		if (game.getLevel() != null) {
			int midH = game.getLevel().getHeight();
			int midW = game.getLevel().getWidth();
			Field head = new Field(midH / 2, midW / 2);
			Field tail = new Field(midH / 2, midW / 2 + 1);
			this.snake = new ArrayList<>();
			snake.add(head);
			snake.add(tail);
			setChanged();
		 	notifyObservers();
		}
	}

	public void move(int dy, int dx) {
		// check for food
		if (game.getFood().equals(snake.get(0))) {
			snake.add((game.getFood().getPosition()));
			// game.getFood() = null;
			score++;
		} else {
			for (int i = 0; i < snake.size(); i++) {
				if (new Field(snake.get(0).getRow() + dx, snake.get(0)
						.getColumn() + dy).equals(snake.get(i))) {
					gameOver = true;
				}
			}
			if (!gameOver) {
				for (int i = snake.size() - 1; i > 0; i--) {
					snake.set(i, snake.get(i - 1));
				}
				snake.set(0, new Field(snake.get(0).getRow() + dx, snake.get(0)
						.getColumn() + dy));
			}
		}
		setChanged();
	 	notifyObservers();
	}

	public ArrayList<Field> getSnake() {
		return snake;
	}
}
