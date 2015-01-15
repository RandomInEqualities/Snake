
package snake.view;

import java.awt.*;

import javax.swing.*;

import snake.control.ControlMenu;


public class ViewMenu extends JPanel {
	
	private static final long serialVersionUID = 4427452065585644066L;
	public static final int MENU_SIDE_LENGTH = 500;
	public static final int MENU_MARGIN = 20;
	public static final int BUTTON_WIDTH = 140;
	public static final int BUTTON_HEIGHT = 50;
	public static final int BUTTON_GAP = 20;
	
	private JButton buttonSingleplayer;
	private JButton buttonMultiplayer;
	private JButton buttonControls;
	private JButton buttonQuit;
	
	public ViewMenu(View view) {
		super();
		
		// Menu Buttons
		buttonSingleplayer = new JButton(new ImageIcon(Images.BUTTON_SINGLEPLAYER));
		buttonMultiplayer = new JButton(new ImageIcon(Images.BUTTON_MULTIPLAYER));
		buttonControls = new JButton(new ImageIcon(Images.BUTTON_CONTROLS));
		buttonQuit = new JButton(new ImageIcon(Images.BUTTON_QUIT));
		setMenuButtonParameters(buttonSingleplayer);
		setMenuButtonParameters(buttonMultiplayer);
		setMenuButtonParameters(buttonControls);
		setMenuButtonParameters(buttonQuit);
		this.add(buttonSingleplayer);
		this.add(buttonMultiplayer);
		this.add(buttonControls);
		this.add(buttonQuit);
		
		// Create the control object for this menu.
		ControlMenu control = new ControlMenu(view);
		buttonSingleplayer.addActionListener(control);
		buttonMultiplayer.addActionListener(control);
		buttonControls.addActionListener(control);
		buttonQuit.addActionListener(control);
		buttonSingleplayer.setActionCommand("singleplayer");
		buttonMultiplayer.setActionCommand("multiplayer");
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
		int titleWidth = Images.TITLE_MENU.getWidth();
		int titleHeight = Images.TITLE_MENU.getHeight();
		int titleX = menuRect.x + menuRect.width/2 - titleWidth/2;
		int titleY = menuRect.y + MENU_MARGIN;
		context.drawImage(Images.TITLE_MENU, titleX, titleY, null);
		
		// Set button positions.
		int initialX = menuRect.x + menuRect.width/2 - BUTTON_WIDTH/2;
		int initialY = titleY + titleHeight + 50;
		buttonSingleplayer.setLocation(initialX, initialY);
		buttonMultiplayer.setLocation(initialX, initialY + (BUTTON_HEIGHT + BUTTON_GAP));
		buttonControls.setLocation(initialX, initialY + 2 * (BUTTON_HEIGHT + BUTTON_GAP));
		buttonQuit.setLocation(initialX, initialY + 3 * (BUTTON_HEIGHT + BUTTON_GAP) + MENU_MARGIN);
	}
	
	public static void drawMenuBackground(Graphics2D context, Rectangle rectangle) {
		context.setColor(Colors.POPUP_COLOUR);
		context.fill(rectangle);
	}

	public static Rectangle computeMenuRectangle(Dimension windowSize) {
		int offsetWidth = (windowSize.width - MENU_SIDE_LENGTH) / 2;
		int offsetHeight = (windowSize.height - MENU_SIDE_LENGTH) / 2;
		return new Rectangle(offsetWidth, offsetHeight, MENU_SIDE_LENGTH, MENU_SIDE_LENGTH);
	}
	
	public static void setMenuButtonParameters(JButton button){
		button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}
	
	public static void drawTileBackground(Graphics2D context, Rectangle rectangle) {
		for (int x = 0; x < rectangle.x + rectangle.width; x += Images.BACKGROUND.getWidth()) {
			for (int y = 0; y < rectangle.y + rectangle.height; y += Images.BACKGROUND.getHeight()) {
				context.drawImage(Images.BACKGROUND, rectangle.x + x, rectangle.y + y, null);
			}
		}
	}
	
}
