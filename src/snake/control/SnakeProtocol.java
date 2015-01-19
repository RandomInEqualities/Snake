package snake.control;

import snake.model.Direction;
import snake.model.Field;
import snake.model.Food;
import snake.model.GameMultiplayer;
import snake.model.Player;

public class SnakeProtocol {
	
	public static void execute(GameMultiplayer game, Player player, String command) {
		
		if (command.startsWith("FOOD")) {
			int foodPosX = Integer.parseUnsignedInt(command.substring(5, 8).replace(" ", ""));
			int foodPosY = Integer.parseUnsignedInt(command.substring(9).replace(" ", ""));
			game.setFood(new Food(new Field(foodPosX, foodPosY)));
			return;
		}
		
		switch (command) {
			case "MOVE LEFT":
				game.move(player, Direction.LEFT);
				break;
			case "MOVE RIGHT":
				game.move(player, Direction.RIGHT);
				break;
			case "MOVE UP":
				game.move(player, Direction.UP);
				break;
			case "MOVE DOWN":
				game.move(player, Direction.DOWN);
				break;
			case "PAUSE":
				game.pause();
				break;
			case "RESUME":
				game.resume();
				break;
			case "RESTART":
				game.reset();
				game.start();
				break;
			default:
				break;
		}
	}
	
}
