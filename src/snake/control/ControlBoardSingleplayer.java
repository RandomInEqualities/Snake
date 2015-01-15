package snake.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;

import snake.model.Direction;
import snake.model.GameSingleplayer;
import snake.view.View;
import snake.view.ViewBoardSingleplayer;
import snake.view.ViewHeaderSingleplayer;

public class ControlBoardSingleplayer extends KeyAdapter implements ActionListener {

	private GameSingleplayer game;
	private View view;
	private ViewHeaderSingleplayer headerView;
	
	public ControlBoardSingleplayer(GameSingleplayer game, View view, ViewBoardSingleplayer viewBoard, ViewHeaderSingleplayer headerView) {
		if (game == null || view == null) {
			throw new NullPointerException();
		}
		this.game = game;
		this.view = view;
		this.headerView = headerView;
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
					replay();
				}
				break;
			default:
				break;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand()=="restart") {
			replay();
		} 
		else if (e.getActionCommand()=="menu") {
			view.showMenu();
			view.requestFocus();
		}
	}
	public void replay(){
		game.reset();
		game.start();
		headerView.showScore();
		view.requestFocus();
	}
}
