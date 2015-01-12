
package snake.model;
import java.util.Observable;


public class Game extends Observable {
	
	public enum State {
		ONGOING,
		WON,
		LOST,
		PAUSED
	}
	
	public enum Event {
		MOVE,
		EAT,
		DIE,
		RESTART,
		PAUSE,
		UNPAUSE
	}
	
	protected static final int DEFAULT_WIDTH = 20;
	protected static final int DEFAULT_HEIGHT = 20;
	
	private State state;
	private int score;
	private Board board;
	private Snake snake;
	private Food food;
	
	public Game() {
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	public Game(int width, int height) {
		super();
		this.state = State.ONGOING;
		this.score = 0;
		this.board = new Board(width, height);
		this.snake = new Snake(this.board);
		this.food = Food.generateRandomFood(snake, board);
	}
	
	/**
	 * Restart the game.
	 */
	public void restart() {
		state = State.ONGOING;
		score = 0;
		snake.setupStartingSnake();
		food = Food.generateRandomFood(snake, board);
		setChanged();
		notifyObservers(Event.RESTART);
	}
	
	/**
	 * Restart the game, with a new width and height.
	 * @param width the new width
	 * @param height the new height
	 */
	public void restart(int width, int height) {
		this.board = new Board(width, height);
		this.snake = new Snake(this.board);
		restart();
	}
	
	public boolean hasWon() {
		return state == State.WON;
	}
	
	public boolean hasLost() {
		return state == State.LOST;
	}
	
	public boolean isOngoing() {
		return state == State.ONGOING;
	}
	
	public boolean isPaused() {
		return state == State.PAUSED;
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
	
	public void pause() {
		if (state == State.ONGOING) {
			state = State.PAUSED;
			setChanged();
			notifyObservers(Event.PAUSE);
		}
	}
	
	public void unPause() {
		if (state == State.PAUSED) {
			state = State.ONGOING;
			setChanged();
			notifyObservers(Event.UNPAUSE);
		}
	}

	public void moveSnake(Direction moveDirection) {
		
		if (state != State.ONGOING) {
			return;
		}
		
		Field newHeadPosition = snake.getNewHeadPosition(moveDirection);
		boolean snakeEatsFood = newHeadPosition.equals(food.getPosition());
		boolean snakeEatsItSelf = snake.move(moveDirection, snakeEatsFood);
		
		if (snakeEatsFood) {
			score++;
			food = Food.generateRandomFood(snake, board);
		}
		
		if (snakeEatsItSelf) {
			state = State.LOST;
		}
		else if (snake.fillsBoard()) {
			state = State.WON;
		}
		
		// Notify the observing classes that the game changed. Send an argument with 
		// the type of event that happened.
		Event event = null;
		if (snakeEatsItSelf) {
			event = Event.DIE;
		}
		else if (snakeEatsFood) {
			event = Event.EAT;
		}
		else {
			event = Event.MOVE;
		}
		setChanged();
		notifyObservers(event);
	}
	
}
