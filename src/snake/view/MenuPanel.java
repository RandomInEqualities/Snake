package snake.view;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

import snake.control.MenuListener;

/**
 * The top level menu panel that greets you when starting the program. It allows you
 * to move on by clicking on buttons.
 */
public class MenuPanel extends JPanel {
	
	private static final long serialVersionUID = 4427452065585644066L;
	public static final int MENU_SIDE_LENGTH = 500;
	public static final int MENU_MARGIN = 20;
	public static final int BUTTON_WIDTH = 140;
	public static final int BUTTON_HEIGHT = 50;
	public static final int BUTTON_GAP = 20;
	
	private JButton buttonSingleplayer;
	private JButton buttonMultiplayer;
	private JButton buttonInternet;
	private JButton buttonControls;
	private JButton buttonQuit;
	
	public MenuPanel(ViewFrame view) {
		super();
		
		// Menu Buttons
		buttonSingleplayer = createMenuButton(ResourceImages.BUTTON_SINGLEPLAYER);
		buttonMultiplayer = createMenuButton(ResourceImages.BUTTON_MULTIPLAYER);
		buttonInternet = createMenuButton(ResourceImages.BUTTON_INTERNET);
		buttonControls = createMenuButton(ResourceImages.BUTTON_CONTROLS);
		buttonQuit = createMenuButton(ResourceImages.BUTTON_QUIT);

		add(buttonSingleplayer);
		add(buttonMultiplayer);
		add(buttonInternet);
		add(buttonControls);
		add(buttonQuit);
		
		// Create the control object for this menu.
		MenuListener control = new MenuListener(view);
		buttonSingleplayer.addActionListener(control);
		buttonMultiplayer.addActionListener(control);
		buttonInternet.addActionListener(control);
		buttonControls.addActionListener(control);
		buttonQuit.addActionListener(control);
		buttonSingleplayer.setActionCommand("singleplayer");
		buttonMultiplayer.setActionCommand("multiplayer");
		buttonInternet.setActionCommand("internet");
		buttonControls.setActionCommand("controls");
		buttonQuit.setActionCommand("quit");
	}

	@Override
	protected void paintComponent(Graphics context) {
		super.paintComponent(context);
		Graphics2D context2D = (Graphics2D) context;
		drawTileBackground(context2D, getVisibleRect());
		drawMenuBackground(context2D, computeMenuRectangle(getSize()));
		drawMenu(context2D);
	}
	
	private void drawMenu(Graphics2D context) {
		
		Rectangle menuRect = computeMenuRectangle(getSize());
		
		// Title Image
		int titleWidth = ResourceImages.TITLE_MENU.getWidth();
		int titleHeight = ResourceImages.TITLE_MENU.getHeight();
		int titleX = menuRect.x + menuRect.width/2 - titleWidth/2;
		int titleY = menuRect.y + MENU_MARGIN;
		context.drawImage(ResourceImages.TITLE_MENU, titleX, titleY, null);
		
		// Set button positions.
		int initialX = menuRect.x + menuRect.width/2 - BUTTON_WIDTH/2;
		int initialY = titleY + titleHeight + 50;
		buttonSingleplayer.setLocation(initialX, initialY);
		buttonMultiplayer.setLocation(initialX, initialY + (BUTTON_HEIGHT + BUTTON_GAP));
		buttonInternet.setLocation(initialX, initialY + 2 * (BUTTON_HEIGHT + BUTTON_GAP));
		buttonControls.setLocation(initialX, initialY + 3 * (BUTTON_HEIGHT + BUTTON_GAP));
		buttonQuit.setLocation(initialX, initialY + 4 * (BUTTON_HEIGHT + BUTTON_GAP) + MENU_MARGIN);
	}
	
	public static void drawMenuBackground(Graphics2D context, Rectangle rectangle) {
		context.setColor(ResourceColors.POPUP_COLOR);
		context.fill(rectangle);
	}

	public static Rectangle computeMenuRectangle(Dimension windowSize) {
		int offsetWidth = (windowSize.width - MENU_SIDE_LENGTH) / 2;
		int offsetHeight = (windowSize.height - MENU_SIDE_LENGTH) / 2;
		return new Rectangle(offsetWidth, offsetHeight, MENU_SIDE_LENGTH, MENU_SIDE_LENGTH);
	}
	
	public static JButton createMenuButton(BufferedImage image) {
		JButton button = new JButton(new ImageIcon(image));
		button.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.setFocusable(false);
		return button;
	}
	
	public static void drawTileBackground(Graphics2D context, Rectangle rectangle) {
		for (int x = 0; x < rectangle.x + rectangle.width; x += ResourceImages.BACKGROUND.getWidth()) {
			for (int y = 0; y < rectangle.y + rectangle.height; y += ResourceImages.BACKGROUND.getHeight()) {
				context.drawImage(ResourceImages.BACKGROUND, rectangle.x + x, rectangle.y + y, null);
			}
		}
	}
	
}
