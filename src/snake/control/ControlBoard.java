
package snake.control;

import java.awt.event.*;

import javax.swing.JButton;

import snake.model.*;
import snake.view.*;


public class ControlBoard extends KeyAdapter implements ActionListener {
	
	private GameSinglePlayer game;
	private View view;
	private ViewBoard boardView;

	public ControlBoard(GameSinglePlayer game, View view) {
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
				game.move(Direction.UP);
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
				if (game.isLost()){
					playAgain();
				}
				break;
			default:
				break;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand()=="playAgain"){
			playAgain();
		} 
		else if (e.getActionCommand()=="menu"){
			view.showMenu();
			boardView.removeButtons();
		}
	}
	public void playAgain(){
		game.reset();
		game.start();
		boardView.removeButtons();
		view.getHeaderSingleplayer().showScore();
		view.requestFocus();
	}
}
