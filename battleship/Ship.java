package battleship;

public class Ship {
	
	private int bowRow; 
	//-- the row (0 to 9) which contains the bow (front) of the ship.
	private int bowColumn; 
	//-- the column (0 to 9) which contains the bow (front) of the ship.
	private int length; 
	//-- the number of squares occupied by the ship. An "empty sea" location has length 1.
	private boolean horizontal; 
	//-- true if the ship occupies a single row, false otherwise.
	private boolean [] hit = new boolean[4]; 
	//-- an array of booleans telling whether that part of the ship has been hit. Only battleships use all four locations; cruisers use the first three; destroyers 2; submarines 1; and "empty sea" either one or none.
	
	
	
	/**
	 * This method exists only to be overridden,
	 * so it doesn't much matter what is returns.
	 * A abstract "ship" doesn't have a fixed length.
	 * 
	 * @return The length of the ship
	 */
	public int getLength() {
		return length;
	}

	/**
	 * 
	 * @return The row of the square that the bow of the ship is placed
	 */
	public int getBowRow() {
		return bowRow;
	}
	
	/**
	 * 
	 * @return The column of the square that the bow of the ship is placed
	 */	
	public int getBowColumn() {
		return bowColumn;
	}
	
	/**
	 * Return if the certain square on hit array is true
	 * @param pos Find the position on hit array
	 * @return If the position is hit
	 */
	public boolean getHit(int pos) {
		return hit[pos];
	}
	 
	 
	/**
	 * 
	 * @return If the ship is horizontally placed
	 */
	public boolean isHorizontal() {
		return horizontal;
	}
	
	/**
	 * 
	 * @param row The row value to be set for the square where bow of the ship is placed
	 */
	public void setBowRow(int row) {
		RuntimeException e = new RuntimeException();
		if (row >= 0 && row <=9) {
			bowRow = row;
		}
		else throw e;
	}
	
	/**
	 * 
	 * @param column The column value to be set for the square where bow of the ship is placed

	 */
	public void setBowColumn(int column) {
		RuntimeException e = new RuntimeException();
		if (column >= 0 && column <=9) {
			bowColumn = column;
		}
		else throw e;
	}
	
	/**
	 * 
	 * @param horizontal Set the ship to be horizontal or not
	 */
	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}
	
	/**
	 * Set the length of the ship
	 * @param len The length of the ship
	 */
	public void setLength(int len) {
		this.length = len;
	}
	
	public void setHit(int pos, boolean isHit) {
		this.hit[pos] = isHit; 
	}
	
	/**
	 * Method exists to be overridden
	 * 
	 * @return The type of the ship in string
	 */
	public String getShipType() {
		return "";
	}
	
	/**
	 * The ship must not overlap another ship, or touch another ship,
	 * and it must not "Stick out" beyond the array
	 * The method will not change wither the ship or the ocean
	 * 
	 * @param row Check if the ship is ok to be placed on this row
	 * @param column Check if the ship is ok to be placed on this column
	 * @param horizontal Check if the ship is ok to be placed horizontally
	 * @param ocean Check if the ship is ok to be placed on this ocean
	 * @return The boolean value if the ship is ok to be placed
	 */
	public boolean okToPlaceShipAt(int row, int column, boolean horizontal, Ocean ocean) {
		
		int startRow;
		int startColumn;
		int endRow;
		int endColumn;
		//The expanded region to be check
		
		if (horizontal) {
			if (column + this.length > 9) {
				return false;
			}
			//Check if the ship will stick out
			
			startRow = Math.max(row - 1, 0);
			startColumn = Math.max(column - 1, 0);
			endRow = Math.min(row + 1, 9);
			endColumn = Math.min(column + this.length, 9);
			
			for (int i = startRow; i < endRow + 1; i++) {
				for (int j = startColumn; j < endColumn + 1; j++) {
					if (ocean.isOccupied(i, j)) {
						return false;
					}
				}
			}
			
		}
		
		else {
			if (row + this.length > 9) {
				return false;
			}
			
			startRow = Math.max(row - 1, 0);
			startColumn = Math.max(column - 1, 0);
			endRow = Math.min(row + this.length, 9);
			endColumn = Math.min(column + 1, 9);
			
			for (int i = startRow; i < endRow + 1; i++) {
				for (int j = startColumn; j < endColumn + 1; j++) {
					if (ocean.isOccupied(i, j)) {
						return false;
					}
				}
			}
		}
		
		return true;
	}
	
	/**
	 * "Puts" the ship in the ocean
	 * The instance variables of a ship will be set
	 * And the shipArray of the ocean will also be set
	 * 
	 * @param row Place the ship to this row in ocean
	 * @param column Place the ship to this column in ocean
	 * @param horizontal Place the ship in ocean horizontally or not
	 * @param ocean Place the ship in the ocean
	 */
	public void placeShipAt(int row, int column, boolean horizontal, Ocean ocean) {
		this.setBowRow(row);
		this.setBowColumn(column);
		this.setHorizontal(horizontal);
		
		if (horizontal) {
			for (int j = column; j < column + this.length; j++) {
				ocean.setShipArray(row, j, this);
			}
		}
		else {
			for (int i = row; i < row + this.length; i++) {
				ocean.setShipArray(i, column, this);
			}
		}
	}
	
	/**
	 * Shoot the ship at a certain square
	 * The hit array of the ship will be set
	 * Return true only if the square is occupied and the ship on it is not sunk
	 * 
	 * @param row Shoot the ship by this row
	 * @param column Shoot the ship by this column
	 * @return If the shoot is successful
	 */
	public boolean shootAt(int row, int column) {
		if (!(this instanceof EmptySea)) {
			if (!this.isSunk()) {
				if (horizontal) {
					hit[column - bowColumn] = true;
					return true;
				}
				else {
					hit[row - bowRow] = true;
					return true;
				}
			}
			else {
				return false;
			}
		}
		else {
			hit[0] = true;
			return false;
		}
	}
	
	//Return true if every part of the ship has been hit, false otherwise.
	/**
	 * Check is the ship is sunk
	 * A ship will be sunk if all valid hits are true
	 */
	public boolean isSunk() {
		if (!(this instanceof EmptySea)) {
			for (int i = 0; i < this.length; i++) {
				if (!hit[i]) {
					return false;
				}
			}
			return true;	
		}
		else return false;		
	}
	
	/**
	 * Return the string of the class
	 */
	public String toString() {
		if (this.isSunk()) {
			return "x";
		}
		else return "S";
	}
}
