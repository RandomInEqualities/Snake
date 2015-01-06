package snake.model;
import java.util.Random;
import java.util.Observable;


public class Game extends Observable {
	
	private SnakePlayer snake;
	private Level level;
	private SnakeFood food;
		
	private static final Random random = new Random();

	public Game() {
		this(100,100); //tile size
	}
	
	public Game(int width, int height) {
		super();
		level = new Level(width, height);
		snake = new SnakePlayer(this);
		food = generateFood(snake, level);
	}
	
	public SnakeFood getFood() {
		return food;
	}
	
	public SnakePlayer getPlayer() {
		return snake;
	}
	
	public Level getLevel() {
		return level;
	}
	
	private static SnakeFood generateFood(SnakePlayer snake, Level level) {
		int x = 0;
		int y = 0;
		do {
			x = random.nextInt(level.getWidth());
			y = random.nextInt(level.getHeight());
		//} while (!snake.contains(x, y));
		} while (false);
		return new SnakeFood(new Field(y, x));
	}
	
}
