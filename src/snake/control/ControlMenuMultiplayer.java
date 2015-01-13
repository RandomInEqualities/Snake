package snake.control;

import java.awt.event.*;

import javax.swing.*;

import snake.model.*;
import snake.view.*;

public class ControlMenuMultiplayer extends ControlMenuSingleplayer {

	private ViewMenuMultiplayer viewMenuMultiplayer;
	private JButton green2, blue2, red2, yellow2;
	
	public ControlMenuMultiplayer(GameSinglePlayer game, View view) {
		super(game, view);
		this.viewMenuMultiplayer = view.getViewMenuMultiplayer();
		green2 = this.viewMenuMultiplayer.getGreenButton();
		blue2 = this.viewMenuMultiplayer.getBlueButton();
		red2 = this.viewMenuMultiplayer.getRedButton();
		yellow2 = this.viewMenuMultiplayer.getYellowButton();
		
		green2.addActionListener(this);
		blue2.addActionListener(this);
		red2.addActionListener(this);
		yellow2.addActionListener(this);
		
		green2.setActionCommand("green2");
		blue2.setActionCommand("blue2");
		red2.setActionCommand("red2");
		yellow2.setActionCommand("yellow2");
		
		//Inherited buttons
		viewMenuMultiplayer.getPlayButton().addActionListener(this);
		viewMenuMultiplayer.getBackButton().addActionListener(this);
//		viewMenuMultiplayer.getKindergartenButton().addActionListener(this);
//		viewMenuMultiplayer.getEasyButton().addActionListener(this);
//		viewMenuMultiplayer.getIntermediateButton().addActionListener(this);
//		viewMenuMultiplayer.getHardButton().addActionListener(this);
//		viewMenuMultiplayer.getGreenButton().addActionListener(this);
//		viewMenuMultiplayer.getBlueButton().addActionListener(this);
//		viewMenuMultiplayer.getRedButton().addActionListener(this);
		
		viewMenuMultiplayer.getPlayButton().setActionCommand("play");
		viewMenuMultiplayer.getBackButton().setActionCommand("back");
//		viewMenuMultiplayer.getKindergartenButton().setActionCommand("kindergarten");
//		viewMenuMultiplayer.getEasy().setActionCommand("easy");
//		viewMenuMultiplayer.getIntermediateButton().setActionCommand("intermediate");
//		viewMenuMultiplayer.getHardButton().setActionCommand("hard");
//		viewMenuMultiplayer.getGreenButton().setActionCommand("green");
//		viewMenuMultiplayer.getBlueButton().setActionCommand("blue");
//		viewMenuMultiplayer.getRedButton().setActionCommand("red");
//		viewMenuMultiplayer.getYellowButton().setActionCommand("yellow");
	}
	@Override
	public void actionPerformed(ActionEvent event) {
		super.actionPerformed(event);
		if (event.getActionCommand() == "green2") {
			System.out.println("test");
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
