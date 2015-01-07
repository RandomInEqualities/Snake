
package snake.view;

import java.awt.*;
import javax.swing.*;
import java.util.*;
import snake.model.*;

public class BoardPanel extends JPanel implements Observer {

	private static final long serialVersionUID = 9109362543987437505L;
	
	private static final Color SNAKE_HEAD_COLOUR = new Color(0.1f, 0.9f, 0.1f);
	private static final Color SNAKE_COLOUR = new Color(0.1f, 0.5f, 0.1f);
	private static final Color FOOD_COLOUR = new Color(0.5f, 0.5f, 0.1f);
	
	private Game game;
	
	public BoardPanel(Game game) {
		super();
		
		if (game == null) {
			throw new NullPointerException();
		}
		game.addObserver(this);
		this.game = game;
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
		context.setColor(SNAKE_HEAD_COLOUR);
		context.fill(getWindowRectangle(snake.getHead()));
	}
	
	private void drawFood(Graphics2D context) {
		context.setColor(FOOD_COLOUR);
		context.fill(getWindowRectangle(game.getFood().getPosition()));
	}
	
	public Rectangle getWindowRectangle(Field position) {

		Dimension windowSize = getSize();
		Dimension gameSize = game.getSize();
		
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