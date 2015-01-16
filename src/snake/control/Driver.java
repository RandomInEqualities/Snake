package snake.control;

import snake.view.ViewFrame;


/**
 * Opens a window for playing the snake game.
 */
public class Driver {

	public static void main(String[] args) {
		
		// The View displays the game. It will open a window with a menu where the user 
		// can choose what game they want to play. It is possible to choose between 
		// multiplayer snake and singleplayer snake.
		// The View creates its own control. This breaks the MVC pattern, a little, but
		// we avoid a lot of useless code (like getters for different JPanels etc.). The
		// view's do not use the control objects, they only create them and then makes sure
		// that the control objects listens to the correct views.
		// The game objects (models) are created in the menu.
		ViewFrame view = new ViewFrame();

		// Show the window.
		view.setVisible(true);
		
	}
}
