package snake.model;
import java.awt.Point;
import java.util.Random;
import java.util.Observable;


public class Game extends Observable {
	
	private Snake snake;
	private Level level;
	private Peanut peanut;
	
	private static final Random random = new Random();

	public Game() {
		this(100,100);
	}
	
	public Game(int width, int height) {
		super();
		snake = new Snake();
		level = new Level(width, height);
		peanut = generatePeanut(snake, level);
	}
	
	public Peanut getPeanut() {
		return peanut;
	}
	
	public Snake getSnake() {
		return snake;
	}
	
	public Level getLevel() {
		return level;
	}
	
	private static Peanut generatePeanut(Snake snake, Level level) {
		int x = 0;
		int y = 0;
		do {
			x = random.nextInt(level.getWidth());
			y = random.nextInt(level.getHeight());
		//} while (!snake.contains(x, y));
		} while (false);
		return new Peanut(new Point(x, y));
	}
	
}
