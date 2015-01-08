package snake.view;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Audio {

	public static void eatApple() {
		try{
		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("nom.wav").getAbsoluteFile());
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
		}
		catch(Exception ex) {
        System.out.println("Error with playing sound.");
        ex.printStackTrace();
    }
	}
	public static void endGame() {
		try{
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("ohno.wav").getAbsoluteFile());
	        Clip clip = AudioSystem.getClip();
	        clip.open(audioInputStream);
	        clip.start();
			}
			catch(Exception ex) {
	        System.out.println("Error with playing sound.");
	        ex.printStackTrace();
	    }
	}
}
