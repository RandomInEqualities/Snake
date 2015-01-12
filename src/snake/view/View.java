
package snake.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.*;

import snake.model.*;


public class View extends JFrame {
	
	public enum State {
		IN_GAME,
		IN_MENU,
		IN_MENU_SINGLEPLAYER,
		IN_MENU_MULTIPLAYER,
		IN_MENU_CONTROLS
	}
	
	private static final long serialVersionUID = 6240347347855679335L;
	
	public State state;
	private ViewHeader headerPanel;
	private ViewMenu menuPanel;
	private ViewMenuSingleplayer menuSingleplayerPanel;
	private ViewMenuMultiplayer menuMultiplayerPanel;
	private ViewMenuControls menuControlsPanel;
	private ViewBoard boardPanel;
	private Audio audio;
	
	public View(Game game) {
		super();
		
		if (game == null) {
			throw new NullPointerException();
		}
		
		this.headerPanel = new ViewHeader(this, game, false);
		this.menuPanel = new ViewMenu(this);
		this.menuSingleplayerPanel = new ViewMenuSingleplayer(this, game);
		//this.menuMultiplayerPanel = new ViewMenuMultiplayer(this, game);
		this.menuControlsPanel = new ViewMenuControls(this);
		this.boardPanel = new ViewBoard(game, this);
		this.audio = new Audio(game);
		
		showMenu();
		
		setTitle("Snake");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
	}
	
	public void showGame() {
		setFrameComponents(headerPanel, boardPanel);
		headerPanel.showScore();
		state = State.IN_GAME;
	}
	
	public void showMenu() {
		setFrameComponents(headerPanel, menuPanel);
		headerPanel.hideScore();
		state = State.IN_MENU;
	}
	
	public void showSingleplayerMenu() {
		setFrameComponents(headerPanel, menuSingleplayerPanel);
		headerPanel.hideScore();
		state = State.IN_MENU_SINGLEPLAYER;
	}
	
	public void showMultiplayerMenu(){
		//setFrameComponents(headerPanel, menuMultiplayerPanel);
		headerPanel.hideScore();
		state = State.IN_MENU_MULTIPLAYER;
	}
	public void showControlsMenu() {
		setFrameComponents(headerPanel, menuControlsPanel);
		headerPanel.hideScore();
		state = State.IN_MENU_CONTROLS;
	}
	
	public boolean inGame() {
		return state == State.IN_GAME;
	}
	
	public State getViewState() {
		return state;
	}
	
	public Audio getAudio() {
		return audio;
	}
	
	public ViewHeader getViewHeader() {
		return headerPanel;
	}
	
	public ViewMenu getViewMenu() {
		return menuPanel;
	}
	
	public ViewMenuSingleplayer getViewMenuSingleplayer() {
		return menuSingleplayerPanel;
	}
	public ViewMenuMultiplayer getViewMenuMultiplayer() {
		return menuMultiplayerPanel;
	}
	
	public ViewMenuControls getViewMenuControls() {
		return menuControlsPanel;
	}
	
	public ViewBoard getViewBoard() {
		return boardPanel;
	}
	
	public void closeWindow() {
		System.exit(0);
	}
	
	public ViewHeader getHeader(){
		return headerPanel;
	}
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(800, 800);
	}
	
	private void setFrameComponents(Component northPanel, Component centerPanel) {
		// Remove the current contents pane components and add the new ones.
		getContentPane().removeAll();
		getContentPane().add(northPanel, BorderLayout.NORTH);
		getContentPane().add(centerPanel, BorderLayout.CENTER);
		getContentPane().revalidate();
		getContentPane().repaint();
		
		// It is very important that this JFrame gets the focus. Otherwise the control
		// objects won't receive keyboard events!
		requestFocus();
		
	}
	
}








