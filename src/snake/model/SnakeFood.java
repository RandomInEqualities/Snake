package snake.model;
import java.util.*;

public class SnakeFood {
	private Field position;

	public SnakeFood(Field position){
		this.position = position;
	}
	
	public Field getPosition(){
		return position;
	}
}
