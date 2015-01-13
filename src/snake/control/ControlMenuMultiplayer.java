package snake.control;

import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import snake.model.*;
import snake.view.*;

public class ControlMenuMultiplayer extends KeyAdapter implements ActionListener {

	private GameSinglePlayer game;
	private View view;
	private ViewMenuMultiplayer viewMenuMultiplayer;
	private JButton play, back, kindergarten, easy, intermediate, hard, green, blue, red, yellow;
	private Border thickBorder;
	
	public ControlMenuMultiplayer(GameSinglePlayer game, View view) {
		this.game = game;
		this.view = view;
		this.viewMenuMultiplayer = view.getViewMenuMultiplayer();
		this.view.addKeyListener(this);
		play = this.viewMenuMultiplayer.getPlayButton();
		back = this.viewMenuMultiplayer.getBackButton();
		kindergarten = this.viewMenuMultiplayer.getKindergartenButton();
		easy = this.viewMenuMultiplayer.getEasyButton();
		intermediate = this.viewMenuMultiplayer.getIntermediateButton();
		hard = this.viewMenuMultiplayer.getHardButton();
		green = this.viewMenuMultiplayer.getGreenButton();
		blue = this.viewMenuMultiplayer.getBlueButton();
		red = this.viewMenuMultiplayer.getRedButton();
		yellow = this.viewMenuMultiplayer.getYellowButton();
		game.disableTimedMovement(); //default difficulty = kindergarten
		kindergarten.setBorder(new LineBorder(Colors.PANEL_COLOUR, 3));
		
		play.addActionListener(this);
		back.addActionListener(this);
		kindergarten.addActionListener(this);
		easy.addActionListener(this);
		intermediate.addActionListener(this);
		hard.addActionListener(this);
		green.addActionListener(this);
		blue.addActionListener(this);
		red.addActionListener(this);
		yellow.addActionListener(this);
		
		play.setActionCommand("play");
		back.setActionCommand("back");
		kindergarten.setActionCommand("kindergarten");
		easy.setActionCommand("easy");
		intermediate.setActionCommand("intermediate");
		hard.setActionCommand("hard");
		green.setActionCommand("green");
		blue.setActionCommand("blue");
		red.setActionCommand("red");
		yellow.setActionCommand("yellow");
		
		thickBorder = new LineBorder(Colors.PANEL_COLOUR, 3);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "play") {
			playGame();
		} else if (e.getActionCommand() == "kindergarten") {
			active(kindergarten, easy, intermediate, hard);
			game.disableTimedMovement();
		} else if (e.getActionCommand() == "easy") {
			active(easy, kindergarten, intermediate, hard);
			game.enableTimedMovement();
			game.setTimedMovementSpeed(300);
		} else if (e.getActionCommand() == "intermediate"){
			active(intermediate, kindergarten, easy, hard);
			game.enableTimedMovement();
			game.setTimedMovementSpeed(150);
		} else if (e.getActionCommand() == "hard") {
			active(hard, kindergarten, easy, intermediate);
			game.enableTimedMovement();
			game.setTimedMovementSpeed(70);
		} else if (e.getActionCommand() == "back") {
			view.showMenu();
		} else if (e.getActionCommand() == "green") {
			active(green, blue, red, yellow);
			view.getViewBoard().setSnakeColour(84, 216, 81);
		} else if (e.getActionCommand() == "blue") {
			active(blue, green, red, yellow);
			view.getViewBoard().setSnakeColour(80, 152, 218);
		} else if (e.getActionCommand() == "red"){
			active(red, green, blue, yellow);
			view.getViewBoard().setSnakeColour(237, 75, 66);
		} else if (e.getActionCommand() == "yellow"){
			active(yellow, green, blue, red);
			view.getViewBoard().setSnakeColour(243, 196, 67);
		}
	}

	@Override
	public void keyPressed(KeyEvent event) {
		if (event == null) {
			throw new NullPointerException();
		}
		if (view.inGame()) {
			return;
		}

		switch (event.getKeyCode()) {
		case KeyEvent.VK_BACK_SPACE:
			view.showMenu();
			break;
		case KeyEvent.VK_ENTER:
			playGame();
			break;
		default:
			break;
		}
	}

	public void playGame() {
		String inputW = getInput(viewMenuMultiplayer.getWidthInput());
		String inputH = getInput(viewMenuMultiplayer.getHeightInput());

		if (inputW.isEmpty() || inputH.isEmpty()) { // if no input
			viewMenuMultiplayer.setFilled(false);
			viewMenuMultiplayer.repaint();
		} else {
			// if input is correct
			int inputWidth = Integer.parseInt(inputW);
			int inputHeight = Integer.parseInt(inputH);
			if (inputWidth >= Board.MIN_WIDTH && inputWidth <= Board.MAX_WIDTH && 
					inputHeight >= Board.MIN_HEIGHT && inputHeight <= Board.MAX_HEIGHT) {
				game.restart(inputWidth, inputHeight);
				view.showGame();
				viewMenuMultiplayer.setValid(true);
				viewMenuMultiplayer.setFilled(true);
			} else {
				// if input is invalid
				viewMenuMultiplayer.setValid(false);
				viewMenuMultiplayer.repaint();
			}
		}
	}

	// Get input without whitespace
	public String getInput(JFormattedTextField input) {
		String in = input.getText();
		String out = in.replace(" ", "");
		return out;
	}
	
	private void active(JButton active, JButton b1, JButton b2, JButton b3){
		active.setBorderPainted(true);
		active.setBorder(thickBorder);
		b1.setBorderPainted(false);
		b2.setBorderPainted(false);
		b3.setBorderPainted(false);
		view.requestFocus();
	}
	
}
