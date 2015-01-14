package snake.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Observable;
import java.util.Observer;

import snake.model.Board;
import snake.model.GameMultiPlayer;
import snake.model.Player;

public class ViewBoardMultiPlayer extends ViewBoardBase  implements Observer {
	
	private GameMultiPlayer game;
	private Color snakeColorPlayerOne;
	private Color snakeColorPlayerTwo;
	private static Color DEFAULT_SNAKE_COLOR_PLAYER_ONE = new Color(81, 216, 167);
	private static Color DEFAULT_SNAKE_COLOR_PLAYER_TWO = new Color(201, 167, 167);
	
	public ViewBoardMultiPlayer(GameMultiPlayer game) {
		super();
		this.game = game;
		this.snakeColorPlayerOne = DEFAULT_SNAKE_COLOR_PLAYER_ONE;
		this.snakeColorPlayerTwo = DEFAULT_SNAKE_COLOR_PLAYER_TWO;
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
	
}
