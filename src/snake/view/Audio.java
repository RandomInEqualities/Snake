
package snake.view;

import java.io.File;
import java.util.Observable;
import java.util.Observer;
import javax.sound.sampled.*;

import snake.model.GameSinglePlayer;


public class Audio implements Observer {

	private boolean muted;
	private Clip eatSound;
	private Clip endSound;
	private Clip winSound;
	
	private AudioInputStream eatSoundStream;
	private AudioInputStream endSoundStream;
	private AudioInputStream winSoundStream;
	private static final String EAT_SOUND_FILENAME = "resources/sounds/nom.wav";
	private static final String END_SOUND_FILENAME = "resources/sounds/ohno.wav";
	private static final String WIN_SOUND_FILENAME = "resources/sounds/yes.wav";
	
	public Audio(GameSinglePlayer game) {
		if (game == null) {
			throw new NullPointerException();
		}
		this.muted = false;
		loadSounds();
		game.addObserver(this);
	}
	
	public boolean isMuted() {
		return muted;
	}
	
	public void setMuted(boolean muted) {
		this.muted = muted;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof GameSinglePlayer && !muted) {
			GameSinglePlayer.Event event = (GameSinglePlayer.Event)arg;
			if (event == GameSinglePlayer.Event.EAT) {
				playEatSound();
			}
			else if (event == GameSinglePlayer.Event.DIE) {
				playEndSound();
			}
			else if (event == GameSinglePlayer.Event.WIN) {
				playWinSound();
			}
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
	
	private void loadSounds() {
		try {
			eatSoundStream = AudioSystem.getAudioInputStream(new File(EAT_SOUND_FILENAME));
			endSoundStream = AudioSystem.getAudioInputStream(new File(END_SOUND_FILENAME));
			winSoundStream = AudioSystem.getAudioInputStream(new File(WIN_SOUND_FILENAME));
			eatSound = AudioSystem.getClip();
			endSound = AudioSystem.getClip();
			winSound = AudioSystem.getClip();
			eatSound.open(eatSoundStream);
			endSound.open(endSoundStream);
			winSound.open(winSoundStream);
		} catch (Exception error) {
			throw new RuntimeException("unable to load sounds: " + error.getMessage());
		}
		
		// Lower the sound volumes, it is really loud with headphones on.
		FloatControl eatSoundGain = (FloatControl) eatSound.getControl(FloatControl.Type.MASTER_GAIN);
		eatSoundGain.setValue(-30.0f);
		FloatControl endSoundGain = (FloatControl) endSound.getControl(FloatControl.Type.MASTER_GAIN);
		endSoundGain.setValue(-20.0f);
	}
}
