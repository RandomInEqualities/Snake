package snake.view;

import java.awt.Font;
import java.awt.Graphics2D;

import javax.swing.*;
import javax.swing.border.LineBorder;

import snake.model.*;


public class ViewMenuMultiplayer extends ViewMenuSingleplayer {

	private static final long serialVersionUID = -494390249706219313L;
	private JButton green2, blue2, red2, yellow2;
	
	public ViewMenuMultiplayer(View view, GameSinglePlayer game){
		super(view, game);
		
		// 2nd player buttons
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
		super.getPanel().add(green2);
		super.getPanel().add(blue2);
		super.getPanel().add(red2);
		super.getPanel().add(yellow2);
	}
	
	@Override
	public void drawColourButtons(Graphics2D context){
		context.setFont(new Font("Sans_Serif", Font.BOLD, 12));
		context.drawString("Player 1", getWidth()/2-175, super.getPanel().getY()+180);
		context.drawString("Player 2", getWidth()/2+25, super.getPanel().getY()+180);
		
		int gap = 10;
		int shift = 100;
		int sizeColour = 30;
		int xBlue = super.getPanel().getWidth()/2-sizeColour-gap/2;
		int yColour = super.getPanel().getY()+180;
		super.getGreenButton().setBounds(xBlue-gap-sizeColour-shift, yColour, sizeColour, sizeColour);
		super.getBlueButton().setBounds(xBlue-shift, yColour, sizeColour, sizeColour);
		super.getRedButton().setBounds(xBlue+gap+sizeColour-shift, yColour, sizeColour, sizeColour);
		super.getYellowButton().setBounds(xBlue+2*gap+2*sizeColour-shift, yColour, sizeColour, sizeColour);
		green2.setBounds(xBlue-gap-sizeColour+shift, yColour, sizeColour, sizeColour);
		blue2.setBounds(xBlue+shift, yColour, sizeColour, sizeColour);
		red2.setBounds(xBlue+gap+sizeColour+shift, yColour, sizeColour, sizeColour);
		yellow2.setBounds(xBlue+2*gap+2*sizeColour+shift, yColour, sizeColour, sizeColour);
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
