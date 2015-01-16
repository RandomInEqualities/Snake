package snake.view;

import java.awt.*;

import javax.swing.*;

import snake.control.OptionsSingleplayerListener;
import snake.model.GameSingleplayer;


/**
 * A JPanel for customising the singleplayer game. In addition to the functionality of OptionsBasePanel
 * it allows users to choose the snake color.
 */
public class OptionsSingleplayerPanel extends OptionsBasePanel {	
	
	private JButton buttonGreen, buttonBlue, buttonRed, buttonYellow;
	private OptionsSingleplayerListener control;
	
	public OptionsSingleplayerPanel(ViewFrame view){
		super();
		
		if (view == null) {
			throw new NullPointerException();
		}
		
		buttonGreen = createColorButton(ResourceImages.BUTTON_GREEN);
		buttonBlue = createColorButton(ResourceImages.BUTTON_BLUE);
		buttonRed = createColorButton(ResourceImages.BUTTON_RED);
		buttonYellow = createColorButton(ResourceImages.BUTTON_YELLOW);
		
		buttonGreen.setActionCommand("green");
		buttonBlue.setActionCommand("blue");
		buttonRed.setActionCommand("red");
		buttonYellow.setActionCommand("yellow");
		
		super.gameOptionsPanel.add(buttonGreen);
		super.gameOptionsPanel.add(buttonBlue);
		super.gameOptionsPanel.add(buttonRed);
		super.gameOptionsPanel.add(buttonYellow);
		
		// The controller of this panel.
		control = new OptionsSingleplayerListener(view, this);
		addKeyListener(control);
		buttonPlay.addActionListener(control);
		buttonBack.addActionListener(control);
		buttonKindergarten.addActionListener(control);
		buttonEasy.addActionListener(control);
		buttonIntermediate.addActionListener(control);
		buttonHard.addActionListener(control);
		buttonGreen.addActionListener(control);
		buttonBlue.addActionListener(control);
		buttonRed.addActionListener(control);
		buttonYellow.addActionListener(control);
		
	}
	
	public void registerGame(GameSingleplayer newGame) {
		control.registerGame(newGame);
		if (newGame != null) {
			setDefaultGameSizeInput(newGame.getBoard().getWidth(), newGame.getBoard().getHeight());
		}
	}
	
	// Used to implement group toggling behaviour.
	@Override
	public void buttonPress(String button) {
		super.buttonPress(button);
		if (button == "green") {
			setActiveButton(buttonGreen, buttonBlue, buttonRed, buttonYellow);
		}
		else if (button == "blue") {
			setActiveButton(buttonBlue, buttonGreen, buttonRed, buttonYellow);
		}
		else if (button == "red") {
			setActiveButton(buttonRed, buttonBlue, buttonGreen, buttonYellow);
		}
		else if (button == "yellow") {
			setActiveButton(buttonYellow, buttonBlue, buttonRed, buttonGreen);
		}
	}
	
	@Override
	protected void paintComponent(Graphics context) {
		super.paintComponent(context);
		Graphics2D context2D = (Graphics2D) context;
		drawColorButtons(context2D);
	}
	
	public void drawColorButtons(Graphics2D context){
		int gap = 10;
		int start = super.gameOptionsPanel.getWidth()/2 - buttonGreen.getWidth() - gap/2;
		int y = 180;
		buttonGreen.setLocation(start - gap - buttonGreen.getWidth(), y);
		buttonBlue.setLocation(start, y);
		buttonRed.setLocation(start + gap + buttonRed.getWidth(), y);
		buttonYellow.setLocation(start + 2*gap + 2*buttonYellow.getWidth(), y);
	}
	
}