package snake.view;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import snake.model.GameSingleplayer;


public class ViewHeader extends JPanel {
	
	private static final long serialVersionUID = -3944388732646932230L;
	private static final int DEFAULT_LOGO_WIDTH = 300;
	private static final int DEFAULT_LOGO_HEIGHT = 80;
	
	private Audio audio;
	private Image logo;
	private JButton sound;

	public ViewHeader(Audio audio) {
		if (audio == null) {
			throw new NullPointerException();
		}
		this.audio = audio;
		this.logo = Images.LOGO.getScaledInstance(DEFAULT_LOGO_WIDTH, DEFAULT_LOGO_HEIGHT, Image.SCALE_SMOOTH);
		this.sound = new JButton();
		ViewMenu.setOptionButton(sound);
		sound.setContentAreaFilled(false);
		setBackground(Colors.PANEL_COLOUR);
		this.add(sound);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(400, 90);
	}
	
	@Override
	protected void paintComponent(Graphics context) {
		super.paintComponent(context);
		Graphics2D context2D = (Graphics2D)context;
		
		// Only show logo if board is wide enough to contain it.
		Dimension size = getSize();
		int logoWidth = logo.getWidth(null);
		if (size.width > logoWidth + 230) {
			context2D.drawImage(logo, size.width/2 - logo.getWidth(null)/2, 0, null);
		}
		
		// Sound icon
		Image soundIcon;		
		if (audio.isMuted()){
			soundIcon = Images.SOUND_OFF;
		} else {
			soundIcon = Images.SOUND_ON;
		}
		int xSound = size.width-Images.SOUND_OFF.getWidth()-10;
		int ySound = 8;
		int width = Images.SOUND_ON.getWidth();
		int height = Images.SOUND_ON.getHeight();
		sound.setIcon(new ImageIcon(soundIcon));
		sound.setBounds(xSound, ySound, width, height);
		
		// Key info
		context2D.drawImage(Images.INFO_KEYS, size.width-Images.INFO_KEYS.getWidth()-10, 35, null);
	}
	
	public JButton getSoundButton(){
		return sound;
	}
}
