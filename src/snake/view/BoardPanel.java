package snake.view;
import java.awt.*;

import javax.swing.*;

import snake.model.Game;
import snake.model.Level;

import java.util.*;

public class BoardPanel extends JPanel implements Observer {

	private static final long serialVersionUID = 9109362543987437505L;
	
	Game game;
	
	public BoardPanel(Game game) {
		super();
		this.game = game;
		game.addObserver(this);
	}
	
	private void drawSnake(Graphics2D context) {
		context.fill(new Rectangle(0,0,getWidth(), getHeight()));
	}
	
	private void drawLevel(Graphics2D context) {
		context.draw(new Rectangle(0, 0, getWidth() - 1, getHeight() - 1));
	}
	
	private void drawPeanut(Graphics2D context) {
		
		Level level = game.getLevel();
		context.fill(new Rectangle(0,0,level.getWidth(), level.getHeight()));
		
	}
	
	public void update(Observable o, Object arg) {
		repaint();
	}

	protected void paintComponent(Graphics context) {
		super.paintComponent(context);

		Graphics2D context2D = (Graphics2D)context;

		drawLevel(context2D);
	}

}