
package snake.control;

import snake.model.Game;
import snake.view.View;

public class Driver {
	
	@SuppressWarnings("unused")
	public static final void main(String[] args) {
		
		// In this program we use the Model-View-Controller pattern to
		// implement a snake game.
		
		// Create the model which is our game logic.
		Game game = null;
		if (args.length == 2) {
			int width, height;
			try {
				width = Integer.parseInt(args[0]);
				height = Integer.parseInt(args[1]);
			} 
			catch (NumberFormatException error) {
				printErrorMessage();
				return;
			}
			game = new Game(width, height);
		}
		else if (args.length == 0) {
			game = new Game(20, 10);
		}
		else {
			printErrorMessage();
			return;
		}
		
		// Create the view which displays the game in a window.
		View view = new View(game);
		
		// Create the control which handles game interaction. It uses the view
		// window for user input.
		Control control = new Control(game, view);
		
		// Show the window.
		view.setVisible(true);
	}
	
	private static void printErrorMessage() {
		System.err.println("Unable to interpret commandline parameters.");
	}

}