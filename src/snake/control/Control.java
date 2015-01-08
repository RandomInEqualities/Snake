
package snake.control;

import java.awt.event.*;
import snake.model.*;
import snake.model.Game.State;
import snake.view.*;

public class Control extends KeyAdapter {
	
	private Game game;
	private ControlTimer controlTimer;
	private View view;
	
	public Control(Game game, View view) {
		if (view == null || game == null) {
			throw new NullPointerException();
		}
		view.addKeyListener(this);
		this.view = view;
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
				break;
			case KeyEvent.VK_M:
				game.isMuted = !game.isMuted;
				break;
				
			case KeyEvent.VK_P:
				if (game.getState() == State.RUNNING){
					game.setState(State.PAUSED);
					view.getBoard().repaint();
				} else if (game.getState() == State.PAUSED){
					game.setState(State.RUNNING);
				}
				break;
		}
		
	}

}
