package snake.view;

import java.awt.*;

import javax.swing.*;
import javax.swing.text.*;

import snake.control.ControlSingleplayer;


public class Singleplayer extends JPanel{
	private View view;
	private CustomImages images = new CustomImages();;
	private Rectangle play;
	private JFormattedTextField inputWidth, inputHeight;
	private boolean valid;
	
	public Singleplayer(View view){
		this.view = view;
		this.valid = true;
		//this.setLayout(new GridLayout(8,1));
		
		chooseSize();
		
		//Button
		JButton play = playButton();
		this.add(play);
		ControlSingleplayer controlSingleplayer = new ControlSingleplayer(view);
		play.addActionListener(controlSingleplayer);
		

	}

	protected @Override void paintComponent(Graphics context) {
		super.paintComponent(context);
		Graphics2D context2D = (Graphics2D) context;
		
		//Background
		view.getMenu().drawBackground(context2D, getWidth(), getHeight());
		view.getMenu().drawBoard(context2D, getWidth());
		
		//Button
		//drawPlay(context2D);
		if (!valid){
			int x = getSize().width/2-20;
			int y = 150;
			context.setFont(new Font("Sans_Serif", Font.BOLD, 12));
			context.setColor(Color.RED);
			context.drawString("Invalid input", x, y);
		}
	}
	
	private JButton playButton(){
		JButton play = new JButton(new ImageIcon(images.play_btn));
		play.setContentAreaFilled(false);
		play.setPreferredSize(new Dimension(140, 50));
		play.setCursor(new Cursor(Cursor.HAND_CURSOR));
		return play;
	}
	
	/*private void drawPlay(Graphics2D context){
		int buttonWidth = images.play_btn.getWidth();
		int buttonHeight = images.play_btn.getHeight();
		int x = getSize().width/2-buttonWidth/2;
		int y = view.getMenu().getRectangleForMenu(getSize().width).height - buttonHeight - 20;

		context.drawImage(images.play_btn, x, y, null);
		play = new Rectangle(x, y, buttonWidth, buttonHeight);
		

	}*/
	public Rectangle getPlay_btn() {
		return play;
	}
	
	private void chooseSize(){
		//Labels
		JLabel sizeLabel = new JLabel("GAME DIMENSIONS", SwingConstants.CENTER);
		JLabel sizeInfo = new JLabel("(Type in dimensions between 5 and 100)", SwingConstants.CENTER);
		JLabel xLabel = new JLabel("x");
		JLabel yLabel = new JLabel("y");
		JLabel gap = new JLabel("          ");
		customizeText(sizeLabel);
		customizeText(xLabel);
		customizeText(yLabel);
		customizeText(gap);
		
		//Formatter (limit input to three digits)
		MaskFormatter formatter = null;
	    try {
	        formatter = new MaskFormatter("###");
	    } catch (java.text.ParseException exc) {
	    	throw new RuntimeException("Formatter error");
	    }
	    
	    //Input fields
		inputWidth = new JFormattedTextField(formatter);
		inputHeight = new JFormattedTextField(formatter);
		setTextFieldFormat(inputWidth);
		setTextFieldFormat(inputHeight);
		
		JPanel sizeText = new JPanel();
		((FlowLayout)sizeText.getLayout()).setVgap(20);
		sizeText.add(sizeLabel);
		sizeText.add(sizeInfo);
		sizeText.setOpaque(false);
		
		JPanel sizePanel = new JPanel();
		sizePanel.add(xLabel);
		sizePanel.add(inputWidth);
		sizePanel.add(gap);
		sizePanel.add(yLabel);
		sizePanel.add(inputHeight);
		sizePanel.setOpaque(false);

		this.add(sizeText);
		this.add(sizePanel);

	}
	
	private void setTextFieldFormat(JFormattedTextField txt){
		txt.setFont(new Font("Sans_Serif", Font.PLAIN, 20)); 
		txt.setHorizontalAlignment(JTextField.CENTER);
		txt.setColumns(3);
		txt.setFocusLostBehavior(JFormattedTextField.COMMIT);
	}

	private void customizeText(JLabel label){
		label.setForeground(CustomColor.PANEL_COLOUR);
		label.setFont(new Font("Sans_Serif", Font.BOLD, 20));
	}
	
	 
    public JFormattedTextField getWidthInput(){
    	return inputWidth;
    }
	
    public JFormattedTextField getHeightInput(){
    	return inputHeight;
    }
    
    public void setValid(boolean tf){
    	valid = tf;
    }
}