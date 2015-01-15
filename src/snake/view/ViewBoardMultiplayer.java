package snake.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
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
			headerView.repaint();
			drawGameOver(context2D, board);
		}
	}
	
	//GAME OVER
	/*@Override
	protected void drawGameOver(Graphics2D context, Board board) {
		super.drawGameOver(context, board);
		
		// Add score text.
		Rectangle boardRect = getRectangleForBoard(board);
		String scoreTxt = "Final Score: " + game.getScore(Player.ONE);
		int x = boardRect.x + boardRect.width/2 - scoreTxt.length()*10/2;
		int y = boardRect.y + boardRect.height/2 + 5;
		context.setColor(Colors.PANEL_COLOUR);
		context.setFont(new Font("Sans_Serif", Font.BOLD, 20));
		context.drawString(scoreTxt, x, y);
	}*/
}
