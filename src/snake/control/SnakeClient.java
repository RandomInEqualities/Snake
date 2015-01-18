package snake.control;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

import snake.model.GameMultiplayer;
import snake.model.Player;


public class SnakeClient {

	private GameMultiplayer game;
	private Player player;
	private Player opponent;
	
	private Socket client;
	private PrintWriter out;
	private BufferedReader in;
	
	public SnakeClient(GameMultiplayer game, Player player, String host) throws IOException {
		this.game = game;
		this.player = player;
		this.opponent = (player == Player.ONE) ? Player.TWO : Player.ONE;
		this.client = new Socket();
		establishConnection(host, SnakeServer.PORT);
		this.out = new PrintWriter(client.getOutputStream(), true);
		this.in = new BufferedReader(new InputStreamReader(client.getInputStream()));
	}
	
	private void establishConnection(String host, int port) throws IOException {
		boolean scanning=true;
		int waitTime = 0;
		int waitTimeMax = SnakeServer.TIME_OUT;
		while(scanning)
		{
		    try
		    {
		    	client.connect(new InetSocketAddress(host, SnakeServer.PORT));
		        scanning=false;
		    }
		    catch(IOException error)
		    {
	            try {
					Thread.sleep(2000);
				} 
	            catch (InterruptedException e) {
					e.printStackTrace();
				}
	            waitTime += 2000;
	            if (waitTime > waitTimeMax) {
	            	throw new IOException("connect time out");
	            }
		    } 
		}
	}
	
	public void close() throws IOException {
		if (client != null) {
			client.close();
		}
		client = null;
		out = null;
		in = null;
	}
	
	public void getServerCommands() throws IOException {
		while (in.ready()) {
			String command = in.readLine();
			SnakeProtocol.execute(game, opponent, command);
			System.out.println("ClientRecieve: " + command);
		}
	}
	
	public void executeCommand(String command) {
		
		// Send to the server.
		out.println(command);
		
		// Execute the command ourselves.
		SnakeProtocol.execute(game, player, command);
		
	}
	
}
