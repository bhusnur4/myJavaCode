package battleship;


public class EmptySea extends Ship {
//every time we look at some location in the array, we have to check if it is null. 
//By putting a non-null value in empty locations, denoting the absence of a ship, we can save all that null checking.
	
	
	public EmptySea() {
		this.setLength(1);
	}

	//This method overrides shootAt(int row, int column) that is 
	//inherited from Ship, and always returns false to indicate that 
	//nothing was hit.
	@Override
	public boolean shootAt(int row, int column) {
		this.setHit(0, true);;
		return false;
	}

	
	//This method overrides isSunk() that is inherited from Ship,
	//and always returns false to indicate that you didn't sink anything.
	@Override
	public boolean isSunk() {
		return false;
	}
	
	
	//	Returns a single-character String to use in the Ocean's 
	//print method (see below).
	@Override
	public String toString() {
		return "-";
	}

	
}
