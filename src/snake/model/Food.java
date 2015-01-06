package snake.model;
import java.util.*;

public class Food extends Observable {
	private Field position;

	public Food(Field position){
		this.position = position;
	}
	
	public Field getPosition(){
		setChanged();
	 	notifyObservers();
		return position;
	}
}
