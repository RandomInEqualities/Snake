package snake.model;
import java.util.Random;
import java.util.Observable;


public class Game extends Observable {
	
	private Snake snake;
	private Food food;
	private int width;
	private int height;
		
	private Random random = new Random();

	public Game() {
		this(100,100); //tile size
	}
	
	public Game(int width, int height) {
		super();
		this.width = width;
		this.height = height;
		
		if (width < 5 || 100 < width) {
			throw new IllegalArgumentException("invalid width " + width);
		}
		if (height < 5 || 100 < height) {
			throw new IllegalArgumentException("invalid height " + height);
		}
		snake = new Snake(this);
		food = generateFood(snake);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	public Food getFood() {
		return food;
	}
	
	public Snake getPlayer() {
		return snake;
	}
	
	private Food generateFood(Snake snake) {
		int x = 0;
		int y = 0;
		do {
			x = random.nextInt(width);
			y = random.nextInt(height);
		//} while (!snake.contains(x, y));
		} while (false);
		return new Food(new Field(y, x));
	}
	
}
