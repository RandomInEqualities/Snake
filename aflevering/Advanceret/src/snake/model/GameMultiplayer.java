package snake.model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Timer;


/**
 * The model/class for playing multiplayer snake.
 */
public class GameMultiplayer extends Game implements ActionListener {
	
	private static final int DEFAULT_WIDTH = 20;
	private static final int DEFAULT_HEIGHT = 20;
	private static final int TIMER_UPDATE_INTERVAL = 15;
	private static final int TIMER_INITIAL_DELAY = 500;
	
	private State state;
	private Board board;
	private Snake snakePlayerOne;
	private Snake snakePlayerTwo;
	private int scorePlayerOne;
	private int scorePlayerTwo;
	private Player winner;
	private Food food;

	// Variables for implementing continuous snake movement.
	private Timer timerPlayerOne;
	private Timer timerPlayerTwo;
	boolean timerEnabled = true;
	private int timerUpdateInterval = 200;
	private long timerLastUpdatePlayerOne = 0;
	private long timerLastUpdatePlayerTwo = 0;
	
	public GameMultiplayer() {
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	public GameMultiplayer(int width, int height) {
		super();
		this.board = new Board(width, height);
		this.snakePlayerOne = new Snake();
		this.snakePlayerTwo = new Snake();
		
		// Create a timer object that send an ActionEvent to this class, in a periodic interval.
		this.timerPlayerOne = new Timer(TIMER_UPDATE_INTERVAL, this);
		this.timerPlayerTwo = new Timer(TIMER_UPDATE_INTERVAL, this);
		this.timerPlayerOne.setActionCommand("player1");
		this.timerPlayerTwo.setActionCommand("player2");
		this.timerPlayerTwo.setInitialDelay(TIMER_INITIAL_DELAY);
		this.timerPlayerOne.setInitialDelay(TIMER_INITIAL_DELAY);
		reset();
	}
	
	public Board getBoard() {
		return board;
	}
	
	public Food getFood() {
		return food;
	}
	
	public Snake getSnake(Player player) {
		if (player == Player.ONE) {
			return snakePlayerOne;
		}
		else if (player == Player.TWO) {
			return snakePlayerTwo;
		}
		else {
			throw new IllegalArgumentException("unknown player");
		}
	}
	
	public int getScore(Player player) {
		if (player == Player.ONE) {
			return scorePlayerOne;
		}
		else if (player == Player.TWO) {
			return scorePlayerTwo;
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
	
	public boolean isTie() {
		if (isEnded() && winner == Player.NONE) {
			return true;
		}
		return false;
	}
	
	public boolean isTimedMovementEnabled() {
		return timerEnabled;
	}

	@Override
	public void start() {
		if (state == State.START) {
			state = State.RUN;
			timerPlayerOne.start();
			timerPlayerTwo.start();
			setChanged();
			notifyObservers(new Event(Event.Type.START));
		}
	}

	@Override
	public void pause() {
		if (state == State.RUN) {
			state = State.PAUSE;
			timerPlayerOne.stop();
			timerPlayerTwo.stop();
			setChanged();
			notifyObservers(new Event(Event.Type.PAUSE));
		}
	}

	@Override
	public void resume() {
		if (state == State.PAUSE) {
			state = State.RUN;
			timerPlayerOne.start();
			timerPlayerTwo.start();
			setChanged();
			notifyObservers(new Event(Event.Type.RESUME));
		}
	}

	@Override
	public void reset() {
		// Set the game variables.
		snakePlayerOne.setup(getInitialSnake(Player.ONE));
		snakePlayerTwo.setup(getInitialSnake(Player.TWO));
		state = State.START;
		winner = Player.NONE;
		scorePlayerOne = 0;
		scorePlayerTwo = 0;
		food = Food.generateRandomFood(snakePlayerOne, snakePlayerTwo, board);
		timerPlayerOne.stop();
		timerPlayerTwo.stop();
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
	
	public void setBoardSize(int width, int height){
		board = new Board(width, height);
		reset();
	}
	
	public void move(Player player, Direction moveDirection) {
		if (state != State.RUN) {
			return;
		}
		
		Snake snake = getSnake(player);
		if (snake.isNeckDirection(moveDirection)) {
			return;
		}
		
		Player opponent = (player == Player.ONE) ? Player.TWO : Player.ONE;
		Snake opponenetSnake = getSnake(opponent);
		Field newHeadPosition = snake.getNextHeadPosition(moveDirection, board);
		if (opponenetSnake.contains(newHeadPosition)) {
			state = State.END;
			
			// The opponent wins if this snake moves into the opponents body. If
			// their heads collide we call it a tie.
			if (!opponenetSnake.getHead().equals(newHeadPosition)) {
				winner = opponent;
			}
			else {
				winner = Player.NONE;
			}
		}
	
		boolean snakeEatsFood = newHeadPosition.equals(food.getPosition());
		boolean snakeEatsItSelf = snake.move(moveDirection, snakeEatsFood, board);
		if (isBoardFull()) {
			state = State.END;
			winner = Player.NONE;
		}
		else if (snakeEatsItSelf) {
			state = State.END;
			winner = opponent;
		}
		else if (snakeEatsFood) {
			food = Food.generateRandomFood(snakePlayerOne, snakePlayerTwo, board);
		}
		
		// Increment score.
		if (snakeEatsFood) {
			if (player == Player.ONE) {
				scorePlayerOne++;
			} 
			else {
				scorePlayerTwo++;
			}
		}
		
		// Notify the observing classes that the game changed. Send an argument with 
		// the type of event that happened.
		Event event;
		if (state == State.END) {
			if (winner == Player.NONE) {
				event = new Event(Event.Type.TIE);
			}
			else {
				event = new Event(Event.Type.WIN, winner);
			}
		}
		else if (snakeEatsFood) {
			event = new Event(Event.Type.EAT, player);
		}
		else {
			event = new Event(Event.Type.MOVE, player);
		}
		setChanged();
		notifyObservers(event);
		
		if (player == Player.ONE) {
			timerLastUpdatePlayerOne = System.currentTimeMillis();
		}
		else {
			timerLastUpdatePlayerTwo = System.currentTimeMillis();
		}
	}
	
	private boolean isBoardFull() {
		return (snakePlayerOne.getSize() + snakePlayerTwo.getSize()) == board.getSize();
	}
	
	private ArrayList<Field> getInitialSnake(Player player) {
		ArrayList<Field> snakePositions = new ArrayList<Field>();
		Field center = board.getCenter();
		if (player == Player.ONE) {
			Field centerRight = new Field(center.getRow(), center.getColumn() + 1);
			snakePositions.add(center);
			snakePositions.add(centerRight);
		} 
		else {
			Field centerUp = new Field(center.getRow() - 1, center.getColumn());
			Field centerUpLeft= new Field(center.getRow() - 1, center.getColumn() - 1);
			snakePositions.add(centerUp);
			snakePositions.add(centerUpLeft);
		}
		return snakePositions;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (!timerEnabled || state != State.RUN) {
			return;
		}
		
		long currentTime = System.currentTimeMillis();
		long elapsedTimePlayerOne = currentTime - timerLastUpdatePlayerOne;
		long elapsedTimePlayerTwo = currentTime - timerLastUpdatePlayerTwo;
		
		String actionCommand = event.getActionCommand();
		if (actionCommand == "player1" && elapsedTimePlayerOne > timerUpdateInterval) {
			move(Player.ONE, snakePlayerOne.getHeadDirection());
			timerLastUpdatePlayerOne = currentTime;
		}
		else if (actionCommand == "player2" && elapsedTimePlayerTwo > timerUpdateInterval) {
			move(Player.TWO, snakePlayerTwo.getHeadDirection());
			timerLastUpdatePlayerTwo = currentTime;
		}
		
	}

}
