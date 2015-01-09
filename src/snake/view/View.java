
package snake.view;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import snake.control.*;
import snake.model.*;


public class View extends JFrame implements Observer {

	private static final long serialVersionUID = -6227614482647030704L;
	
	private Game game;
	private BoardPanel boardPanel;
	private ScorePanel scorePanel;
	private Menu menu;
	private Audio audio;

	public View(Game game) {
		super();
		ControlTimer control = new ControlTimer(game, this);
		ControlButton controlButton = new ControlButton(game, this);
		ControlKeys controlKeys = new ControlKeys(game, this);
		
		if (game == null) {
			throw new NullPointerException();
		}
		
		game.addObserver(this);
		this.game = game;
		this.boardPanel = new BoardPanel(game);
		this.scorePanel = new ScorePanel(game, boardPanel);
		this.menu = new Menu(game);
		this.audio = new Audio(game);
		
		getContentPane().add(scorePanel, BorderLayout.NORTH);
		
		if (game.getState() == Game.State.RUNNING) {
			getContentPane().add(boardPanel, BorderLayout.CENTER);
			setTitle("Snake");
		} 
		if (game.getState() == Game.State.MENU){
			getContentPane().add(menu, BorderLayout.CENTER);
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(525, 400));
		pack();
		setLocationRelativeTo(null);
	}
	
	public void update(Observable o, Object arg) {
		repaint();
	}
	
	public void displayEndGameDialog(String message, String title) {
		
		String[] options = {"Restart", "Quit"};
		
		int selection = JOptionPane.showOptionDialog(this, message, title, JOptionPane.YES_NO_OPTION,	JOptionPane.QUESTION_MESSAGE,null, options,options[0]);
		
		if (selection == 0) {
			game.restart();
		}
		else {
			// Close the window.
			setVisible(false);
			dispose();
		}
	}
	
	public BoardPanel getBoard() {
		return boardPanel;
	}
}








