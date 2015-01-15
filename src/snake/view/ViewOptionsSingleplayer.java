package snake.view;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import snake.model.GameSingleplayer;


public class ViewOptionsSingleplayer extends ViewOptions{	
	private JButton green, blue, red, yellow;
	
	public ViewOptionsSingleplayer(View view, GameSingleplayer game){
		super(view, game.getBoard());
		// Formatter (limit input to three digits)
		
		green = new JButton(new ImageIcon (Images.BUTTON_GREEN));
		setColourButtonParameters(green);
		green.setBorder(new LineBorder(Colors.PANEL_COLOUR, 3));
		blue = new JButton(new ImageIcon (Images.BUTTON_BLUE));
		setColourButtonParameters(blue);
		red = new JButton(new ImageIcon (Images.BUTTON_RED));
		setColourButtonParameters(red);
		yellow = new JButton(new ImageIcon(Images.BUTTON_YELLOW));
		setColourButtonParameters(yellow);
		
		super.panelOptions.add(green);
		super.panelOptions.add(blue);
		super.panelOptions.add(red);
		super.panelOptions.add(yellow);
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
		int xBlue = super.panelOptions.getWidth()/2-sizeColour-gap/2;
		int yColour = 180;
		green.setBounds(xBlue-gap-sizeColour, yColour, sizeColour, sizeColour);
		blue.setBounds(xBlue, yColour, sizeColour, sizeColour);
		red.setBounds(xBlue+gap+sizeColour, yColour, sizeColour, sizeColour);
		yellow.setBounds(xBlue+2*gap+2*sizeColour, yColour, sizeColour, sizeColour);
	}
}