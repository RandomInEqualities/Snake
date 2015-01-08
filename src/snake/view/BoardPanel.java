package snake.view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.util.*;

import snake.model.*;

public class BoardPanel extends JPanel implements Observer {
	private static final long serialVersionUID = 9109362543987437505L;
	private static final Color SNAKE_COLOUR = new Color(0.153f, 0.68f, 0.38f);
	private static final Color BACKGROUND_COLOUR = new Color(0.7451f, 0.7647f,
			0.78f);
	private static final Color PANEL_COLOUR = new Color(0.2f, 0.286f, 0.3686f);
	private BufferedImage apple, head1, head2, head3, head4, bg;
	private Game game;

	public BoardPanel(Game game) {
		super();

		if (game == null) {
			throw new NullPointerException();
		}
		game.addObserver(this);
		this.game = game;
		
		//Tile background
		try {
			bg = ImageIO.read(new File("TileBG.png"));
		} catch (IOException ex) {
			System.out.println("Image not found");
		}
		
		setBackground(PANEL_COLOUR);

		
		// head & apple picture
		try {
			apple = ImageIO.read(new File("apple.png"));
			head1 = ImageIO.read(new File("head1.png"));
			head2 = ImageIO.read(new File("head2.png"));
			head3 = ImageIO.read(new File("head3.png"));
			head4 = ImageIO.read(new File("head4.png"));
		} catch (IOException ex) {
			throw new RuntimeException("Image not found" + ex.getMessage());
		}
	}

	public void update(Observable o, Object arg) {
		repaint();
	}

	public Dimension getPreferredSize() {
		int x;
		int y;
		
		//Wrap window around board
		if (game.getBoardHeight() > game.getBoardWidth()+20) { //if board is narrow
			x = 700;
			y = 800;
		} else if (game.getBoardWidth() > game.getBoardHeight()+20){ //if board is wide
			x = 800;
			y = 500;
		} else { //if board is (approximately) square
			x = 800;
			y = 800;
		}
		return new Dimension(x, y);

	}

	protected void paintComponent(Graphics context) {
		super.paintComponent(context);
		Graphics2D context2D = (Graphics2D) context;
		
		//Tile background
		 for (int x = 0; x < getSize().width ;x += bg.getWidth()) {  
	            for (int y = 0; y < getSize().height; y += bg.getHeight()) {  
	            	context.drawImage(bg, x, y, this); 
	            }  
	        } 
		drawLevel(context2D);
		drawFood(context2D);
		drawSnake(context2D);
	}

	private void drawLevel(Graphics2D context) {
		// Draw a level outline.
		context.setColor(BACKGROUND_COLOUR);
		context.fill(getWindowBoard());
	}

	private boolean headDirection(Direction direction) {
		
		int neckRow = game.getSnake().getNeck().getRow();
		int headRow = game.getSnake().getHead().getRow();
		int neckColumn = game.getSnake().getNeck().getColumn();
		int headColumn = game.getSnake().getHead().getColumn();
		
		boolean forwardDirection, torrusDirection;
		if(direction == Direction.UP) {
			forwardDirection = neckRow - headRow == 1;
			torrusDirection = headRow - neckRow == game.getBoardHeight() - 1;
			return forwardDirection || torrusDirection;
		} else if(direction == Direction.LEFT) {
			forwardDirection = neckColumn - headColumn == 1;
			torrusDirection = headColumn - neckColumn == game.getBoardWidth() - 1;
			return forwardDirection || torrusDirection;
		} else if(direction == Direction.DOWN) {
			forwardDirection = headRow - neckRow == 1;
			torrusDirection = neckRow - headRow == game.getBoardHeight() - 1;
			return forwardDirection || torrusDirection;
		} else { //Direction.RIGHT
			forwardDirection = headColumn - neckColumn == 1;
			torrusDirection = neckColumn - headColumn == game.getBoardWidth() - 1;
			return forwardDirection || torrusDirection;
		}
	}
	
	private void drawSnake(Graphics2D context) {
		Snake snake = game.getSnake();

		// Draw the whole snake.
		context.setColor(SNAKE_COLOUR);
		for (Field position : snake.getPositions()) {
			context.fill(getWindowRectangle(position));
		}

		// Draw the with a different colour.
		Rectangle headRectangle = getWindowRectangle(snake.getHead());
		Image scaledHead1 = head1.getScaledInstance(headRectangle.width,
				headRectangle.height, Image.SCALE_SMOOTH);
		Image scaledHead2 = head2.getScaledInstance(headRectangle.width,
				headRectangle.height, Image.SCALE_SMOOTH);
		Image scaledHead3 = head3.getScaledInstance(headRectangle.width,
				headRectangle.height, Image.SCALE_SMOOTH);
		Image scaledHead4 = head4.getScaledInstance(headRectangle.width,
				headRectangle.height, Image.SCALE_SMOOTH);

		if (headDirection(Direction.UP)){
			context.drawImage(scaledHead1, headRectangle.x, headRectangle.y,
					BACKGROUND_COLOUR, null);
		} else if (headDirection(Direction.LEFT)) {
			context.drawImage(scaledHead2, headRectangle.x, headRectangle.y,
					BACKGROUND_COLOUR, null);
		} else if (headDirection(Direction.DOWN)) {
			context.drawImage(scaledHead3, headRectangle.x, headRectangle.y,
					BACKGROUND_COLOUR, null);
		} else if (headDirection(Direction.RIGHT)) {
			context.drawImage(scaledHead4, headRectangle.x, headRectangle.y,
					BACKGROUND_COLOUR, null);
		}

	}

	private void drawFood(Graphics2D context) {
		Rectangle foodRectangle = getWindowRectangle(game.getFood()
				.getPosition());
		Image scaledApple = apple.getScaledInstance(foodRectangle.width,
				foodRectangle.height, Image.SCALE_SMOOTH);
		context.drawImage(scaledApple, foodRectangle.x, foodRectangle.y, null);
	}

	public Rectangle getWindowRectangle(Field position) {
		Rectangle rectangle = new Rectangle(position.getColumn()
				* getOptimalPatchSize() + getWindowBoard().x, position.getRow()
				* getOptimalPatchSize() + getWindowBoard().y,
				getOptimalPatchSize(), getOptimalPatchSize());

		return rectangle;
	}

	public Rectangle getWindowBoard() {
		Dimension windowSize = getSize();
		Dimension gameSize = game.getBoardSize();

		int offsetHeight = 10;
		int offsetWidth = (windowSize.width - getOptimalPatchSize()
				* gameSize.width) / 2;

		Rectangle rectangle = new Rectangle(offsetWidth, offsetHeight,
				getOptimalPatchSize() * gameSize.width, getOptimalPatchSize()
						* gameSize.height);
		return rectangle;
	}

	public int getOptimalPatchSize() {
		Dimension windowSize = getSize();
		Dimension gameSize = game.getBoardSize();
		int patchWidth = windowSize.width / gameSize.width;
		int patchHeight = windowSize.height / gameSize.height;

		if (patchWidth >= patchHeight) {
			patchWidth = patchHeight;
		} else {
			patchHeight = patchWidth;
		}
		return patchWidth;
	}
}
