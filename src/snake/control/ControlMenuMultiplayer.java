package snake.control;

import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.Border;

import snake.model.*;
import snake.view.*;

public class ControlMenuMultiplayer extends ControlMenuOptions {
	private GameSingleplayer game;
	private View view;
	private ViewMenuMultiplayer viewMenuMultiplayer;
	private JButton green, blue, red, yellow, green2, blue2, red2, yellow2;
	
	public ControlMenuMultiplayer(GameMultiplayer game, View view, ViewMenuMultiplayer menuView, ViewBoardMultiplayer boardView) {
		super(view, menuView);
		this.viewMenuMultiplayer = menuView;
		
		
		green2 = this.viewMenuMultiplayer.getGreenButton();
		blue2 = this.viewMenuMultiplayer.getBlueButton();
		red2 = this.viewMenuMultiplayer.getRedButton();
		yellow2 = this.viewMenuMultiplayer.getYellowButton();
		
		
		green.addActionListener(this);
		blue.addActionListener(this);
		red.addActionListener(this);
		yellow.addActionListener(this);
		green2.addActionListener(this);
		blue2.addActionListener(this);
		red2.addActionListener(this);
		yellow2.addActionListener(this);
		
		green.setActionCommand("green");
		blue.setActionCommand("blue");
		red.setActionCommand("red");
		yellow.setActionCommand("yellow");
		green2.setActionCommand("green2");
		blue2.setActionCommand("blue2");
		red2.setActionCommand("red2");
		yellow2.setActionCommand("yellow2");
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		super.actionPerformed(event);
		if (event.getActionCommand() == "green2") {
			setActiveButton(green2, blue2, red2, yellow2);
			//view.getViewBoard().setSnakeColour(84, 216, 81);
		} 
		else if (event.getActionCommand() == "blue2") {
			setActiveButton(blue2, green2, red2, yellow2);
			//view.getViewBoard().setSnakeColour(80, 152, 218);
		} 
		else if (event.getActionCommand() == "red2"){
			setActiveButton(red2, green2, blue2, yellow2);
			//view.getViewBoard().setSnakeColour(237, 75, 66);
		} 
		else if (event.getActionCommand() == "yellow2"){
			setActiveButton(yellow2, green2, blue2, red2);
			//view.getViewBoard().setSnakeColour(243, 196, 67);
		}
	}
	
	public void playGame() {
		System.out.print("play");
	}	
}