package snake.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JPanel;

import snake.control.ControlsListener;


/**
 * Panel for displaying an image that has the controls of the game.
 */
public class ControlsPanel extends JPanel {

	private static final long serialVersionUID = 6121636944519601998L;

	private JButton buttonBack;
	
	public ControlsPanel(ViewFrame view) {
		if (view == null) {
			throw new NullPointerException();
		}
		
		buttonBack = MenuPanel.createMenuButton(ResourceImages.BUTTON_BACK);
		add(buttonBack);
		
		// Create the control object.
		ControlsListener control = new ControlsListener(view);
		buttonBack.addActionListener(control);
	}
	
	@Override
	protected void paintComponent(Graphics context) {
		super.paintComponent(context);
		Graphics2D context2D = (Graphics2D) context;
		
		// Background.
		Rectangle menuRect = MenuPanel.computeMenuRectangle(getSize());
		MenuPanel.drawTileBackground(context2D, getVisibleRect());
		MenuPanel.drawMenuBackground(context2D, menuRect);
		
		// Title image.
		int xTitle = menuRect.x + menuRect.width/2 - ResourceImages.TITLE_CONTROLS.getWidth()/2;
		int yTitle = menuRect.y + MenuPanel.MENU_MARGIN;
		context.drawImage(ResourceImages.TITLE_CONTROLS, xTitle, yTitle, null);
		
		// Control image.
		int xImage = menuRect.x + menuRect.width/2 - ResourceImages.CONTROLS.getWidth()/2;
		int yImage = yTitle + ResourceImages.TITLE_CONTROLS.getHeight()/2 + 50;
		context.drawImage(ResourceImages.CONTROLS, xImage, yImage, null);
		
		// Back button.
		int xBack = menuRect.x + menuRect.width/2 - MenuPanel.BUTTON_WIDTH/2;
		int yBack = menuRect.y + menuRect.height - MenuPanel.BUTTON_HEIGHT - 10;
		buttonBack.setLocation(xBack, yBack);
	}
	
}
