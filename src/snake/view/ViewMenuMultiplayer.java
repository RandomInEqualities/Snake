package snake.view;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.*;
import javax.swing.border.LineBorder;

import snake.model.*;


public class ViewMenuMultiplayer extends ViewMenuOptions {
	private JButton green, blue, red, yellow, green2, blue2, red2, yellow2;
	public ViewMenuMultiplayer(View view, GameMultiplayer game){
		super(view, game.getBoard());
		
		//Colour buttons
		green = new JButton(new ImageIcon (Images.BUTTON_GREEN));
		view.getViewMenu().setCommonButtonParameters(green);
		green.setBorder(new LineBorder(Colors.PANEL_COLOUR, 3));
		blue = new JButton(new ImageIcon (Images.BUTTON_BLUE));
		view.getViewMenu().setOptionButton(blue);
		red = new JButton(new ImageIcon (Images.BUTTON_RED));
		view.getViewMenu().setOptionButton(red);
		yellow = new JButton(new ImageIcon(Images.BUTTON_YELLOW));
		view.getViewMenu().setOptionButton(yellow);
		green2 = new JButton(new ImageIcon(Images.BUTTON_GREEN));
		view.getViewMenu().setCommonButtonParameters(green2);
		green2.setBorder(new LineBorder(Colors.PANEL_COLOUR, 3));
		blue2 = new JButton(new ImageIcon(Images.BUTTON_BLUE));
		view.getViewMenu().setOptionButton(blue2);
		red2 = new JButton(new ImageIcon(Images.BUTTON_RED));
		view.getViewMenu().setOptionButton(red2);
		yellow2 = new JButton(new ImageIcon(Images.BUTTON_YELLOW));
		view.getViewMenu().setOptionButton(yellow2);
		
		// Add new buttons
		super.panel.add(green);
		super.panel.add(blue);
		super.panel.add(red);
		super.panel.add(yellow);
		super.panel.add(green2);
		super.panel.add(blue2);
		super.panel.add(red2);
		super.panel.add(yellow2);
	}
	
	protected void paintComponent(Graphics context) {
		super.paintComponent(context);
		Graphics2D context2D = (Graphics2D) context;
		drawColourButtons(context2D);
	}
	
	public void drawColourButtons(Graphics2D context){
		context.setColor(Colors.PANEL_COLOUR);
		context.setFont(new Font("Sans_Serif", Font.BOLD, 12));
		context.drawString("Player 1", getWidth()/2-175, super.getPanel().getY()+180);
		context.drawString("Player 2", getWidth()/2+25, super.getPanel().getY()+180);
		
		int gap = 10;
		int shift = 100;
		int sizeColour = 30;
		int xBlue = super.getPanel().getWidth()/2-sizeColour-gap/2;
		int yColour = super.getPanel().getY()+180;
		green.setBounds(xBlue-gap-sizeColour-shift, yColour, sizeColour, sizeColour);
		blue.setBounds(xBlue-shift, yColour, sizeColour, sizeColour);
		red.setBounds(xBlue+gap+sizeColour-shift, yColour, sizeColour, sizeColour);
		yellow.setBounds(xBlue+2*gap+2*sizeColour-shift, yColour, sizeColour, sizeColour);
		green2.setBounds(xBlue-gap-sizeColour+shift, yColour, sizeColour, sizeColour);
		blue2.setBounds(xBlue+shift, yColour, sizeColour, sizeColour);
		red2.setBounds(xBlue+gap+sizeColour+shift, yColour, sizeColour, sizeColour);
		yellow2.setBounds(xBlue+2*gap+2*sizeColour+shift, yColour, sizeColour, sizeColour);
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
	
	public JButton getGreenButton2() {
		return green2;
	}
	
	public JButton getBlueButton2() {
		return blue2;
	}
	
	public JButton getRedButton2() {
		return red2;
	}
	
	public JButton getYellowButton2() {
		return yellow2;
	}
}
