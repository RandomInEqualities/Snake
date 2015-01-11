package snake.view;

import java.awt.*;

import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.*;
import snake.control.*;
import snake.model.*;
public class BoardPanel extends JPanel implements Observer {

	private Game game;
	private CustomImages images;
	private Rectangle playAgain_btn;
	private Rectangle menu_btn;
	private JButton playAgain, menu;
	private View view;
	private static final long serialVersionUID = 9109362543987437505L;
	
	//Popup size
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
		
		//Buttons
		playAgain = new JButton(new ImageIcon(images.playAgain_btn));
		view.getMenu().setButton(playAgain);
		menu = new JButton(new ImageIcon(images.menu_btn));
		view.getMenu().setButton(menu);
		
		//Control buttons
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
			drawGameOver(context2D);
		} else if (game.getState() == Game.State.PAUSED){
			drawPaused(context2D);
		}
	}

	//Game board
	private void drawBoard(Graphics2D context) {
		context.setColor(CustomColor.BOARD_COLOUR);
		context.fill(getRectangleForBoard());
	}

	private void drawSnake(Graphics2D context) {
		Snake snake = game.getSnake();
		
		//Temporary snake body
		context.setColor(CustomColor.SNAKE_COLOUR);
		for (Field position : snake.getPositions()) {
		context.fill(getRectangleForField(position));
		}
		
		//Incomplete snake body
		/*for(int j = 1; j<snake.getPositions().size(); j++){
		Rectangle fieldSize = getRectangleForField(snake.getPositions().get(j));
		Image horizontalScaled = images.snakeHorizontal.getScaledInstance(fieldSize.width, fieldSize.height, Image.SCALE_SMOOTH);
		Image verticalScaled = images.snakeVertical.getScaledInstance(fieldSize.width, fieldSize.height, Image.SCALE_SMOOTH);
		Image RUScaled = images.snakeVertical.getScaledInstance(fieldSize.width, fieldSize.height, Image.SCALE_SMOOTH);
		Image LUScaled = images.snakeVertical.getScaledInstance(fieldSize.width, fieldSize.height, Image.SCALE_SMOOTH);
		Image RDScaled = images.snakeVertical.getScaledInstance(fieldSize.width, fieldSize.height, Image.SCALE_SMOOTH);
		Image LDScaled = images.snakeVertical.getScaledInstance(fieldSize.width, fieldSize.height, Image.SCALE_SMOOTH);
			//Loop tegner slangen
			for (int i = 0; i<snake.getBody().size(); i++){
				//Unders�ger f�rste led
				if(snake.getBody().get(i).getPrevLink() == Direction.LEFT){
					//Unders�ger andet led
					if(snake.getBody().get(i).getNextLink() == Direction.RIGHT){
						//Tegner leddet
				}
				//Unders�ger f�rste led 
				else if(snake.getBody().get(i).getPrevLink() == Direction.RIGHT){
					if(snake.getBody().get(i).getNextLink() == Direction.LEFT){
						context.drawImage(horizontalScaled,fieldSize.x, fieldSize.y, CustomColor.BOARD_COLOUR, null);
					}
					else if(snake.getBody().get(i).getNextLink() == Direction.UP){
						context.drawImage(RUScaled,fieldSize.x, fieldSize.y, CustomColor.BOARD_COLOUR, null);
					}
					else if(snake.getBody().get(i).getNextLink() == Direction.DOWN){
						context.drawImage(RDScaled,fieldSize.x, fieldSize.y, CustomColor.BOARD_COLOUR, null);
					}
				}
				//f�rste led blah blah
				else if(snake.getBody().get(i).getPrevLink() == Direction.UP){
					if(snake.getBody().get(i).getNextLink() == Direction.DOWN){
						context.drawImage(verticalScaled,fieldSize.x, fieldSize.y, CustomColor.BOARD_COLOUR, null);
					}
					else if(snake.getBody().get(i).getNextLink() == Direction.RIGHT){
						context.drawImage(RUScaled,fieldSize.x, fieldSize.y, CustomColor.BOARD_COLOUR, null);
					}
					else if(snake.getBody().get(i).getNextLink() == Direction.LEFT){
						context.drawImage(LUScaled,fieldSize.x, fieldSize.y, CustomColor.BOARD_COLOUR, null);
					}
				}
				//f�rste led
				else if(snake.getBody().get(i).getPrevLink() == Direction.DOWN){
					if(snake.getBody().get(i).getNextLink() == Direction.UP){
						context.drawImage(verticalScaled,fieldSize.x, fieldSize.y, CustomColor.BOARD_COLOUR, null);
					}
					else if(snake.getBody().get(i).getNextLink() == Direction.LEFT){
						context.drawImage(LDScaled,fieldSize.x, fieldSize.y, CustomColor.BOARD_COLOUR, null);
					}
					else if(snake.getBody().get(i).getNextLink() == Direction.RIGHT){
						context.drawImage(RDScaled,fieldSize.x, fieldSize.y, CustomColor.BOARD_COLOUR, null);
					}
				}
			}
		}*/
		// Draw the head field with a different colour.
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
		
		Image headScaled = head.getScaledInstance(headRectangle.width,
				headRectangle.height, Image.SCALE_SMOOTH);
		context.drawImage(headScaled, headRectangle.x, headRectangle.y, CustomColor.BOARD_COLOUR, null);
		}


	private void drawFood(Graphics2D context) {
		Rectangle foodRectangle = getRectangleForField(game.getFood()
				.getPosition());
		Image scaledApple = images.apple.getScaledInstance(foodRectangle.width, foodRectangle.height, Image.SCALE_SMOOTH);
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
		int x2 = getRectangleForBoard().x + getRectangleForBoard().width / 2 - images.gameOverTitle.getWidth()/2;
		int y2 = yPopup;;
		context.drawImage(images.gameOverTitle, x2, y2, null);
		
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
		int xPlayAgain = getRectangleForBoard().x + getRectangleForBoard().width / 2- buttonWidth / 2 - 100;
		int xMenu = getRectangleForBoard().x + getRectangleForBoard().width / 2- buttonWidth / 2 + 100;
		int yPlayAgain = yPopup + heightPopup - buttonHeight - 20;
		
		playAgain.setBounds(xPlayAgain, yPlayAgain, buttonWidth, buttonHeight);
		menu.setBounds(xMenu, yPlayAgain, buttonWidth, buttonHeight);
		this.add(playAgain);
		this.add(menu);
	}
	
	private void drawPaused(Graphics2D context){
		drawPopup(context);
		
		//Title
		int x2 = getRectangleForBoard().x + getRectangleForBoard().width / 2 - images.pausedTitle.getWidth()/2;
		int y2 = yPopup;
		context.drawImage(images.pausedTitle, x2, y2, null);
		
		//Text
		String pauseMessage = "Press 'P' to resume game";
		int x3 = getRectangleForBoard().x + getRectangleForBoard().width / 2 - pauseMessage.length() * 10 / 2;
		int y3 = getRectangleForBoard().y + getRectangleForBoard().height / 2 + 25;
		context.setColor(CustomColor.PANEL_COLOUR);
		context.setFont(new Font("Sans_Serif", Font.BOLD, 20));
		context.drawString(pauseMessage, x3, y3);
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
	
	public JButton getPlayAgain(){
		return playAgain;
	}
	
	public JButton getMenu(){
		return menu;
	}
}
