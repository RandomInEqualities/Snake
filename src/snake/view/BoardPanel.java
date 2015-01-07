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
	private static final Color SNAKE_HEAD_COLOUR = new Color(0.1f, 0.9f, 0.1f);
	private static final Color SNAKE_COLOUR = new Color(0.153f, 0.68f, 0.38f);
	private static final Color BACKGROUND_COLOUR = new Color(0.7451f, 0.7647f,0.78f);
	private BufferedImage apple, head1, head2, head3, head4;

	private Game game;

	public BoardPanel(Game game) {
		super();

		if (game == null) {
			throw new NullPointerException();
		}
		game.addObserver(this);
		this.game = game;
		setBackground(BACKGROUND_COLOUR);
		//apple picture
		try {
			apple = ImageIO.read(new File("apple.png"));
		} catch (IOException ex) {
			System.out.println("Image not found");
		}
		//head pictures
		try {
			head1 = ImageIO.read(new File("head1.png"));
			head2 = ImageIO.read(new File("head2.png"));
			head3 = ImageIO.read(new File("head3.png"));
			head4 = ImageIO.read(new File("head4.png"));
		} catch (IOException ex) {
			System.out.println("Image not found");
		}
	}

	public void update(Observable o, Object arg) {
		repaint();
	}

	public Dimension getPreferredSize() {
		return new Dimension(800, 800);
	}

	protected void paintComponent(Graphics context) {
		super.paintComponent(context);
		Graphics2D context2D = (Graphics2D) context;
		drawLevel(context2D);
		drawSnake(context2D);
		drawFood(context2D);
	}

	private void drawLevel(Graphics2D context) {
		// Draw a level outline.
		context.draw(getWindowBoard());
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
		Image scaledHead1 = head1.getScaledInstance(headRectangle.width, headRectangle.height, Image.SCALE_SMOOTH);
		Image scaledHead2 = head2.getScaledInstance(headRectangle.width, headRectangle.height, Image.SCALE_SMOOTH);
		Image scaledHead3 = head3.getScaledInstance(headRectangle.width, headRectangle.height, Image.SCALE_SMOOTH);
		Image scaledHead4 = head4.getScaledInstance(headRectangle.width, headRectangle.height, Image.SCALE_SMOOTH);
		
		if (snake.getNeck().getRow() - snake.getHead().getRow() == 1 || snake.getHead().getRow() - snake.getNeck().getRow() == game.getBoardHeight() - 1) { //Direction UP
			context.drawImage(scaledHead1, headRectangle.x, headRectangle.y, BACKGROUND_COLOUR, null);
	 	} else if (snake.getNeck().getColumn() - snake.getHead().getColumn() == 1 || snake.getHead().getColumn() - snake.getNeck().getColumn() == game.getBoardWidth() - 1) { //Direction LEFT
			context.drawImage(scaledHead2, headRectangle.x, headRectangle.y, BACKGROUND_COLOUR, null);
		} else if (snake.getHead().getRow() - snake.getNeck().getRow() == 1 || snake.getNeck().getRow() - snake.getHead().getRow() == game.getBoardHeight() - 1) { //Direction DOWN
			context.drawImage(scaledHead3, headRectangle.x, headRectangle.y, BACKGROUND_COLOUR, null);
	 	} else if (snake.getHead().getColumn() - snake.getNeck().getColumn() == 1 || snake.getNeck().getColumn() - snake.getHead().getColumn() == game.getBoardWidth() - 1) { //Direction RIGHT
			context.drawImage(scaledHead4, headRectangle.x, headRectangle.y, BACKGROUND_COLOUR, null);
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

		Dimension windowSize = getSize();
		Dimension gameSize = game.getBoardSize();

		// if (windowSize.width < gameSize.width || windowSize.height <
		// gameSize.height) {
		// throw new
		// IllegalArgumentException("window is too small to display board");
		// }

		Rectangle rectangle = new Rectangle(position.getColumn() * getOptimalPatchSize()
				+ getWindowBoard().x, position.getRow() * getOptimalPatchSize() + getWindowBoard().y,
				getOptimalPatchSize(), getOptimalPatchSize());

		return rectangle;
	}

	public Rectangle getWindowBoard() {
		Dimension windowSize = getSize();
		Dimension gameSize = game.getBoardSize();
		
		int offsetHeight = (windowSize.height - getOptimalPatchSize() * gameSize.height) / 2;
		int offsetWidth = (windowSize.width - getOptimalPatchSize() * gameSize.width) / 2;

		Rectangle rectangle = new Rectangle(offsetWidth, offsetHeight, getOptimalPatchSize()*gameSize.width, getOptimalPatchSize()*gameSize.height);
		return rectangle;
	}
	
	public int getOptimalPatchSize(){
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
