
package snake.control;

import java.awt.event.*;

import javax.swing.JButton;

import snake.model.*;
import snake.view.*;


public class Control extends KeyAdapter implements ActionListener{
	
	private View view;
	
	@SuppressWarnings("unused")
	private ControlBoardSinglePlayer boardControl;
	@SuppressWarnings("unused")
	private ControlMenu menuControl;
	@SuppressWarnings("unused")
	private ControlMenuSingleplayer menuSingleplayerControl;
	@SuppressWarnings("unused")
	private ControlMenuMultiplayer menuMultiplayerControl;
	@SuppressWarnings("unused")
	private ControlMenuControls menuControlsControl;
	private JButton soundButton;
	
	public Control(GameSinglePlayer game, View view) {
		if (view == null || game == null) {
			throw new NullPointerException();
		}
		this.view = view;
		view.addKeyListener(this);
		
		boardControl = new ControlBoardSinglePlayer(game, view, view.getViewBoard());
		menuControl = new ControlMenu(view);
		menuSingleplayerControl = new ControlMenuSingleplayer(game, view);
		menuMultiplayerControl = new ControlMenuMultiplayer(game, view);
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
