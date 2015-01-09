package snake.model;

import java.awt.Dimension;

public class Board {

	private int width;
	private int height;
	
	public Board(int width, int height) {
		if (width < 5 || width > 100) {
			throw new IllegalArgumentException("invalid width " + width);
		}
		if (height < 5 || height > 100) {
			throw new IllegalArgumentException("invalid height " + height);
		}
		this.width = width;
		this.height = height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public Dimension getDimension() {
		return new Dimension(width, height);
	}
	
	public int getSize() {
		return width*height;
	}
	
	public Field getCenter() {
		int row = (height - 1) / 2;
		int column = (width - 1) / 2;
		return new Field(row, column);
	}
	
	public Field wrap(int row, int column) {
		
		if (row < 0) {
			row = height - 1;
		}
		else if (row >= height) {
			row = 0;
		}
		
		if (column < 0) {
			column = width - 1;
		}
		else if (column >= width) {
			column = 0;
		}
		
		return new Field(row, column);
	}
	
	public Field wrap(Field field) {
		return wrap(field.getRow(), field.getColumn());
	}
}
