
package snake.view;

import java.awt.*;
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
	
	private State state;
	private ViewHeader headerPanel;
	private ViewMenu menuPanel;
	private ViewControls menuControlsPanel;
	private ViewAudio audio;
	private ViewHeader currentHeader;
	
	public View() {
		super();
		
		this.state = State.STARTUP;
		this.audio = new ViewAudio();
		this.menuPanel = new ViewMenu(this);
		this.headerPanel = new ViewHeader(this, this.audio);
		this.menuControlsPanel = new ViewControls(this);
		this.currentHeader = headerPanel;
		showMenu();
		
		setTitle("Snake");
		BufferedImage appleIcon = Images.APPLE;
		Image scaledIcon = appleIcon.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
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
	
	public void showGame(ViewHeaderSingleplayer headerSingle, ViewBoardSingleplayer boardView) {
		if (state != State.IN_GAME) {
			setFrameComponents(headerSingle, boardView);
			state = State.IN_GAME;
		}
	}
	
	public void showGame(ViewHeaderMultiplayer headerMulti, ViewBoardMultiplayer boardView) {
		if (state != State.IN_GAME) {
			setFrameComponents(headerMulti, boardView);
			state = State.IN_GAME;
		}
	}
	
	public void showMenu(ViewOptionsSingleplayer menu) {
		if (state != State.IN_MENU_SINGLEPLAYER) {
			setFrameComponents(headerPanel, menu);
			state = State.IN_MENU_SINGLEPLAYER;
		}
	}
	
	public void showMenu(ViewOptionsMultiplayer menu){
		if (state != State.IN_MENU_MULTIPLAYER) {
			setFrameComponents(headerPanel, menu);
			state = State.IN_MENU_MULTIPLAYER;
		}
	}
	
	public void showControlsMenu() {
		if (state != State.IN_MENU_CONTROLS) {
			setFrameComponents(headerPanel, menuControlsPanel);
			state = State.IN_MENU_CONTROLS;
		}
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
		return currentHeader;
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(800, 800);
	}
	
	private void setFrameComponents(Component northPanel, Component centerPanel) {
		// Remove current content pane components and add new ones
		currentHeader = (ViewHeader) northPanel;
		getContentPane().removeAll();
		getContentPane().add(northPanel, BorderLayout.NORTH);
		getContentPane().add(centerPanel, BorderLayout.CENTER);
		getContentPane().revalidate();
		getContentPane().repaint();
		
		// Request focus for JFrame to make it possible to receive keyboard events
		requestFocus();
	}
}








