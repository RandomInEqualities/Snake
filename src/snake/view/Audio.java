package snake.view;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.sound.sampled.*;

import snake.model.*;


public class Audio extends Observable implements Observer {
	
	private Clip soundEat;
	private Clip soundEnd;
	private Clip soundWin;
	private Clip soundStart;
	private Clip soundtrack;
	private boolean isMuted;
	private Game registeredGame = null;
	
	public Audio() {
		super();
		this.isMuted = false;
		this.registeredGame = null;
		loadSounds();
		soundtrack.start();
	}
	
	public boolean isMuted() {
		return isMuted;
	}
	
	public void toggleMute() {
		isMuted = !isMuted;
		if (isMuted) {
			soundtrack.stop();
		}
		else {
			soundtrack.start();
		}
		setChanged();
		notifyObservers();
	}
	
	public void registerGame(Game game) {
		if (game == null) {
			throw new NullPointerException();
		}
		if (registeredGame != null) {
			registeredGame.deleteObserver(this);
		}
		game.addObserver(this);
		registeredGame = game;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (isMuted) {
			return;
		}

		Event event = (Event)arg;
		if (event.getType() == Event.Type.EAT) {
			playEatSound();
		}
		else if (event.getType() == Event.Type.LOSE) {
			playEndSound();
		}
		else if (event.getType() == Event.Type.WIN) {
			playWinSound();
		}
		else if (event.getType() == Event.Type.START) {
			playStartSound();
		}

	}
	
	private void playEatSound() {
		soundEat.setFramePosition(0);
		soundEat.start();
	}

	private void playEndSound() {
		soundEnd.setFramePosition(0);
		soundEnd.start();
	}
	
	private void playWinSound() {
		soundWin.setFramePosition(0);
		soundWin.start();
	}
	
	private void playStartSound() {
		soundStart.setFramePosition(0);
		soundStart.start();
	}
	
	private void loadSounds() {
		try {
			soundEat = AudioSystem.getClip();
			soundEnd = AudioSystem.getClip();
			soundWin = AudioSystem.getClip();
			soundStart = AudioSystem.getClip();
			soundtrack = AudioSystem.getClip();
			soundEat.open(ResourceSounds.EAT_STREAM);
			soundEnd.open(ResourceSounds.END_STREAM);
			soundWin.open(ResourceSounds.WIN_STREAM);
			soundStart.open(ResourceSounds.START_STREAM);
			soundtrack.open(ResourceSounds.SOUNDTRACK_STREAM);
		} 
		catch (LineUnavailableException | IOException error) {
			throw new RuntimeException("unable to load clip: " + error.getMessage());
		}
	}
	
}
