package snake.model;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;


public class Snake {
	
	private Board board;
	private Direction headDirection;
	private ArrayList<Field> positions;
	private ArrayList<Body> body;
	public Snake(Board board) {
		if (board == null) {
			throw new NullPointerException();
		}
		
		this.board = board;
		this.positions = new ArrayList<>();
		this.body = new ArrayList<>();
		setupStartingSnake();
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
		return headDirection;
	}
	
	public List<Field> getPositions() {
		// Return an array that can't be changed. This prevent outside classes
		// from changing the snake array.
		return Collections.unmodifiableList(positions);
	}
	
	public List<Body> getBody() {
		return body;
	}
	
	public int getSize() {
		return positions.size();
	}
	
	public boolean contains(Field position) {
		return positions.contains(position);
	}
	
	public boolean fillsBoard() {
		return getSize() == board.getSize();
	}
	
	/**
	 * Move the snake. If the snake eat any food its tail will not move. The 
	 * snake's head moves in the given direction. If the direction is towards
	 * the snake's neck the head will not move.
	 * 
	 * @param moveDirection direction of the move.
	 * @param eatFood set to true if the snakes eats something, false otherwise.
	 * @return true if the snake tries to eat its own body, false otherwise.
	 */
	boolean move(Direction moveDirection, boolean eatFood) {
		
		// Test if the snakes neck blocks the move.
		if (moveDirection == getOppositeOf(headDirection)) {
			return false;
		}
		
		// Find the position to move into.
		Field newHeadPosition = getNewHeadPosition(moveDirection);
		
		// Remove tail if snake does not eat anything.
		if (!eatFood) {
			positions.remove(positions.size() - 1);
		}
		
		// Test if the snake eat its body.
		if (positions.contains(newHeadPosition)) {
			return true;
		}
		
		// Move to the new position.
		positions.add(0, newHeadPosition);
		headDirection = moveDirection;
		
		return false;
	}
	
	Field getNewHeadPosition(Direction moveDirection) {
		// Find the row and column of the new head position.
		Field currentHead = getHead();
		Field direction = getDirectionAsField(moveDirection);
		int newHeadRow = currentHead.getRow() + direction.getRow();
		int newHeadColumn = currentHead.getColumn() + direction.getColumn();
		
		// Make sure that the row and column is within the board, if not we
		// wrap them around it.
		return board.wrap(newHeadRow, newHeadColumn);
	}
	
	void setupStartingSnake() {
		Field center = board.getCenter();
		Field head = new Field(center.getRow(), center.getColumn());
		Field tail = new Field(center.getRow(), center.getColumn() + 1);
		headDirection = Direction.LEFT;
		body.add(new Body());
		body.get(0).prevLink = Direction.LEFT;
		positions.clear();
		positions.add(head);
		positions.add(tail);
	}
	
	private static Field getDirectionAsField(Direction direction) {
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
	
	private static Direction getOppositeOf(Direction direction) {
		switch (direction) {
			case UP:
				return Direction.DOWN;
			case DOWN:
				return Direction.UP;
			case LEFT:
				return Direction.RIGHT;
			case RIGHT:
				return Direction.LEFT;
			default:
				throw new IllegalArgumentException();
		}
	}
	
}
