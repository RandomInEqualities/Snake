package snake.driver;
import snake.controller.SnakeControl;
import snake.model.Game;
import snake.view.GameView;


public class Driver {

	@SuppressWarnings("unused")
	public static final void main(String[] args) {
		Game game = new Game(50, 50);
		GameView view = new GameView(game);
		SnakeControl controller = new SnakeControl(game, view);
		view.setVisible(true);
	}
}
