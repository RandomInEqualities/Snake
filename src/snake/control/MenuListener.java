package snake.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import snake.model.GameMultiplayer;
import snake.model.GameSingleplayer;
import snake.view.ViewFrame;


/**
 * The menu listener holds the game objects (which is the models in the MVC pattern).
 * We have a single and multiplayer game and uses the ViewFrame class to display them.
 */
public class MenuListener implements ActionListener {

	private ViewFrame view;
	private GameSingleplayer gameSingleplayer;
	private GameMultiplayer gameMultiplayer;
	
	public MenuListener(ViewFrame view) {
		if (view == null) {
			throw new NullPointerException();
		}
		this.view = view;
		this.gameSingleplayer = new GameSingleplayer();
		this.gameMultiplayer = new GameMultiplayer();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand() == "singleplayer") {
			gameSingleplayer.reset();
			view.showOptionsMenu(gameSingleplayer);
		} 
		else if (event.getActionCommand() == "multiplayer") {
			gameMultiplayer.reset();
			view.showOptionsMenu(gameMultiplayer);
		}
		else if (event.getActionCommand() == "internet") {
			gameMultiplayer.reset();
			view.showInternetGame(gameMultiplayer);
		}
		else if (event.getActionCommand() == "controls") {
			view.showControlsMenu();
		} 
		else if (event.getActionCommand() == "quit") {
			view.closeWindow();
		}
	}

}

