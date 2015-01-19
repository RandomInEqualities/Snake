package snake.view;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.*;

import snake.control.OptionsMultiplayerListener;
import snake.model.*;


/**
 * A JPanel for customising the multiplayer game. In addition to the functionality of OptionsBasePanel
 * it allows users to choose snake colors.
 */
public class OptionsMultiplayerPanel extends OptionsBasePanel {
	
	private static final Font COLOR_TEXT_FONT = new Font("Sans_Serif", Font.BOLD, 12);
	
	private JButton buttonGreen;
	private JButton buttonBlue;
	private JButton buttonRed;
	private JButton buttonYellow;
	private JButton buttonGreen2;
	private JButton buttonBlue2;
	private JButton buttonRed2;
	private JButton buttonYellow2;
	
	private OptionsMultiplayerListener control;
	
	public OptionsMultiplayerPanel(ViewFrame view) {
		super();
		
		if (view == null) {
			throw new NullPointerException();
		}
		
		//Color buttons
		buttonGreen = createColorButton(ResourceImages.BUTTON_GREEN);
		buttonBlue = createColorButton(ResourceImages.BUTTON_BLUE);
		buttonRed = createColorButton(ResourceImages.BUTTON_RED);
		buttonYellow = createColorButton(ResourceImages.BUTTON_YELLOW);
		buttonGreen2 = createColorButton(ResourceImages.BUTTON_GREEN);
		buttonBlue2 = createColorButton(ResourceImages.BUTTON_BLUE);
		buttonRed2 = createColorButton(ResourceImages.BUTTON_RED);
		buttonYellow2 = createColorButton(ResourceImages.BUTTON_YELLOW);
		
		buttonGreen.setActionCommand("green");
		buttonBlue.setActionCommand("blue");
		buttonRed.setActionCommand("red");
		buttonYellow.setActionCommand("yellow");
		buttonGreen2.setActionCommand("green2");
		buttonBlue2.setActionCommand("blue2");
		buttonRed2.setActionCommand("red2");
		buttonYellow2.setActionCommand("yellow2");
		
		// Add new buttons
		super.gameOptionsPanel.add(buttonGreen);
		super.gameOptionsPanel.add(buttonBlue);
		super.gameOptionsPanel.add(buttonRed);
		super.gameOptionsPanel.add(buttonYellow);
		super.gameOptionsPanel.add(buttonGreen2);
		super.gameOptionsPanel.add(buttonBlue2);
		super.gameOptionsPanel.add(buttonRed2);
		super.gameOptionsPanel.add(buttonYellow2);
		
		// The controller of this panel.
		control = new OptionsMultiplayerListener(view, this);
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
		buttonGreen2.addActionListener(control);
		buttonBlue2.addActionListener(control);
		buttonRed2.addActionListener(control);
		buttonYellow2.addActionListener(control);
		
	}
	
	public void registerGame(GameMultiplayer newGame) {
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
		else if (button == "green2") {
			setActiveButton(buttonGreen2, buttonBlue2, buttonRed2, buttonYellow2);
		}
		else if (button == "blue2") {
			setActiveButton(buttonBlue2, buttonGreen2, buttonRed2, buttonYellow2);
		}
		else if (button == "red2") {
			setActiveButton(buttonRed2, buttonBlue2, buttonGreen2, buttonYellow2);
		}
		else if (button == "yellow2") {
			setActiveButton(buttonYellow2, buttonBlue2, buttonRed2, buttonGreen2);
		}
	}
	
	@Override
	protected void paintComponent(Graphics context) {
		super.paintComponent(context);
		Graphics2D context2D = (Graphics2D) context;
		drawColorButtons(context2D);
	}
	
	public void drawColorButtons(Graphics2D context){
		context.setColor(ResourceColors.PANEL_COLOR);
		context.setFont(COLOR_TEXT_FONT);
		context.drawString("Player 1", getWidth()/2 - 175, super.gameOptionsPanel.getY() + 180);
		context.drawString("Player 2", getWidth()/2 + 25, super.gameOptionsPanel.getY() + 180);
		
		int gap = 10;
		int shift = 100;
		int width = buttonGreen.getWidth();
		int start = super.gameOptionsPanel.getWidth()/2 - width - gap/2;
		int y = 190;
		buttonGreen.setLocation(start - gap - width - shift, y);
		buttonBlue.setLocation(start - shift, y);
		buttonRed.setLocation(start + gap + width - shift, y);
		buttonYellow.setLocation(start + 2*gap + 2*width - shift, y);
		buttonGreen2.setLocation(start - gap - width + shift, y);
		buttonBlue2.setLocation(start + shift, y);
		buttonRed2.setLocation(start + gap + width + shift, y);
		buttonYellow2.setLocation(start + 2*gap + 2*width + shift, y);
	}
	
}
