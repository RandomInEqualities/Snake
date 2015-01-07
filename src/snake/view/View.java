
package snake.view;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import snake.model.*;


public class View extends JFrame implements Observer {

	private static final long serialVersionUID = -6227614482647030704L;
	
	private Game game;
	private BoardPanel boardPanel;
	private ScorePanel scorePanel;

	public View(Game game) {
		super();
		setTitle("Snake");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 800);
		setLocationRelativeTo(null);
		
		if (game == null) {
			throw new NullPointerException();
		}
		
		game.addObserver(this);
		this.game = game;
		this.boardPanel = new BoardPanel(game);
		this.scorePanel = new ScorePanel(game);

		getContentPane().add(boardPanel, BorderLayout.CENTER);
		getContentPane().add(scorePanel, BorderLayout.NORTH);
	}
	
	public void update(Observable o, Object arg) {
		if (game.getState() == Game.State.WON) {
			JOptionPane.showMessageDialog(this, "Won!", "You are Awesome", JOptionPane. INFORMATION_MESSAGE); 
		}
		else if (game.getState() == Game.State.LOST) {
			JOptionPane.showMessageDialog(this, "Lost!", "You are not Awesome", JOptionPane. INFORMATION_MESSAGE); 
		}
	}

}