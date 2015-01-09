package snake.model;

public class Body {
	Direction prevLink;
	Direction nextLink;
	
	public Body(){
	}
	
	public Direction getPrevLink(){
		return prevLink;
	}
	public Direction getNextLink(){
		return nextLink;
	}

	public void setLink(Field thisField, Field prevField, Field nextField){
		//Set forward direction
		if(isLeft(thisField, prevField)==true){
			prevLink = Direction.LEFT;
		}
		else if(isRight(thisField, prevField)==true){
			prevLink = Direction.RIGHT;
		}
		else if(isAbove(thisField, prevField)==true){
			prevLink = Direction.UP;
		}
		else if(isBelow(thisField, prevField)==true){
			prevLink = Direction.DOWN;
		}
		//Set backward direction
		if(isLeft(thisField, nextField)==true){
			nextLink = Direction.LEFT;
		}
		else if(isRight(thisField, nextField)==true){
			nextLink = Direction.RIGHT;
		}
		else if(isAbove(thisField, nextField)==true){
			nextLink = Direction.UP;
		}
		else if(isBelow(thisField, nextField)==true){
			nextLink = Direction.DOWN;
		}
	}
	public boolean isLeft(Field thisField, Field otherField){
		if(thisField.getColumn()<otherField.getColumn()){
			return true;
		}
		else return false;
	}
	public boolean isRight(Field thisField, Field otherField){
		if(thisField.getColumn()>otherField.getColumn()){
			return true;
		}
		else return false;
	}
	public boolean isAbove(Field thisField, Field otherField){
		if(thisField.getRow()>otherField.getRow()){
			return true;
		}
		else return false;
	}
	public boolean isBelow(Field thisField, Field otherField){
		if(thisField.getRow()<otherField.getRow()){
			return true;
		}
		else return false;
	}
}