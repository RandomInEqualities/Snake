package snake.control;

import snake.model.Game;
import snake.view.View;

public class Driver {

	@SuppressWarnings("unused")
	public static final void main(String[] args) {

		// In this program we use the Model-View-Controller pattern to
		// implement a snake game.

		// Create the view which displays the game in a window.
		Game game = null;
		View view = new View(game);

		ControlMenu controlMenu = new ControlMenu(view);
		ControlSingleplayer controlSingleplayer = new ControlSingleplayer(view);

		// Show the window.
		view.setVisible(true);
	}
}
