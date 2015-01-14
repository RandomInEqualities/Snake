package snake.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Observable;
import java.util.Observer;

import snake.model.Board;
import snake.model.GameSingleplayer;


public class ViewBoardSingleplayer extends ViewBoard  implements Observer {
	
	private GameSingleplayer game;
	private Color snakeColor;
	private static Color DEFAULT_SNAKE_COLOR = new Color(81, 216, 167);
	
	public ViewBoardSingleplayer(GameSingleplayer game) {
		super();
		this.game = game;
		this.snakeColor = DEFAULT_SNAKE_COLOR;
		game.addObserver(this);
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
		super.paintComponent(context);
		Graphics2D context2D = (Graphics2D) context;
		Board board = game.getBoard();
		
		drawBoard(context2D, board);
		drawFood(context2D, game.getFood(), board);
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
		String scoreTxt = "Final Score: " + game.getScore();
		int x = boardRect.x + boardRect.width/2 - scoreTxt.length()*10/2;
		int y = boardRect.y + boardRect.height/2 + 5;
		context.setColor(Colors.PANEL_COLOUR);
		context.setFont(new Font("Sans_Serif", Font.BOLD, 20));
		context.drawString(scoreTxt, x, y);
	}
	
}
