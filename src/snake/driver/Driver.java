
package snake.driver;

import snake.controller.Control;
import snake.model.Game;
import snake.view.View;

public class Driver {

	@SuppressWarnings("unused")
	public static final void main(String[] args) {
		
		Game game = new Game(20, 10);
		View view = new View(game);
		Control controller = new Control(game, view);
		
		view.setVisible(true);
	}
	
}
