package snake.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;

import snake.model.Direction;
import snake.model.GameMultiplayer;
import snake.model.Player;
import snake.view.View;
import snake.view.ViewBoardMultiplayer;

public class ControlBoardMultiplayer extends KeyAdapter implements ActionListener {

	private GameMultiplayer game;
	private View view;
	private ViewBoardMultiplayer viewBoard;

	public ControlBoardMultiplayer(GameMultiplayer game, View view, ViewBoardMultiplayer viewBoard) {
		if (game == null || view == null) {
			throw new NullPointerException();
		}
		this.game = game;
		this.view = view;
		this.viewBoard = viewBoard;
		
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand()=="restart") {
			game.reset();
			game.start();
			view.requestFocus();
		} 
		else if (e.getActionCommand()=="menu") {
			view.showMenu();
			view.requestFocus();
		}
	}
	
}
