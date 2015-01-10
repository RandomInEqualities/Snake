
package snake.model;

import java.util.Observable;

import snake.control.ControlKeys;
import snake.control.ControlGame;
import snake.control.ControlTimer;
import snake.view.Audio;


public class Game extends Observable {
	
	public enum State {
		RUNNING,
		WON,
		LOST,
		PAUSED
	}
	
	public enum Event {
		MOVE,
		EAT,
		DIE,
		RESTART
	}
	
	private State state;
	private int score;
	private Board board;
	private Snake snake;
	private Food food;
	private int width;
	private int height;
	private Audio audio;
	public boolean isMuted;
	private static final int DEFAULT_WIDTH = 10;
	private static final int DEFAULT_HEIGHT = 10;

	public Game() {
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	public Game(int width, int height) {
		super();
		this.width = width;
		this.height = height;
		this.audio = new Audio(this);
		this.isMuted = false;
		this.state = State.RUNNING;
		this.score = 0;
		this.board = new Board(width, height);
		this.snake = new Snake(this.board);
		this.food = Food.generateRandomFood(this.snake, this.board);

	}
	
	public void setSize(int width, int height){
		this.board.setDimension(width, height);
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
	
	public void setState(State state){
		this.state = state;
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
			food = Food.generateRandomFood(snake, board);
		}
		
		if (snakeEatsItSelf) {
			state = State.LOST;
		}
		else if (snake.fillsBoard()) {
			state = State.WON;
		}
		
		// Notify the observing classes that the game changed. We send an argument
		// with the type of event that happened.
		Event event = null;
		if (getState() == State.RUNNING) {
			if (snakeEatsItSelf) {
				event = Event.DIE;
			}
			else if (snakeEatsFood) {
				event = Event.EAT;
			}
			else {
				event = Event.MOVE;
			}
		} 
		setChanged();
		notifyObservers(event);
	}
	
	public void restart() {
		state = State.RUNNING;
		score = 0;
		snake.setupStartingSnake();
		food = Food.generateRandomFood(snake, board);
		
		// Notify classes that the game changed.
		setChanged();
		notifyObservers(Event.RESTART);
	}
}
