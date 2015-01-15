package snake.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import snake.model.GameMultiplayer;
import snake.model.GameSingleplayer;
import snake.view.*;

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
		
		JButton singleplayer = view.getViewMenu().getSinglePlayerButton();
		JButton multiplayer = view.getViewMenu().getMultiPlayerButton();
		JButton controls = view.getViewMenu().getControlsButton();
		JButton quit = view.getViewMenu().getQuitButton();
		
		singleplayer.addActionListener(this);
		singleplayer.setActionCommand("singleplayer");
		multiplayer.addActionListener(this);
		multiplayer.setActionCommand("multiplayer");
		controls.addActionListener(this);
		controls.setActionCommand("controls");
		quit.addActionListener(this);
		quit.setActionCommand("quit");
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
			view.getAudio().registerGame(gameSingle);
			view.showMenu(menuViewSingle);
		} 
		else if (e.getActionCommand() == "multiplayer"){
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

