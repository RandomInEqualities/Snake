package snake.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.List;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import snake.model.*;


public class ViewBoard extends JPanel {
	
	private JButton buttonPlayAgain;
	private JButton buttonMenu;
	private boolean showingButtons;

	// Arrays with coloured snake images.
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
	
	public ViewBoard() {
		super();
		
		this.buttonPlayAgain = new JButton(new ImageIcon(Images.BUTTON_PLAY_AGAIN));
		this.buttonMenu = new JButton(new ImageIcon(Images.BUTTON_MENU));
		this.showingButtons = false;
		
		ViewMenu.setMenuButtonParameters(buttonPlayAgain);
		ViewMenu.setMenuButtonParameters(buttonMenu);

		setBackground(Colors.PANEL_COLOUR);
		
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
	}
	
	public JButton getButtonPlayAgain() {
		return buttonPlayAgain;
	}
	
	public JButton getButtonMenu() {
		return buttonMenu;
	}

	protected void drawBoard(Graphics2D context, Board board) {
		drawBackground(context);
		context.setColor(Colors.BOARD_COLOUR);
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
	
	protected void drawSnakeHead(Graphics2D context, Snake snake, Board board, int colorIndex) {
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
	
	protected void drawSnakeTail(Graphics2D context, Snake snake, Board board, int colorIndex) {
		
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
	
	protected void drawSnakeBody(Graphics2D context, Snake snake, Board board, int colorIndex) {
		List<Field> snakeArray = snake.getPositions();
		for (int index = 1; index < snakeArray.size() - 1; index++){ 

			Field current = snakeArray.get(index);
			Field front = snakeArray.get(index - 1);
			Field behind = snakeArray.get(index + 1);

			Image body = null;
			if (current.getRow() == front.getRow() && current.getRow() == behind.getRow()){ 
				body = snakeBodyHorizontal.get(colorIndex);
			} 
			else if (current.getColumn() == front.getColumn() && current.getColumn() == behind.getColumn()) {
				body = snakeBodyVertical.get(colorIndex);
			} 
			else if (isSnakeCorner("TopRight", current, front, behind, board)) {
				body = snakeBodyTR.get(colorIndex);
			} 
			else if (isSnakeCorner("TopLeft", current, front, behind, board)) {
				body = snakeBodyTL.get(colorIndex);;
			} 
			else if (isSnakeCorner("BottomLeft", current, front, behind, board)) {
				body = snakeBodyBL.get(colorIndex);;
			} 
			else if (isSnakeCorner("BottomRight", current, front, behind, board)) {
				body = snakeBodyBR.get(colorIndex);;
			}
			Rectangle bodyRect = getRectangleForField(snakeArray.get(index), board);
			Image bodyScaled = body.getScaledInstance(bodyRect.width, bodyRect.height, Image.SCALE_SMOOTH);
			context.drawImage(bodyScaled, bodyRect.x, bodyRect.y, null);
		}
	}
	
	/* a: Current piece compared to adjacent column, b: Current piece compared to adjacent row, 
	 * c/d: Outer rows, e/f: Outer columns, g/h: Corners of the board.
	 * see more info in report.
	 */
	protected boolean isSnakeCorner(String corner, Field current, Field front, Field behind, Board board) {
		int a = 0, b = 0, c = 0, d = 0, e = 0, f = 0, g = 0, h = 0, w = 0, x = 0, y = 0, z = 0;
		int lastRow = board.getHeight() - 1;
		int lastColumn = board.getWidth() - 1;
		if (corner.equals("TopRight")) {
			a = 1; b = 1; c = lastRow; d = 0; e = lastColumn; f = 0; g = 0; h = 0;
			w = front.getColumn(); x = behind.getRow(); y = behind.getColumn(); z = front.getRow();
		} else if (corner.equals("TopLeft")) {
			a = -1; b = 1; c = lastRow; d = 0; e = 0; f = lastColumn; g = 0; h = lastColumn;	
			w = front.getRow(); x = behind.getColumn(); y = behind.getRow(); z = front.getColumn();
		} else if (corner.equals("BottomLeft")) {
			a = -1; b = -1; c = 0; d = lastRow; e = 0; f = lastColumn; g = lastColumn; h = lastRow;
			w = front.getColumn(); x = behind.getRow(); y = behind.getColumn(); z = front.getRow();
		} else if (corner.equals("BottomRight")){
			a = 1; b = -1; c = 0; d = lastRow; e = lastColumn; f = 0; g = lastColumn; h = 0;
			w = front.getRow(); x = behind.getColumn(); y = behind.getRow(); z = front.getColumn();
		} else {
			throw new IllegalArgumentException("corner does not exist");
		}

		boolean isCorner = current.getColumn()+a == front.getColumn() && current.getRow()+b == behind.getRow()//piece in the middle of the board
				||current.getColumn()+a == behind.getColumn() && current.getRow()+b == front.getRow()
				||current.getRow() == c && current.getColumn()+a == front.getColumn() && behind.getRow() == d //piece in the bottom row
				||current.getRow() == c && current.getColumn()+a == behind.getColumn() && front.getRow() == d
				||current.getColumn() == e && current.getRow()+b == front.getRow() && behind.getColumn() == f //piece in the last column
				||current.getColumn() == e && current.getRow()+b == behind.getRow() && front.getColumn() == f
				||current.getColumn() == e && current.getRow() == c && w == g && x == h //piece in corner of the board
				||current.getColumn() == e && current.getRow() == c && y == g && z == h;
		return isCorner;
	}

	protected void drawFood(Graphics2D context, Food food, Board board) {
		Rectangle foodRectangle = getRectangleForField(food.getPosition(), board);
		Image scaledApple = Images.APPLE.getScaledInstance(foodRectangle.width, foodRectangle.height, Image.SCALE_SMOOTH);
		context.drawImage(scaledApple, foodRectangle.x, foodRectangle.y, null);
	}
	
	protected void drawGameLost(Graphics2D context, Board board) {
		Rectangle boardRectangle = getRectangleForBoard(board);
		Rectangle popupRectangle = getRectangleForPopUp(board);
		drawGameOver(context, board);
		// Title
		int x2 = boardRectangle.x + boardRectangle.width/2 - Images.TITLE_GAME_OVER.getWidth()/2;
		int y2 = popupRectangle.y;
		context.drawImage(Images.TITLE_GAME_OVER, x2, y2, null);
		
	}
	protected void drawGameWon(Graphics2D context, Board board) {
		Rectangle boardRectangle = getRectangleForBoard(board);
		Rectangle popupRectangle = getRectangleForPopUp(board);
		
		drawGameOver(context, board);
		// Title
		int x2 = boardRectangle.x + boardRectangle.width/2 - Images.TITLE_GAME_WON.getWidth()/2;
		int y2 = popupRectangle.y;
		context.drawImage(Images.TITLE_GAME_WON, x2, y2, null);
	}
	
	protected void drawGameOver(Graphics2D context, Board board) {
		context.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		Rectangle boardRectangle = getRectangleForBoard(board);
		Rectangle popupRectangle = getRectangleForPopUp(board);
		
		drawPopup(context, popupRectangle);

		// Buttons
		int buttonWidth = 140;
		int buttonHeight = 50;
		int xPlayAgain = boardRectangle.x + boardRectangle.width/2 - buttonWidth/2 - 100;
		int xMenu = boardRectangle.x + boardRectangle.width/2 - buttonWidth/2 + 100;
		int yPlayAgain = popupRectangle.y + popupRectangle.height - buttonHeight - 20;

		buttonPlayAgain.setBounds(xPlayAgain, yPlayAgain, buttonWidth, buttonHeight);
		buttonMenu.setBounds(xMenu, yPlayAgain, buttonWidth, buttonHeight);
	}

	protected void drawPaused(Graphics2D context, Board board) {
		Rectangle boardRectangle = getRectangleForBoard(board);
		Rectangle popupRectangle = getRectangleForPopUp(board);
		
		drawPopup(context, popupRectangle);

		// Title
		int x2 = boardRectangle.x + boardRectangle.width/2 - Images.TITLE_PAUSED.getWidth()/2;
		int y2 = popupRectangle.y;
		context.drawImage(Images.TITLE_PAUSED, x2, y2, null);

		// Text
		String pauseMessage = "Press 'P' to resume game";
		int x3 = boardRectangle.x + boardRectangle.width/2 - pauseMessage.length()*10/2;
		int y3 = boardRectangle.y + boardRectangle.height/2 + 25;
		context.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		context.setColor(Colors.PANEL_COLOUR);
		context.setFont(new Font("Sans_Serif", Font.BOLD, 20));
		context.drawString(pauseMessage, x3, y3);
	}
	
	protected void drawBackground(Graphics2D context) {
		for (int x = 0; x < getWidth(); x += Images.BACKGROUND.getWidth()) {
			for (int y = 0; y < getHeight(); y += Images.BACKGROUND.getHeight()) {
				context.drawImage(Images.BACKGROUND, x, y, this);
			}
		}
	}
	
	protected void drawPopup(Graphics2D context, Rectangle popupRectangle) {
		context.setColor(Colors.TRANSPARENT_BLACK);
		context.fillRect(0, 0, getWidth(), getHeight());
		context.setColor(Colors.POPUP_COLOUR);
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

	protected Rectangle getRectangleForBoard(Board board) {
		Dimension windowSize = getSize();
		Dimension gameSize = new Dimension(board.getWidth(), board.getHeight());
		int fieldSideLength = getFieldSideLength(board);

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
	
	protected Rectangle getRectangleForPopUp(Board board) {
		Rectangle boardRectangle = getRectangleForBoard(board);
		int width = getSize().width;
		int height = 200;
		int x = 0;
		int y = boardRectangle.y + boardRectangle.height/2 - height/2;
		return new Rectangle(x, y, width, height);
	}
	
	protected int getFieldSideLength(Board board) {
		Dimension windowSize = getSize();
		Dimension gameSize = new Dimension(board.getWidth(), board.getHeight());
		int fieldWidth = windowSize.width / gameSize.width;
		int fieldHeight = (windowSize.height-20) / gameSize.height;
		if (fieldWidth >= fieldHeight) {
			fieldWidth = fieldHeight;
		} else {
			fieldHeight = fieldWidth;
		}
		return fieldWidth;
	}
	
	protected void addSnakeColor(Color color) {
		if (snakeColors.contains(color)) {
			return;
		}
		// Generate images with the colour.
		snakeColors.add(color);
		snakeBodyBL.add(colourSnakeImage(Images.SNAKE_CORNER_BL, color));
		snakeBodyBR.add(colourSnakeImage(Images.SNAKE_CORNER_BR, color));
		snakeBodyTL.add(colourSnakeImage(Images.SNAKE_CORNER_TL, color));
		snakeBodyTR.add(colourSnakeImage(Images.SNAKE_CORNER_TR, color));
		snakeBodyVertical.add(colourSnakeImage(Images.SNAKE_VERTICAL, color));
		snakeBodyHorizontal.add(colourSnakeImage(Images.SNAKE_HORIZONTAL, color));
		snakeHeadUp.add(colourSnakeImage(Images.SNAKE_HEAD_UP, color));
		snakeHeadDown.add(colourSnakeImage(Images.SNAKE_HEAD_DOWN, color));
		snakeHeadLeft.add(colourSnakeImage(Images.SNAKE_HEAD_LEFT, color));
		snakeHeadRight.add(colourSnakeImage(Images.SNAKE_HEAD_RIGHT, color));
		snakeTailUp.add(colourSnakeImage(Images.SNAKE_TAIL_UP, color));
		snakeTailDown.add(colourSnakeImage(Images.SNAKE_TAIL_DOWN, color));
		snakeTailLeft.add(colourSnakeImage(Images.SNAKE_TAIL_LEFT, color));
		snakeTailRight.add(colourSnakeImage(Images.SNAKE_TAIL_RIGHT, color));
	}
	
	private BufferedImage colourSnakeImage(BufferedImage image, Color color) {
		int red = color.getRed();
		int green = color.getGreen();
		int blue = color.getBlue();
		WritableRaster raster = image.getRaster();
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				// Do not colour the eye pixels.
				if (image.getRGB(x, y) != -13547430){ 
					int[] pixel = raster.getPixel(x, y, (int[]) null);
					pixel[0] = red;
					pixel[1] = green;
					pixel[2] = blue;
					raster.setPixel(x, y, pixel);
				}
			}
		}
		return image;
	}
}
