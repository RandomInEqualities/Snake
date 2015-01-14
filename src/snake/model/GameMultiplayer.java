package snake.model;

import java.util.ArrayList;
import java.util.Observable;

import snake.model.Game;


public class GameMultiplayer extends Observable implements Game {
	
	private enum State {
		START,
		RUN,
		PAUSE,
		END
	}
	
	private static final int DEFAULT_WIDTH = 20;
	private static final int DEFAULT_HEIGHT = 20;
	
	private State state;
	private Board board;
	private Snake snake1;
	private Snake snake2;
	private int score1;
	private int score2;
	private Food food;
	
	public GameMultiplayer() {
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	public GameMultiplayer(int width, int height) {
		this.board = new Board(width, height);
		this.snake1 = new Snake();
		this.snake2 = new Snake();
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
			setChanged();
			notifyObservers(Event.START);
		}
	}

	@Override
	public void pause() {
		if (state == State.RUN) {
			state = State.PAUSE;
			setChanged();
			notifyObservers(Event.PAUSE);
		}
	}

	@Override
	public void resume() {
		if (state == State.PAUSE) {
			state = State.RUN;
			setChanged();
			notifyObservers(Event.RESUME);
		}
	}

	@Override
	public void reset() {
		// Set the game variables.
		snake1.setup(getInitialSnake(Player.ONE));
		snake2.setup(getInitialSnake(Player.TWO));
		state = State.START;
		score1 = 0;
		score2 = 0;
		food = Food.generateRandomFood(snake1, snake2, board);
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
			return;
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

}
