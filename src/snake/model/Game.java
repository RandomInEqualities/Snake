package snake.model;


/**
 * The interface for a generic game.
 */
public interface Game {
	
	public boolean isStarted();
	public boolean isRunning();
	public boolean isPaused();
	public boolean isEnded();

	public void start();
	public void pause();
	public void resume();
	public void reset();
	
	/**
	 * An event class representing possible game events. Is general enough to handle
	 * both singleplayer and multiplayer events.
	 */
	public class Event {
		
		// The events that can happen.
		public static final int MOVE = 0;
		public static final int EAT = 1;
		public static final int LOSE = 2;
		public static final int WIN = 3;
		public static final int START = 4;
		public static final int PAUSE = 5;
		public static final int RESUME = 6;
		
		private final int type;
		private final Player player;
		private final boolean havePlayer;
		
		public Event(int type) {
			this.type = type;
			this.player = Player.ONE;
			this.havePlayer = false;
		}
		
		public Event(int type, Player player) {
			this.type = type;
			this.player = player;
			this.havePlayer = true;
		}
		
		public int getType() {
			return type;
		}
		
		public Player getPlayer() {
			if (!havePlayer) {
				throw new RuntimeException("event has no player");
			}
			return player;
		}		
	}
	
}
