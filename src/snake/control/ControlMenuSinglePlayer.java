package snake.control;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import snake.model.*;
import snake.view.*;
public class ControlMenuSinglePlayer extends KeyAdapter implements
		ActionListener {

	private Game game;
	private View view;
	private ViewMenuSinglePlayer viewMenuSinglePlayer;
	private JButton play, back, easy, intermediate, hard, green, blue, red, yellow;
	private Border thickBorder;
	
	public ControlMenuSinglePlayer(Game game, View view) {
		this.game = game;
		this.view = view;
		this.viewMenuSinglePlayer = view.getViewMenuSinglePlayer();
		this.view.addKeyListener(this);
		game.setTimedMovementSpeed(300); //default difficulty = easy
		play = this.viewMenuSinglePlayer.getPlayButton();
		back = this.viewMenuSinglePlayer.getBackButton();
		easy = this.viewMenuSinglePlayer.getEasyButton();
		intermediate = this.viewMenuSinglePlayer.getIntermediateButton();
		hard = this.viewMenuSinglePlayer.getHardButton();
		green = this.viewMenuSinglePlayer.getGreenButton();
		blue = this.viewMenuSinglePlayer.getBlueButton();
		red = this.viewMenuSinglePlayer.getRedButton();
		yellow = this.viewMenuSinglePlayer.getYellowButton();
		play.addActionListener(this);
		back.addActionListener(this);
		easy.addActionListener(this);
		intermediate.addActionListener(this);
		hard.addActionListener(this);
		green.addActionListener(this);
		blue.addActionListener(this);
		red.addActionListener(this);
		yellow.addActionListener(this);
		play.setActionCommand("play");
		back.setActionCommand("back");
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
			// kindergarten.setEnabled(false);
			easy.setEnabled(true);
			intermediate.setEnabled(true);
			hard.setEnabled(true);
			game.disableTimedMovement();
		} else if (e.getActionCommand() == "easy") {
			// kindergarten.setEnabled(true);
			easy.setEnabled(false);
			intermediate.setEnabled(true);
			hard.setEnabled(true);	
			game.enableTimedMovement();
			game.setTimedMovementSpeed(300);
		} else if (e.getActionCommand() == "intermediate"){
			// kindergarten.setBorderPainted(true);
			easy.setEnabled(true);
			intermediate.setEnabled(false);
			hard.setEnabled(true);
			game.enableTimedMovement();
			game.setTimedMovementSpeed(150);
		} else if (e.getActionCommand() == "hard") {
			// kindergarten.setBorderPainted(true);
			hard.setEnabled(true);
			easy.setEnabled(true);
			intermediate.setEnabled(true);
			hard.setEnabled(false);
			game.setTimedMovementSpeed(70);
		} else if (e.getActionCommand() == "back") {
			view.showMenu();
		} else if (e.getActionCommand() == "green") {
			green.setBorder(thickBorder);
			green.setBorderPainted(true);
			blue.setBorderPainted(false);
			red.setBorderPainted(false);
			yellow.setBorderPainted(false);
			view.getViewBoard().setColour(84, 216, 81);
		} else if (e.getActionCommand() == "blue") {
			green.setBorderPainted(false);
			blue.setBorder(thickBorder);
			blue.setBorderPainted(true);
			red.setBorderPainted(false);
			yellow.setBorderPainted(false);
			view.getViewBoard().setColour(80, 152, 218);
		} else if (e.getActionCommand() == "red"){
			green.setBorderPainted(false);
			blue.setBorderPainted(false);
			red.setBorderPainted(true);
			red.setBorder(thickBorder);
			yellow.setBorderPainted(false);
			view.getViewBoard().setColour(237, 75, 66);
		} else if (e.getActionCommand() == "yellow"){
			green.setBorderPainted(false);
			blue.setBorderPainted(false);
			red.setBorderPainted(false);
			yellow.setBorderPainted(true);
			yellow.setBorder(thickBorder);
			view.getViewBoard().setColour(243, 196, 67);
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
		String inputW = getInput(viewMenuSinglePlayer.getWidthInput());
		String inputH = getInput(viewMenuSinglePlayer.getHeightInput());

		if (inputW.isEmpty() || inputH.isEmpty()) { // if no input
			viewMenuSinglePlayer.setFilled(false);
			viewMenuSinglePlayer.repaint();
		} else {
			// if input is correct
			int inputWidth = Integer.parseInt(inputW);
			int inputHeight = Integer.parseInt(inputH);
			if (inputWidth >= 5 && inputWidth <= 100 && inputHeight >= 5 && inputHeight <= 100) {
				game.restart(inputWidth, inputHeight);
				view.showGame();
				viewMenuSinglePlayer.setValid(true);
				viewMenuSinglePlayer.setFilled(true);
			} else {
				// if input is invalid
				viewMenuSinglePlayer.setValid(false);
				viewMenuSinglePlayer.repaint();
			}
		}
	}

	// Get input without whitespace
	public String getInput(JFormattedTextField input) {
		String in = input.getText();
		String out = in.replace(" ", "");
		return out;
	}
}
