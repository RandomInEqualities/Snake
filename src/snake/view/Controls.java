package snake.view;

import java.awt.*;
import javax.swing.*;
import snake.control.*;

public class Controls extends JPanel{
	private View view;
	private CustomImages images;
	private JButton back;
	
	public Controls(View view){
		this.view = view;
		images = new CustomImages();
		back = new JButton (new ImageIcon(images.back_btn));
		view.getMenu().setButton(back);
		ControlSingleplayer control = new ControlSingleplayer(view);
		back.setActionCommand("back");
		back.addActionListener(control);
	}
	
	@Override
	protected void paintComponent(Graphics context) {
		super.paintComponent(context);
		Graphics2D context2D = (Graphics2D) context;
		
		//Background
		view.getMenu().drawBackground(context2D, getWidth(), getHeight());
		view.getMenu().drawBoard(context2D, getWidth());
		
		//Title
		int xTitle = getSize().width/2-images.controlsTitle.getWidth()/2;
		int yTitle = 20;
		int xImage = getSize().width/2-images.controlsImage.getWidth()/2;
		int yImage = yTitle+80;
		context.drawImage(images.controlsTitle, xTitle, yTitle, null);
		context.drawImage(images.controlsImage, xImage, yImage, null);
		
		//Button
		int buttonWidth = images.back_btn.getWidth();
		int buttonHeight = images.back_btn.getHeight();
		int xBack = getSize().width/2-buttonWidth/2;
		int yBack = view.getMenu().getRectangleForMenu(getSize().width).height - buttonHeight - 20;
		back.setBounds(xBack, yBack, buttonWidth, buttonHeight);
		this.add(back);
	}
}

