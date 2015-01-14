
package snake.view;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.sound.sampled.*;

import snake.model.Game;
import snake.model.GameMultiplayer;
import snake.model.GameSingleplayer;


public class ViewAudio implements Observer {

	private boolean muted;
	
	private Clip eatSound;
	private Clip endSound;
	private Clip winSound;
	private Clip startSound;
	
	private AudioInputStream eatSoundStream;
	private AudioInputStream endSoundStream;
	private AudioInputStream winSoundStream;
	private AudioInputStream startSoundStream;

	private static final String EAT_SOUND_FILENAME = "resources/sounds/nom.wav";
	private static final String END_SOUND_FILENAME = "resources/sounds/ohno.wav";
	private static final String WIN_SOUND_FILENAME = "resources/sounds/yes.wav";
	private static final String START_SOUND_FILENAME = "resources/sounds/hereicome.wav";
	
	public ViewAudio() {
		this.muted = false;
		loadSounds();
	}
	
	public boolean isMuted() {
		return muted;
	}
	
	public void setMuted(boolean muted) {
		this.muted = muted;
	}
	
	public void registerGame(GameSingleplayer game) {
		if (game == null) {
			throw new NullPointerException();
		}
		game.addObserver(this);
	}
	
	public void registerGame(GameMultiplayer game) {
		if (game == null) {
			throw new NullPointerException();
		}
		game.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (muted) {
			return;
		}

		Game.Event event = (Game.Event)arg;
		if (event.getType() == Game.Event.EAT) {
			playEatSound();
		}
		else if (event.getType() == Game.Event.LOSE) {
			playEndSound();
		}
		else if (event.getType() == Game.Event.WIN) {
			playWinSound();
		}
		else if (event.getType() == Game.Event.START) {
			playStartSound();
		}

	}
	
	private void playEatSound() {
		eatSound.setFramePosition(0);
		eatSound.start();
	}

	private void playEndSound() {
		endSound.setFramePosition(0);
		endSound.start();
	}
	
	private void playWinSound() {
		winSound.setFramePosition(0);
		winSound.start();
	}
	
	private void playStartSound() {
		startSound.setFramePosition(0);
		startSound.start();
	}
	
	private void loadSounds() {
		try {
			eatSoundStream = AudioSystem.getAudioInputStream(new File(EAT_SOUND_FILENAME));
			endSoundStream = AudioSystem.getAudioInputStream(new File(END_SOUND_FILENAME));
			winSoundStream = AudioSystem.getAudioInputStream(new File(WIN_SOUND_FILENAME));
			startSoundStream = AudioSystem.getAudioInputStream(new File(START_SOUND_FILENAME));
			eatSound = AudioSystem.getClip();
			endSound = AudioSystem.getClip();
			winSound = AudioSystem.getClip();
			startSound = AudioSystem.getClip();
			eatSound.open(eatSoundStream);
			endSound.open(endSoundStream);
			winSound.open(winSoundStream);
			startSound.open(startSoundStream);
		} 
		catch (Exception error) {
			throw new RuntimeException("unable to load sounds: " + error.getMessage());
		}
	}
	
}
