
package snake.view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.util.*;

import snake.model.*;

public class BoardPanel extends JPanel implements Observer {

	private static final long serialVersionUID = 9109362543987437505L;
	
	private static final Color SNAKE_COLOUR = new Color(0.153f, 0.68f, 0.38f);
	private static final Color FOOD_COLOUR = new Color(0.91f, 0.298f, 0.24f);
	private static final Color BACKGROUND_COLOUR = new Color(0.7451f, 0.7647f, 0.78f);
	private BufferedImage apple, head;
	
	private Game game;
	
	public BoardPanel(Game game) {
		super();
		
		if (game == null) {
			throw new NullPointerException();
		}
		game.addObserver(this);
		this.game = game;
		setBackground(BACKGROUND_COLOUR);
		try {
			apple = ImageIO.read(new File("apple.png"));
		} catch (IOException ex) {
			System.out.println("Image not found");
		}
		try {
			head = ImageIO.read(new File("head.png"));
		} catch (IOException ex) {
			System.out.println("Image not found");
		}
	}
	
	public void update(Observable o, Object arg) {
		repaint();
	}

	protected void paintComponent(Graphics context) {
		super.paintComponent(context);
		Graphics2D context2D = (Graphics2D)context;
		drawLevel(context2D);
		drawSnake(context2D);
		drawFood(context2D);
	}
	
	private void drawLevel(Graphics2D context) {
		// Draw a level outline.
		context.draw(new Rectangle(0, 0, getWidth() - 1, getHeight() - 1));
	}
	
	private void drawSnake(Graphics2D context) {
		Snake snake = game.getSnake();
		
		// Draw the whole snake.
		context.setColor(SNAKE_COLOUR);
		for (Field position : snake.getPositions()) {
			context.fill(getWindowRectangle(position));
		}
		
		// Draw the with a different colour.
		Image scaledHead = head.getScaledInstance(10, 10, head.SCALE_SMOOTH);
		Rectangle head = getWindowRectangle(snake.getHead());
		int headXPosition = head.x;
		int headYPosition = head.y;
		context.drawImage(scaledHead, headXPosition, headYPosition, null);
	}
	
	private void drawFood(Graphics2D context) {
		Image scaledApple = apple.getScaledInstance(20, 20, apple.SCALE_SMOOTH);
		Rectangle foodPosition = getWindowRectangle(game.getFood().getPosition());
		int xPosition = foodPosition.x - foodPosition.width/8;
		int yPosition = foodPosition.y - foodPosition.height/2;
		context.drawImage(scaledApple, xPosition, yPosition, null);
		
	}
	
	public Rectangle getWindowRectangle(Field position) {

		Dimension windowSize = getSize();
		Dimension gameSize = game.getBoardSize();
		
		if (windowSize.width < gameSize.width || windowSize.height < gameSize.height) {
			throw new IllegalArgumentException("window is too small to display board");
		}
		
		int patchWidth = windowSize.width/gameSize.width;
		int patchHeight = windowSize.height/gameSize.height;
		Rectangle rectangle = new Rectangle(
			position.getColumn() * patchWidth, 
			position.getRow() * patchHeight, 
			patchWidth, 
			patchHeight
		);
		
		return rectangle;
	}

}