package snake.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import snake.model.GameMultiplayer;
import snake.model.Player;

public class SnakeServer {
	
	public static final int TIME_OUT = 30000;
	public static final int PORT = 4444;

	private GameMultiplayer game;
	private Player player;
	private Player opponent;
	
	private ServerSocket server;
	private Socket client;
	private PrintWriter out;
	private BufferedReader in;
	
	public SnakeServer(GameMultiplayer game, Player player) throws IOException {
		this.game = game;
		this.player = player;
		this.opponent = (player == Player.ONE) ? Player.TWO : Player.ONE;
		
		try {
			server = new ServerSocket();
			server.setReuseAddress(true);
			server.bind(new InetSocketAddress(PORT));
			server.setSoTimeout(TIME_OUT);
			client = server.accept();
			out = new PrintWriter(client.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		}
		catch (IOException error) {
			if (client != null) {
				client.close();
			}
			if (server != null) {
				server.close();
			}
			throw error;
		}
	}
	
	public void close() throws IOException {
		if (client != null) {
			client.close();
		}
		if (server != null) {
			server.close();
		}
		server = null;
		client = null;
		out = null;
		in = null;
	}
	
	public void checkClientCommands() throws IOException {
		while (in.ready()) {
			String command = in.readLine();
			SnakeProtocol.execute(game, opponent, command);
			System.out.println("ServerReceive: " + command);
		}
	}
	
	public void executeCommand(String command) {
		
		// Send to the client.
		out.println(command);
		
		// Execute the command ourselves.
		SnakeProtocol.execute(game, player, command);
		
	}
	
}
