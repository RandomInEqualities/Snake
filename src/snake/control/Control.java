
package snake.control;

import java.awt.event.*;
import snake.model.*;
import snake.view.*;


public class Control extends KeyAdapter {
	
	private View view;
	
	@SuppressWarnings("unused")
	private ControlBoard boardControl;
	@SuppressWarnings("unused")
	private ControlMenu menuControl;
	@SuppressWarnings("unused")
	private ControlMenuSinglePlayer menuSinglePlayerConstrol;
	
	public Control(Game game, View view) {
		if (view == null || game == null) {
			throw new NullPointerException();
		}
		this.view = view;
		view.addKeyListener(this);
		
		boardControl = new ControlBoard(game, view);
		menuControl = new ControlMenu(view);
		menuSinglePlayerConstrol = new ControlMenuSinglePlayer(game, view);
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
				break;
			case KeyEvent.VK_ESCAPE:
				view.showMenu();
				break;
			default:
				break;
		}
		
	}

}
