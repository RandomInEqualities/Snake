package snake.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import snake.model.*;
import snake.view.*;


/**
 * Control for the multiplayer board view (BoardMultiplayerPanel). This class interacts
 * with the game from keyboard input.
 */
public class BoardInternetListener extends KeyAdapter implements ActionListener , Observer {

	private GameMultiplayer game;
	private ViewFrame view;
	
	SnakeServer server;
	SnakeClient client;
	
	private Timer timer;
	private int timerUpdateInterval = 16;
	
	public BoardInternetListener(ViewFrame view) {
		if (view == null) {
			throw new NullPointerException();
		}
		this.game = null;
		this.server = null;
		this.client = null;
		this.view = view;
		this.timer = new Timer(timerUpdateInterval, this);
	}
	
	public void closeConnection() throws IOException {
		if (server != null) {
			server.close();
		}
		if (client != null) {
			client.close();
		}
		timer.stop();
	}
	
	public void registerGame(GameMultiplayer newGame, Player player, String host) throws IOException {
		if (newGame == null) {
			throw new NullPointerException();
		}
		if (server != null) {
			server.close();
		}
		if (client != null) {
			client.close();
		}
		
		if (player == Player.ONE) {
			server = new SnakeServer(newGame, Player.ONE);
		}
		else {
			client = new SnakeClient(newGame, Player.TWO, host);
		}
		
		if (game != null) {
			game.deleteObserver(this);
		}
		
		game = newGame;
		newGame.addObserver(this);
		timer.start();
	}
	
	@Override
	public void keyPressed(KeyEvent event) {
		if (game == null) {
			return;
		}
		switch (event.getKeyCode()) {
			case KeyEvent.VK_UP:
				executeCommand("MOVE UP");
				break;
			case KeyEvent.VK_DOWN:
				executeCommand("MOVE DOWN");
				break;
			case KeyEvent.VK_LEFT:
				executeCommand("MOVE LEFT");
				break;
			case KeyEvent.VK_RIGHT:
				executeCommand("MOVE RIGHT");
				break;
			case KeyEvent.VK_W:
				executeCommand("MOVE UP");
				break;
			case KeyEvent.VK_S:
				executeCommand("MOVE DOWN");
				break;
			case KeyEvent.VK_A:
				executeCommand("MOVE LEFT");
				break;
			case KeyEvent.VK_D:
				executeCommand("MOVE RIGHT");
				break;
			case KeyEvent.VK_P:
				if (game.isPaused()) {
					executeCommand("RESUME");
				}
				else {
					executeCommand("PAUSE");
				}
				break;
			case KeyEvent.VK_ENTER: 
			case KeyEvent.VK_SPACE:
				if (game.isEnded()){
					executeCommand("RESTART");
				}
				break;
			default:
				break;
		}
	}
	
	void executeCommand(String command) {
		if (client == null) {
			server.executeCommand(command);
		} 
		else {
			client.executeCommand(command);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		if (game == null) {
			return;
		}
		
		if (event.getActionCommand() == "restart") {
			executeCommand("RESTART");
		} 
		else if (event.getActionCommand() == "menu") {
			view.showMenu();
		}
		
		// Check if we have received any command from the other computer.
		try {
			if (client == null) {
				server.checkClientCommands();
			} 
			else {
				client.getServerCommands();
			}
		}
		catch (IOException error) {
			JOptionPane.showMessageDialog(view, "connection failed");
			view.showMenu();
		}
		
	}

	@Override
	public void update(Observable o, Object arg) {
		if (server == null) {
			return;
		}
		
		// If we one snake ate the food we sync the new food position between the 
		// two computers.
		Event event = (Event)arg;
		if (event.getType() == Event.Type.EAT || event.getType() == Event.Type.START) {
			Field foodPosition = game.getFood().getPosition();
			String row = Integer.toString(foodPosition.getRow());
			String column = Integer.toString(foodPosition.getColumn());
			
			if (row.length() == 1) {
				row = row + "  ";
			}
			else if (row.length() == 2) {
				row = row + " ";
			}
			
			if (column.length() == 1) {
				column = column + "  ";
			}
			else if (column.length() == 2) {
				column = column + " ";
			}
			
			executeCommand("FOOD " + row + " " + column);
		}
	}

}
