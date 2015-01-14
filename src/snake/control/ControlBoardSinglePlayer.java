package snake.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;

import snake.model.Direction;
import snake.model.GameSinglePlayer;
import snake.view.View;
import snake.view.ViewBoardSinglePlayer;

public class ControlBoardSinglePlayer extends KeyAdapter implements ActionListener {

	private GameSinglePlayer game;
	private View view;

	public ControlBoardSinglePlayer(GameSinglePlayer game, View view, ViewBoardSinglePlayer viewBoard) {
		if (game == null || view == null) {
			throw new NullPointerException();
		}
		this.game = game;
		this.view = view;
		
		view.addKeyListener(this);
		
		JButton buttonPlayAgain = viewBoard.getButtonPlayAgain();
		JButton buttonMenu = viewBoard.getButtonMenu();
		buttonPlayAgain.addActionListener(this);
		buttonMenu.addActionListener(this);
		buttonPlayAgain.setActionCommand("restart");
		buttonMenu.setActionCommand("menu");
	}
	
	@Override
	public void keyPressed(KeyEvent event) {
		if (event == null) {
			throw new NullPointerException();
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
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand()=="restart") {
			game.reset();
			game.start();
		} 
		else if (e.getActionCommand()=="menu") {
			view.showMenu();
		}
	}
	
}
