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
		green2 = new JButton();
		view.getViewMenu().setCommonButtonParameters(green2);
		green2.setBackground(Colors.GREEN);
		green2.setBorder(new LineBorder(Colors.PANEL_COLOUR, 3));
		blue2 = new JButton();
		view.getViewMenu().setCommonButtonParameters(blue2);
		blue2.setBorderPainted(false);
		blue2.setBackground(Colors.BLUE);
		red2 = new JButton();
		view.getViewMenu().setCommonButtonParameters(red2);
		red2.setBorderPainted(false);
		red2.setBackground(Colors.RED);
		yellow2 = new JButton();
		view.getViewMenu().setCommonButtonParameters(yellow2);
		yellow2.setBorderPainted(false);
		yellow2.setBackground(Colors.YELLOW);
		
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
	
}
