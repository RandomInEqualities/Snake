package snake.control;

import java.awt.event.*;

import javax.swing.*;

import snake.model.*;
import snake.view.*;

public class ControlMenuMultiplayer extends ControlMenuSingleplayer {

	private ViewMenuMultiplayer viewMenuMultiplayer;
	private JButton green, blue, red, yellow, green2, blue2, red2, yellow2, kindergarten, easy, intermediate, hard;
	
	public ControlMenuMultiplayer(GameSinglePlayer game, View view) { //tbchanged
		super(game, view);
		this.viewMenuMultiplayer = view.getViewMenuMultiplayer();
		green2 = this.viewMenuMultiplayer.getGreenButton2();
		blue2 = this.viewMenuMultiplayer.getBlueButton2();
		red2 = this.viewMenuMultiplayer.getRedButton2();
		yellow2 = this.viewMenuMultiplayer.getYellowButton2();
		
		green2.addActionListener(this);
		blue2.addActionListener(this);
		red2.addActionListener(this);
		yellow2.addActionListener(this);
		
		green2.setActionCommand("green2");
		blue2.setActionCommand("blue2");
		red2.setActionCommand("red2");
		yellow2.setActionCommand("yellow2");
		
		//Inherited buttons
		kindergarten = viewMenuMultiplayer.getKindergartenButton();
		easy = viewMenuMultiplayer.getEasyButton();
		intermediate = viewMenuMultiplayer.getIntermediateButton();
		hard = viewMenuMultiplayer.getHardButton();
		green = viewMenuMultiplayer.getGreenButton();
		blue = viewMenuMultiplayer.getBlueButton();
		red = viewMenuMultiplayer.getRedButton();
		yellow = viewMenuMultiplayer.getYellowButton();
		
		viewMenuMultiplayer.getPlayButton().addActionListener(this);
		viewMenuMultiplayer.getBackButton().addActionListener(this);
		kindergarten.addActionListener(this);
		easy.addActionListener(this);
		intermediate.addActionListener(this);
		hard.addActionListener(this);
		green.addActionListener(this);
		blue.addActionListener(this);
		red.addActionListener(this);
		
		viewMenuMultiplayer.getPlayButton().setActionCommand("play");
		viewMenuMultiplayer.getBackButton().setActionCommand("back");
		kindergarten.setActionCommand("kindergarten");
		easy.setActionCommand("easy");
		intermediate.setActionCommand("intermediate");
		hard.setActionCommand("hard");
		green.setActionCommand("green");
		blue.setActionCommand("blue");
		red.setActionCommand("red");
		yellow.setActionCommand("yellow");
	}
	@Override
	public void actionPerformed(ActionEvent event) {
		super.actionPerformed(event);
		if (event.getActionCommand() == "green2") {
			setActiveButton(green2, blue2, red2, yellow2);
			//view.getViewBoard().setSnakeColour2(84, 216, 81);
		} 
		else if (event.getActionCommand() == "blue2") {
			setActiveButton(blue2, green2, red2, yellow2);
			//view.getViewBoard().setSnakeColour2(80, 152, 218);
		} 
		else if (event.getActionCommand() == "red2"){
			setActiveButton(red2, green2, blue2, yellow2);
			//view.getViewBoard().setSnakeColour2(237, 75, 66);
		} 
		else if (event.getActionCommand() == "yellow2"){
			setActiveButton(yellow2, green2, blue2, red2);
			//view.getViewBoard().setSnakeColour2(243, 196, 67);
		}
	}
	
	public void playGame() {
		System.out.print("play");
	}	
}
