package snake.model;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.Timer;

import snake.model.Game;
import snake.model.GameSingleplayer;


public class GameMultiplayer extends Observable implements Game, ActionListener {
	
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
	private Snake snake1;
	private Snake snake2;
	private int score1, score2, speedIncrease;
	private Food food;
	private Player winner = null;

	// Variables for implementing continuous snake movement.
	private Timer timer;
	boolean timerEnabled = true;
	private int timerUpdateInterval = 200;
	private long timerLastUpdateTime = 0;
	
	public GameMultiplayer() {
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT, null);
	}
	
	public GameMultiplayer(int width, int height, ArrayList<Field> walls) {
		this.board = new Board(width, height, walls);
		this.snake1 = new Snake();
		this.snake2 = new Snake();
		
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
	
	public boolean isTimedMovementEnabled() {
		return timerEnabled;
	}
	
	public Snake getSnake(Player player) {
		if (player == Player.ONE) {
			return snake1;
		}
		else if (player == Player.TWO) {
			return snake2;
		}
		else {
			throw new IllegalArgumentException("unknown player");
		}
	}
	
	public int getScore(Player player) {
		if (player == Player.ONE) {
			return score1;
		}
		else if (player == Player.TWO) {
			return score2;
		}
		else {
			throw new IllegalArgumentException("unknown player");
		}
	}
	
	public Player getWinner() {
		return winner;
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
		snake1.setup(getInitialSnake(Player.ONE));
		snake2.setup(getInitialSnake(Player.TWO));
		state = State.START;
		timer.start();
		score1 = 0;
		score2 = 0;
		food = Food.generateRandomFood(snake1, snake2, board);
		timerUpdateInterval -= speedIncrease;
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
	
	public void move(Player player, Direction moveDirection) {
		if (state != State.RUN) {
			return;
		}
		
		Snake snake = getSnake(player);
		if (!snake.validMoveDirection(moveDirection)) {
			return;
		}
		
		Field newHeadPosition = snake.getNewHeadPosition(moveDirection, board);
		if (getSnake(player == Player.ONE ? Player.TWO : Player.ONE).contains(newHeadPosition)) {
			state = State.END;
		}
		
		if(getSnake(player.ONE).contains(snake2.getNewHeadPosition(moveDirection, board))) {
			winner = player.ONE;
		} else {
			winner = player.TWO;
		}
	
		boolean snakeEatsFood = newHeadPosition.equals(food.getPosition());
		if (snakeEatsFood) {
			if (player == Player.ONE) {
				score1++;
			} 
			else {
				score2++;
			}
			food = Food.generateRandomFood(snake1, snake2, board);
		}
		
		boolean snakeEatsItSelf = snake.move(moveDirection, snakeEatsFood, board);
		if (snakeEatsItSelf) {
			state = State.END;
		}
		
		// Notify the observing classes that the game changed. Send an argument with 
		// the type of event that happened.
		Event event;
		if (snakeEatsItSelf) {
			event = new Event(Event.WIN, player);
		}
		else if (snakeEatsFood) {
			event = new Event(Event.EAT, player);
		}
		else {
			event = new Event(Event.MOVE, player);
		}
		timerLastUpdateTime = System.currentTimeMillis();
		setChanged();
		notifyObservers(event);
	}
	
	private ArrayList<Field> getInitialSnake(Player player) {
		ArrayList<Field> snakePositions = new ArrayList<Field>();
		Field center = board.getCenter();
		if (player == Player.ONE) {
			Field centerRIGHT = new Field(center.getRow(), center.getColumn() + 1);
			snakePositions.add(center);
			snakePositions.add(centerRIGHT);
		} 
		else {
			Field centerUP = new Field(center.getRow() - 1, center.getColumn());
			Field centerUPLEFT= new Field(center.getRow() - 1, center.getColumn() - 1);
			snakePositions.add(centerUP);
			snakePositions.add(centerUPLEFT);
		}
		return snakePositions;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		long currentTime = System.currentTimeMillis();
		long elapsedTime = currentTime - timerLastUpdateTime;
		if (state == State.RUN && timerEnabled && elapsedTime > timerUpdateInterval) {
			move(Player.ONE, snake1.getHeadDirection());
			move(Player.TWO, snake2.getHeadDirection());
			timerLastUpdateTime = currentTime;
		}
	}

}
