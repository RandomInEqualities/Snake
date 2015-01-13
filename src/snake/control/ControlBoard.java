
package snake.control;

import java.awt.event.*;

import javax.swing.JButton;

import snake.model.*;
import snake.view.*;


public class ControlBoard extends KeyAdapter implements ActionListener {
	
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
		
		JButton playAgain = boardView.getPlayAgainButton();
		JButton menu = boardView.getMenuButton();
		playAgain.addActionListener(this);
		playAgain.setActionCommand("playAgain");
		menu.addActionListener(this);
		menu.setActionCommand("menu");
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
				if (game.getState() == Game.State.LOST){
					game.restart();
					view.showGame();
					boardView.removeButtons();
				}
				break;
			default:
				break;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand()=="playAgain"){
			game.restart();
			view.showGame();
			boardView.removeButtons();
			view.requestFocus();
		} 
		else if (e.getActionCommand()=="menu"){
			view.showMenu();
			boardView.removeButtons();
		}
	}

}
