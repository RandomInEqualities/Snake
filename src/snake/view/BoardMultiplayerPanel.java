package snake.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Observable;
import java.util.Observer;

import snake.control.BoardMultiplayerListener;
import snake.model.Board;
import snake.model.GameMultiplayer;
import snake.model.Player;


/**
 * JPanel for displaying a multiplayer snake game.
 */
public class BoardMultiplayerPanel extends BoardBasePanel  implements Observer {
	
	private static final Color DEFAULT_SNAKE_COLOR_ONE = ResourceColors.GREEN;
	private static final Color DEFAULT_SNAKE_COLOR_TWO = ResourceColors.RED;
	private static final Font SCORE_FONT = new Font("Sans_Serif", Font.BOLD, 20);
	
	private GameMultiplayer game;
	private Color snakeColorPlayerOne;
	private Color snakeColorPlayerTwo;
	private BoardMultiplayerListener control;
	
	public BoardMultiplayerPanel(ViewFrame view) {
		super();
		this.game = null;
		this.snakeColorPlayerOne = DEFAULT_SNAKE_COLOR_ONE;
		this.snakeColorPlayerTwo = DEFAULT_SNAKE_COLOR_TWO;
		
		// Add a controller for this panel. It must unfortunately be a field of this class
		// as the control needs to know when a game is registered.
		control = new BoardMultiplayerListener(view);
		addKeyListener(control);
		buttonPlayAgain.setActionCommand("restart");
		buttonMenu.setActionCommand("menu");
		buttonPlayAgain.addActionListener(control);
		buttonMenu.addActionListener(control);
	}
	
	public void registerGame(GameMultiplayer newGame) {
		control.registerGame(newGame);
		if (game != null) {
			game.deleteObserver(this);
		}
		if (newGame != null) {
			newGame.addObserver(this);
		}
		game = newGame;
	}
	
	public void setSnakeColor(Player player, Color color) {
		if (player == Player.ONE) {
			snakeColorPlayerOne = color;
		}
		else {
			snakeColorPlayerTwo = color;
		}
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
		drawFood(context2D, game.getFood(), board);
		drawSnake(context2D, game.getSnake(Player.ONE), board, snakeColorPlayerOne);
		drawSnake(context2D, game.getSnake(Player.TWO), board, snakeColorPlayerTwo);
		
		if (game.isPaused()) {
			drawPaused(context2D, board);
		} 
		else if (game.isEnded()){
			drawGameOver(context2D, board);
		}
	}
	
	@Override
	protected void drawGameOver(Graphics2D context, Board board) {
		if (!game.isEnded()) {
			throw new IllegalArgumentException("game has not ended");
		}
		super.drawGameOver(context, board);
		
		// Add score text.
		Rectangle boardRect = getRectangleForBoard(board);
		Rectangle popupRectangle = getRectangleForPopUp(board);
		
		String scoreText = null;
		Image titleImage = null;
		
		if (game.isTie()) {
			titleImage = ResourceImages.TITLE_GAME_OVER;
			scoreText = "It's a tie. Try Again.";
		}
		else if (game.getWinner() == Player.ONE) {
			titleImage = ResourceImages.TITLE_PLAYER_ONE;
			scoreText = "Final Score: " + game.getScore(Player.ONE);
		} 
		else if (game.getWinner() == Player.TWO) {
			titleImage = ResourceImages.TITLE_PLAYER_TWO;
			scoreText = "Final Score: " + game.getScore(Player.TWO);
		}
		
		int titleX = boardRect.x + boardRect.width/2 - ResourceImages.TITLE_PLAYER_ONE.getWidth()/2;
		int titleY = popupRectangle.y;
		context.drawImage(titleImage, titleX, titleY, null);
		
		int scoreX = boardRect.x + boardRect.width/2 - scoreText.length()*10/2;
		int scoreY = boardRect.y + boardRect.height/2 + 5;
		context.setColor(ResourceColors.PANEL_COLOR);
		context.setFont(SCORE_FONT);
		context.drawString(scoreText, scoreX, scoreY);
	}
	
}
