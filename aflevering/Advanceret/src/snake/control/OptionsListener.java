package snake.control;

import java.awt.event.*;

import snake.view.*;


/**
 * Base control class for the options menus. Sub classes should implement the 
 * startGame method.
 */
public abstract class OptionsListener extends KeyAdapter implements ActionListener {
	
	public enum Difficulty {
		KINDERGARTEN,
		EASY,
		INTERMEDIATE,
		HARD
	}
	
	private ViewFrame view;
	private OptionsBasePanel optionsPanel;
	protected Difficulty difficulty;
	
	public OptionsListener(ViewFrame view, OptionsBasePanel optionsPanel) {
		super();
		this.view = view;
		this.optionsPanel = optionsPanel;
		
		// Default difficulty is easy.
		this.difficulty = Difficulty.EASY;
		optionsPanel.buttonPress("easy");
	}

	/**
	 * Start the game. Is called when the user clicks play.
	 */
	public abstract void startGame();

	@Override
	public void actionPerformed(ActionEvent event) {
		
		if (event.getActionCommand() == "play") {
			startGame();
		} 
		else if (event.getActionCommand() == "kindergarten") {
			difficulty = Difficulty.KINDERGARTEN;
		} 
		else if (event.getActionCommand() == "easy") {
			difficulty = Difficulty.EASY;
		} 
		else if (event.getActionCommand() == "intermediate"){
			difficulty = Difficulty.INTERMEDIATE;
		} 
		else if (event.getActionCommand() == "hard") {
			difficulty = Difficulty.HARD;
		} 
		else if (event.getActionCommand() == "back") {
			view.showMenu();
		} 
		
		optionsPanel.buttonPress(event.getActionCommand());
	}

	@Override
	public void keyPressed(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			view.showMenu();
		}
		if (event.getKeyCode() == KeyEvent.VK_ENTER) {
			startGame();
		}
	}
	
}
