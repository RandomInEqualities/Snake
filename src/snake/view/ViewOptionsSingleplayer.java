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
		setColorButtonParameters(green);
		green.setBorder(new LineBorder(Colors.PANEL_COLOR, 3));
		blue = new JButton(new ImageIcon (Images.BUTTON_BLUE));
		setColorButtonParameters(blue);
		red = new JButton(new ImageIcon (Images.BUTTON_RED));
		setColorButtonParameters(red);
		yellow = new JButton(new ImageIcon(Images.BUTTON_YELLOW));
		setColorButtonParameters(yellow);
		
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
		drawColorButtons(context2D);
	}
	public void drawColorButtons(Graphics2D context){
		int gap = 10;
		int sizeColor = 30;
		int xBlue = super.panelOptions.getWidth()/2-sizeColor-gap/2;
		int yColor = 180;
		green.setBounds(xBlue-gap-sizeColor, yColor, sizeColor, sizeColor);
		blue.setBounds(xBlue, yColor, sizeColor, sizeColor);
		red.setBounds(xBlue+gap+sizeColor, yColor, sizeColor, sizeColor);
		yellow.setBounds(xBlue+2*gap+2*sizeColor, yColor, sizeColor, sizeColor);
	}
}