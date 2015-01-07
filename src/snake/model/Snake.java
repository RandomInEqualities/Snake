
package snake.model;

import java.awt.Dimension;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class Snake {
	
	public enum Move {
		EAT_NECK,
		EAT_TAIL,
		EAT_FOOD,
		NORMAL
	}
	
	private Dimension board;
	private ArrayList<Field> positions;

	public Snake(Dimension board) {
		if (board == null) {
			throw new NullPointerException();
		}
		if (board.width <= 0 || board.height <= 0) {
			throw new IllegalArgumentException();
		}
		this.board = board;
		this.positions = new ArrayList<>();
		
		// Construct the initial snake.
		Field initialHead = new Field(board.width/2, board.height/2);
		Field initialTail = new Field(board.width/2 + 1, board.height/2);
		positions.add(initialHead);
		positions.add(initialTail);
	}

	public Move makeMove(Direction direction, Food food) {
		
		// Find the position to move into.
		Field movePosition = findMovePosition(direction);
		
		// Test if the snakes neck blocks the move.
		Field neckPosition = positions.get(1);
		if (movePosition.equals(neckPosition)) {
			return Move.EAT_NECK;
		}
		
		// Test if the snake eats its tail.
		if (positions.contains(movePosition)) {
			return Move.EAT_TAIL;
		}
		
		// Move to the new position.
		positions.add(0, movePosition);
		
		// Test if the snake east any food.
		if (movePosition.equals(food.getPosition())) {
			return Move.EAT_FOOD;
		} 
		
		// Remove the snakes tail.
		positions.remove(positions.size() - 1);
		
		return Move.NORMAL;
	}
	
	public Field getHead() {
		return positions.get(0);
	}
	
	public Field getTail() {
		return positions.get(positions.size() - 1);
	}
	
	public int getSize() {
		return positions.size();
	}
	
	public List<Field> getPositions() {
		return Collections.unmodifiableList(positions);
	}
	
	public boolean contains(Field position) {
		return positions.contains(position);
	}
	
	private Field findMovePosition(Direction direction) {

		// Find the move position.
		Field head = getHead();
		Field difference = directionToField(direction);
		int moveRow = head.getRow() + difference.getRow();
		int moveColumn = head.getColumn() + difference.getColumn();
		
		// The board is a torus and we wrap the move positions around the it.
		if (moveRow < 0) {
			moveRow = board.height - 1;
		}
		else if (board.height <= moveRow) {
			moveRow = 0;
		}
		
		if (moveColumn < 0) {
			moveColumn = board.width - 1;
		}
		else if (board.width <= moveColumn) {
			moveColumn = 0;
		}
		
		return new Field(moveRow, moveColumn);
	}
	
	private Field directionToField(Direction direction) {
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
				throw new IllegalArgumentException("unknown direction");
		}
	}
	
}
