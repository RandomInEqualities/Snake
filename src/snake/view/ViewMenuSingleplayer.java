package snake.view;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import snake.model.GameSinglePlayer;


public class ViewMenuSingleplayer extends ViewMenuOptions{	
	private JButton green, blue, red, yellow;
	
	public ViewMenuSingleplayer(View view, GameSinglePlayer game){
		super(view, game.getBoard());
		// Formatter (limit input to three digits)
		
		green = new JButton(new ImageIcon (Images.BUTTON_GREEN));
		view.getViewMenu().setCommonButtonParameters(green);
		green.setBorder(new LineBorder(Colors.PANEL_COLOUR, 3));
		blue = new JButton(new ImageIcon (Images.BUTTON_BLUE));
		view.getViewMenu().setOptionButton(blue);
		red = new JButton(new ImageIcon (Images.BUTTON_RED));
		view.getViewMenu().setOptionButton(red);
		yellow = new JButton(new ImageIcon(Images.BUTTON_YELLOW));
		view.getViewMenu().setOptionButton(yellow);
		
		super.panel.add(green);
		super.panel.add(blue);
		super.panel.add(red);
		super.panel.add(yellow);
	}
	
	public JButton getGreenButton() {
		return green;
	}
	
	public JButton getBlueButton() {
		return blue;
	}
	
	public JButton getRedButton() {
		return red;
	}
	
	public JButton getYellowButton() {
		return yellow;
	}
	
	protected void paintComponent(Graphics context) {
		super.paintComponent(context);
		Graphics2D context2D = (Graphics2D) context;
		drawColourButtons(context2D);
	}
	public void drawColourButtons(Graphics2D context){
		int gap = 10;
		int sizeColour = 30;
		int xBlue = super.panel.getWidth()/2-sizeColour-gap/2;
		int yColour = super.panel.getY()+180;
		green.setBounds(xBlue-gap-sizeColour, yColour, sizeColour, sizeColour);
		blue.setBounds(xBlue, yColour, sizeColour, sizeColour);
		red.setBounds(xBlue+gap+sizeColour, yColour, sizeColour, sizeColour);
		yellow.setBounds(xBlue+2*gap+2*sizeColour, yColour, sizeColour, sizeColour);
	}
}