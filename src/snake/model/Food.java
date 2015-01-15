
package snake.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Class representing a food object that a snake can eat.
 */
public class Food {
	
	private static final Random random = new Random();
	
	private final Field position;

	public Food(Field position){
		if (position == null) {
			throw new NullPointerException();
		}
		this.position = position;
	}
	
	public Field getPosition(){
		return position;
	}
	
	@Override
	public String toString() {
		return "Food at " + position.toString();
	}
	
	public static Food generateRandomFood(Snake snake, Board board) {
		List<Field> snakeFields = new ArrayList<Field>();
		snakeFields.addAll(snake.getPositions());
		return generateFood(snakeFields, board);
	}
	
	public static Food generateRandomFood(Snake snake1, Snake snake2, Board board) {
		List<Field> snakeFields = new ArrayList<Field>();
		snakeFields.addAll(snake1.getPositions());
		snakeFields.addAll(snake2.getPositions());
		return generateFood(snakeFields, board);
	}
	
	private static Food generateFood(List<Field> snakeFields, Board board) {
		if (snakeFields == null || board == null) {
			throw new NullPointerException();
		}
		
		int width = board.getWidth();
		int height = board.getHeight();
		Field foodPosition;
		
		// If the snake is small we select a random location until the
		// location is not occupied.
		if (2*snakeFields.size() < board.getSize()) {
			do {
				int column = random.nextInt(width);
				int row = random.nextInt(height);
				foodPosition = new Field(row, column);
				System.out.println(snakeFields);
				System.out.println(foodPosition);
			} while (snakeFields.contains(foodPosition));
		}
		else {
			// Find all locations on the board that can contain food.
			ArrayList<Field> foodPositions = new ArrayList<Field>();
			for (int column = 0; column < width; column++) {
				for (int row = 0; row < height; row++) {
					Field position = new Field(row, column);
					if (!snakeFields.contains(position)) {
						foodPositions.add(position);
					}
				}
			}
			
			// Select a random food location.
			if (foodPositions.size() == 0) {
				return null;
			}
			int selection = random.nextInt(foodPositions.size());
			foodPosition = foodPositions.get(selection);
		}
		return new Food(foodPosition);
	}
	
}
