
package snake.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import snake.control.ViewFrameListener;
import snake.model.GameMultiplayer;
import snake.model.GameSingleplayer;


public class ViewFrame extends JFrame {
	
	public enum State {
		STARTUP,
		IN_MENU,
		IN_MENU_OPTIONS_SINGLEPLAYER,
		IN_MENU_OPTIONS_MULTIPLAYER,
		IN_MENU_CONTROLS,
		IN_GAME_SINGLEPLAYER,
		IN_GAME_MULTIPLAYER
	}
	
	private State state;
	
	private Audio audio;
	private MenuPanel menuPanel;
	private ControlsPanel controlsPanel;
	
	private HeaderBasePanel headerPanel;
	private HeaderSingleplayerPanel headerSingleplayerPanel;
	private HeaderMultiplayerPanel headerMultiplayerPanel;
	
	private BoardMultiplayerPanel boardMultiplayerPanel;
	private BoardSingleplayerPanel boardSingleplayerPanel;
	
	private OptionsSingleplayerPanel optionsSingleplayerPanel;
	private OptionsMultiplayerPanel optionsMultiplayerPanel;
	
	public ViewFrame() {
		super();
		
		this.state = State.STARTUP;
		
		// Load all panels at the start so switching between them is faster.
		// This also means that we need to pass in a game object to the
		// panels that needs it before switching to it.
		this.audio = new Audio();
		this.menuPanel = new MenuPanel(this);
		this.controlsPanel = new ControlsPanel(this);
		
		this.headerPanel = new HeaderBasePanel(this);
		this.headerSingleplayerPanel = new HeaderSingleplayerPanel(this);
		this.headerMultiplayerPanel = new HeaderMultiplayerPanel(this);
		
		this.boardMultiplayerPanel = new BoardMultiplayerPanel(this);
		this.boardSingleplayerPanel = new BoardSingleplayerPanel(this);
		
		this.optionsSingleplayerPanel = new OptionsSingleplayerPanel(this);
		this.optionsMultiplayerPanel = new OptionsMultiplayerPanel(this);
		
		showMenu();
		
		setTitle("Snake");
		setIconImage(ResourceImages.APPLE.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setFocusable(true);
		requestFocus();
		
		// Minimum size ensures that the window can't completely collapse. This prevents errors 
		// where width and height can't be zero.
		setMinimumSize(new Dimension(125, 125));
		
		// Create a control that listen for relevant key events. It listens to all sub panels, this
		// emulates a kind of global key listener.
		ViewFrameListener control = new ViewFrameListener(this);
		addKeyListener(control);
		menuPanel.addKeyListener(control);
		controlsPanel.addKeyListener(control);
		headerPanel.addKeyListener(control);
		headerSingleplayerPanel.addKeyListener(control);
		headerMultiplayerPanel.addKeyListener(control);
		boardMultiplayerPanel.addKeyListener(control);
		boardSingleplayerPanel.addKeyListener(control);
		optionsSingleplayerPanel.addKeyListener(control);
		optionsMultiplayerPanel.addKeyListener(control);
	}
	
	public boolean inMenu() {
		return state == State.IN_MENU;
	}
	
	public boolean inOptionsSingleplayer() {
		return state == State.IN_MENU_OPTIONS_SINGLEPLAYER;
	}
	
	public boolean inOptionsMultiplayer() {
		return state == State.IN_MENU_OPTIONS_MULTIPLAYER;
	}
	
	public boolean inControls() {
		return state == State.IN_MENU_CONTROLS;
	}
	
	public boolean inGame() {
		return state == State.IN_GAME_SINGLEPLAYER || state == State.IN_GAME_MULTIPLAYER;
	}
	
	public boolean inGameSingleplayer() {
		return state == State.IN_GAME_SINGLEPLAYER;
	}
	
	public boolean inGameMultiplayer() {
		return state == State.IN_GAME_MULTIPLAYER;
	}
	
	public Audio getAudio() {
		return audio;
	}
	
	public BoardSingleplayerPanel getBoardSingleplayerPanel() {
		return boardSingleplayerPanel;
	}
	
	public BoardMultiplayerPanel getBoardMultiplayerPanel() {
		return boardMultiplayerPanel;
	}
	
	public void showMenu() {
		if (state == State.IN_MENU) {
			return;
		}
		setFrameComponents(headerPanel, menuPanel);
		state = State.IN_MENU;
	}
	
	public void showOptionsMenu(GameSingleplayer game) {
		headerSingleplayerPanel.registerGame(game);
		optionsSingleplayerPanel.registerGame(game);
		audio.registerGame(game);
		setFrameComponents(headerPanel, optionsSingleplayerPanel);
		state = State.IN_MENU_OPTIONS_SINGLEPLAYER;
	}
	
	public void showOptionsMenu(GameMultiplayer game) {
		headerMultiplayerPanel.registerGame(game);
		optionsMultiplayerPanel.registerGame(game);
		audio.registerGame(game);
		setFrameComponents(headerPanel, optionsMultiplayerPanel);
		state = State.IN_MENU_OPTIONS_MULTIPLAYER;
	}
	
	public void showControlsMenu() {
		if (state == State.IN_MENU_CONTROLS) {
			return;
		}
		setFrameComponents(headerPanel, controlsPanel);
		state = State.IN_MENU_CONTROLS;
	}
	
	public void showGame(GameSingleplayer game) {
		headerSingleplayerPanel.registerGame(game);
		boardSingleplayerPanel.registerGame(game);
		setFrameComponents(headerSingleplayerPanel, boardSingleplayerPanel);
		state = State.IN_GAME_SINGLEPLAYER;
	}
	
	public void showGame(GameMultiplayer game) {
		headerMultiplayerPanel.registerGame(game);
		boardMultiplayerPanel.registerGame(game);
		setFrameComponents(headerMultiplayerPanel, boardMultiplayerPanel);
		state = State.IN_GAME_MULTIPLAYER;
	}
	
	public void closeWindow() {
		// Send an event to the window system that we want to close the frame.
		dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
	
	private void setFrameComponents(Component headerPanel, Component centerPanel) {
		
		// Remove current content pane components and add the new ones.
		getContentPane().removeAll();
		getContentPane().add(headerPanel, BorderLayout.NORTH);
		getContentPane().add(centerPanel, BorderLayout.CENTER);
		getContentPane().revalidate();
		getContentPane().repaint();
		
		// Let the center panel be the target of keyboard events.
		centerPanel.requestFocus();
		
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(800, 800);
	}
	
}








