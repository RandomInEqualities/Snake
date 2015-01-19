package snake.control;

import snake.view.ViewFrame;


/**
 * Opens a window for playing the snake game.
 */
public class Driver {

	public static void main(String[] args) {
		
		// The ViewFrame displays the game. It will open a window with a menu where the user 
		// can choose what game they want to play. It is possible to choose between 
		// multiplayer snake and singleplayer snake.
		//
		// The views creates its own control. This breaks the MVC pattern, a little, but
		// we avoid a lot of useless code (like getters for JButtons and JPanel etc.). The
		// view's do not use the control objects, they only create them and then make sure
		// that the control objects listens to the correct objects.
		//
		// The game objects (GameSingleplayer and GameMultiplayer), which are the models in
		// the MVC pattern, are created in the menu listener.
		ViewFrame view = new ViewFrame();

		// Show the window.
		view.setVisible(true);
		
	}
}
