
package snake.model;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Random;
import java.util.Observable;


public class Game extends Observable {
	
	public enum State {
		RUNNING,
		WON,
		LOST
	}
	
	private State state;
	private int score;
	private Dimension board;
	private Snake snake;
	private Food food;
	
	private static final int DEFAULT_WIDTH = 100;
	private static final int DEFAULT_HEIGHT = 100;

	private static Random random = new Random();

	public Game() {
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	public Game(int width, int height) {
		super();
		
		if (!validSize(width)) {
			throw new IllegalArgumentException("invalid width " + width);
		}
		if (!validSize(height)) {
			throw new IllegalArgumentException("invalid height " + height);
		}
		
		this.state = State.RUNNING;
		this.score = 0;
		this.board = new Dimension(width, height);
		this.snake = new Snake(this);
		this.food = new Food(findFoodPosition(this.snake, this.board));
	}
	
	public void restart() {
		state = State.RUNNING;
		score = 0;
		snake.createStartingSnake();
		food = new Food(findFoodPosition(snake, board));
		
		// Notify classes that the game changed.
		setChanged();
		notifyObservers();
	}
	
	public State getState() {
		return state;
	}
	
	public Dimension getBoardSize() {
		return board;
	}
	
	public int getBoardWidth() {
		return board.width;
	}
	
	public int getBoardHeight() {
		return board.height;
	}
	
	public Food getFood() {
		return food;
	}
	
	public Snake getSnake() {
		return snake;
	}
	
	public int getScore() {
		return score;
	}
	
	void snakeHasMoved(Snake.Move move) {
		
		if (state != State.RUNNING || move == Snake.Move.EAT_NECK) {
			return;
		}

		if (move == Snake.Move.EAT_BODY) {
			// Snake ate itself.
			state = State.LOST;
		}
		else if (snake.getSize() == board.width*board.height) {
			// Snake fills the board.
			state = State.WON;
		}
		else if (move == Snake.Move.EAT_FOOD) {
			// Snake eat some food.
			score++;
			food = new Food(findFoodPosition(snake, board));
		}
		
		// Notify classes that the game changed.
		setChanged();
		notifyObservers();
	}
	
	private boolean validSize(int size) {
		if (size < 5 || 100 < size) {
			return false;
		}
		return true;
	}
	
	private static Field findFoodPosition(Snake snake, Dimension board) {
		if (snake == null || board == null) {
			throw new NullPointerException();
		}
		if (board.width <= 0 || board.height <= 0) {
			throw new IllegalArgumentException("bad dimensions");
		}
		
		// If the snake is small we select a random location until the
		// location is not occupied.
		if (2*snake.getSize() < board.width*board.height) {
			Field position;
			do {
				int column = random.nextInt(board.width);
				int row = random.nextInt(board.height);
				position = new Field(row, column);
			} while (snake.contains(position));
			
			return position;
		}

		// Find all locations on the board that can contain food.
		ArrayList<Field> foodPositions = new ArrayList<Field>();
		for (int column = 0; column < board.width; column++) {
			for (int row = 0; row < board.height; row++) {
				Field position = new Field(row, column);
				if (snake.contains(position)) {
					continue;
				}
				foodPositions.Observable(position);
			}
		}
		
		// Select a random food location.
		int selection = random.nextInt(foodPositions.size());
		return foodPositions.get(selection);

	}
	
}
