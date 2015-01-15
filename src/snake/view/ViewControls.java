package snake.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import snake.control.ControlControls;


public class ViewControls extends JPanel {

	private static final long serialVersionUID = 6121636944519601998L;

	private JButton buttonBack;
	
	public ViewControls(View view) {
		if (view == null) {
			throw new NullPointerException();
		}
		
		buttonBack = new JButton(new ImageIcon(Images.BUTTON_BACK));
		ViewMenu.setMenuButtonParameters(buttonBack);
		this.add(buttonBack);
		
		// Create the control object.
		ControlControls control = new ControlControls(view);
		buttonBack.addActionListener(control);
	}
	
	@Override
	protected void paintComponent(Graphics context) {
		super.paintComponent(context);
		Graphics2D context2D = (Graphics2D) context;
		
		// Background.
		Rectangle menuRect = ViewMenu.computeMenuRectangle(getSize());
		ViewMenu.drawTileBackground(context2D, getVisibleRect());
		ViewMenu.drawMenuBackground(context2D, menuRect);
		
		// Title image.
		int xTitle = menuRect.x + menuRect.width/2 - Images.TITLE_CONTROLS.getWidth()/2;
		int yTitle = menuRect.y + ViewMenu.MENU_MARGIN;
		context.drawImage(Images.TITLE_CONTROLS, xTitle, yTitle, null);
		
		// Control image.
		int xImage = menuRect.x + menuRect.width/2 - Images.CONTROLS.getWidth()/2;
		int yImage = yTitle + Images.TITLE_CONTROLS.getHeight()/2 + 50;
		context.drawImage(Images.CONTROLS, xImage, yImage, null);
		
		// Back button.
		int xBack = menuRect.x + menuRect.width/2 - ViewMenu.BUTTON_WIDTH/2;
		int yBack = menuRect.y + menuRect.height - ViewMenu.BUTTON_HEIGHT - 10;
		buttonBack.setLocation(xBack, yBack);
	}
	
}
