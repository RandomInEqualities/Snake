package snake.model;

public interface Game {
	
	public class Event {
		
		// Type of events.
		public static final int MOVE = 0;
		public static final int EAT = 1;
		public static final int LOSE = 2;
		public static final int WIN = 3;
		public static final int START = 4;
		public static final int PAUSE = 5;
		public static final int RESUME = 6;
		
		public int type;
		public Player player;
		
		public Event(int type, Player player) {
			this.type = type;
			this.player = player;
		}
		
	}
	
	public enum State {
		START,
		RUN,
		PAUSE,
		END
	}
	
	public boolean isStarted();
	public boolean isRunning();
	public boolean isPaused();
	public boolean isEnded();

	public void start();
	public void pause();
	public void resume();
	public void reset();
	
}
