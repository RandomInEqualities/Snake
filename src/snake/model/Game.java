package snake.model;

public interface Game {
	
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
