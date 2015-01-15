package snake.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import snake.model.GameMultiplayer;
import snake.model.GameSingleplayer;
import snake.view.*;

@SuppressWarnings("unused")
public class ControlMenu implements ActionListener {

	private View view;
	
	private GameSingleplayer gameSingle;
	private ViewBoardSingleplayer boardViewSingle;
	private ViewOptionsSingleplayer menuViewSingle;
	private ViewHeaderSingleplayer headerSingle;
	private ControlBoardSingleplayer boardControlSingle;
	private ControlOptionsSingleplayer menuControlSingle;
	
	private GameMultiplayer gameMulti;
	private ViewBoardMultiplayer boardViewMulti;
	private ViewOptionsMultiplayer menuViewMulti;
	private ViewHeaderMultiplayer headerMulti;
	private ControlBoardMultiplayer boardControlMulti;
	private ControlOptionsMultiplayer menuControlMulti;
	
	public ControlMenu(View view) {
		this.view = view;

		gameSingle = new GameSingleplayer();
		menuViewSingle = new ViewOptionsSingleplayer(view, gameSingle);
		headerSingle = new ViewHeaderSingleplayer(view, gameSingle, true);
		boardViewSingle = new ViewBoardSingleplayer(headerSingle, gameSingle);
		boardControlSingle = new ControlBoardSingleplayer(gameSingle, view, boardViewSingle, headerSingle);
		menuControlSingle = new ControlOptionsSingleplayer(gameSingle, view, menuViewSingle, boardViewSingle, headerSingle);
		
		gameMulti = new GameMultiplayer();
		menuViewMulti = new ViewOptionsMultiplayer(view, gameMulti);
		headerMulti = new ViewHeaderMultiplayer(view, gameMulti, true);
		boardViewMulti = new ViewBoardMultiplayer(headerMulti, gameMulti);
		boardControlMulti = new ControlBoardMultiplayer(gameMulti, view, boardViewMulti, headerMulti);
		menuControlMulti = new ControlOptionsMultiplayer(gameMulti, view, menuViewMulti, boardViewMulti, headerMulti);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "singleplayer") {
			gameSingle.reset();
			view.getAudio().registerGame(gameSingle);
			view.showMenu(menuViewSingle);
		} 
		else if (e.getActionCommand() == "multiplayer"){
			gameMulti.reset();
			view.getAudio().registerGame(gameMulti);
			view.showMenu(menuViewMulti);
		}
		else if (e.getActionCommand() == "controls"){
			view.showControlsMenu();
		} 
		else if (e.getActionCommand() == "quit"){
			view.closeWindow();
		}
	}

}

