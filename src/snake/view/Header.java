
package snake.view;
import java.awt.*;
import java.util.*;
import javax.swing.JPanel;

import snake.model.*;

public class Header extends JPanel implements Observer {
	private CustomImages images;
	public Header() {
		super();
		images = new CustomImages();
		setBackground(CustomColor.PANEL_COLOUR);
	}
	
	public @Override void update(Observable o, Object arg) {
		repaint();
	}
	
	public @Override Dimension getPreferredSize() {
		return new Dimension(400, 90);
	}
	
	protected @Override void paintComponent(Graphics context) {
		super.paintComponent(context);
		int width = 300;
		int height = 80;
		context.drawImage(images.logo.getScaledInstance(width, height, Image.SCALE_SMOOTH), getSize().width/2-width/2, 0, null);
	}
}
