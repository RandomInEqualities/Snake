package snake.model;
public final class Field {
	
	private final int row;
	private final int column;
	
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
	
	public boolean equals(Field field){
		return row == field.getRow() && column == field.getColumn();
	}
	
}