package snake.model;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

public class Snake extends Observable {
	private Game game;
	private ArrayList<Field> snake;
	private boolean gameOver;

	public Snake(Game game) {
		this.game = game;
		this.gameOver = false;

		// Place snake
		Field head = new Field(game.getSize().height / 2, game.getSize().width / 2);
		Field tail = new Field(game.getSize().height / 2, game.getSize().width / 2 + 1);
		this.snake = new ArrayList<>();
		snake.add(head);
		snake.add(tail);
		setChanged();
		notifyObservers();
	}

	public void move(int dy, int dx) {
		// check for food
		if (game.getFood().getPosition().equals(snake.get(0))) {
			snake.add((game.getFood().getPosition()));
			
			//Update food
			System.out.println("eat");
			Random random = new Random();
			int x = random.nextInt(game.getSize().width);
			int y = random.nextInt(game.getSize().height);
			
			Score.score++;
		} else {
			for (int i = 0; i < snake.size(); i++) {
				if (new Field(snake.get(0).getRow() + dx, snake.get(0).getColumn() + dy).equals(snake.get(i))) {
					gameOver = true;
					Score.score = 0;
				}
			}
			if (!gameOver) {
				for (int i = snake.size() - 1; i > 0; i--) {
					snake.set(i, snake.get(i - 1));
				}
				snake.set(0, new Field(snake.get(0).getRow() + dx, snake.get(0).getColumn() + dy));
			}
		}
		setChanged();
		notifyObservers();
	}

	public ArrayList<Field> getSnake() {
		return snake;
	}
}
