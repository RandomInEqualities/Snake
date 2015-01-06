package snake.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import snake.model.Game;
import snake.model.Score;

public class GameView extends JFrame {

	private static final long serialVersionUID = -6227614482647030704L;
	
	Game game;
	Score score;
	BoardPanel boardPanel;
	ScorePanel scorePanel;

	public GameView(Game game) {
		super();
		setTitle("Snake");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 800);

		this.game = game;
		score = new Score();
		boardPanel = new BoardPanel(game);
		scorePanel = new ScorePanel(score);

		getContentPane().add(boardPanel, BorderLayout.CENTER);
		getContentPane().add(scorePanel, BorderLayout.NORTH);
	}

}