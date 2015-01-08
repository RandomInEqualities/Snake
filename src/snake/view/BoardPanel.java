package snake.view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.*;
import snake.model.*;
public class BoardPanel extends JPanel implements Observer {

	private Game game;
	private BufferedImage apple, headUp, headDown, headLeft, headRight, background, gameOverTitle, pausedTitle;
	private Rectangle playAgain;
	private static final long serialVersionUID = 9109362543987437505L;
	
	//Popup size
	int widthPopup;
	int heightPopup;
	int xPopup;
	int yPopup;
	
	public BoardPanel(Game game) {
		super();

		if (game == null) {
			throw new NullPointerException();
		}
		this.game = game;
		game.addObserver(this);
		setBackground(CustomColor.PANEL_COLOUR);

		// Load the board images.
		try {
			apple = ImageIO.read(new File("apple.png"));
			headUp = ImageIO.read(new File("head_up.png"));
			headDown = ImageIO.read(new File("head_down.png"));
			headLeft = ImageIO.read(new File("head_left.png"));
			headRight = ImageIO.read(new File("head_right.png"));
			background = ImageIO.read(new File("TileBackground.png"));
			gameOverTitle = ImageIO.read(new File("TitleGameOver.png"));
			pausedTitle = ImageIO.read(new File("TitlePaused.png"));
		} catch (IOException error) {
			throw new RuntimeException("Image not found: " + error.getMessage());
		}
	}

	public @Override void update(Observable o, Object arg) {
		repaint();
	}

	public @Override Dimension getPreferredSize() {
		int x;
		int y;

		// Wrap window around board
		if (game.getBoard().getHeight() > game.getBoard().getWidth() + 20) { // if board is narrow
			x = 500;
			y = 800;
		} else if (game.getBoard().getWidth() > game.getBoard().getHeight() + 20) { // if board is wide
			x = 800;
			y = 500;
		} else { // if board is (approximately) square
			x = 800;
			y = 800;
		}
		return new Dimension(x, y);

	}

	protected @Override void paintComponent(Graphics context) {
		super.paintComponent(context);

		Graphics2D context2D = (Graphics2D) context;
		drawBackground(context2D);
		drawBoard(context2D);
		drawFood(context2D);
		drawSnake(context2D);
		if (game.getState() == Game.State.LOST) {
			drawGameOver(context2D);
		} else if (game.getState() == Game.State.PAUSED){
			drawPaused(context2D);
		}
	}

	//Tile background
	private void drawBackground(Graphics2D context) {
		for (int x = 0; x < getWidth(); x += background.getWidth()) {
			for (int y = 0; y < getHeight(); y += background.getHeight()) {
				context.drawImage(background, x, y, this);
			}
		}
	}

	//Game board
	private void drawBoard(Graphics2D context) {
		context.setColor(CustomColor.BOARD_COLOUR);
		context.fill(getRectangleForBoard());
	}

	private void drawSnake(Graphics2D context) {
		Snake snake = game.getSnake();

		// Draw the whole snake.
		context.setColor(CustomColor.SNAKE_COLOUR);
		for (Field position : snake.getPositions()) {
			context.fill(getRectangleForField(position));
		}

		// Draw the with a different colour.
		Rectangle headRectangle = getRectangleForField(snake.getHead());
		Image head = null;
		switch (snake.getHeadDirection()) {
		case UP:
			head = headUp;
			break;
		case DOWN:
			head = headDown;
			break;
		case LEFT:
			head = headLeft;
			break;
		case RIGHT:
			head = headRight;
			break;
		}
		Image headScaled = head.getScaledInstance(headRectangle.width,
				headRectangle.height, Image.SCALE_SMOOTH);
		context.drawImage(headScaled, headRectangle.x, headRectangle.y, CustomColor.BOARD_COLOUR, null);
	}

	private void drawFood(Graphics2D context) {
		Rectangle foodRectangle = getRectangleForField(game.getFood()
				.getPosition());
		Image scaledApple = apple.getScaledInstance(foodRectangle.width, foodRectangle.height, Image.SCALE_SMOOTH);
		context.drawImage(scaledApple, foodRectangle.x, foodRectangle.y, null);
	}

	public Rectangle getRectangleForField(Field position) {
		Rectangle boardRectangle = getRectangleForBoard();
		int fieldSideLength = getFieldSideLength();
		Rectangle rectangle = new Rectangle(position.getColumn()
				* fieldSideLength + boardRectangle.x, position.getRow()
				* fieldSideLength + boardRectangle.y, fieldSideLength,
				fieldSideLength);
		return rectangle;
	}

	public Rectangle getRectangleForBoard() {
		Dimension windowSize = getSize();
		Dimension gameSize = game.getBoard().getDimension();
		int fieldSideLength = getFieldSideLength();

		int offsetHeight = 10;
		int offsetWidth = (windowSize.width - fieldSideLength * gameSize.width) / 2;

		Rectangle rectangle = new Rectangle(offsetWidth, offsetHeight,
				fieldSideLength * gameSize.width, fieldSideLength
						* gameSize.height);
		return rectangle;
	}

	private void drawPopup(Graphics2D context) {
		// Behind
		context.setColor(CustomColor.TRANS_BLACK);
		context.fillRect(0, 0, getWidth(), getHeight());
		
		// Popup
		widthPopup = getSize().width;
		heightPopup = 200;
		xPopup = 0;
		yPopup = getRectangleForBoard().y + getRectangleForBoard().height / 2 - heightPopup / 2;
		context.setColor(CustomColor.POPUP_COLOUR);
		context.fillRect(xPopup, yPopup, widthPopup, heightPopup);
		context.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
	}

	private void drawGameOver(Graphics2D context){
		drawPopup(context);
		
		//Title
		int x2 = getRectangleForBoard().x + getRectangleForBoard().width / 2 - gameOverTitle.getWidth()/2;
		int y2 = yPopup;;
		context.drawImage(gameOverTitle, x2, y2, null);
		
		// Text
		String scoreTxt = "Final Score: " + game.getScore();
		int x3 = getRectangleForBoard().x + getRectangleForBoard().width / 2 - scoreTxt.length() * 10 / 2;
		int y3 = getRectangleForBoard().y + getRectangleForBoard().height / 2 + 5;
		context.setColor(CustomColor.PANEL_COLOUR);
		context.setFont(new Font("Sans_Serif", Font.BOLD, 20));
		context.drawString(scoreTxt, x3, y3);

		// Buttons
		int width4 = 150;
		int height4 = 50;
		int x4 = getRectangleForBoard().x + getRectangleForBoard().width / 2- width4 / 2 - 100;
		int x42 = getRectangleForBoard().x + getRectangleForBoard().width / 2- width4 / 2 + 100;
		int y4 = yPopup + heightPopup - height4 - 20;
		context.setColor(CustomColor.YELLOW_COLOUR);
		playAgain = new Rectangle(x4, y4, width4, height4);
		context.fillRect(x4, y4, width4, height4);
		context.fillRect(x42, y4, width4, height4);
			
		//text in buttons
		context.setColor(CustomColor.PANEL_COLOUR);
		context.drawString("Menu", x42 + 45, y4 + 30);
		context.drawString("Play Again", x4 + 20, y4 + 30);
	}
	
	private void drawPaused(Graphics2D context){
		drawPopup(context);
		
		//Title
		int x2 = getRectangleForBoard().x + getRectangleForBoard().width / 2 - pausedTitle.getWidth()/2;
		int y2 = yPopup;
		context.drawImage(pausedTitle, x2, y2, null);
		
		//Text
		String pauseMessage = "Press 'P' to start game again";
		int x3 = getRectangleForBoard().x + getRectangleForBoard().width / 2 - pauseMessage.length() * 10 / 2;
		int y3 = getRectangleForBoard().y + getRectangleForBoard().height / 2 + 25;
		context.setColor(CustomColor.PANEL_COLOUR);
		context.setFont(new Font("Sans_Serif", Font.BOLD, 20));
		context.drawString(pauseMessage, x3, y3);
	}
	public Rectangle getPlayAgain() {
		return playAgain;
	}
	
	public int getFieldSideLength() {
		Dimension windowSize = getSize();
		Dimension gameSize = game.getBoard().getDimension();
		int fieldWidth = windowSize.width / gameSize.width;
		int fieldHeight = windowSize.height / gameSize.height;

		if (fieldWidth >= fieldHeight) {
			fieldWidth = fieldHeight;
		} else {
			fieldHeight = fieldWidth;
		}
		// Bottom gap.
		return fieldWidth-4;
	}
}
