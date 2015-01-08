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
	private BufferedImage apple, headUp, headDown, headLeft, headRight,
			background;
	private Rectangle playAgain;
	private static final Color SNAKE_COLOUR = new Color(0.153f, 0.68f, 0.38f);
	private static final Color BACKGROUND_COLOUR = new Color(0.7451f, 0.7647f,
			0.78f);
	private static final Color PANEL_COLOUR = new Color(0.2f, 0.286f, 0.3686f);
	private static final Color YELLOW_COLOUR = new Color(0.949f, 0.757f, 0.267f);
	private static final Color POPUP_COLOUR = new Color(0f, 0f, 0f, 0.8f);
	private static final Color SCORE_COLOUR = new Color(0.9255f, 0.941f,
			0.9451f);
	private static final long serialVersionUID = 9109362543987437505L;

	public BoardPanel(Game game) {
		super();

		if (game == null) {
			throw new NullPointerException();
		}
		this.game = game;
		game.addObserver(this);
		setBackground(PANEL_COLOUR);

		// Load the board images.
		try {
			apple = ImageIO.read(new File("apple.png"));
			headUp = ImageIO.read(new File("head_up.png"));
			headDown = ImageIO.read(new File("head_down.png"));
			headLeft = ImageIO.read(new File("head_left.png"));
			headRight = ImageIO.read(new File("head_right.png"));
			background = ImageIO.read(new File("TileBackground.png"));
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
			drawPopup(context2D);
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
		context.setColor(BACKGROUND_COLOUR);
		context.fill(getRectangleForBoard());
	}

	private void drawSnake(Graphics2D context) {
		Snake snake = game.getSnake();

		// Draw the whole snake.
		context.setColor(SNAKE_COLOUR);
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
		context.drawImage(headScaled, headRectangle.x, headRectangle.y,
				BACKGROUND_COLOUR, null);
	}

	private void drawFood(Graphics2D context) {
		Rectangle foodRectangle = getRectangleForField(game.getFood()
				.getPosition());
		Image scaledApple = apple.getScaledInstance(foodRectangle.width,
				foodRectangle.height, Image.SCALE_SMOOTH);
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
		// Window
		int width = getSize().width;
		int height = 200;
		int x = 0;
		int y = getRectangleForBoard().y + getRectangleForBoard().height / 2
				- height / 2;
		context.setColor(POPUP_COLOUR);
		context.fillRect(x, y, width, height);

		// Text
		String gameOverTxt = "Game Over";
		int x2 = getRectangleForBoard().x + getRectangleForBoard().width / 2
				- gameOverTxt.length() * 30 / 2;
		int y2 = y + 50;
		context.setColor(YELLOW_COLOUR);
		context.setFont(new Font("Sans_Serif", Font.BOLD, 50));
		context.drawString(gameOverTxt, x2, y2);

		String scoreTxt = "Final Score: " + game.getScore();
		int x3 = getRectangleForBoard().x + getRectangleForBoard().width / 2
				- scoreTxt.length() * 10 / 2;
		int y3 = getRectangleForBoard().y + getRectangleForBoard().height / 2;
		context.setColor(SCORE_COLOUR);
		context.setFont(new Font("Sans_Serif", Font.BOLD, 20));
		context.drawString(scoreTxt, x3, y3);

		// Buttons
		int width4 = 150;
		int height4 = 50;
		int x4 = getRectangleForBoard().x + getRectangleForBoard().width / 2
				- width4 / 2 - 100;
		int x42 = getRectangleForBoard().x + getRectangleForBoard().width / 2
				- width4 / 2 + 100;
		int y4 = y + height - height4 - 20;
		context.setColor(YELLOW_COLOUR);
		playAgain = new Rectangle(x4, y4, width4, height4);
		context.fillRect(x4, y4, width4, height4);
		context.fillRect(x42, y4, width4, height4);
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
		return fieldWidth-1;
	}
}
