
package snake.model;

import java.awt.Dimension;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class Snake {
	
	public enum Move {
		EAT_NECK,
		EAT_BODY,
		EAT_FOOD,
		NORMAL
	}
	
	private Game game;
	private ArrayList<Field> positions;

	public Snake(Game game) {
		if (game == null) {
			throw new NullPointerException();
		}
		this.game = game;
		this.positions = new ArrayList<>();
		
		// Construct the initial snake.
		createStartingSnake();
	}

	public void move(Direction direction) {
		
		// Find the position to move into.
		Field movePosition = findMovePosition(direction);
		
		// Test if the snakes neck blocks the move.
		Field neckPosition = positions.get(1);
		if (movePosition.equals(neckPosition)) {
			game.snakeHasMoved(Move.EAT_NECK);
			return;
		}
		
		// Test if the snake eats its body.
		Field tailPosition = positions.get(positions.size() - 1);
		if (positions.contains(movePosition) && !movePosition.equals(tailPosition)) {
			game.snakeHasMoved(Move.EAT_BODY);
			return;
		}
		
		// Move to the new position.
		positions.add(0, movePosition);
		
		// Test if the snake east any food.
		if (movePosition.equals(game.getFood().getPosition())) {
			game.snakeHasMoved(Move.EAT_FOOD);
			return;
		} 
		
		// Remove the snakes tail.
		positions.remove(positions.size() - 1);
		
		game.snakeHasMoved(Move.NORMAL);
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
	
	public int getSize() {
		return positions.size();
	}
	
	public List<Field> getPositions() {
		return Collections.unmodifiableList(positions);
	}
	
	public boolean contains(Field position) {
		return positions.contains(position);
	}
	
	void createStartingSnake() {
		Dimension board = game.getBoardSize();
		Field initialHead = new Field((board.height-1)/2, (board.width-1)/2);
		Field initialTail = new Field((board.height-1)/2, (board.width-1)/2+1);
		positions.clear();
		positions.add(initialHead);
		positions.add(initialTail);
	}
	
	private Field findMovePosition(Direction direction) {

		// Find the move position.
		Field head = getHead();
		Field difference = directionToField(direction);
		int moveRow = head.getRow() + difference.getRow();
		int moveColumn = head.getColumn() + difference.getColumn();
		
		// The board is a torus and we wrap the move positions around the it.
		Dimension board = game.getBoardSize();
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
