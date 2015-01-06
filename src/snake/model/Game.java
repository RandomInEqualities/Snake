package snake.model;
import java.util.Random;
import java.util.Observable;


public class Game extends Observable {
	
	private SnakePlayer snake;
	private SnakeFood food;
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
		snake = new SnakePlayer(this);
		food = generateFood(snake);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	public SnakeFood getFood() {
		return food;
	}
	
	public SnakePlayer getPlayer() {
		return snake;
	}
	
	private SnakeFood generateFood(SnakePlayer snake) {
		int x = 0;
		int y = 0;
		do {
			x = random.nextInt(width);
			y = random.nextInt(height);
		//} while (!snake.contains(x, y));
		} while (false);
		return new SnakeFood(new Field(y, x));
	}
	
}
