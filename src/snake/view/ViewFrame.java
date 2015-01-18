
package snake.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.InetAddress;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import snake.control.ViewFrameListener;
import snake.model.GameMultiplayer;
import snake.model.GameSingleplayer;
import snake.model.Player;


public class ViewFrame extends JFrame {
	
	public enum State {
		STARTUP,
		IN_MENU,
		IN_MENU_OPTIONS_SINGLEPLAYER,
		IN_MENU_OPTIONS_MULTIPLAYER,
		IN_MENU_CONTROLS,
		IN_GAME_SINGLEPLAYER,
		IN_GAME_MULTIPLAYER,
		IN_GAME_INTERNET
	}
	
	private State state;
	
	private Audio audio;
	private MenuPanel menuPanel;
	private ControlsPanel controlsPanel;
	
	private HeaderBasePanel headerPanel;
	private HeaderSingleplayerPanel headerSingleplayerPanel;
	private HeaderMultiplayerPanel headerMultiplayerPanel;
	
	private BoardSingleplayerPanel boardSingleplayerPanel;
	private BoardMultiplayerPanel boardMultiplayerPanel;
	private BoardInternetPanel boardInternetPanel;
	
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
		
		this.boardSingleplayerPanel = new BoardSingleplayerPanel(this);
		this.boardMultiplayerPanel = new BoardMultiplayerPanel(this);
		this.boardInternetPanel = new BoardInternetPanel(this);
		
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
		
		// Minimum size ensures that the window can't completely collapse. This prevents weird
		// overlaps when the window gets too small.
		setMinimumSize(new Dimension(230, 300));
		
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
		boardInternetPanel.addKeyListener(control);
		boardSingleplayerPanel.addKeyListener(control);
		optionsSingleplayerPanel.addKeyListener(control);
		optionsMultiplayerPanel.addKeyListener(control);
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
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(800, 800);
	}
	
	public void showMenu() {
		if (state == State.IN_GAME_INTERNET) {
			boardInternetPanel.closeConnection();
		}
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
		setFrameComponents(headerSingleplayerPanel, optionsSingleplayerPanel);
		state = State.IN_MENU_OPTIONS_SINGLEPLAYER;
	}
	
	public void showOptionsMenu(GameMultiplayer game) {
		headerMultiplayerPanel.registerGame(game);
		optionsMultiplayerPanel.registerGame(game);
		audio.registerGame(game);
		setFrameComponents(headerMultiplayerPanel, optionsMultiplayerPanel);
		state = State.IN_MENU_OPTIONS_MULTIPLAYER;
	}
	
	public void showInternetGame(GameMultiplayer game) {
		
		Player player = showPlayerSelectionDialog();
		if (player == Player.NONE) {
			return;
		}
		
		String thisComputerIP;
		try {
			thisComputerIP = InetAddress.getLocalHost().getHostAddress();
		} 
		catch (IOException error) {
			System.out.println("unable to find computer ip.");
			return;
		}
		
		String hostname = null;
		if (player == Player.ONE) {
			String message = 
					"The other computer needs to connect to your computer.\n" + 
					"Your IP address is " + thisComputerIP + ".\n" + 
					"The other computer should use this address to connect!\n" +
					"Please press OK at the same time on both computers.";
		 	JOptionPane.showMessageDialog(this, message);
		}
		else {
			String message =
					"Please input the other computers IP!\n" + 
					"They should get a screen telling them what IP they have.\n" +
					"Please press OK at the same time on both computers.";
			do {
				hostname = JOptionPane.showInputDialog(this, message);
				if (hostname == null) {
					return;
				}
			} while (hostname.isEmpty());
		}
		
		// Find the IP of the other computer and start the server.
		try {
			headerMultiplayerPanel.registerGame(game);
			boardInternetPanel.registerGame(game, player, hostname);
			audio.registerGame(game);
		} 
		catch (IOException error) {
			JOptionPane.showMessageDialog(this, "Unable to establish connection\n" + error.getMessage()); 
			return;
		}

		setFrameComponents(headerMultiplayerPanel, boardInternetPanel);
		state = State.IN_GAME_INTERNET;
		game.disableTimedMovement();
		game.start();
	}
	
	public Player showPlayerSelectionDialog() {
		// Find what player user want to be.
		Object[] options = {
			"Player One",
			"Player Two",
			"Cancel"
		};
		
		int selection = JOptionPane.showOptionDialog(
			this,
			"Do you want to be player one or player two?\n" + 
			"It is very important that you choose differently that the one you play against.",
			"Choose Player",
			JOptionPane.YES_NO_CANCEL_OPTION,
			JOptionPane.QUESTION_MESSAGE,
			null,
			options,
			options[2]
		);
		
		if (selection == 0) {
			return Player.ONE;
		}
		else if (selection == 1) {
			return Player.TWO;
		}
		else {
			return Player.NONE;
		}
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
		audio.registerGame(game);
		setFrameComponents(headerSingleplayerPanel, boardSingleplayerPanel);
		state = State.IN_GAME_SINGLEPLAYER;
	}
	
	public void showGame(GameMultiplayer game) {
		headerMultiplayerPanel.registerGame(game);
		boardMultiplayerPanel.registerGame(game);
		audio.registerGame(game);
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
	
}








