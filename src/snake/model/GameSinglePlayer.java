package snake.model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;


public class GameSinglePlayer extends Game implements ActionListener {
	
	protected static final int DEFAULT_WIDTH = 20;
	protected static final int DEFAULT_HEIGHT = 20;
	
	// Variable for implementing continuous snake movement.
	private Timer timer;
	boolean timerEnabled = true;
	private int timerUpdateInterval = 200;
	private int timerDecrease = 0;
	private long timerLastUpdateTime = 0;
	private static final int TIMER_INTERVAL = 16;
	private static final int TIMER_INITIAL_DELAY = 500;
	
	public GameSinglePlayer() {
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	public GameSinglePlayer(int width, int height) {
		super();
		
		// Add a single snake and single food to the board.
		generateSingleRandomFood();
		add(new Snake(getBoard()));
		setupInitialSnake();
		
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
		
		setState(State.RUN);
		setScore(0, 0);
		removeFood(0);
		generateSingleRandomFood();
		setupInitialSnake();

		timerUpdateInterval += timerDecrease;
		timerDecrease = 0;
		
		setChanged();
		notifyObservers(Event.START);
	}
	
	/**
	 * Restart the game, with a new width and height.
	 * @param width the new width
	 * @param height the new height
	 */
	public void restart(int width, int height) {
		Board board = new Board(width, height);
		Snake snake = new Snake(board);
		setBoard(board);
		add(snake);
		generateSingleRandomFood();
		restart();
	}
	
	public boolean hasWon() {
		
		return state == State.WIN;
	}
	
	public boolean hasLost() {
		return state == State.LOSE;
	}
	
	public boolean isPaused() {
		return state == State.PAUSE;
	}
	
	public boolean isTimedMovementEnabled() {
		return timerEnabled;
	}
	
	public Food getFood() {
		return getFood(0);
	}
	
	public Snake getSnake() {
		return getSnake(0);
	}
	
	public int getScore() {
		return getScore(0);
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
		this.timerDecrease = 0;
	}

	public void moveSnake(Direction moveDirection) {
		
		if (state != State.RUN) {
			return;
		}
		
		Snake snake = getSnake();
		Food food = getFood();
		if (!snake.canMove(moveDirection)) {
			return;
		}
		
		timerLastUpdateTime = System.currentTimeMillis();
		Field newHeadPosition = snake.getNewHeadPosition(moveDirection);
		boolean snakeEatsFood = newHeadPosition.equals(food.getPosition());
		boolean snakeEatsItSelf = snake.move(moveDirection, snakeEatsFood);
		
		if (snakeEatsFood) {
			incrementScore(0);
			removeFood(0);
			food = null;
			generateSingleRandomFood();
			if (getScore() % 5 == 0){
				timerDecrease += 5;
				timerUpdateInterval -= 5;
			}
		}
		
		if (snakeEatsItSelf) {
			state = State.LOSE;
		}
		else if (snake.fillsBoard()) {
			
			state = State.WIN;
		}
		
		// Notify the observing classes that the game changed. Send an argument with 
		// the type of event that happened.
		Event event = null;
		if (snakeEatsItSelf) {
			event = Event.DIE;
		}
		else if (getSnake().fillsBoard()) {
			event = Event.WIN;
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
		if (state == State.RUN && timerEnabled && elapsedTime > timerUpdateInterval) {
			moveSnake(getSnake().getHeadDirection());
			timerLastUpdateTime = currentTime;
		}
	}
	
	private void setupInitialSnake() {
		Field center = getBoard().getCenter();
		Field headPos = new Field(center.getRow(), center.getColumn());
		Field tailPos = new Field(center.getRow(), center.getColumn() + 1);
		Direction headDirection = Direction.LEFT;
		getSnake().setup(headPos, tailPos, headDirection);
	}
	
}
