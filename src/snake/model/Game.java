package snake.model;

import java.util.*;


public class Game extends Observable {

	public enum State {
		START,
		PAUSE,
		RUN,
		END,
		WIN,
		LOSE
	}
	
	public enum Event {
		MOVE,
		EAT,
		DIE,
		WIN,
		START,
		PAUSE,
		UNPAUSE
	}
	
	protected State state;
	private Board board;
	private ArrayList<Snake> snakes;
	private ArrayList<Integer> scores;
	private ArrayList<Food> foods;
	protected static final int DEFAULT_WIDTH = 20;
	protected static final int DEFAULT_HEIGHT = 20;
	private static final Random random = new Random();
	
	public Game() {
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	public Game(int width, int height) {
		super();
		this.state = State.START;
		this.board = new Board(width, height);
		this.snakes = new ArrayList<Snake>();
		this.scores = new ArrayList<Integer>();
		this.foods = new ArrayList<Food>();
	}
	
	public State getState() {
		return state;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public List<Snake> getSnakes() {
		return Collections.unmodifiableList(snakes);
	}
	
	public List<Integer> getScores() {
		return Collections.unmodifiableList(scores);
	}
	
	public List<Food> getFoods() {
		return Collections.unmodifiableList(foods);
	}
	
	public boolean isPaused() {
		return state == State.PAUSE;
	}
	
	public void pause() {
		if (state == State.RUN) {
			state = State.PAUSE;
			setChanged();
			notifyObservers(Event.PAUSE);
		}
	}
	
	public void unPause() {
		if (state == State.PAUSE) {
			state = State.RUN;
			setChanged();
			notifyObservers(Event.UNPAUSE);
		}
	}
	
	/**
	 * Move a snake in a specified direction. The specified snake must be in the game.
	 * This move implementation is bare bones. Consider extending this class and 
	 * implementing new custom snake movement/game play logic.
	 * 
	 * @param movingSnake the snake to move. It must be part of the board.
	 * @param moveDirection the direction to move the snake in.
	 */
	protected void move(Snake movingSnake, Direction moveDirection) {
		
		// Test that the snake can move.
		int movingSnakeIndex = snakes.indexOf(movingSnake);
		if (movingSnakeIndex == -1) {
			throw new IllegalArgumentException("snake is not in the game");
		}
		
		if (state != State.RUN) {
			return;
		}
		
		// Snake and only move forward, left and right.
		if (!movingSnake.canMove(moveDirection)) {
			return;
		}
		
		// Snake can not move into other snakes.
		Field newHeadPosition = movingSnake.getNewHeadPosition(moveDirection);
		for (Snake snake : snakes) {
			if (snake != movingSnake && snake.contains(newHeadPosition)) {
				return;
			}
		}
		
		// Are we eating food?
		boolean eatsFood = false;
		for (int index = 0; index < foods.size(); index++) {
			Food food = foods.get(index);
			if (food.getPosition().equals(newHeadPosition)) {
				foods.remove(food);
				generateSingleRandomFood();
				scores.set(movingSnakeIndex, scores.get(movingSnakeIndex) + 1);
				eatsFood = true;
				break;
			}
		}
		
		// Move the snake.
		boolean eatsItself = movingSnake.move(moveDirection, eatsFood);
		if (eatsItself) {
			state = State.END;
		}
		
		// Check win state.
		int totalOccupiedFields = 0;
		for (Snake snake : snakes) {
			totalOccupiedFields += snake.getSize();
		}
		if (totalOccupiedFields == board.getSize()) {
			state = State.END;
		}

		// Notify the observing classes that the game changed. Send an argument with 
		// the type of event that happened.
		Event event = null;
		if (eatsItself) {
			event = Event.DIE;
		}
		else if (eatsFood) {
			event = Event.EAT;
		}
		else {
			event = Event.MOVE;
		}
		
		setChanged();
		notifyObservers(event);
	}
	
	protected int getSnakeAmount() {
		return snakes.size();
	}
	
	protected int getFoodAmount() {
		return foods.size();
	}
	
	protected Snake getSnake(int index) {
		return snakes.get(index);
	}
	
	protected Food getFood(int index) {
		return foods.get(index);
	}
	
	protected int getScore(int index) {
		return scores.get(index);
	}
	
	protected void setState(State newState) {
		state = newState;
	}
	
	protected void setBoard(Board newBoard) {
		emptyBoard();
		board = newBoard;
	}
	
	protected void setSnake(int index, Snake snake) {
		snakes.set(index, snake);
	}
	
	protected void setFood(int index, Food food) {
		foods.set(index, food);
	}
	
	protected void setScore(int index, int score) {
		scores.set(index, score);
	}

	protected void add(Snake snake) {
		snakes.add(snake);
		scores.add(0);
	}
	
	protected void add(Food food) {
		foods.add(food);
	}
	
	protected void removeSnake(int index) {
		snakes.remove(index);
		scores.remove(index);
	}
	
	protected void removeFood(int index) {
		foods.remove(index);
	}
	
	/**
	 * Removes all snakes and foods from the board.
	 */
	protected void emptyBoard() {
		snakes.clear();
		scores.clear();
		foods.clear();
	}
	
	protected void generateRandomFood(int amount) {
		for (int food = 0; food < amount; food++) {
			generateSingleRandomFood();
		}
	}
	
	protected void generateSingleRandomFood() {
		// Find all locations on the board that can contain food.
		int width = board.getWidth();
		int height = board.getHeight();
		ArrayList<Field> foodPositions = new ArrayList<Field>();
		for (int column = 0; column < width; column++) {
			for (int row = 0; row < height; row++) {
				
				Field position = new Field(row, column);
				boolean occupied = false;
				
				for (Food food : foods) {
					if (food.getPosition().equals(position)) {
						occupied = true;
						break;
					}
				}
				
				for (Snake snake : snakes) {
					if (occupied || snake.contains(position)) {
						occupied = true;
						break;
					}
				}
				
				if (!occupied) {
					foodPositions.add(position);
				}
			}
		}
		
		// Select a random food location.
		int selection = random.nextInt(foodPositions.size());
		Field foodPosition = foodPositions.get(selection);
		add(new Food(foodPosition));
	}
	
	protected void incrementScore(int index) {
		scores.set(index, scores.get(0) + 1);
	}
	
}
