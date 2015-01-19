package snake.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import snake.model.*;
import snake.view.*;


/**
 * Control for the multiplayer board view (BoardMultiplayerPanel). This class interacts
 * with the game from keyboard input.
 */
public class BoardMultiplayerListener extends KeyAdapter implements ActionListener {

	private GameMultiplayer game;
	private ViewFrame view;
	
	public BoardMultiplayerListener(ViewFrame view) {
		if (view == null) {
			throw new NullPointerException();
		}
		this.game = null;
		this.view = view;
	}
	
	public void registerGame(GameMultiplayer newGame) {
		game = newGame;
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
	
	
	@Override
	public void keyPressed(KeyEvent event) {
		if (game == null) {
			return;
		}
		switch (event.getKeyCode()) {
			case KeyEvent.VK_UP:
				game.move(Player.ONE, Direction.UP);
				break;
			case KeyEvent.VK_DOWN:
				game.move(Player.ONE, Direction.DOWN);
				break;
			case KeyEvent.VK_LEFT:
				game.move(Player.ONE, Direction.LEFT);
				break;
			case KeyEvent.VK_RIGHT:
				game.move(Player.ONE, Direction.RIGHT);
				break;
			case KeyEvent.VK_W:
				game.move(Player.TWO, Direction.UP);
				break;
			case KeyEvent.VK_S:
				game.move(Player.TWO, Direction.DOWN);
				break;
			case KeyEvent.VK_A:
				game.move(Player.TWO, Direction.LEFT);
				break;
			case KeyEvent.VK_D:
				game.move(Player.TWO, Direction.RIGHT);
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
	

}
