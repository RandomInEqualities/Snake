package snake.view;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;


public class ResourceSounds {

	public static final AudioInputStream EAT_STREAM;
	public static final AudioInputStream END_STREAM;
	public static final AudioInputStream WIN_STREAM;
	public static final AudioInputStream START_STREAM;
	public static final AudioInputStream SOUNDTRACK_STREAM;
	
	private static final String SOUND_PATH = "/sounds/";
	
	static {
		EAT_STREAM = loadSound("nom.wav");
		END_STREAM = loadSound("ohno.wav");
		WIN_STREAM = loadSound("yes.wav");
		START_STREAM = loadSound("hereicome.wav");
		SOUNDTRACK_STREAM = loadSound("soundtrack.wav");
	}
	
	private static AudioInputStream loadSound(String filename) {
		try {
			// We load external resources by an URL. With this we use JAR files.
			URL location = ResourceSounds.class.getResource(SOUND_PATH + filename);
			return AudioSystem.getAudioInputStream(location);
		} 
		catch (UnsupportedAudioFileException | IOException error) {
			throw new RuntimeException("unable to load sound " + filename + ": " + error.getMessage());
		}
	}
	
}
