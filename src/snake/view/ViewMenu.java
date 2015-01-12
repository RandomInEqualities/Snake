
package snake.view;

import java.awt.*;
import javax.swing.*;


public class ViewMenu extends JPanel {
	
	private static final long serialVersionUID = 4427452065585644066L;
	
	private JButton singleplayer, controls, quit;

	public ViewMenu(View view){
		
		//Buttons
		singleplayer = new JButton(new ImageIcon(Images.BUTTON_SINGLEPLAYER));
		setButton(singleplayer);
		controls = new JButton(new ImageIcon(Images.BUTTON_CONTROLS));
		setButton(controls);
		quit = new JButton(new ImageIcon(Images.BUTTON_QUIT));
		setButton(quit);
		
	}
	
	public JButton getSinglePlayerButton() {
		return singleplayer;
	}
	
	public JButton getControlsButton() {
		return controls;
	}
	
	public JButton getQuitButton() {
		return quit;
	}

	@Override
	protected void paintComponent(Graphics context) {
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
		int xSingleplayer = getSize().width/2-buttonWidth/2;
		int ySingleplayer = y+Images.TITLE_MENU.getHeight()+50;
		int gap = 10;
		
		singleplayer.setBounds(xSingleplayer, ySingleplayer, buttonWidth, buttonHeight);
		controls.setBounds(xSingleplayer, ySingleplayer+2*(buttonHeight+gap), buttonWidth, buttonHeight);
		quit.setBounds(xSingleplayer, ySingleplayer+4*(buttonHeight+gap), buttonWidth, buttonHeight);
		
		this.add(singleplayer);
		this.add(controls);
		this.add(quit);

	}

	public Rectangle getRectangleForMenu(int width) {
		int offsetHeight = 10;
		int offsetWidth = (width - 500) /2;

		Rectangle rectangle = new Rectangle(offsetWidth, offsetHeight, 500, 500);
		return rectangle;
	}
	
	// Tile background
	public void drawBackground(Graphics2D context, int width, int height) {
		for (int x = 0; x < width; x += Images.BACKGROUND.getWidth()) {
			for (int y = 0; y < height; y += Images.BACKGROUND.getHeight()) {
				context.drawImage(Images.BACKGROUND, x, y, this);
			}
		}
	}
	
	public void setButton(JButton button){
		button.setPreferredSize(new Dimension(140, 50));
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}
	
}