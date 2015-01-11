package snake.view;

import java.awt.*;

import javax.swing.*;

import java.util.*;

import snake.control.*;
import snake.model.*;

public class BoardPanel extends JPanel implements Observer {

	private Game game;
	private CustomImages images;
	private JButton playAgain, menu;
	private View view;

	// Popup size
	int widthPopup;
	int heightPopup;
	int xPopup;
	int yPopup;

	public BoardPanel(Game game, View view) {
		super();

		if (game == null) {
			throw new NullPointerException();
		}
		this.game = game;
		this.view = view;

		game.addObserver(this);
		images = new CustomImages();

		// Buttons
		playAgain = new JButton(new ImageIcon(images.playAgain_btn));
		view.getMenu().setButton(playAgain);
		menu = new JButton(new ImageIcon(images.menu_btn));
		view.getMenu().setButton(menu);

		// Control buttons
		ControlGame controlGame = new ControlGame(game, view);
		playAgain.addActionListener(controlGame);
		playAgain.setActionCommand("playAgain");
		menu.addActionListener(controlGame);
		menu.setActionCommand("menu");
	}

	public @Override void update(Observable o, Object arg) {
		repaint();
	}

	public @Override Dimension getPreferredSize() {
		return new Dimension(800, 100);
	}

	protected @Override void paintComponent(Graphics context) {
		super.paintComponent(context);
		Graphics2D context2D = (Graphics2D) context;
		view.getMenu().drawBackground(context2D, getWidth(), getHeight());
		drawBoard(context2D);
		drawFood(context2D);
		drawSnake(context2D);
		if (game.getState() == Game.State.LOST) {
			drawGameLost(context2D);
		} else if (game.getState() == Game.State.PAUSED) {
			drawPaused(context2D);
		} else if (game.getState() == Game.State.WON){
			drawGameWon(context2D);
		}
	}

	// Game board
	private void drawBoard(Graphics2D context) {
		context.setColor(CustomColor.POPUP_COLOUR);
		context.fill(getRectangleForBoard());
	}

	private void drawSnake(Graphics2D context) {
		Snake snake = game.getSnake();
		int lastRow = game.getBoard().getHeight()-1;
		int lastColumn = game.getBoard().getWidth()-1;
		
		//Draw body
		for (int i = 1; i<snake.getPositions().size()-1; i++){ //run through every body piece
			Rectangle bodyRectangle = getRectangleForField(snake.getPositions().get(i)); //the piece' position
			Field current = snake.getPositions().get(i);
			Field front = snake.getPositions().get(i-1);
			Field behind = snake.getPositions().get(i+1);
			Image body = null;

			if (current.getRow() == front.getRow() && current.getRow() == behind.getRow()){ //if current piece and front and behind are in the same row 
				body = images.snakeHorizontal;
			} else if (current.getColumn() == front.getColumn() && current.getColumn()==behind.getColumn()){ //if current piece and front and behind are in the same column
				body = images.snakeVertical;
				//Corner pieces
			} else if (current.getColumn()+1 == front.getColumn() && current.getRow()+1 == behind.getRow()//piece in the middle of the board
					||current.getColumn()+1 == behind.getColumn() && current.getRow()+1 == front.getRow()
					||current.getRow() == lastRow && current.getColumn()+1 == front.getColumn() && behind.getRow() == 0 //piece in the bottom row
					||current.getRow() == lastRow && current.getColumn()+1 == behind.getColumn() && front.getRow() == 0
					||current.getColumn() == lastColumn && current.getRow()+1 == front.getRow() && behind.getColumn() == 0 //piece in the last column
					||current.getColumn() == lastColumn && current.getRow()+1 == behind.getRow() && front.getColumn() == 0
					||current.getColumn() == lastColumn && current.getRow() == lastRow && front.getColumn() == 0 && behind.getRow() == 0 //piece in corner
					||current.getColumn() == lastColumn && current.getRow() == lastRow && behind.getColumn() == 0 && front.getRow() == 0){
				body = images.snakeCornerTL;
			} else if (current.getColumn()-1 == front.getColumn() && current.getRow()+1 == behind.getRow() //piece in the middle of the board
					||current.getColumn()-1 == behind.getColumn() && current.getRow()+1 == front.getRow()
					||current.getRow() == lastRow && current.getColumn()-1 == front.getColumn() && behind.getRow() == 0 //piece in the bottom row
					||current.getRow() == lastRow && current.getColumn()-1 == behind.getColumn() && front.getRow() == 0
					||current.getColumn() == 0 && current.getRow()+1 == front.getRow() && behind.getColumn() == lastColumn //piece in the first column
					||current.getColumn() == 0 && current.getRow()+1 == behind.getRow() && front.getColumn() == lastColumn
					||current.getColumn()==0 && current.getRow() == lastRow && front.getRow() == 0 && behind.getColumn() == lastColumn
					||current.getColumn()==0 && current.getRow() == lastRow && behind.getRow() == 0 && front.getColumn() == lastColumn){
				body = images.snakeCornerTR;
			} else if (current.getColumn()-1 == front.getColumn() && current.getRow()-1 == behind.getRow() //piece in the middle of the board
					||current.getColumn()-1 == behind.getColumn() && current.getRow()-1 == front.getRow()
					||current.getRow() == 0 && current.getColumn()-1 == front.getColumn() && behind.getRow() == lastRow //piece in the first row
					||current.getRow() == 0 && current.getColumn()-1 == behind.getColumn() && front.getRow() == lastRow
					||current.getColumn() == 0 && current.getRow()-1 == front.getRow() && behind.getColumn() == lastColumn //piece in the first column
					||current.getColumn() == 0 && current.getRow()-1 == behind.getRow() && front.getColumn() == lastColumn
					||current.getColumn() == 0 && current.getRow() == 0 && front.getColumn() == lastColumn && behind.getRow() == lastRow //piece in corner
					||current.getColumn() == 0 && current.getRow() == 0 && behind.getColumn() == lastColumn && front.getRow() == lastRow){
				body = images.snakeCornerBR;
			} else {
				body = images.snakeCornerBL;
			}
			Image bodyscaled = body.getScaledInstance(getFieldSideLength(), getFieldSideLength(), Image.SCALE_SMOOTH);
			context.drawImage(bodyscaled, bodyRectangle.x, bodyRectangle.y, null);
		}
			
		// Draw tail
		Rectangle tailRectangle = getRectangleForField(snake.getTail());
		Image tail = null;
		Field tailPiece = snake.getTail();
		Field beforeTail = snake.getPositions().get(snake.getSize()-2);
		if (tailPiece.getColumn()+1 == beforeTail.getColumn()||tailPiece.getColumn() == lastColumn && beforeTail.getColumn()==0){
			tail = images.snakeTailR;
		} else if (tailPiece.getColumn()-1 == beforeTail.getColumn() || tailPiece.getColumn()==0 && beforeTail.getColumn()==lastColumn){
			tail = images.snakeTailL;
		} else if (tailPiece.getRow()-1 == beforeTail.getRow() || tailPiece.getRow() == 0 && beforeTail.getRow()==lastRow){
			tail = images.snakeTailU;
		} else {
			tail = images.snakeTailD;
		}
		Image tailscaled = tail.getScaledInstance(getFieldSideLength(), getFieldSideLength(), Image.SCALE_SMOOTH);
		context.drawImage(tailscaled, tailRectangle.x, tailRectangle.y, null);
		
		
		// Draw head
		Rectangle headRectangle = getRectangleForField(snake.getHead());
		Image head = null;
		switch (snake.getHeadDirection()) {
		case UP:
			head = images.headUp;
			break;
		case DOWN:
			head = images.headDown;
			break;
		case LEFT:
			head = images.headLeft;
			break;
		case RIGHT:
			head = images.headRight;
			break;
		}
		
		Image headScaled = head.getScaledInstance(headRectangle.width, headRectangle.height, Image.SCALE_SMOOTH);
		context.drawImage(headScaled, headRectangle.x, headRectangle.y, null);
		}

	private void drawFood(Graphics2D context) {
		Rectangle foodRectangle = getRectangleForField(game.getFood()
				.getPosition());
		Image scaledApple = images.apple.getScaledInstance(foodRectangle.width,
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
		// Behind
		context.setColor(CustomColor.TRANS_BLACK);
		context.fillRect(0, 0, getWidth(), getHeight());

		// Popup
		widthPopup = getSize().width;
		heightPopup = 200;
		xPopup = 0;
		yPopup = getRectangleForBoard().y + getRectangleForBoard().height / 2
				- heightPopup / 2;
		context.setColor(CustomColor.POPUP_COLOUR);
		context.fillRect(xPopup, yPopup, widthPopup, heightPopup);
		context.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

	}
	private void drawGameLost(Graphics2D context) {
		drawGameOver(context);
		// Title
		int x2 = getRectangleForBoard().x + getRectangleForBoard().width / 2 - images.gameOverTitle.getWidth() / 2;
		int y2 = yPopup;
		context.drawImage(images.gameOverTitle, x2, y2, null);
		
	}
	private void drawGameWon(Graphics2D context){
		drawGameOver(context);
		// Title
		int x2 = getRectangleForBoard().x + getRectangleForBoard().width / 2 - images.gameWonTitle.getWidth() / 2;
		int y2 = yPopup;
		context.drawImage(images.gameWonTitle, x2, y2, null);
	}
	
	private void drawGameOver(Graphics2D context) {
		drawPopup(context);

		// Text
		String scoreTxt = "Final Score: " + game.getScore();
		int x3 = getRectangleForBoard().x + getRectangleForBoard().width / 2 - scoreTxt.length() * 10 / 2;
		int y3 = getRectangleForBoard().y + getRectangleForBoard().height / 2 + 5;
		context.setColor(CustomColor.PANEL_COLOUR);
		context.setFont(new Font("Sans_Serif", Font.BOLD, 20));
		context.drawString(scoreTxt, x3, y3);

		// Buttons
		int buttonWidth = 140;
		int buttonHeight = 50;
		int xPlayAgain = getRectangleForBoard().x + getRectangleForBoard().width / 2 - buttonWidth / 2 - 100;
		int xMenu = getRectangleForBoard().x + getRectangleForBoard().width / 2 - buttonWidth / 2 + 100;
		int yPlayAgain = yPopup + heightPopup - buttonHeight - 20;

		playAgain.setBounds(xPlayAgain, yPlayAgain, buttonWidth, buttonHeight);
		menu.setBounds(xMenu, yPlayAgain, buttonWidth, buttonHeight);
		this.add(playAgain);
		this.add(menu);
	}

	private void drawPaused(Graphics2D context) {
		drawPopup(context);

		// Title
		int x2 = getRectangleForBoard().x + getRectangleForBoard().width / 2
				- images.pausedTitle.getWidth() / 2;
		int y2 = yPopup;
		context.drawImage(images.pausedTitle, x2, y2, null);

		// Text
		String pauseMessage = "Press 'P' to resume game";
		int x3 = getRectangleForBoard().x + getRectangleForBoard().width / 2
				- pauseMessage.length() * 10 / 2;
		int y3 = getRectangleForBoard().y + getRectangleForBoard().height / 2
				+ 25;
		context.setColor(CustomColor.PANEL_COLOUR);
		context.setFont(new Font("Sans_Serif", Font.BOLD, 20));
		context.drawString(pauseMessage, x3, y3);
	}

	public int getFieldSideLength() {
		Dimension windowSize = getSize();
		Dimension gameSize = game.getBoard().getDimension();
		int fieldWidth = windowSize.width / gameSize.width;
		int fieldHeight = (windowSize.height - 20) / gameSize.height;
		if (fieldWidth >= fieldHeight) {
			fieldWidth = fieldHeight;
		} else {
			fieldHeight = fieldWidth;
		}
		return fieldWidth;
	}

	public JButton getPlayAgain() {
		return playAgain;
	}

	public JButton getMenu() {
		return menu;
	}
}
