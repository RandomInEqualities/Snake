package snake.control;

import snake.view.View;


/**
 * Opens a window for playing the snake game.
 */
public class Driver {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// The View displays the game. It will open a window with a menu where the user 
		// can choose what game they want to play. It is possible to choose between 
		// multiplayer snake and singleplayer snake.
		View view = new View();

		// To control listens to input events and control the game with them. It requires
		// the view window to do this.
		Control control = new Control(view);

		// Show the window.
		view.setVisible(true);
	}
	
}
