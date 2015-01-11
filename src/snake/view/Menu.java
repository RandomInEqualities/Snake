package snake.view;

import java.awt.*;

import javax.swing.*;

import snake.control.ControlMenu;

public class Menu extends JPanel{
	
	private CustomImages images;
	private View view;
	private JButton singleplayer, controls, quit;

	public Menu(View view){
		this.view = view;
		images = new CustomImages();
		
		//Buttons
		singleplayer = new JButton(new ImageIcon(images.singleplayer_btn));
		setButton(singleplayer);
		controls = new JButton(new ImageIcon(images.controls_btn));
		setButton(controls);
		quit = new JButton(new ImageIcon(images.quit_btn));
		setButton(quit);
		
		//Control buttons
		ControlMenu control = new ControlMenu(view);
		singleplayer.addActionListener(control);
		singleplayer.setActionCommand("singleplayer");
		controls.addActionListener(control);
		controls.setActionCommand("controls");
		quit.addActionListener(control);
		quit.setActionCommand("quit");
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
		context.setColor(CustomColor.POPUP_COLOUR);
		context.fill(getRectangleForMenu(width));
	}
	private void drawMenu(Graphics2D context) {
		//Title
		int x = getSize().width/2-images.menuTitle.getWidth()/2;
		int y = 20;
		context.drawImage(images.menuTitle, x, y, null);

		// Buttons
		int buttonWidth = images.singleplayer_btn.getWidth();
		int buttonHeight = images.singleplayer_btn.getHeight();
		int xSingleplayer = getSize().width/2-buttonWidth/2;
		int ySingleplayer = y+images.menuTitle.getHeight()+50;
		int gap = 10;
		
		singleplayer.setBounds(xSingleplayer, ySingleplayer, buttonWidth, buttonHeight);
		controls.setBounds(xSingleplayer, ySingleplayer+2*(buttonHeight+gap), buttonWidth, buttonHeight);
		quit.setBounds(xSingleplayer, ySingleplayer+4*(buttonHeight+gap), buttonWidth, buttonHeight);
		
		this.add(singleplayer);
		this.add(controls);
		this.add(quit);
		



		/*
		context.drawImage(images.multiplayer_btn, x2, y2+buttonHeight+gap, null);

		context.drawImage(images.highScores_btn, x2, y2+3*(buttonHeight+gap), null);
		context.drawImage(images.quit_btn, x2, y2+4*(buttonHeight+gap), null);
		singleplayer = new Rectangle(x2, y2, buttonWidth, buttonHeight);
		multiplayer = new Rectangle(x2, y2+buttonHeight+gap, buttonWidth, buttonHeight);
		controls = new Rectangle(x2, y2+2*(buttonHeight+gap), buttonWidth, buttonHeight);
		highScores = new Rectangle(x2, y2+3*(buttonHeight+gap), buttonWidth, buttonHeight);
		quit = new Rectangle(x2, y2+4*(buttonHeight+gap), buttonWidth, buttonHeight);*/

	}

	public Rectangle getRectangleForMenu(int width) {
		int offsetHeight = 10;
		int offsetWidth = (width - 500) /2;

		Rectangle rectangle = new Rectangle(offsetWidth, offsetHeight, 500, 500);
		return rectangle;
	}
	

	//Tile background
	public void drawBackground(Graphics2D context, int width, int height) {
		for (int x = 0; x < width; x += images.background.getWidth()) {
			for (int y = 0; y < height; y += images.background.getHeight()) {
				context.drawImage(images.background, x, y, this);
			}
		}
	}
	
	public void setButton(JButton button){
		button.setPreferredSize(new Dimension(140, 50));
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}
}
