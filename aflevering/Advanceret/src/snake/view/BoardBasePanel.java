package snake.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.List;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import snake.model.*;


/**
 * Base JPanel for displaying the game board. We derive a single and multiplayer panel from
 * this class.
 */
public abstract class BoardBasePanel extends JPanel {
	
	private static final Font PAUSE_FONT = new Font("Sans_Serif", Font.BOLD, 20);
	
	protected JButton buttonPlayAgain;
	protected JButton buttonMenu;
	private boolean showingButtons;

	// Arrays with colored snake images.
	private ArrayList<Color> snakeColors;
	private ArrayList<BufferedImage> snakeBodyBL;
	private ArrayList<BufferedImage> snakeBodyBR;
	private ArrayList<BufferedImage> snakeBodyTL;
	private ArrayList<BufferedImage> snakeBodyTR;
	private ArrayList<BufferedImage> snakeBodyVertical;
	private ArrayList<BufferedImage> snakeBodyHorizontal;
	private ArrayList<BufferedImage> snakeHeadUp;
	private ArrayList<BufferedImage> snakeHeadDown;
	private ArrayList<BufferedImage> snakeHeadLeft;
	private ArrayList<BufferedImage> snakeHeadRight;
	private ArrayList<BufferedImage> snakeTailUp;
	private ArrayList<BufferedImage> snakeTailDown;
	private ArrayList<BufferedImage> snakeTailLeft;
	private ArrayList<BufferedImage> snakeTailRight;
	
	public BoardBasePanel() {
		super();
		
		this.buttonPlayAgain = MenuPanel.createMenuButton(ResourceImages.BUTTON_PLAY_AGAIN);
		this.buttonMenu = MenuPanel.createMenuButton(ResourceImages.BUTTON_MENU);
		this.showingButtons = false;
		
		this.snakeColors = new ArrayList<Color>();
		this.snakeBodyBL = new ArrayList<BufferedImage>();
		this.snakeBodyBR = new ArrayList<BufferedImage>();
		this.snakeBodyTL = new ArrayList<BufferedImage>();
		this.snakeBodyTR = new ArrayList<BufferedImage>();
		this.snakeBodyVertical = new ArrayList<BufferedImage>();
		this.snakeBodyHorizontal = new ArrayList<BufferedImage>();
		this.snakeHeadUp = new ArrayList<BufferedImage>();
		this.snakeHeadDown = new ArrayList<BufferedImage>();
		this.snakeHeadLeft = new ArrayList<BufferedImage>();
		this.snakeHeadRight = new ArrayList<BufferedImage>();
		this.snakeTailUp = new ArrayList<BufferedImage>();
		this.snakeTailDown = new ArrayList<BufferedImage>();
		this.snakeTailLeft = new ArrayList<BufferedImage>();
		this.snakeTailRight = new ArrayList<BufferedImage>();
		
		setBackground(ResourceColors.PANEL_COLOR);
	}

	protected void drawBoard(Graphics2D context, Board board) {
		drawBackground(context);
		context.setColor(ResourceColors.BOARD_COLOR);
		context.fill(getRectangleForBoard(board));
	}
	
	protected void drawSnake(Graphics2D context, Snake snake, Board board, Color color) {
		int colorIndex = snakeColors.indexOf(color);
		if (colorIndex == -1) {
			addSnakeColor(color);
			colorIndex = snakeColors.size() - 1;
		}
		
		drawSnakeBody(context, snake, board, colorIndex);
		drawSnakeTail(context, snake, board, colorIndex);
		drawSnakeHead(context, snake, board, colorIndex);
	}
	
	private void drawSnakeHead(Graphics2D context, Snake snake, Board board, int colorIndex) {
		Rectangle headRect = getRectangleForField(snake.getHead(), board);
		BufferedImage headImage = null;
		switch (snake.getHeadDirection()) {
			case UP:
				headImage = snakeHeadUp.get(colorIndex);
				break;
			case DOWN:
				headImage = snakeHeadDown.get(colorIndex);
				break;
			case LEFT:
				headImage = snakeHeadLeft.get(colorIndex);
				break;
			case RIGHT:
				headImage = snakeHeadRight.get(colorIndex);
				break;
		}
		Image headScaledImage = headImage.getScaledInstance(headRect.width, headRect.height, Image.SCALE_SMOOTH);
		context.drawImage(headScaledImage, headRect.x, headRect.y, null);
	}
	
	private void drawSnakeTail(Graphics2D context, Snake snake, Board board, int colorIndex) {
		int lastRow = board.getHeight() - 1;
		int lastCol = board.getWidth() - 1;
		
		Rectangle tailRect = getRectangleForField(snake.getTail(), board);
		List<Field> snakeArray = snake.getPositions();
		Field tail = snakeArray.get(snakeArray.size() - 1);
		Field beforeTail = snakeArray.get(snakeArray.size() - 2);
		
		BufferedImage tailImage = null;
		if (tail.getColumn() + 1 == beforeTail.getColumn() || tail.getColumn() == lastCol && beforeTail.getColumn() == 0) {
			tailImage = snakeTailRight.get(colorIndex);
		} 
		else if (tail.getColumn() - 1 == beforeTail.getColumn() || tail.getColumn() == 0 && beforeTail.getColumn() == lastCol) {
			tailImage = snakeTailLeft.get(colorIndex);
		} 
		else if (tail.getRow() - 1 == beforeTail.getRow() || tail.getRow() == 0 && beforeTail.getRow() == lastRow) {
			tailImage = snakeTailUp.get(colorIndex);
		} 
		else {
			tailImage = snakeTailDown.get(colorIndex);
		}
		
		Image tailScaledImage = tailImage.getScaledInstance(tailRect.width, tailRect.height, Image.SCALE_SMOOTH);
		context.drawImage(tailScaledImage, tailRect.x, tailRect.y, null);
	}
	
	private void drawSnakeBody(Graphics2D context, Snake snake, Board board, int colorIndex) {
		List<Field> snakeArray = snake.getPositions();
		
		// Find the scaled instances of the 6 possible snake body types.
		Image bodyHorizontal = snakeBodyHorizontal.get(colorIndex);
		Image bodyVertical = snakeBodyVertical.get(colorIndex);
		Image bodyTR = snakeBodyTR.get(colorIndex);
		Image bodyTL = snakeBodyTL.get(colorIndex);
		Image bodyBL = snakeBodyBL.get(colorIndex);
		Image bodyBR = snakeBodyBR.get(colorIndex);
		
		int fieldSize = getFieldSideLength(board);
		Image scaledBodyHorizontal = bodyHorizontal.getScaledInstance(fieldSize, fieldSize, Image.SCALE_SMOOTH);
		Image scaledBodyVertical = bodyVertical.getScaledInstance(fieldSize, fieldSize, Image.SCALE_SMOOTH);
		Image scaledBodyTR = bodyTR.getScaledInstance(fieldSize, fieldSize, Image.SCALE_SMOOTH);
		Image scaledBodyTL = bodyTL.getScaledInstance(fieldSize, fieldSize, Image.SCALE_SMOOTH);
		Image scaledBodyBL = bodyBL.getScaledInstance(fieldSize, fieldSize, Image.SCALE_SMOOTH);
		Image scaledBodyBR = bodyBR.getScaledInstance(fieldSize, fieldSize, Image.SCALE_SMOOTH);
		
		// Loop through the snake body and draw the scaled body images.
		for (int index = 1; index < snakeArray.size() - 1; index++){ 

			Field current = snakeArray.get(index);
			Field front = snakeArray.get(index - 1);
			Field behind = snakeArray.get(index + 1);

			Image body = null;
			if (current.getRow() == front.getRow() && current.getRow() == behind.getRow()){ 
				body = scaledBodyHorizontal;
			} 
			else if (current.getColumn() == front.getColumn() && current.getColumn() == behind.getColumn()) {
				body = scaledBodyVertical;
			} 
			else if (isSnakeCorner("TopRight", current, front, behind, board)) {
				body = scaledBodyTR;
			} 
			else if (isSnakeCorner("TopLeft", current, front, behind, board)) {
				body = scaledBodyTL;
			} 
			else if (isSnakeCorner("BottomLeft", current, front, behind, board)) {
				body = scaledBodyBL;
			} 
			else if (isSnakeCorner("BottomRight", current, front, behind, board)) {
				body = scaledBodyBR;
			}
			Rectangle bodyRect = getRectangleForField(snakeArray.get(index), board);
			context.drawImage(body, bodyRect.x, bodyRect.y, null);
		}
	}
	
	/* Check if the field consisting of current, front and behind is equivalent to the corner specified
	 * by the corner string. The corner string can be TopRight, TopLeft, BottomLeft and BottomRight.
	 * a: Current piece compared to adjacent column, b: Current piece compared to adjacent row, 
	 * c/d: Outer rows, e/f: Outer columns, g/h: Corners of the board.
	 */
	private boolean isSnakeCorner(String corner, Field current, Field front, Field behind, Board board) {
		int a = 0, b = 0, c = 0, d = 0, e = 0, f = 0, g = 0, h = 0, w = 0, x = 0, y = 0, z = 0;
		int lastRow = board.getHeight() - 1;
		int lastColumn = board.getWidth() - 1;
		if (corner.equals("TopRight")) {
			a = 1; b = 1; c = lastRow; d = 0; e = lastColumn; f = 0; g = 0; h = 0;
			w = front.getColumn(); x = behind.getRow(); y = behind.getColumn(); z = front.getRow();
		} 
		else if (corner.equals("TopLeft")) {
			a = -1; b = 1; c = lastRow; d = 0; e = 0; f = lastColumn; g = 0; h = lastColumn;	
			w = front.getRow(); x = behind.getColumn(); y = behind.getRow(); z = front.getColumn();
		} 
		else if (corner.equals("BottomLeft")) {
			a = -1; b = -1; c = 0; d = lastRow; e = 0; f = lastColumn; g = lastColumn; h = lastRow;
			w = front.getColumn(); x = behind.getRow(); y = behind.getColumn(); z = front.getRow();
		} 
		else if (corner.equals("BottomRight")){
			a = 1; b = -1; c = 0; d = lastRow; e = lastColumn; f = 0; g = lastColumn; h = 0;
			w = front.getRow(); x = behind.getColumn(); y = behind.getRow(); z = front.getColumn();
		} 
		else {
			throw new IllegalArgumentException("unknown cornor " + corner);
		}

		boolean isCorner = 
			current.getColumn()+a == front.getColumn() && current.getRow()+b == behind.getRow() || //piece in the middle of the board
		 	current.getColumn()+a == behind.getColumn() && current.getRow()+b == front.getRow() || 
		 	current.getRow() == c && current.getColumn()+a == front.getColumn() && behind.getRow() == d || //piece in the bottom row
		 	current.getRow() == c && current.getColumn()+a == behind.getColumn() && front.getRow() == d || 
		 	current.getColumn() == e && current.getRow()+b == front.getRow() && behind.getColumn() == f || //piece in the last column
			current.getColumn() == e && current.getRow()+b == behind.getRow() && front.getColumn() == f || 
			current.getColumn() == e && current.getRow() == c && w == g && x == h || //piece in corner of the board
			current.getColumn() == e && current.getRow() == c && y == g && z == h;
		
		return isCorner;
	}

	protected void drawFood(Graphics2D context, Food food, Board board) {
		Rectangle foodRectangle = getRectangleForField(food.getPosition(), board);
		Image scaledApple = ResourceImages.APPLE.getScaledInstance(foodRectangle.width, foodRectangle.height, Image.SCALE_SMOOTH);
		context.drawImage(scaledApple, foodRectangle.x, foodRectangle.y, null);
	}
	
	protected void drawGameLost(Graphics2D context, Board board) {
		drawGameOver(context, board);
		
		// Title Image.
		Rectangle boardRectangle = getRectangleForBoard(board);
		Rectangle popupRectangle = getRectangleForPopUp(board);
		int x2 = boardRectangle.x + boardRectangle.width/2 - ResourceImages.TITLE_GAME_OVER.getWidth()/2;
		int y2 = popupRectangle.y;
		context.drawImage(ResourceImages.TITLE_GAME_OVER, x2, y2, null);
		
	}
	
	protected void drawGameWon(Graphics2D context, Board board) {
		drawGameOver(context, board);

		// Title Image.
		Rectangle boardRectangle = getRectangleForBoard(board);
		Rectangle popupRectangle = getRectangleForPopUp(board);
		int x2 = boardRectangle.x + boardRectangle.width/2 - ResourceImages.TITLE_GAME_WON.getWidth()/2;
		int y2 = popupRectangle.y;
		context.drawImage(ResourceImages.TITLE_GAME_WON, x2, y2, null);
	}
	
	protected void drawGameOver(Graphics2D context, Board board) {
		context.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		Rectangle boardRectangle = getRectangleForBoard(board);
		Rectangle popupRectangle = getRectangleForPopUp(board);
		
		drawPopUp(context, popupRectangle);

		// Buttons
		int xPlayAgain = boardRectangle.x + boardRectangle.width/2 - buttonPlayAgain.getWidth()/2 - 100;
		int xMenu = boardRectangle.x + boardRectangle.width/2 - buttonMenu.getWidth()/2 + 100;
		int yPlayAgain = popupRectangle.y + popupRectangle.height - buttonPlayAgain.getHeight() - 20;
		buttonPlayAgain.setLocation(xPlayAgain, yPlayAgain);
		buttonMenu.setLocation(xMenu, yPlayAgain);
	}

	protected void drawPaused(Graphics2D context, Board board) {
		Rectangle boardRectangle = getRectangleForBoard(board);
		Rectangle popupRectangle = getRectangleForPopUp(board);
		
		drawPopUp(context, popupRectangle);

		// Pause Title.
		int pauseX = boardRectangle.x + boardRectangle.width/2 - ResourceImages.TITLE_PAUSED.getWidth()/2;
		int pauseY = popupRectangle.y;
		context.drawImage(ResourceImages.TITLE_PAUSED, pauseX, pauseY, null);

		// Text.
		String pauseMessage = "Press 'P' to resume game";
		int pauseTextX = boardRectangle.x + boardRectangle.width/2 - pauseMessage.length()*10/2;
		int pauseTextY = boardRectangle.y + boardRectangle.height/2 + 25;
		context.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		context.setColor(ResourceColors.PANEL_COLOR);
		context.setFont(PAUSE_FONT);
		context.drawString(pauseMessage, pauseTextX, pauseTextY);
	}
	
	protected void drawBackground(Graphics2D context) {
		for (int x = 0; x < getWidth(); x += ResourceImages.BACKGROUND.getWidth()) {
			for (int y = 0; y < getHeight(); y += ResourceImages.BACKGROUND.getHeight()) {
				context.drawImage(ResourceImages.BACKGROUND, x, y, this);
			}
		}
	}
	
	protected void drawPopUp(Graphics2D context, Rectangle popupRectangle) {
		// Draw a transparent black on top of everything.
		context.setColor(ResourceColors.TRANSPARENT_BLACK);
		context.fillRect(0, 0, getWidth(), getHeight());
		// Draw a pop up area.
		context.setColor(ResourceColors.POPUP_COLOR);
		context.fill(popupRectangle);
	}
	
	protected void showButtons() {
		if (!showingButtons) {
			add(buttonPlayAgain);
			add(buttonMenu);
			showingButtons = true;
			validate();
		}
	}
	
	protected void removeButtons() {
		if (showingButtons) {
			remove(buttonPlayAgain);
			remove(buttonMenu);
			showingButtons = false;
			validate();
		}
	}
	
	/**
	 * Get the window rectangle for a field on the board. This rectangle is where we display
	 * the field.
	 */
	protected Rectangle getRectangleForField(Field position, Board board) {
		Rectangle boardRectangle = getRectangleForBoard(board);
		int fieldSideLength = getFieldSideLength(board);
		Rectangle rectangle = new Rectangle(
			position.getColumn()* fieldSideLength + boardRectangle.x, 
			position.getRow() * fieldSideLength + boardRectangle.y, 
			fieldSideLength,
			fieldSideLength
		);
		return rectangle;
	}

	/**
	 * Get the window rectangle for the board. This rectangle is where we display the board.
	 */
	protected Rectangle getRectangleForBoard(Board board) {
		Dimension windowSize = getSize();
		Dimension gameSize = new Dimension(board.getWidth(), board.getHeight());
		int fieldSideLength = getFieldSideLength(board);

		int offsetHeight = (windowSize.height - fieldSideLength * gameSize.height) / 2;;
		int offsetWidth = (windowSize.width - fieldSideLength * gameSize.width) / 2;

		Rectangle rectangle = new Rectangle(
			offsetWidth, 
			offsetHeight, 
			fieldSideLength * gameSize.width, 
			fieldSideLength * gameSize.height
		);
		return rectangle;
	}
	
	/**
	 * Get the window rectangle for the popup area. This is where we display pause screen, etc..
	 */
	protected Rectangle getRectangleForPopUp(Board board) {
		Rectangle boardRectangle = getRectangleForBoard(board);
		int width = getSize().width;
		int height = 200;
		int x = 0;
		int y = boardRectangle.y + boardRectangle.height/2 - height/2;
		return new Rectangle(x, y, width, height);
	}
	
	/**
	 * Get the pixel side length of fields when they are displayed in the window.
	 */
	protected int getFieldSideLength(Board board) {
		Dimension windowSize = getSize();
		Dimension gameSize = new Dimension(board.getWidth(), board.getHeight());
		int fieldWidth = windowSize.width / gameSize.width;
		int fieldHeight = (windowSize.height - 20) / gameSize.height;
		
		// Choose the smallest of the two dimensions. This gives square fields that
		// fits in the window.
		int fieldSideLength = 0;
		if (fieldWidth >= fieldHeight) {
			fieldSideLength = fieldHeight;
		} else {
			fieldSideLength = fieldWidth;
		}
		
		if (fieldSideLength == 0) {
			fieldSideLength = 1;
		}
		return fieldSideLength;
	}
	
	/**
	 * Generate images that has a new snake color. We use these images to draw different
	 * colored snakes.
	 */
	private void addSnakeColor(Color color) {
		if (snakeColors.contains(color)) {
			return;
		}
		
		// Generate images with the specified color.
		snakeColors.add(color);
		snakeBodyBL.add(colorSnakeImage(ResourceImages.SNAKE_CORNER_BL, color));
		snakeBodyBR.add(colorSnakeImage(ResourceImages.SNAKE_CORNER_BR, color));
		snakeBodyTL.add(colorSnakeImage(ResourceImages.SNAKE_CORNER_TL, color));
		snakeBodyTR.add(colorSnakeImage(ResourceImages.SNAKE_CORNER_TR, color));
		snakeBodyVertical.add(colorSnakeImage(ResourceImages.SNAKE_VERTICAL, color));
		snakeBodyHorizontal.add(colorSnakeImage(ResourceImages.SNAKE_HORIZONTAL, color));
		snakeHeadUp.add(colorSnakeImage(ResourceImages.SNAKE_HEAD_UP, color));
		snakeHeadDown.add(colorSnakeImage(ResourceImages.SNAKE_HEAD_DOWN, color));
		snakeHeadLeft.add(colorSnakeImage(ResourceImages.SNAKE_HEAD_LEFT, color));
		snakeHeadRight.add(colorSnakeImage(ResourceImages.SNAKE_HEAD_RIGHT, color));
		snakeTailUp.add(colorSnakeImage(ResourceImages.SNAKE_TAIL_UP, color));
		snakeTailDown.add(colorSnakeImage(ResourceImages.SNAKE_TAIL_DOWN, color));
		snakeTailLeft.add(colorSnakeImage(ResourceImages.SNAKE_TAIL_LEFT, color));
		snakeTailRight.add(colorSnakeImage(ResourceImages.SNAKE_TAIL_RIGHT, color));
	}
	
	/**
	 * Color a single image, replacing the original snake color with a new.
	 */
	private BufferedImage colorSnakeImage(BufferedImage image, Color color) {
		// Create and return a deep copy of the image with the new color.
		int red = color.getRed();
		int green = color.getGreen();
		int blue = color.getBlue();
		WritableRaster raster = image.copyData(null);
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				// Do not color the eye pixels.
				if (image.getRGB(x, y) != -13547430){ 
					int[] pixel = raster.getPixel(x, y, (int[]) null);
					pixel[0] = red;
					pixel[1] = green;
					pixel[2] = blue;
					raster.setPixel(x, y, pixel);
				}
			}
		}
		return new BufferedImage(image.getColorModel(), raster, image.isAlphaPremultiplied(), null);
	}

}
