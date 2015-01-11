
package snake.control;

import java.awt.Cursor;
import java.awt.event.*;

import snake.model.*;
import snake.model.Game.State;
import snake.view.*;


public class ControlBoard extends MouseAdapter implements KeyListener {
	
	private Game game;
	private View view;
	private ViewBoard boardView;

	public ControlBoard(Game game, View view) {
		if (game == null || view == null) {
			throw new NullPointerException();
		}
		this.game = game;
		this.view = view;
		this.boardView = view.getViewBoard();
		this.view.addKeyListener(this);
	}
	
	@Override
	public void keyPressed(KeyEvent event) {
		if (event == null) {
			throw new NullPointerException();
		}
		if (!view.inGame()) {
			return;
		}
		
		switch (event.getKeyCode()) {
			case KeyEvent.VK_UP:
				game.moveSnake(Direction.UP);
			case KeyEvent.VK_DOWN:
				game.moveSnake(Direction.DOWN);
				break;
			case KeyEvent.VK_LEFT:
				game.moveSnake(Direction.LEFT);
				break;
			case KeyEvent.VK_RIGHT:
				game.moveSnake(Direction.RIGHT);
				break;
			case KeyEvent.VK_P:
				if (game.isPaused()) {
					game.unPause();
				}
				else {
					game.pause();
				}
				break;
			case KeyEvent.VK_ENTER: 
			case KeyEvent.VK_SPACE:
				if (game.getState() == State.LOST){
					game.restart();
				}
				break;
			default:
				break;
		}
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		if (game.getState() == Game.State.LOST) {
			if (boardView.getPlayAgainButton().contains(event.getPoint())) {
				game.restart();
			} 
			else if (boardView.getMenuButton().contains(event.getPoint())) {
				view.showMenu();
			}
			boardView.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}

	@Override
	public void mouseMoved(MouseEvent event) {
		if (game.getState() == Game.State.LOST) {
			if (boardView.getPlayAgainButton().contains(event.getPoint()) || boardView.getMenuButton().contains(event.getPoint())) {
				boardView.setCursor(new Cursor(Cursor.HAND_CURSOR));
			} 
			else  {
				boardView.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

}
