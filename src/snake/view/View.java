
package snake.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.*;


public class View extends JFrame {
	
	public enum State {
		STARTUP,
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
	//private ViewHeaderSingleplayer headerPanelSingleplayer;
	//private ViewMenuSinglePlayer menuSingleplayer;
	//private ViewMenuMultiplayer menuMultiplayer;
	//private ViewBoardSinglePlayer boardSingleplayer;
	//private ViewBoardMultiPlayer boardMultiplayer;
	private ViewControls menuControlsPanel;
	//private ViewBoardSingleplayer boardPanel;
	private ViewAudio audio;
	
	public View() {
		super();
		
		this.state = State.STARTUP;
		this.audio = new ViewAudio();
		this.menuPanel = new ViewMenu();
		this.headerPanel = new ViewHeader(this.audio);
		this.menuControlsPanel = new ViewControls(this);
		
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
	
	public void showMenu() {
		if (state != State.IN_MENU) {
			setFrameComponents(headerPanel, menuPanel);
			state = State.IN_MENU;
		}
	}
	
	public void showGame(ViewBoardSingleplayer boardView) {
		if (state != State.IN_GAME) {
			setFrameComponents(headerPanel, boardView);
			state = State.IN_GAME;
		}
	}
	
	public void showGame(ViewBoardMultiplayer boardView) {
		if (state != State.IN_GAME) {
			setFrameComponents(headerPanel, boardView);
			state = State.IN_GAME;
		}
	}
	
	public void showMenu(ViewHeaderSingleplayer header, ViewOptionsSingleplayer menu) {
		if (state != State.IN_MENU_SINGLEPLAYER) {
			setFrameComponents(header, menu);
			state = State.IN_MENU_SINGLEPLAYER;
		}
	}
	
	public void showMenu(ViewHeaderMultiplayer header, ViewOptionsMultiplayer menu){
		if (state != State.IN_MENU_MULTIPLAYER) {
			setFrameComponents(header, menu);
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
	
	public ViewAudio getAudio() {
		return audio;
	}
	
	public ViewHeader getViewHeader() {
		return headerPanel;
	}
	
	public ViewMenu getViewMenu() {
		return menuPanel;
	}
	
	public ViewControls getViewMenuControls() {
		return menuControlsPanel;
	}
	
	public void closeWindow() {
		// Send an event to the window system that we want to close the frame.
		dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
	
	public ViewHeader getHeader(){
		return headerPanel;
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








