package snake.view;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import snake.model.Game;


public class Audio implements Observer {
	
	private File eatSoundFile = new File("nom.wav");
	private File endSoundFile = new File("ohno.wav");
	private AudioInputStream eatSoundStream;
	private AudioInputStream endSoundStream;
	private Clip eatSound;
	private Clip endSound;
	
	public Audio(Game game) {
		if (game == null) {
			throw new NullPointerException();
		}
		game.addObserver(this);
		
		// Load the sounds.
		try {
			eatSoundStream = AudioSystem.getAudioInputStream(eatSoundFile);
			endSoundStream = AudioSystem.getAudioInputStream(endSoundFile);
			eatSound = AudioSystem.getClip();
			endSound = AudioSystem.getClip();
			eatSound.open(eatSoundStream);
			endSound.open(endSoundStream);
		} catch (Exception error) {
			throw new RuntimeException(error.getMessage());
		}
	}

	public void playEatSound() {
		eatSound.setFramePosition(0);
		eatSound.start();
	}
	
	public void playEndSound() {
		endSound.setFramePosition(0);
		endSound.start();
	}

	public @Override void update(Observable o, Object arg) {
		if (!(o instanceof Game)) {
			throw new IllegalArgumentException();
		}
		Game.Event event = (Game.Event)arg;
		if (event == Game.Event.EAT) {
			playEatSound();
		} 
		if (event == Game.Event.DIE) {
			playEndSound();
		}
	}
}
