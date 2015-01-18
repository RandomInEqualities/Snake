package snake.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Observable;
import java.util.Observer;

import snake.control.BoardSingleplayerListener;
import snake.model.Board;
import snake.model.GameSingleplayer;


/**
 * JPanel for displaying a singleplayer snake game.
 */
public class BoardSingleplayerPanel extends BoardBasePanel  implements Observer {
	
	private static final Color DEFAULT_SNAKE_COLOR = new Color(84, 216, 81);
	private static final Font SCORE_FONT = new Font("Sans_Serif", Font.BOLD, 20);
	
	private GameSingleplayer game;
	private Color snakeColor;
	private BoardSingleplayerListener control;
	
	public BoardSingleplayerPanel(ViewFrame view) {
		super();
		this.game = null;
		this.snakeColor = DEFAULT_SNAKE_COLOR;
		
		// Add a controller for this panel. It must unfortunately be a field of this class
		// as the control needs to know when a game is registered.
		control = new BoardSingleplayerListener(view);
		addKeyListener(control);
		buttonPlayAgain.setActionCommand("restart");
		buttonMenu.setActionCommand("menu");
		buttonPlayAgain.addActionListener(control);
		buttonMenu.addActionListener(control);
	}
	
	public void registerGame(GameSingleplayer newGame) {
		control.registerGame(newGame);
		if (game != null) {
			game.deleteObserver(this);
		}
		if (newGame != null) {
			newGame.addObserver(this);
		}
		game = newGame;
	}
	
	public void setSnakeColor(Color color) {
		snakeColor = color;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (game.isEnded()) {
			showButtons();
		}
		else {
			removeButtons();
		}
		repaint();
	}

	@Override
	protected void paintComponent(Graphics context) {
		if (game == null) {
			throw new NullPointerException("this panel should not be shown without a game");
		}
		super.paintComponent(context);
		
		Graphics2D context2D = (Graphics2D) context;
		Board board = game.getBoard();
		drawBoard(context2D, board);
		if (!game.isWon()){
			drawFood(context2D, game.getFood(), board);
		}
		
		drawSnake(context2D, game.getSnake(), board, snakeColor);
		
		if (game.isLost()) {
			drawGameLost(context2D, board);
		} 
		else if (game.isPaused()) {
			drawPaused(context2D, board);
		} 
		else if (game.isWon()){
			drawGameWon(context2D, board);
		}
	}
	
	@Override
	protected void drawGameOver(Graphics2D context, Board board) {
		super.drawGameOver(context, board);
		
		// Add score text.
		Rectangle boardRect = getRectangleForBoard(board);
		String scoreText = "Final Score: " + game.getScore();
		int x = boardRect.x + boardRect.width/2 - 5*scoreText.length();
		int y = boardRect.y + boardRect.height/2 + 5;
		context.setColor(ResourceColors.PANEL_COLOR);
		context.setFont(SCORE_FONT);
		context.drawString(scoreText, x, y);
	}
	
}
