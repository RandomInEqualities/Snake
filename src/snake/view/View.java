
package snake.view;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import snake.model.Game;


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
		Game.State state = game.getState();
		if (state == Game.State.WON) {
			displayEndGameDialog(
				"You Won! You score is " + game.getScore().getValue() + ". What do you want to do?", 
				"You're awesome"
			);
		}
		else if (state == Game.State.LOST) {
			displayEndGameDialog(
				"You lost. You score is " + game.getScore().getValue() + ". What do you want to do?", 
				"Snake"
			);
		}
	}
	
	public void displayEndGameDialog(String message, String title) {
		
		String[] options = {"Restart", "Quit"};
		
		int selection = JOptionPane.showOptionDialog(
			this, 
			message, 
			title,
			JOptionPane.YES_NO_OPTION,
			JOptionPane.QUESTION_MESSAGE,
			null,
			options, 
			options[0]
		);
		
		if (selection == 0) {
			game.restart();
		}
		else {
			// Close the window.
			setVisible(false);
			dispose();
		}
	}

}








