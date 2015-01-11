
package snake.view;

import java.awt.*;

import javax.swing.*;

import java.util.*;

import snake.model.*;


public class ViewBoard extends JPanel implements Observer {

	private Game game;
	private Rectangle playAgainButton;
	private Rectangle menuButton;
	private static final long serialVersionUID = 9109362543987437505L;
	
	public ViewBoard(Game game, ViewMenu menu) {
		super();

		if (game == null) {
			throw new NullPointerException();
		}
		this.game = game;
		game.addObserver(this);
		setBackground(Colors.PANEL_COLOUR);
	}

	@Override
	public void update(Observable o, Object arg) {
		repaint();
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(800, 100);
	}

	@Override
	protected void paintComponent(Graphics context) {
		super.paintComponent(context);
		
		Graphics2D context2D = (Graphics2D) context;
		drawBackground(context2D);
		drawBoard(context2D);
		drawFood(context2D);
		drawSnake(context2D);
		
		Game.State gameState = game.getState();
		if (gameState == Game.State.LOST) {
			drawGameOver(context2D);
		} 
		if (gameState == Game.State.PAUSED){
			drawPaused(context2D);
		}
	}

	private void drawBoard(Graphics2D context) {
		context.setColor(Colors.BOARD_COLOUR);
		context.fill(getRectangleForBoard());
	}

	private void drawSnake(Graphics2D context) {
		Snake snake = game.getSnake();
		
		// Snake body.
		context.setColor(Colors.SNAKE_COLOUR);
		for (Field position : snake.getPositions()) {
			context.fill(getRectangleForField(position));
		}

		// Draw the head field with a different colour.
		Rectangle headRectangle = getRectangleForField(snake.getHead());
		Image head = null;
		switch (snake.getHeadDirection()) {
		case UP:
			head = Images.SNAKE_HEAD_UP;
			break;
		case DOWN:
			head = Images.SNAKE_HEAD_DOWN;
			break;
		case LEFT:
			head = Images.SNAKE_HEAD_LEFT;
			break;
		case RIGHT:
			head = Images.SNAKE_HEAD_RIGHT;
			break;
		}
		
		Image headScaled = head.getScaledInstance(headRectangle.width, headRectangle.height, Image.SCALE_SMOOTH);
		context.drawImage(headScaled, headRectangle.x, headRectangle.y, Colors.BOARD_COLOUR, null);
	}

	private void drawFood(Graphics2D context) {
		Rectangle foodRectangle = getRectangleForField(game.getFood().getPosition());
		Image scaledApple = Images.APPLE.getScaledInstance(foodRectangle.width, foodRectangle.height, Image.SCALE_SMOOTH);
		context.drawImage(scaledApple, foodRectangle.x, foodRectangle.y, null);
	}

	private void drawGameOver(Graphics2D context){
		Rectangle popupRectangle = drawPopupBackground(context);
		
		context.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		context.setFont(new Font("Sans_Serif", Font.BOLD, 20));
		
		// You lost the game title image.
		Rectangle boardRectangle = getRectangleForBoard();
		int titleX = boardRectangle.x + boardRectangle.width/2 - Images.TITLE_GAME_OVER.getWidth()/2;
		int titleY = popupRectangle.y;
		context.drawImage(Images.TITLE_GAME_OVER, titleX, titleY, null);
		
		// Score text
		String scoreText = "Final Score: " + game.getScore();
		int scoreX = boardRectangle.x + boardRectangle.width/2 - 5*scoreText.length();
		int scoreY = boardRectangle.y + boardRectangle.height/2 + 5;
		context.setColor(Colors.PANEL_COLOUR);
		context.drawString(scoreText, scoreX, scoreY);

		// Buttons with play again and go to menu.
		int buttonWidth = 150;
		int buttonHeight = 50;
		int playAgainX = boardRectangle.x + boardRectangle.width/2- buttonWidth/2 - 100;
		int menuX = boardRectangle.x + boardRectangle.width/2- buttonWidth/2 + 100;
		int buttonY = popupRectangle.y + popupRectangle.height - buttonHeight - 20;
		
		context.setColor(Colors.PANEL_COLOUR);
		playAgainButton = new Rectangle(playAgainX, buttonY, buttonWidth, buttonHeight);
		menuButton = new Rectangle(menuX, buttonY, buttonWidth, buttonHeight);
		context.fillRect(playAgainX, buttonY, buttonWidth, buttonHeight);
		context.fillRect(menuX, buttonY, buttonWidth, buttonHeight);
			 
		// Text in buttons.
		context.setColor(Colors.YELLOW);
		context.drawString("Menu", menuX + 45, buttonY + 30);
		context.drawString("Play Again", playAgainX + 20, buttonY + 30);
	}
	
	private void drawPaused(Graphics2D context){
		Rectangle popupRectangle = drawPopupBackground(context);
		
		context.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		context.setFont(new Font("Sans_Serif", Font.BOLD, 20));
		
		// You are paused title image.
		Rectangle boardRectangle = getRectangleForBoard();
		int titleX = boardRectangle.x + boardRectangle.width/2 - Images.TITLE_PAUSE.getWidth()/2;
		int titleY = popupRectangle.y;
		context.drawImage(Images.TITLE_PAUSE, titleX, titleY, null);
		
		String pauseMessage = "Press 'P' to resume game";
		int messageX = boardRectangle.x + boardRectangle.width/2 - 5*pauseMessage.length();
		int messageY = boardRectangle.y + boardRectangle.height/2 + 25;
		context.setColor(Colors.PANEL_COLOUR);
		context.drawString(pauseMessage, messageX, messageY);
	}
	
	private Rectangle drawPopupBackground(Graphics2D context) {
		// Dim the whole board.
		context.setColor(Colors.TRANSPARENT_BLACK);
		context.fillRect(0, 0, getWidth(), getHeight());
		
		// Draw the pop up graphics.
		Rectangle boardRectangle = getRectangleForBoard();
		int popupWidth = getWidth();
		int popupHeight = 200;
		int popupX = 0;
		int popupY = boardRectangle.y + boardRectangle.height / 2 - popupHeight / 2;
		context.setColor(Colors.POPUP_COLOUR);
		context.fillRect(popupX, popupY, popupWidth, popupHeight);
		
		return new Rectangle(popupX, popupY, popupWidth, popupHeight);
	}
	
	public void drawBackground(Graphics2D context) {
		for (int x = 0; x < getWidth(); x += Images.BACKGROUND.getWidth()) {
			for (int y = 0; y < getHeight(); y += Images.BACKGROUND.getHeight()) {
				context.drawImage(Images.BACKGROUND, x, y, this);
			}
		}
	}
	
	public Rectangle getPlayAgainButton() {
		return playAgainButton;
	}
	
	public Rectangle getMenuButton() {
		return menuButton;
	}
	
	public Rectangle getRectangleForField(Field position) {
		Rectangle boardRectangle = getRectangleForBoard();
		int fieldSideLength = getFieldSideLength();
		Rectangle rectangle = new Rectangle(
			position.getColumn()* fieldSideLength + boardRectangle.x, 
			position.getRow() * fieldSideLength + boardRectangle.y, 
			fieldSideLength,
			fieldSideLength
		);
		return rectangle;
	}

	public Rectangle getRectangleForBoard() {
		Dimension windowSize = getSize();
		Dimension gameSize = game.getBoard().getDimension();
		int fieldSideLength = getFieldSideLength();

		int offsetHeight = 10;
		int offsetWidth = (windowSize.width - fieldSideLength * gameSize.width) / 2;

		Rectangle rectangle = new Rectangle(
			offsetWidth, 
			offsetHeight, 
			fieldSideLength * gameSize.width, 
			fieldSideLength * gameSize.height
		);
		return rectangle;
	}
	
	public int getFieldSideLength() {
		Dimension windowSize = getSize();
		Dimension gameSize = game.getBoard().getDimension();
		int fieldWidth = windowSize.width / gameSize.width;
		int fieldHeight = (windowSize.height-20) / gameSize.height;
		if (fieldWidth >= fieldHeight) {
			fieldWidth = fieldHeight;
		} else {
			fieldHeight = fieldWidth;
		}
		return fieldWidth;
	}
}
