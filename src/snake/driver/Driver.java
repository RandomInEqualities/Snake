package snake.driver;
import snake.model.Game;
import snake.view.GameView;


public class Driver {

	public static final void main(String[] args) {
		Game game = new Game(50, 50);
		GameView window = new GameView(game);
		window.setVisible(true);
	}
}
