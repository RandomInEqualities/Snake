package snake.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import snake.model.GameSingleplayer;
import snake.view.*;

public class ControlMenu implements ActionListener {

	private View view;
	
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
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "singleplayer") {
			GameSingleplayer game = new GameSingleplayer();
			ViewBoardSingleplayer boardView = new ViewBoardSingleplayer(game);
			ViewMenuSingleplayer menuView = new ViewMenuSingleplayer(view, game);
			ControlBoardSingleplayer boardControl = new ControlBoardSingleplayer(game, view, boardView);
			ControlMenuSingleplayer menuControl = new ControlMenuSingleplayer(game, view, menuView, boardView);
			view.getAudio().registerGame(game);
			view.showSingleplayerMenu(menuView);
			
			
			view.showGame(boardView);
		} 
		else if (e.getActionCommand() == "multiplayer"){
			//view.showMultiplayerMenu();
		}
		else if (e.getActionCommand() == "controls"){
			view.showControlsMenu();
		} 
		else if (e.getActionCommand() == "quit"){
			view.closeWindow();
		}
	}

}

