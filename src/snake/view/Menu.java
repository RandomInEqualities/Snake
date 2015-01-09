package snake.view;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

import snake.model.Game;

public class Menu extends JPanel{

	private static final long serialVersionUID = -5571239145421408870L;
	
	private Game game;
	private BoardPanel board;
	private BufferedImage background;
	
	public Menu(Game game){
		this.game = game;
	}
	
	protected @Override void paintComponent(Graphics context) {
		super.paintComponent(context);

		Graphics2D context2D = (Graphics2D) context;
		drawBoard(context2D);
		drawBackground(context2D);
	}
	
	private void drawBoard(Graphics2D context) {
		context.setColor(CustomColor.BOARD_COLOUR);
		context.fill(getRectangleForMenu());
	}
	
	private void drawBackground(Graphics2D context) {
		context.setBackground(CustomColor.PANEL_COLOUR);
	}
	
	public @Override Dimension getPreferredSize() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		return new Dimension(screenSize.height-250, screenSize.height-250);
	}
	
	public Rectangle getRectangleForMenu() {
		Dimension windowSize = getSize();
		Dimension gameSize = game.getBoard().getDimension();
		int fieldSideLength = windowSize.width / gameSize.width;

		int offsetHeight = 10;
		int offsetWidth = (windowSize.width - fieldSideLength * gameSize.width) / 2;

		Rectangle rectangle = new Rectangle(offsetWidth, offsetHeight,
				fieldSideLength * gameSize.width, fieldSideLength
						* gameSize.height);
		return rectangle;
	}
}
