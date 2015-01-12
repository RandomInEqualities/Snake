
package snake.control;

import java.awt.event.*;

import snake.model.*;
import snake.model.Game.State;
import snake.view.*;


public class Control extends KeyAdapter {
	
	private View view;
	private Game game;
	
	@SuppressWarnings("unused")
	private ControlBoard boardControl;
	@SuppressWarnings("unused")
	private ControlMenu menuControl;
	@SuppressWarnings("unused")
	private ControlMenuSingleplayer menuSinglePlayerConstrol;
	@SuppressWarnings("unused")
	private ControlMenuControls menuControlsControl;
	
	public Control(Game game, View view) {
		if (view == null || game == null) {
			throw new NullPointerException();
		}
		this.view = view;
		view.addKeyListener(this);
		
		boardControl = new ControlBoard(game, view);
		menuControl = new ControlMenu(view);
		menuSinglePlayerConstrol = new ControlMenuSingleplayer(game, view);
		menuControlsControl = new ControlMenuControls(view);
	}

	@Override
	public void keyPressed(KeyEvent event) {
		
		if (event == null) {
			throw new NullPointerException();
		}
		
		switch (event.getKeyCode()) {
			case KeyEvent.VK_M:
				Audio audio = view.getAudio();
				audio.setMuted(!audio.isMuted());
				view.getHeader().repaint();
				break;
			case KeyEvent.VK_ESCAPE:
				view.showMenu();
				view.getViewBoard().removeButtons();
				break;
			default:
				break;
		}
		
	}

}
