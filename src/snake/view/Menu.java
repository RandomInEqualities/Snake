package snake.view;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;
import snake.model.Game;

public class Menu extends JPanel{
	
	private static final long serialVersionUID = -5571239145421408870L;

	private Game game;
	private BoardPanel board;
	private CustomImages images;
	private Rectangle singleplayer, multiplayer, controls, highscore, quit;

	public Menu(Game game, BoardPanel board){
		this.game = game;
		this.board = board;
		images = new CustomImages();
	}

	protected @Override void paintComponent(Graphics context) {
		super.paintComponent(context);

		Graphics2D context2D = (Graphics2D) context;
		board.drawBackground(context2D, getWidth(), getHeight());
		drawBoard(context2D);
		drawMenu(context2D);
	}

	private void drawBoard(Graphics2D context) {
		context.setColor(CustomColor.BOARD_COLOUR);
		context.fill(getRectangleForMenu());
	}

	private void drawMenu(Graphics2D context) {
		context.setColor(CustomColor.PANEL_COLOUR);
		context.setFont(new Font("Sans_Serif", Font.BOLD, 25));
		int menuHeight = 53;
		context.drawString("Menu", getWidth()/2-35, getRectangleForMenu().y+menuHeight);

		// Buttons
		int width = 150;
		int height = 40;
		int x = getRectangleForMenu().x + getRectangleForMenu().width / 2 - width/2;
		int y = 60;
		context.setColor(CustomColor.PANEL_COLOUR);
		singleplayer = new Rectangle(x, y + menuHeight, width, height);
		context.fillRect(x, y + menuHeight, width, height);
		multiplayer = new Rectangle(x, y + 2*menuHeight, width, height);
		context.fillRect(x, y + 2*menuHeight, width, height);
		controls = new Rectangle(x, y + 3*menuHeight, width, height);
		context.fillRect(x, y + 3*menuHeight, width, height);
		highscore = new Rectangle(x, y + 4*menuHeight, width, height);
		context.fillRect(x, y + 4*menuHeight, width, height);
		quit = new Rectangle(x, y + 5*menuHeight, width, height);
		context.fillRect(x, y + 5*menuHeight, width, height);
			
		//text in buttons
		int textWidth = x + width/2;
		context.setColor(CustomColor.YELLOW_COLOUR);
		context.setFont(new Font("Sans_Serif", Font.BOLD, 20));
		context.drawString("Singleplayer", textWidth - 65, y + menuHeight + 25);
		context.drawString("Multiplayer", textWidth - 60, y + 2*menuHeight + 25);
		context.drawString("Controls", textWidth - 45, y + 3*menuHeight + 25);
		context.drawString("HighScore", textWidth - 53, y + 4*menuHeight + 25);
		context.drawString("Quit", textWidth - 20, y + 5*menuHeight + 25);
	}
	
	public Rectangle getSingleplayer() {
		return singleplayer;
	}
	
	public Rectangle getMultiplayer() {
		return multiplayer;
	}
	
	public Rectangle getControls() {
		return controls;
	}
	
	public Rectangle getHighScore() {
		return highscore;
	}
	
	public Rectangle getQuit() {
		return quit;
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
		int offsetWidth = (getWidth() - 500) /2;

		Rectangle rectangle = new Rectangle(offsetWidth, offsetHeight,
				500, 400);
		return rectangle;
	}

}
