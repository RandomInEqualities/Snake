package snake.model;

public class Level {
	
	private int width;
	private int height;
	private static final int DEFAULT_WIDTH = 10;
	private static final int DEFAULT_HEIGHT = 10;
	
	public Level() {
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	public Level(int width, int height) {
		if (width < 5 || 100 < width) {
			throw new IllegalArgumentException("invalid width " + width);
		}
		if (height < 5 || 100 < height) {
			throw new IllegalArgumentException("invalid height " + height);
		}
		this.width = width;
		this.height = height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}
