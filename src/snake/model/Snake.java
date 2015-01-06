package snake.model;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

import snake.controller.Direction;

public class Snake extends Observable {
	private Game game;
	private ArrayList<Field> snake;
	private Action act;
	private Field head;
	private Field tail;
	private Field front;

	public Snake(Game game) {
		this.game = game;

		// Place snake
		front = new Field(0,0);
		head = new Field(game.getSize().height / 2, game.getSize().width / 2);
		tail = new Field(game.getSize().height / 2, game.getSize().width / 2 + 1);
		this.snake = new ArrayList<>();
		snake.add(head);
		snake.add(tail);
		//setChanged();
		//notifyObservers();
	}

	public void move(int dy, int dx) {
		front.setField(snake.get(0).getRow() + dx, snake.get(0).getColumn() + dy);
	
		// check for food
		if (game.getFood().getPosition().equals(front)) {
			act = Action.EAT;
		} else {
			for (int i = 0; i < snake.size()-1; i++) {
				if (front.equals(snake.get(i))) {
					System.out.print("die");
					act = Action.KILL;
				}
			}
			if (!game.gameOver) {
				for (int i = snake.size() - 1; i > 0; i--) {
					snake.set(i, snake.get(i - 1));
				}
				snake.set(0, new Field(snake.get(0).getRow() + dx, snake.get(0).getColumn() + dy));
			}
		}
		setChanged();
		notifyObservers();
	}
	
	public Action getAction(){
		return act;
	}

	public Action setAction(Action act){
		return this.act = act;
	}
	public ArrayList<Field> getSnake() {
		return snake;
	}
}
