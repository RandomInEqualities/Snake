package snake.driver;
import java.awt.Dimension;

import snake.controller.Control;
import snake.model.Game;
import snake.view.GameView;


public class Driver {

	@SuppressWarnings("unused")
	public static final void main(String[] args) {
		Game game = new Game(new Dimension(50, 50));
		GameView view = new GameView(game);
		Control controller = new Control(game, view);
		view.setVisible(true);
	}
}
