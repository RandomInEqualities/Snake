
package snake.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

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
	
	private State state;
	private ViewHeader headerPanel;
	private ViewMenu menuPanel;
	private ViewHeaderSingleplayer headerPanelSingleplayer;
	private ViewMenuSingleplayer menuSingleplayer;
	private ViewMenuMultiplayer menuMultiplayer;
	private ViewBoardSinglePlayer boardSingleplayer;
	private ViewBoardMultiPlayer boardMultiplayer;
	private ViewMenuControls menuControlsPanel;
	private ViewBoardSinglePlayer boardPanel;
	private Audio audio;
	
	public View(GameSinglePlayer game) {
		super();
		
		this.menuPanel = new ViewMenu();
		this.headerPanel = new ViewHeader(this);
		this.headerPanelSingleplayer = new ViewHeaderSingleplayer(this, game, true);
		this.menuControlsPanel = new ViewMenuControls(this);
		this.boardPanel = new ViewBoardSinglePlayer(game);
		this.audio = new Audio(game);
		
		showMenu();
		
		setTitle("Snake");
		
		BufferedImage appleIcon = Images.APPLE;
		Image scaledIcon = appleIcon.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		setIconImage(scaledIcon);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setFocusable(true);
		requestFocus();
	}
	
	public void showGame() {
		if (state != State.IN_GAME) {
			setFrameComponents(headerPanelSingleplayer, boardPanel);
			state = State.IN_GAME;
		}
	}
	public void createGameView(GameSinglePlayer game){
		menuSingleplayer = new ViewMenuSingleplayer(this, game);
		boardSingleplayer = new ViewBoardSinglePlayer(game);
	}
	
	public void createGameView(GameMultiPlayer game){
		menuMultiplayer = new ViewMenuMultiplayer (this, game);
		boardMultiplayer = new ViewBoardMultiPlayer(game);
	}
	
	public ViewMenuSingleplayer getMenuSingleplayer(){
		return menuSingleplayer;
	}
	
	public ViewMenuMultiplayer getMenuMultiplayer(){
		return menuMultiplayer;
	}
	
	public ViewBoardSinglePlayer getBoardSingleplayer(){
		return boardSingleplayer;
	}
	
	public ViewBoardMultiPlayer getBoardMultiplayer(){
		return boardMultiplayer;
	}
	
	public void showMenu() {
		if (state != State.IN_MENU) {
			setFrameComponents(headerPanel, menuPanel);
			state = State.IN_MENU;
		}
	}
	
	public void showSingleplayerMenu() {
		if (state != State.IN_MENU_SINGLEPLAYER) {
			setFrameComponents(headerPanel, menuSingleplayer);
			state = State.IN_MENU_SINGLEPLAYER;
		}
	}
	
	public void showMultiplayerMenu(){
		if (state != State.IN_MENU_MULTIPLAYER) {
			setFrameComponents(headerPanel, menuMultiplayer);
			state = State.IN_MENU_MULTIPLAYER;
		}
	}
	
	public void showControlsMenu() {
		if (state != State.IN_MENU_CONTROLS) {
			setFrameComponents(headerPanel, menuControlsPanel);
			state = State.IN_MENU_CONTROLS;
		}
	}
	
	public boolean inGame() {
		return state == State.IN_GAME;
	}
	
	public boolean inControls() {
		return state == State.IN_MENU_CONTROLS;
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
	
	public ViewMenuControls getViewMenuControls() {
		return menuControlsPanel;
	}
	
	public ViewBoardSinglePlayer getViewBoard() {
		return boardPanel;
	}
	
	public void closeWindow() {
		// Send an event to the window system that we want to close the frame.
		dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
	
	public ViewHeader getHeader(){
		return headerPanel;
	}
	
	public ViewHeaderSingleplayer getHeaderSingleplayer(){
		return headerPanelSingleplayer;
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(800, 800);
	}
	
	private void setFrameComponents(Component northPanel, Component centerPanel) {
		// Remove the current content pane components and add the new ones.
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








