package snake.control;

import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import snake.view.*;

public abstract class ControlOptions extends KeyAdapter implements ActionListener {
	public enum Difficulty {
		KINDERGARTEN,
		EASY,
		INTERMEDIATE,
		HARD
	}
	private View view;
	private ViewOptions viewMenuOptions;
	private JButton play, back, kindergarten, easy, intermediate, hard;
	private Border thickBorder;
	private Difficulty difficulty;
	
	public ControlOptions(View view, ViewOptions viewOptions) {
		super();
		this.view = view;
		this.viewMenuOptions = viewOptions;
		this.view.addKeyListener(this);
		play = this.viewMenuOptions.getPlayButton();
		back = this.viewMenuOptions.getBackButton();
		kindergarten = this.viewMenuOptions.getKindergartenButton();
		easy = this.viewMenuOptions.getEasyButton();
		intermediate = this.viewMenuOptions.getIntermediateButton();
		hard = this.viewMenuOptions.getHardButton();
		
		play.addActionListener(this);
		back.addActionListener(this);
		kindergarten.addActionListener(this);
		easy.addActionListener(this);
		intermediate.addActionListener(this);
		hard.addActionListener(this);

		play.setActionCommand("play");
		back.setActionCommand("back");
		kindergarten.setActionCommand("kindergarten");
		easy.setActionCommand("easy");
		intermediate.setActionCommand("intermediate");
		hard.setActionCommand("hard");

		thickBorder = new LineBorder(Colors.PANEL_COLOUR, 3);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand() == "play") {
			playGame();
		} 
		else if (event.getActionCommand() == "kindergarten") {
			setActiveButton(kindergarten, easy, intermediate, hard);
			setKindergardenDifficulty();
		} 
		else if (event.getActionCommand() == "easy") {
			setActiveButton(easy, kindergarten, intermediate, hard);
			setEasyDifficulty();
		} 
		else if (event.getActionCommand() == "intermediate"){
			setActiveButton(intermediate, kindergarten, easy, hard);
			setIntermediatDifficulty();
		} 
		else if (event.getActionCommand() == "hard") {
			setActiveButton(hard, kindergarten, easy, intermediate);
			setHardDifficulty();
		} 
		else if (event.getActionCommand() == "back") {
			view.showMenu();
			viewMenuOptions.setValid(true);
			viewMenuOptions.setFilled(true);
		} 
	}

	@Override
	public void keyPressed(KeyEvent event) {
		if (event == null) {
			throw new NullPointerException();
		}

		switch (event.getKeyCode()) {
			case KeyEvent.VK_BACK_SPACE:
				view.showMenu();
				viewMenuOptions.setValid(true);
				viewMenuOptions.setFilled(true);
				break;
			case KeyEvent.VK_ENTER:
				playGame();
				break;
			default:
				break;
		}
	}
	
	private void setKindergardenDifficulty() {
		setDifficulty(Difficulty.KINDERGARTEN);
	}
	
	private void setEasyDifficulty() {
		setDifficulty(Difficulty.EASY);
	}
	
	private void setIntermediatDifficulty() {
		setDifficulty(Difficulty.INTERMEDIATE);
	}
	
	private void setHardDifficulty() {
		setDifficulty(Difficulty.HARD);
	}

	public abstract void playGame();
	
	public void setActiveButton(JButton active, JButton b1, JButton b2, JButton b3){
		active.setBorderPainted(true);
		active.setBorder(thickBorder);
		b1.setBorderPainted(false);
		b2.setBorderPainted(false);
		b3.setBorderPainted(false);
		
		// Request focus so we still receive keyboard events.
		view.requestFocus();
	}
	public void setDifficulty(Difficulty d){
		this.difficulty = d;
	}
	
	public Difficulty getDifficulty(){
		return difficulty;
	}
	
	// Get input without whitespace
	public String getInput(JFormattedTextField input) {
		String in = input.getText();
		String out = in.replace(" ", "");
		return out;
	}
}
