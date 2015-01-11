package snake.view;

import java.awt.*;
import javax.swing.*;

public class ViewMenu extends JPanel {
	
	private static final long serialVersionUID = -5571239145421408870L;

	private Rectangle singleplayer, multiplayer, controls, highScores, quit;

	public ViewMenu() {

	}

	protected @Override void paintComponent(Graphics context) {
		super.paintComponent(context);
		Graphics2D context2D = (Graphics2D) context;
		drawBackground(context2D, getWidth(), getHeight());
		drawBoard(context2D, getWidth());
		drawMenu(context2D);
	}

	public void drawBoard(Graphics2D context, int width) {
		context.setColor(Colors.POPUP_COLOUR);
		context.fill(getRectangleForMenu(width));
	}

	private void drawMenu(Graphics2D context) {
		//Title
		int x = getSize().width/2-Images.TITLE_MENU.getWidth()/2;
		int y = 20;
		context.drawImage(Images.TITLE_MENU, x, y, null);

		// Buttons
		int buttonWidth = Images.BUTTON_SINGLEPLAYER.getWidth();
		int buttonHeight = Images.BUTTON_SINGLEPLAYER.getHeight();
		int x2 = getSize().width/2-buttonWidth/2;
		int y2 = y+Images.TITLE_MENU.getHeight()+50;
		int gap = 10;

		context.drawImage(Images.BUTTON_SINGLEPLAYER, x2, y2, null);
		context.drawImage(Images.BUTTON_MULTIPLAYER, x2, y2+buttonHeight+gap, null);
		context.drawImage(Images.BUTTON_CONTROLS, x2, y2+2*(buttonHeight+gap), null);
		context.drawImage(Images.BUTTON_HIGHSCORE, x2, y2+3*(buttonHeight+gap), null);
		context.drawImage(Images.BUTTON_QUIT, x2, y2+4*(buttonHeight+gap), null);
		singleplayer = new Rectangle(x2, y2, buttonWidth, buttonHeight);
		multiplayer = new Rectangle(x2, y2+buttonHeight+gap, buttonWidth, buttonHeight);
		controls = new Rectangle(x2, y2+2*(buttonHeight+gap), buttonWidth, buttonHeight);
		highScores = new Rectangle(x2, y2+3*(buttonHeight+gap), buttonWidth, buttonHeight);
		quit = new Rectangle(x2, y2+4*(buttonHeight+gap), buttonWidth, buttonHeight);

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
	
	public Rectangle getHighscore() {
		return highScores;
	}
	
	public Rectangle getQuit() {
		return quit;
	}

	public Rectangle getRectangleForMenu(int width) {
		int offsetHeight = 10;
		int offsetWidth = (width - 500) /2;

		Rectangle rectangle = new Rectangle(offsetWidth, offsetHeight, 500, 500);
		return rectangle;
	}
	
	public void drawBackground(Graphics2D context, int width, int height) {
		for (int x = 0; x < width; x += Images.BACKGROUND.getWidth()) {
			for (int y = 0; y < height; y += Images.BACKGROUND.getHeight()) {
				context.drawImage(Images.BACKGROUND, x, y, this);
			}
		}
	}
	
}
