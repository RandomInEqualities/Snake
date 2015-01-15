package snake.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;


/**
 * A snake located on board positions.
 */
public class Snake {
	
	private ArrayList<Field> positions;
	private Direction headDirection;
	
	public Snake() {
		this.positions = new ArrayList<Field>();
		this.headDirection = Direction.RIGHT;
	}

	public Snake(ArrayList<Field> snake) {
		this.positions = snake;
		this.headDirection = findHeadDirection(snake);
	}
	
	public Field getHead() {
		return positions.get(0);
	}
	
	public Field getNeck() {
		return positions.get(1);
	}
	
	public Field getTail() {
		return positions.get(positions.size() - 1);
	}
	
	public Direction getHeadDirection() {
		if (positions.size() < 2) {
			throw new IndexOutOfBoundsException("snake is too small");
		}
		return headDirection;
	}
	
	public List<Field> getPositions() {
		// Return a list that can't be changed. This prevents outside classes
		// from changing the snake list.
		return Collections.unmodifiableList(positions);
	}
	
	public int getSize() {
		return positions.size();
	}
	
	public boolean contains(Field position) {
		return positions.contains(position);
	}
	
	public boolean fills(Board board) {
		return getSize() == board.getSize();
	}
	
	/**
	 * Test if the snake is allow to move in a given direction. The snake can for example
	 * not move in its necks direction.
	 * @param direction the direction to test for
	 * @return true if it can move in direction, otherwise false.
	 */
	boolean validMoveDirection(Direction direction) {
		if (direction == Direction.getOppositeOf(headDirection)) {
			return false;
		}
		return true;
	}
	
	/**
	 * Move the snake. If the snake eat any food its tail will not move. Will return
	 * true if it tries to move into itself.
	 * 
	 * @param direction direction of the move.
	 * @param eatFood set to true if the snakes eats something, false otherwise.
	 * @return true if the snake tries to eat its own body, false otherwise.
	 */
	boolean move(Direction direction, boolean eatFood, Board board) {
		if (!validMoveDirection(direction)) {
			return false;
		}
		
		// Test if the snake eat its body.
		Field newHeadPosition = getNewHeadPosition(direction, board);
		if (positions.contains(newHeadPosition)) {
			int headIndex = positions.indexOf(newHeadPosition);
			int tailIndex = positions.size() - 1;
			if (headIndex != tailIndex || eatFood) {
				return true;
			}
		}
		// Move tail if snake does not eat anything.
		if (!eatFood) {
			positions.remove(positions.size() - 1);
		}
		positions.add(0, newHeadPosition);
		headDirection = direction;
		return false;
	}
	
	Field getNewHeadPosition(Direction moveDirection, Board board) {
		// Find the row and column of the new head position.
		Field currentHead = getHead();
		Field direction = directionToField(moveDirection);
		int newHeadRow = currentHead.getRow() + direction.getRow();
		int newHeadColumn = currentHead.getColumn() + direction.getColumn();
		
		// Make sure that the row and column is within the board, if not we
		// wrap them around it.
		return board.wrap(newHeadRow, newHeadColumn);
	}
	
	void setup(ArrayList<Field> snake) {
		positions = snake;
		headDirection = findHeadDirection(snake);
	}
	
	// Find the head direction from an array of fields.
	private Direction findHeadDirection(ArrayList<Field> snake) {
		Field head = snake.get(0);
		Field neck = snake.get(1);
		int rowDiff = head.getRow() - neck.getRow();
		int columnDiff = head.getColumn() - neck.getColumn();
		if (rowDiff == 0 && columnDiff == -1) {
			return Direction.LEFT;
		}
		else if (rowDiff == 0 && columnDiff == 1) {
			return Direction.RIGHT;
		}
		else if (rowDiff == -1 && columnDiff == 0) {
			return Direction.UP;
		}
		else if (rowDiff == 1 && columnDiff == 0) {
			return Direction.DOWN;
		}
		throw new IllegalArgumentException("snake array is malformed");
	}
	
	private static Field directionToField(Direction direction) {
		switch (direction) {
			case UP:
				return new Field(-1,0);
			case DOWN:
				return new Field(1,0);
			case LEFT:
				return new Field(0,-1);
			case RIGHT:
				return new Field(0,1);
			default:
				throw new IllegalArgumentException();
		}
	}

}
