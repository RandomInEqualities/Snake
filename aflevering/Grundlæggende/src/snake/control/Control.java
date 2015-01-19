
package snake.control;

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

	@Override
	public void keyPressed(KeyEvent event) {
		
		if (event == null) {
			throw new NullPointerException();
		}
		
		Snake snake = game.getSnake();
		switch (event.getKeyCode()) {
			case KeyEvent.VK_UP:
				snake.move(Direction.UP);
				break;
			case KeyEvent.VK_DOWN:
				snake.move(Direction.DOWN);
				break;
			case KeyEvent.VK_LEFT:
				snake.move(Direction.LEFT);
				break;
			case KeyEvent.VK_RIGHT:
				snake.move(Direction.RIGHT);
				break;
			default:
				break;
		}
		
	}

}
