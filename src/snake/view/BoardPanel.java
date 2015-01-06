package snake.view;
import java.awt.*;

import javax.swing.*;

import snake.model.Field;
import snake.model.Game;

import java.util.*;

public class BoardPanel extends JPanel implements Observer {

	private static final long serialVersionUID = 9109362543987437505L;
	
	Game game;
	
	public BoardPanel(Game game) {
		super();
		this.game = game;
		game.getPlayer().addObserver(this);
		game.getFood().addObserver(this);
	}
	
	private void drawSnake(Graphics2D context) {
		if (game.getPlayer().getSnake() == null) {
			System.out.println("WHAT");
			return;
		}
		for (Field position : game.getPlayer().getSnake()) {
			context.fill(getWindowRectangle(position));
		}
	}
	
	private void drawLevel(Graphics2D context) {
		context.draw(new Rectangle(0, 0, getWidth() - 1, getHeight() - 1));
	}
	
	private void drawFood(Graphics2D context) {
		context.fill(getWindowRectangle(game.getFood().getPosition()));
	}
	
	public Rectangle getWindowRectangle(Field position) {
		
		Dimension windowSize = getSize();
		Dimension gameSize = game.getSize();
		
		if (windowSize.width < gameSize.width || windowSize.height < gameSize.height) {
			throw new IllegalArgumentException("window is too small");
		}
		
		int patchWidth = windowSize.width/gameSize.width;
		int patchHeight = windowSize.height/gameSize.height;
		Rectangle windowRectangle = new Rectangle(
			position.getColumn() * patchWidth, 
			position.getRow() * patchHeight, 
			patchWidth, 
			patchHeight
		);
		
		return windowRectangle;
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

}