package battleship;


public class Submarine  extends Ship {

	public Submarine (int len) {
		this.setLength(len);
		this.setHit(0, false);
	}
	
	@Override
	//Returns one of the strings "battleship", "cruiser", "destroyer", or 
	//"submarine", as appropriate.
	public String getShipType() {
		return "submarine";
	}
	
	@Override
	//Returns a single-character String to use in the Ocean's print
	//method (see below).
	//This method should return "x" if the ship has been sunk, "S" if it has not 
	//been sunk. This method can be used to print out locations in the ocean that
	//have been shot at; it should not be used to print locations that have not 
	//been shot at. 
	//Since toString behaves exactly the same for all ship types, it can be
	//moved into the Ship class, and simply inherited by each individual type of 
	//ship.
	public String toString() {
		if (this.isSunk()) {
			return "x";
		}
		else return "S";
	}
	

	

}
