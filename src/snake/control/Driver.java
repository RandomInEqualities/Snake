
package snake.control;

import java.awt.Dimension;
import snake.model.Game;
import snake.view.View;


@SuppressWarnings("unused")
public class Driver {

	public static void main(String[] args) {
		
		// We use the Model-View-Controller pattern to implement a snake game.
		// In this file we set up the model (game), its view and the control.
		
		// Create the game.
		Dimension size = parseArguments(args);
		Game game;
		if (size == null) {
			game = new Game();
		}
		else {
			game = new Game(size.width, size.height);
		}

		// Create the view which displays the game.
		View view = new View(game);

		// Create the controls which controls the game.
		Control control = new Control(game, view);

		// Show the window.
		view.setVisible(true);
	}
	
	private static Dimension parseArguments(String[] args) {
		if (args.length != 2) {
			return null;
		}
		
		// Try to determine the initial game width and height from the command line.
		int width, height;
		try {
			width = Integer.parseInt(args[0]);
			height = Integer.parseInt(args[1]);
		} 
		catch (NumberFormatException error) {
			return null;
		}
		
		return new Dimension(width, height);
	}
	
}
