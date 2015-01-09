
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
		ControlGame controlButton = new ControlGame(game, this);
		ControlKeys controlKeys = new ControlKeys(game, this);
		ControlMenu controlMenu = new ControlMenu(game, this);
				
		
		if (game == null) {
			throw new NullPointerException();
		}
		
		game.addObserver(this);
		this.game = game;
		this.boardPanel = new BoardPanel(game);
		this.menu = new Menu(game, boardPanel);
		this.scorePanel = new ScorePanel(game, boardPanel, menu);
		this.audio = new Audio(game);
	
		getContentPane().add(scorePanel, BorderLayout.NORTH);
		
		if (game.getState() == Game.State.RUNNING) {
			getContentPane().add(boardPanel, BorderLayout.CENTER);
			setTitle("Snake");
		} 
		if (game.getState() == Game.State.MENU){
			getContentPane().add(menu, BorderLayout.CENTER);
		}

		setTitle("Snake");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(550, 550));
		pack();
		setLocationRelativeTo(null);
		
	}

	
	public void update(Observable o, Object arg) {
		repaint();
	}
	
	public BoardPanel getBoard() {
		return boardPanel;
	}
	
	public Menu getMenu() {
		return menu;
	}
}








