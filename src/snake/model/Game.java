
package snake.model;

import java.util.Observable;

import snake.view.Audio;


public class Game extends Observable {
	
	public enum State {
		RUNNING,
		WON,
		LOST
	}
	
	private State state;
	private int score;
	private Board board;
	private Snake snake;
	private Food food;
	
	private static final int DEFAULT_WIDTH = 20;
	private static final int DEFAULT_HEIGHT = 20;

	public Game() {
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	public Game(int width, int height) {
		super();
		this.state = State.RUNNING;
		this.score = 0;
		this.board = new Board(width, height);
		this.snake = new Snake(this.board);
		this.food = Food.generateRandomFood(this.snake, this.board);
	}
	
	public State getState() {
		return state;
	}
	
	public Board getBoard() {
		return board;
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
	
	public void moveSnake(Direction moveDirection) {
		
		if (state != State.RUNNING) {
			return;
		}
		
		Field newHeadPosition = snake.getNewHeadPosition(moveDirection);
		boolean snakeEatsFood = newHeadPosition.equals(food.getPosition());
		boolean snakeEatsItSelf = snake.move(moveDirection, snakeEatsFood);
		
		if (snakeEatsFood) {
			score++;
			Audio.eatApple();
			food = Food.generateRandomFood(snake, board);
		}
		
		if (snakeEatsItSelf) {
			state = State.LOST;
			Audio.endGame();
		}
		else if (snake.fillsBoard()) {
			state = State.WON;
		}
		
		setChanged();
		notifyObservers();
	}
	
	public void restart() {
		state = State.RUNNING;
		score = 0;
		snake.setupStartingSnake();
		food = Food.generateRandomFood(snake, board);
		
		// Notify classes that the game changed.
		setChanged();
		notifyObservers();
	}
	
}
