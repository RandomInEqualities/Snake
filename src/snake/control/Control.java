
package snake.control;

import java.awt.event.*;

import javax.swing.JButton;

import snake.model.*;
import snake.view.*;


public class Control extends KeyAdapter implements ActionListener{
	
	private View view;
	
	@SuppressWarnings("unused")
	private ControlBoard boardControl;
	@SuppressWarnings("unused")
	private ControlMenu menuControl;
	@SuppressWarnings("unused")
	private ControlMenuSingleplayer menuSinglePlayerControl;
	@SuppressWarnings("unused")
	private ControlMenuControls menuControlsControl;
	private JButton soundButton;
	
	public Control(GameSinglePlayer game, View view) {
		if (view == null || game == null) {
			throw new NullPointerException();
		}
		this.view = view;
		view.addKeyListener(this);
		
		boardControl = new ControlBoard(game, view);
		menuControl = new ControlMenu(view);
		menuSinglePlayerControl = new ControlMenuSingleplayer(game, view);
		menuControlsControl = new ControlMenuControls(view);
		
		this.soundButton = view.getHeader().getSoundButton();
		soundButton.addActionListener(this);
		soundButton.setActionCommand("mute");
	}

	@Override
	public void keyPressed(KeyEvent event) {
		
		if (event == null) {
			throw new NullPointerException();
		}
		
		switch (event.getKeyCode()) {
			case KeyEvent.VK_M:
				toggleSound();
				break;
			case KeyEvent.VK_ESCAPE:
				view.showMenu();
				view.getViewBoard().removeButtons();
				break;
			default:
				break;
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "mute"){
			toggleSound();
		}
	}
	
	private void toggleSound(){
		Audio audio = view.getAudio();
		audio.setMuted(!audio.isMuted());
		view.getHeader().repaint();
		view.requestFocus();
	}
}
