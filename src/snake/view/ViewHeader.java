package snake.view;

import java.awt.*;

import javax.swing.*;

import snake.control.Control;

public class ViewHeader extends JPanel {
	
	private static final int DEFAULT_LOGO_WIDTH = 300;
	private static final int DEFAULT_LOGO_HEIGHT = 80;
	
	private ViewAudio audio;
	private Image logo;
	private JButton buttonSound;
	private ImageIcon imageSoundOn, imageSoundOff;

	public ViewHeader(View view, ViewAudio audio) {
		if (audio == null) {
			throw new NullPointerException();
		}
		this.audio = audio;
		this.logo = Images.LOGO.getScaledInstance(DEFAULT_LOGO_WIDTH, DEFAULT_LOGO_HEIGHT, Image.SCALE_SMOOTH);
		this.buttonSound = new JButton();
		int width = Images.SOUND_ON.getWidth();
		int height = Images.SOUND_ON.getHeight();
		buttonSound.setPreferredSize(new Dimension(width, height));
		buttonSound.setCursor(new Cursor(Cursor.HAND_CURSOR));
		buttonSound.setBorderPainted(false);
		buttonSound.setContentAreaFilled(false);
		setBackground(Colors.PANEL_COLOUR);
		this.add(buttonSound);
		
		imageSoundOn = new ImageIcon(Images.SOUND_ON);
		imageSoundOff = new ImageIcon(Images.SOUND_OFF);
		
		Control control = new Control(view);
		buttonSound.setActionCommand("mute");
		buttonSound.addActionListener(control);
		view.addKeyListener(control);
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
			context2D.drawImage(logo, size.width/2 - logoWidth/2, 0, null);
		}
		
		// Sound icon
		ImageIcon soundIcon;
		if (audio.isMuted()){
			soundIcon = imageSoundOff;
		} else {
			soundIcon = imageSoundOn;
		}
		int xSound = size.width-Images.SOUND_OFF.getWidth()-10;
		int ySound = 8;
		int width = Images.SOUND_ON.getWidth();
		int height = Images.SOUND_ON.getHeight();
		buttonSound.setIcon(soundIcon);
		buttonSound.setBounds(xSound, ySound, width, height);
		
		// Key info
		context2D.drawImage(Images.INFO_KEYS, size.width-Images.INFO_KEYS.getWidth()-10, 35, null);
	}
}
