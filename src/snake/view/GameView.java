package snake.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import snake.model.Game;

public class GameView extends JFrame {

	private static final long serialVersionUID = -6227614482647030704L;
	
	Game game;
	BoardPanel boardPanel;
	ScorePanel scorePanel;

	public GameView(Game game) {
		super();
		setTitle("Snake");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferedSize(800, 800);

		this.game = game;
		boardPanel = new BoardPanel(game);
		scorePanel = new ScorePanel(game);

		getContentPane().add(boardPanel, BorderLayout.CENTER);
		getContentPane().add(scorePanel, BorderLayout.NORTH);
		pack();
	}

}