
package snake.control;

import java.awt.Dimension;
import snake.model.GameSinglePlayer;
import snake.view.View;


@SuppressWarnings("unused")
public class Driver {

	public static void main(String[] args) {
		
		// We use the Model-View-Controller pattern to implement a snake game.
		// In this file we set up the model (game), its view and the control.
		GameSinglePlayer game = new GameSinglePlayer();
		// Create the view which displays the game. It will open a menu where the
		// user can choose what game they want to play (singleplayer or multiplayer).
		View view = new View(game);

		// Create the controls which controls the game and what happens in menus.
		Control control = new Control(game, view);

		// Show the window.
		view.setVisible(true);
	}
	
}
