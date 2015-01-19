package snake.model;

import java.util.Observable;


/**
 * The interface for a generic game.
 */
public abstract class Game extends Observable {
	
	protected enum State {
		START,
		RUN,
		PAUSE,
		END
	}
	
	public Game() {
		super();
	}
	
	public abstract boolean isStarted();
	public abstract boolean isRunning();
	public abstract boolean isPaused();
	public abstract boolean isEnded();

	public abstract void start();
	public abstract void pause();
	public abstract void resume();
	public abstract void reset();
	
}
