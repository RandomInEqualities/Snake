
package snake.controller;

import java.awt.event.*;
import snake.model.*;
import snake.view.*;

public class Control extends KeyAdapter {
	
	private Game game;

	public Control(Game game, View view) {
		if (view == null || game == null) {
			throw new NullPointerException();
		}
		view.addKeyListener(this);
		this.game = game;
	}

	public @Override void keyPressed(KeyEvent event) {
		
		if (event == null) {
			throw new NullPointerException();
		}
		
		switch (event.getKeyCode()) {
			case KeyEvent.VK_UP:
				game.makeSnakeMove(Direction.UP);
				break;
			case KeyEvent.VK_DOWN:
				game.makeSnakeMove(Direction.DOWN);
				break;
			case KeyEvent.VK_LEFT:
				game.makeSnakeMove(Direction.LEFT);
				break;
			case KeyEvent.VK_RIGHT:
				game.makeSnakeMove(Direction.RIGHT);
				break;
			default:
				break;
		}
		
	}

}
