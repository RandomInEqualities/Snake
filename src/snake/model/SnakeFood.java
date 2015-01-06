package snake.model;
import java.util.*;

public class SnakeFood extends Observable {
	private Field position;

	public SnakeFood(Field position){
		this.position = position;
	}
	
	public Field getPosition(){
		setChanged();
	 	notifyObservers();
		return position;
	}
}
