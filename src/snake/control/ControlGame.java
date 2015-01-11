package snake.control;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JList;

import snake.model.*;
import snake.view.*;

public class ControlGame implements ActionListener {

	private Game game;
	private View view;
	
	public ControlGame(Game game, View view) {
		this.game = game;
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand()=="playAgain"){
			game.restart();
			view.getBoard().remove(view.getBoard().getPlayAgain());
			view.getBoard().remove(view.getBoard().getMenu());
			view.requestFocus();
		} else if (e.getActionCommand()=="menu"){
			view.getContentPane().removeAll();
			view.add(view.getHeader(), BorderLayout.NORTH);
			view.add(view.getMenu());
			view.revalidate();
			view.repaint();
		}
		
	}
}
