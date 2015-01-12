package snake.view;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ViewMenuControls extends JPanel {


	private static final long serialVersionUID = 6121636944519601998L;

	private View view;
	private JButton back;
	
	public ViewMenuControls(View view){
		this.view = view;
		back = new JButton(new ImageIcon(Images.BUTTON_BACK));
		view.getViewMenu().setButton(back);
	}
	
	public JButton getBackButton() {
		return back;
	}
	
	@Override
	protected void paintComponent(Graphics context) {
		super.paintComponent(context);
		Graphics2D context2D = (Graphics2D) context;
		
		//Background
		view.getViewMenu().drawBackground(context2D, getWidth(), getHeight());
		view.getViewMenu().drawBoard(context2D, getWidth());
		
		//Title
		int xTitle = getSize().width/2-Images.TITLE_CONTROLS.getWidth()/2;
		int yTitle = 20;
		int xImage = getSize().width/2-Images.CONTROLS.getWidth()/2;
		int yImage = yTitle+80;
		context.drawImage(Images.TITLE_CONTROLS, xTitle, yTitle, null);
		context.drawImage(Images.CONTROLS, xImage, yImage, null);
		
		//Button
		int buttonWidth = Images.BUTTON_BACK.getWidth();
		int buttonHeight = Images.BUTTON_BACK.getHeight();
		int xBack = getSize().width/2-buttonWidth/2;
		int yBack = view.getViewMenu().getRectangleForMenu(getSize().width).height - buttonHeight - 20;
		back.setBounds(xBack, yBack, buttonWidth, buttonHeight);
		this.add(back);
	}
	
}
