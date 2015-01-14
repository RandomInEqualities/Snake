package snake.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Observable;
import java.util.Observer;

import snake.model.Board;
import snake.model.GameSinglePlayer;


public class ViewBoardSinglePlayer extends ViewBoardBase  implements Observer {
	
	private GameSinglePlayer game;
	private Color snakeColor;
	private static Color DEFAULT_SNAKE_COLOR = new Color(81, 216, 167);
	
	public ViewBoardSinglePlayer(GameSinglePlayer game) {
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
	
}
