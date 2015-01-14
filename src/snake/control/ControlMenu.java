package snake.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import snake.model.GameMultiplayer;
import snake.model.GameSingleplayer;
import snake.view.*;

public class ControlMenu implements ActionListener {

	private View view;
	
	GameSingleplayer gameSingle;
	ViewBoardSingleplayer boardViewSingle;
	ViewMenuSingleplayer menuViewSingle;
	ViewHeaderSingleplayer headerSingle;
	ControlBoardSingleplayer boardControlSingle;
	ControlMenuSingleplayer menuControlSingle;
	
	GameMultiplayer gameMulti;
	ViewBoardMultiplayer boardViewMulti;
	ViewMenuMultiplayer menuViewMulti;
	ViewHeaderMultiplayer headerMulti;
	ControlBoardMultiplayer boardControlMulti;
	ControlMenuMultiplayer menuControlMulti;
	
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
		boardViewSingle = new ViewBoardSingleplayer(gameSingle);
		menuViewSingle = new ViewMenuSingleplayer(view, gameSingle);
		headerSingle = new ViewHeaderSingleplayer(view, gameSingle, true);
		boardControlSingle = new ControlBoardSingleplayer(gameSingle, view, boardViewSingle);
		menuControlSingle = new ControlMenuSingleplayer(gameSingle, view, menuViewSingle, boardViewSingle);
		
		gameMulti = new GameMultiplayer();
		boardViewMulti = new ViewBoardMultiplayer(gameMulti);
		menuViewMulti = new ViewMenuMultiplayer(view, gameMulti);
		headerMulti = new ViewHeaderMultiplayer(view, gameMulti, true);
		boardControlMulti = new ControlBoardMultiplayer(gameMulti, view, boardViewMulti);
		menuControlMulti = new ControlMenuMultiplayer(gameMulti, view, menuViewMulti, boardViewMulti);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "singleplayer") {
			//GameSingleplayer game = new GameSingleplayer();
			//ViewBoardSingleplayer boardView = new ViewBoardSingleplayer(game);
			//ViewMenuSingleplayer menuView = new ViewMenuSingleplayer(view, game);
			//ViewHeaderSingleplayer header = new ViewHeaderSingleplayer(view, game, true);
			//ControlBoardSingleplayer boardControl = new ControlBoardSingleplayer(game, view, boardView);
			//ControlMenuSingleplayer menuControl = new ControlMenuSingleplayer(game, view, menuView, boardView);
			view.getAudio().registerGame(gameSingle);
			view.showMenu(headerSingle, menuViewSingle);
		} 
		else if (e.getActionCommand() == "multiplayer"){
			view.getAudio().registerGame(gameMulti);
			view.showMenu(headerMulti, menuViewMulti);
		}
		else if (e.getActionCommand() == "controls"){
			view.showControlsMenu();
		} 
		else if (e.getActionCommand() == "quit"){
			view.closeWindow();
		}
	}

}

