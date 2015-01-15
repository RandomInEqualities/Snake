package snake.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
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
	private Player player;
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
	
	//Game Over
	@Override
	protected void drawGameOver(Graphics2D context, Board board) {
		super.drawGameOver(context, board);
		
		// Add score text
		Rectangle boardRect = getRectangleForBoard(board);
		Rectangle popupRectangle = getRectangleForPopUp(board);
		
		String scoreTxt = null;
		int x = boardRect.x + boardRect.width/2 - Images.TITLE_PLAYER_ONE.getWidth()/2;
		int y = popupRectangle.y;
		
		if (game.getWinner() == Player.ONE) {
			context.drawImage(Images.TITLE_PLAYER_ONE, x, y, null);
			scoreTxt = "Congratulations! Player 1 - YOU WIN!    Final Score: " + game.getScore(Player.ONE);
		} else if (game.getWinner() == Player.TWO) {
			context.drawImage(Images.TITLE_PLAYER_TWO, x, y, null);
			scoreTxt = "Congratulations! Player 2 - YOU WIN!    Final Score: " + game.getScore(Player.TWO);
		} else {
			context.drawImage(Images.TITLE_GAME_OVER, x, y, null);
			scoreTxt = "It's a tie. Try Again.";
		}
		int x2 = boardRect.x + boardRect.width/2 - scoreTxt.length()*10/2;
		int y2 = boardRect.y + boardRect.height/2 + 5;
		context.setColor(Colors.PANEL_COLOR);
		context.setFont(new Font("Sans_Serif", Font.BOLD, 20));
		context.drawString(scoreTxt, x2, y2);
		
	}
}
