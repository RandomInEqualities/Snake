package snake.model;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.Timer;

import snake.model.Game;


public class GameSingleplayer extends Observable implements Game , ActionListener {
	
	private enum State {
		START,
		RUN,
		PAUSE,
		END
	}
	
	private static final int DEFAULT_WIDTH = 20;
	private static final int DEFAULT_HEIGHT = 20;
	private static final int TIMER_UPDATE_INTERVAL = 16;
	private static final int TIMER_INITIAL_DELAY = 500;
	
	private State state;
	private Board board;
	private Snake snake;
	private int score, speedIncrease;
	private Food food;
	private boolean isWon = false;
	private boolean isLost = false;
	
	// Variables for implementing continuous snake movement.
	private Timer timer;
	boolean timerEnabled = true;
	private int timerUpdateInterval = 200;
	private long timerLastUpdateTime = 0;
	
	public GameSingleplayer() {
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT, null);
	}
	
	public GameSingleplayer(int width, int height, ArrayList<Field> walls) {
		this.board = new Board(width, height, walls);
		this.snake = new Snake();
		
		// Create a timer object that send an ActionEvent to this class, in a periodic interval.
		this.timer = new Timer(TIMER_UPDATE_INTERVAL, this);
		this.timer.setInitialDelay(TIMER_INITIAL_DELAY);
		this.speedIncrease = 0;
		reset();
	}
	
	public void setBoardSize(int width, int height){
		board = new Board(width, height, null);
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

	@Override
	public boolean isStarted() {
		return state != State.START;
	}

	@Override
	public boolean isRunning() {
		return state == State.RUN;
	}

	@Override
	public boolean isPaused() {
		return state == State.PAUSE;
	}

	@Override
	public boolean isEnded() {
		return state == State.END;
	}
	
	public boolean isWon() {
		return isWon;
	}
	
	public boolean isLost() {
		return isLost;
	}
	
	public boolean isTimedMovementEnabled() {
		return timerEnabled;
	}

	@Override
	public void start() {
		if (state == State.START) {
			state = State.RUN;
			timer.start();
			setChanged();
			notifyObservers(new Event(Event.START));
		}
	}

	@Override
	public void pause() {
		if (state == State.RUN) {
			state = State.PAUSE;
			timer.stop();
			setChanged();
			notifyObservers(new Event(Event.PAUSE));
		}
	}

	@Override
	public void resume() {
		if (state == State.PAUSE) {
			state = State.RUN;
			timer.start();
			setChanged();
			notifyObservers(new Event(Event.RESUME));
		}
	}

	@Override
	public void reset() {
		// Set the game variables.
		isLost = false;
		isWon = false;
		snake.setup(getInitialSnake());
		state = State.START;
		score = 0;
		food = Food.generateRandomFood(snake, board);
		timerUpdateInterval -= speedIncrease;
		speedIncrease = 0;
		timer.stop();
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
	
	public void move(Direction moveDirection) {
		if (state != State.RUN) {
			return;
		}
		if (!snake.validMoveDirection(moveDirection)) {
			return;
		}
	
		Field newHeadPosition = snake.getNewHeadPosition(moveDirection, board);
		boolean snakeEatsFood = newHeadPosition.equals(food.getPosition());		
		boolean snakeEatsItSelf = snake.move(moveDirection, snakeEatsFood, board);
		if (snakeEatsItSelf) {
			state = State.END;
			isLost = true;
		}
		else if (snake.fills(board)) {
			state = State.END;
			isWon = true;

		} else if (snakeEatsFood) {
			score++;
			if(score % 5 == 0) {
				speedIncrease +=5;
				this.timerUpdateInterval -=5;
			}
			food = Food.generateRandomFood(snake, board);
		}
		
		// Notify the observing classes that the game changed. Send an argument with 
		// the type of event that happened.
		Event event;
		if (snake.fills(board)) {
			event = new Event(Event.WIN);
		}
		else if (snakeEatsItSelf) {
			event = new Event(Event.LOSE);
		}
		else if (snakeEatsFood) {
			event = new Event(Event.EAT);
		}
		else {
			event = new Event(Event.MOVE);
		}
		timerLastUpdateTime = System.currentTimeMillis();
		setChanged();
		notifyObservers(event);
	}
	
	public void setBoard(Board board) {
		this.board = board;
		reset();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		long currentTime = System.currentTimeMillis();
		long elapsedTime = currentTime - timerLastUpdateTime;
		if (state == State.RUN && timerEnabled && elapsedTime > timerUpdateInterval) {
			move(snake.getHeadDirection());
			timerLastUpdateTime = currentTime;
		}
	}
	
	private ArrayList<Field> getInitialSnake() {
		// Find initial snake position.
		Field center = board.getCenter();
		Field centerRight = new Field(center.getRow(), center.getColumn() + 1);
		ArrayList<Field> snakePositions = new ArrayList<Field>();
		snakePositions.add(center);
		snakePositions.add(centerRight);
		return snakePositions;
	}

}
