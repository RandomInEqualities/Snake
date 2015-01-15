package snake.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Observable;
import java.util.Observer;

import snake.model.Board;
import snake.model.GameMultiplayer;
import snake.model.Player;

public class ViewBoardMultiplayer extends ViewBoard  implements Observer {
	
	private GameMultiplayer game;
	private ViewHeaderMultiplayer headerView;
	private Color snakeColorPlayerOne;
	private Color snakeColorPlayerTwo;
	private static Color DEFAULT_SNAKE_COLOR_PLAYER_ONE = new Color(84, 216, 81);
	private static Color DEFAULT_SNAKE_COLOR_PLAYER_TWO = new Color(84, 216, 81);
	
	public ViewBoardMultiplayer(ViewHeaderMultiplayer headerView, GameMultiplayer game) {
		super();
		this.game = game;
		this.headerView = headerView;
		this.snakeColorPlayerOne = DEFAULT_SNAKE_COLOR_PLAYER_ONE;
		this.snakeColorPlayerTwo = DEFAULT_SNAKE_COLOR_PLAYER_TWO;
		game.addObserver(this);
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
			headerView.hideScore();
			drawGameOver(context2D, board);
		}
	}
}
