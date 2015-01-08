package snake.view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import snake.model.Game;

public class MenuHeader extends JPanel{

	private BufferedImage title;
	private View view;
	public MenuHeader(View view){
		this.view = view;
	}
	
	protected @Override void paintComponent(Graphics context) {
		super.paintComponent(context);
		
		try {
			title = ImageIO.read(new File("MenuHeader.png"));
		} catch (IOException error) {
			throw new RuntimeException("Image not found: " + error.getMessage());
		}
		context.drawImage(title, view.getWidth(), 50, null);
	
	}
}
