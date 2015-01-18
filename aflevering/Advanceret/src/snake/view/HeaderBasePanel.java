package snake.view;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import snake.control.HeaderListener;


/**
 * The basic header that is shown in the top of the window.
 */
public class HeaderBasePanel extends JPanel implements Observer {
	
	private static final int DEFAULT_LOGO_WIDTH = 300;
	private static final int DEFAULT_LOGO_HEIGHT = 80;
	private final int DEFAULT_BUTTON_WIDTH = ResourceImages.SOUND_ON.getWidth();
	private final int DEFAULT_BUTTON_HEIGHT = ResourceImages.SOUND_ON.getHeight();
	
	private ViewFrame view;
	private Image logo;
	private ImageIcon imageSoundOn;
	private ImageIcon imageSoundOff;
	private JButton buttonMuteSound;

	public HeaderBasePanel(ViewFrame view) {
		if (view == null) {
			throw new NullPointerException();
		}
		
		this.view = view;
		this.logo = ResourceImages.LOGO.getScaledInstance(DEFAULT_LOGO_WIDTH, DEFAULT_LOGO_HEIGHT, Image.SCALE_SMOOTH);
		this.imageSoundOn = new ImageIcon(ResourceImages.SOUND_ON);
		this.imageSoundOff = new ImageIcon(ResourceImages.SOUND_OFF);
		
		// Create button.
		this.buttonMuteSound = new JButton();
		buttonMuteSound.setPreferredSize(new Dimension(DEFAULT_BUTTON_WIDTH, DEFAULT_BUTTON_HEIGHT));
		buttonMuteSound.setCursor(new Cursor(Cursor.HAND_CURSOR));
		buttonMuteSound.setBorderPainted(false);
		buttonMuteSound.setContentAreaFilled(false);
		buttonMuteSound.setFocusable(false);
		updateMuteButtonIcon();
		add(buttonMuteSound);
		
		setBackground(ResourceColors.PANEL_COLOR);
		setFocusable(false);
		
		// Listen for changes in the Audio object (muting, etc.).
		view.getAudio().addObserver(this);
		
		// Create a control object that listens for button clicks.
		HeaderListener control = new HeaderListener(view);
		buttonMuteSound.setActionCommand("mute");
		buttonMuteSound.addActionListener(control);
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
		
		// Mute button.
		updateMuteButtonIcon();
		int xSound = size.width - DEFAULT_BUTTON_WIDTH - 10;
		int ySound = 8;
		buttonMuteSound.setLocation(xSound, ySound);
		
		// Key info
		context2D.drawImage(ResourceImages.INFO_KEYS, size.width-ResourceImages.INFO_KEYS.getWidth() - 10, 35, null);
	}

	@Override
	public void update(Observable o, Object arg) {
		repaint();
	}
	
	public void updateMuteButtonIcon() {
		ImageIcon icon;
		if (view.getAudio().isMuted()){
			icon = imageSoundOff;
		} 
		else {
			icon = imageSoundOn;
		}
		buttonMuteSound.setIcon(icon);
	}
	
}
