package snake.model;
import java.util.*;

public class SnakeFood {
	private SnakeGame game;
	private Field position;

	public SnakeFood(SnakeGame game){
		this.game = game;
		this.position = new Field(Random.nextInt(game.level.length+1), Random.nextInt(game.level.length+1));
	}
	
	public Field getPosition(){
		return position;
	}
}
