
package snake.model;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.Timer;


public class Game extends Observable implements ActionListener {
	
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
	
	public State state;
	private int score;
	private Board board;
	private Snake snake;
	private Food food;
	
	private Timer timer;
	boolean timerEnabled = true;
	private int timerUpdateInterval = 200;
	private int speedIncrease = 0;
	private long timerLastUpdateTime = 0;
	private static final int TIMER_INTERVAL = 16;
	private static final int TIMER_INITIAL_DELAY = 500;
	
	public Game() {
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	public Game(int width, int height) {
		super();
		this.state = State.PAUSED;
		this.score = 0;
		this.board = new Board(width, height);
		this.snake = new Snake(this.board);
		this.food = Food.generateRandomFood(snake, board);
		
		// Create a timer object that java swing will call in a periodic
		// interval. The timer will then send an ActionEvent to this class.
		this.timer = new Timer(TIMER_INTERVAL, this);
		this.timer.setInitialDelay(TIMER_INITIAL_DELAY);
		this.timer.start();
	}
	
	/**
	 * Restart the game.
	 */
	public void restart() {
		state = State.ONGOING;
		score = 0;
		snake.setupStartingSnake();
		food = Food.generateRandomFood(snake, board);
		timerUpdateInterval += speedIncrease;
		speedIncrease = 0;
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
	
	public boolean isPaused() {
		return state == State.PAUSED;
	}
	
	public boolean isTimedMovementEnabled() {
		return timerEnabled;
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
	
	public void enableTimedMovement() {
		timerEnabled = true;
	}
	
	public void disableTimedMovement() {
		timerEnabled = false;
	}
	
	public void setTimedMovementSpeed(int speed) {
		if (speed <= 0) {
			throw new IllegalArgumentException("speed " + speed + " is not allowed");
		}
		this.timerUpdateInterval = speed;
	}

	public void moveSnake(Direction moveDirection) {
		
		if (state != State.ONGOING) {
			return;
		}
		
		if (!snake.canMove(moveDirection)) {
			return;
		}
		
		timerLastUpdateTime = System.currentTimeMillis();
		Field newHeadPosition = snake.getNewHeadPosition(moveDirection);
		boolean snakeEatsFood = newHeadPosition.equals(food.getPosition());
		boolean snakeEatsItSelf = snake.move(moveDirection, snakeEatsFood);
		
		if (snakeEatsFood) {
			score++;
			food = Food.generateRandomFood(snake, board);
			if (score%5 == 0){
				speedIncrease += 5;
				timerUpdateInterval -= 5;
			}
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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		long currentTime = System.currentTimeMillis();
		long elapsedTime = currentTime - timerLastUpdateTime;
		if (state == State.ONGOING && timerEnabled && elapsedTime > timerUpdateInterval) {
			moveSnake(snake.getHeadDirection());
			timerLastUpdateTime = currentTime;
		}
	}
	
}
