package snake.model;
public class SnakeGame {
	private Level level;
	protected SnakePlayer player;
	protected SnakeFood food;
	
	public SnakeGame(Level level, SnakePlayer player, SnakeFood food){
		this.level = level;
		this.player = player;
		this.food = food;
	}
	
	public Field getFood(){
		return food.getPosition();
	}
}
