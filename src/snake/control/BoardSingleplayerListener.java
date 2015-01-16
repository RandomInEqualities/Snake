package snake.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import snake.model.Direction;
import snake.model.GameSingleplayer;
import snake.view.ViewFrame;


/**
 * Control for the singleplayer board view (BoardSingleplayerPanel). This class interacts
 * with the game from keyboard input.
 */
public class BoardSingleplayerListener extends KeyAdapter implements ActionListener {

	private GameSingleplayer game;
	private ViewFrame view;
	
	public BoardSingleplayerListener(ViewFrame view) {
		if (view == null) {
			throw new NullPointerException();
		}
		this.game = null;
		this.view = view;
	}
	
	public void registerGame(GameSingleplayer newGame) {
		game = newGame;
	}
	
	@Override
	public void keyPressed(KeyEvent event) {
		if (game == null) {
			return;
		}
		switch (event.getKeyCode()) {
			case KeyEvent.VK_UP:
				game.move(Direction.UP);
				break;
			case KeyEvent.VK_DOWN:
				game.move(Direction.DOWN);
				break;
			case KeyEvent.VK_LEFT:
				game.move(Direction.LEFT);
				break;
			case KeyEvent.VK_RIGHT:
				game.move(Direction.RIGHT);
				break;
			case KeyEvent.VK_P:
				if (game.isPaused()) {
					game.resume();
				}
				else {
					game.pause();
				}
				break;
			case KeyEvent.VK_ENTER: 
			case KeyEvent.VK_SPACE:
				if (game.isEnded()){
					game.reset();
					game.start();
				}
				break;
			default:
				break;
		}
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (game == null) {
			return;
		}
		if (event.getActionCommand() == "restart") {
			game.reset();
			game.start();
		} 
		else if (event.getActionCommand() == "menu") {
			view.showMenu();
		}
	}

}
