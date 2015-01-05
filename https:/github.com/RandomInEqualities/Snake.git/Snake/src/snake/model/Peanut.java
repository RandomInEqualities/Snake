package snake.model;
import java.awt.Point;

public class Peanut {
	
	private Point position;

	public Peanut(Point position) {
		if (position == null) {
			throw new NullPointerException();
		}
		this.position = position;
	}
	
	public Point getPosition() {
		return position;
	}
	
}
