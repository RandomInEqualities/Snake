package snake.view;

import java.awt.*;
import java.awt.event.FocusEvent;

import javax.swing.*;
import javax.swing.text.MaskFormatter;

import snake.control.ControlSingleplayer;


public class Singleplayer extends JPanel{
	private View view;
	private CustomImages images = new CustomImages();;
	//private Rectangle play;
	private JFormattedTextField inputWidth, inputHeight;
	private ControlSingleplayer controlSingleplayer;
	private boolean valid;
	private boolean filled;
	private int buttonWidth;
	private int buttonHeight;
	private int x;
	private int y;
	private JPanel panel;
	private JButton play;
	private CustomColor colours;
	
	public Singleplayer(View view){
		this.view = view;
		this.valid = true;
		this.filled = true;
		this.setLayout(null);
		this.colours = new CustomColor();
		panel = new JPanel();
		
		//Formatter (limit input to three digits)
		MaskFormatter formatter = null;
		try {
			formatter = new MaskFormatter("###");
		} catch (java.text.ParseException exc) {
				throw new RuntimeException("Formatter error");
		}
		inputWidth = new JFormattedTextField(formatter);
		inputHeight = new JFormattedTextField(formatter);
		
		
		play = playButton();
	}

	protected @Override void paintComponent(Graphics context) {
		super.paintComponent(context);
		Graphics2D context2D = (Graphics2D) context;
		
		//Background
		view.getMenu().drawBackground(context2D, getWidth(), getHeight());
		view.getMenu().drawBoard(context2D, getWidth());
		
		//Create transparent panel in the board area
		Rectangle board = view.getMenu().getRectangleForMenu(getWidth());
		board.height = 400;
		panel.setBounds(board);
		panel.setOpaque(false);
		panel.setLayout(null);
		
		//Text
		((Graphics2D) context).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		context.setFont(new Font("Sans_Serif", Font.BOLD, 20));
		context.setColor(CustomColor.PANEL_COLOUR);
		context.drawString("GAME DIMENSIONS", panel.getX()+20, panel.getY()+30);
		
		context.setFont(new Font("Sans_Serif", Font.PLAIN, 11));
		context.drawString("(Type in dimensions between 5 and 100)", panel.getX()+230, panel.getY()+26);
		
		context.setFont(new Font("Sans_Serif", Font.BOLD, 15));
		context.drawString("width", getWidth()/2-150, panel.getY()+70);
		context.drawString("height", getWidth()/2, panel.getY()+70);
		setTextFieldFormat(inputWidth);
		setTextFieldFormat(inputHeight);
		inputWidth.setBounds(150, panel.getY()+40, 50, 30);
		inputHeight.setBounds(panel.getWidth()-200, panel.getY()+40, 50, 30);
		panel.add(inputWidth);
		panel.add(inputHeight);
		this.add(panel);
		
		
		//Add play button
		buttonWidth = images.play_btn.getWidth();
		buttonHeight = images.play_btn.getHeight();
		x = getSize().width/2-buttonWidth/2;
		y = view.getMenu().getRectangleForMenu(getSize().width).height - buttonHeight - 20;
		play.setBounds(x, y, buttonWidth, buttonHeight);
		this.add(play);
		
		//Error message
		int y = 110;
		context.setColor(Color.RED);
		context.setFont(new Font("Sans_Serif", Font.BOLD, 12));
		if (!valid){
			int x = getWidth()/2-40;
			context.drawString("Invalid input", x, y);
		} else if (!filled){
			int x = getWidth()/2-55;
			context.drawString("Type in game size", x, y);
		}
	}

	private JButton playButton(){
		controlSingleplayer = new ControlSingleplayer(view);
		JButton play = new JButton(new ImageIcon(images.play_btn));
		play.setPreferredSize(new Dimension(140, 50));
		play.setCursor(new Cursor(Cursor.HAND_CURSOR));
		play.addActionListener(controlSingleplayer);
		return play;
	}
	
	private void setTextFieldFormat(JFormattedTextField txt){
		txt.setFont(new Font("Sans_Serif", Font.PLAIN, 20)); 
		txt.setHorizontalAlignment(JTextField.CENTER);
		txt.setFocusLostBehavior(JFormattedTextField.COMMIT); //Commit the new input

	}
	 
    public JFormattedTextField getWidthInput(){
    	return inputWidth;
    }
	
    public JFormattedTextField getHeightInput(){
    	return inputHeight;
    }
    
    public void setValid(boolean b){
    	valid = b;
    }

	public void setFilled(boolean b) {
		filled = b;		
	}
}