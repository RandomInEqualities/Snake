
package snake.model;

public class Food {
	
	private Field position;

	public Food(Field position){
		if (position == null) {
			throw new NullPointerException();
		}
		this.position = position;
	}
	
	public Field getPosition(){
		return position;
	}
	
	public @Override String toString() {
		return "Food at " + position.toString();
	}
	
}
