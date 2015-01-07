
package snake.model;

public class Field {
	
	private int row;
	private int column;
	
	public Field(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}
	
	public @Override boolean equals(Object other){
		if (other instanceof Field) {
			Field field = (Field)other;
			return row == field.row && column == field.column;
		}
		return false;
	}
	
	public @Override String toString() {
		return "Field(" + row + "," + column + ")";
	}
	
}