
package snake.control;

import java.awt.event.*;

import snake.model.*;
import snake.model.Game.State;
import snake.view.*;

public class Control extends KeyAdapter {
	
	private Game game;
	private ControlTimer controlTimer;

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
			case KeyEvent.VK_ENTER: case KeyEvent.VK_SPACE:
				if (game.getState() == State.LOST){
					game.restart();
				}
			case KeyEvent.VK_M:
				game.isMuted = !game.isMuted;
			case KeyEvent.VK_P:
				game.isPaused = !game.isPaused;
				if (game.isPaused) {
					controlTimer.pauseTimer(controlTimer.getTimer());
				} else {
					game.isPaused = false;
					controlTimer.getTimer().start();
				}
			default:
				break;
		}
		
	}

}
