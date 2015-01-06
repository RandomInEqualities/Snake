package snake.model;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Random;
import java.util.Observable;


public class Game extends Observable {
	
	private Snake snake;
	private Food food;
	private Dimension size;
	public boolean gameOver;
		
	private Random random = new Random();

	public Game() {
		this(new Dimension(100, 100));
	}
	
	public Game(Dimension size) {
		super();
		
		if (size.width < 5 || 100 < size.width) {
			throw new IllegalArgumentException("invalid width " + size.width);
		}
		if (size.height < 5 || 100 < size.height) {
			throw new IllegalArgumentException("invalid height " + size.height);
		}
		
		this.size = size;
		this.snake = new Snake(this);
		this.food = generateFood(snake);
	}
	
	public Dimension getSize() {
		return size;
	}
	
	public Food getFood() {
		return food;
	}
	
	public Snake getPlayer() {
		return snake;
	}
	
	public void checkAction(){
		if (snake.getAction() == Action.EAT){
			snake.getSnake().add(0, food.getPosition());
			Score.score++;
			food = generateFood(snake);
			snake.setAction(Action.MOVE);
		} else if (snake.getAction() == Action.KILL){
			System.out.println("dead");
			Score.score = 0;
			gameOver = true;
		}
	}
	private Food generateFood(Snake snake) {
		// If the snake is small we select random locations until the
		// location is not occupied.
		if (snake.getSnake().size() < size.width*size.height/3) {
			Field position;
			do {
				int x = random.nextInt(size.width);
				int y = random.nextInt(size.height);
				position = new Field(x, y);
			} while (snake.getSnake().contains(position));
			
			return new Food(position);
		}
		else {
			// Find all locations on the board that can contain food.
			ArrayList<Field> foodPositions = new ArrayList<Field>();
			for (int width = 0; width < size.width; width++) {
				for (int height = 0; height < size.width; height++) {
					Field position = new Field(width, height);
					if (snake.getSnake().contains(position)) {
						continue;
					}
					if (food.getPosition().equals(position)) {
						continue;
					}
					foodPositions.add(position);
				}
			}
			
			// Select a random food location.
			int selection = random.nextInt(foodPositions.size());
			return new Food(foodPositions.get(selection));
		}
	}
	
}
