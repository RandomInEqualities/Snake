
package snake.model;

import java.util.ArrayList;
import java.util.Random;

public class Food {
	
	private Field position;
	private static final Random random = new Random();

	public Food(Field position){
		if (position == null) {
			throw new NullPointerException();
		}
		this.position = position;
	}
	
	public Field getPosition(){
		return position;
	}
	
	public @Override String toString() {
		return "Food at " + position.toString();
	}
	
	public static Food generateRandomFood(Snake snake, Board board) {
		
		if (snake == null || board == null) {
			throw new NullPointerException();
		}
		int width = board.getWidth();
		int height = board.getHeight();
		Field foodPosition = null;
		
		// If the snake is small we select a random location until the
		// location is not occupied.
		if (2*snake.getSize() < width*height) {
			do {
				int column = random.nextInt(width);
				int row = random.nextInt(height);
				foodPosition = new Field(row, column);
			} while (snake.contains(foodPosition));
		}
		else {
			// Find all locations on the board that can contain food.
			ArrayList<Field> foodPositions = new ArrayList<Field>();
			for (int column = 0; column < width; column++) {
				for (int row = 0; row < height; row++) {
					Field position = new Field(row, column);
					if (!snake.contains(position)) {
						foodPositions.add(position);
					}
				}
			}
			
			// Select a random food location.
			if (foodPositions.size()!=0){
				int selection = random.nextInt(foodPositions.size());
				foodPosition = foodPositions.get(selection);
			} else {
				return new Food(new Field(101,101)); //return unattainable food
			}
		}

		return new Food(foodPosition);
	}
	
}
