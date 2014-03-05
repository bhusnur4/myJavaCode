package battleship;

import java.util.Random;

public class Ocean {

	//Used to quickly determine which ship is in any given location.
	private Ship[][] ships = new Ship[10][10]; 
	//The total number of shots fired by the user.		
	private int shotsFired;
	//The number of times a shot hit a ship. If the user shoots the same 
	//part of a ship more than once, every hit is counted, even though the 
	//additional "hits" don't do the user any good.
	private int hitCount; 
	//The number of ships sunk (10 ships in all).
	private int shipsSunk;
	
	/**
	 * The constructor creates an empty ocean (ships array filled with EmptySeas)
	 * Instance variables start to count
	 */
	public Ocean() {
		for (int row = 0; row < 10; row++) {
			for (int column = 0; column < 10; column++) {
				EmptySea emptySea = new EmptySea();
				emptySea.placeShipAt(row, column, true, this);
			}
		}
		shotsFired = 0;
		hitCount = 0;
		shipsSunk = 0;
	}
	
	/**
	 * make an array of ships for placing in the (initially) empty ocean
	 * 
	 * @return An array of unplaced ships of different type
	 */
	public Ship[] createShips() {
		Ship[] notPlacedShips = new Ship[10];
		notPlacedShips[0] = new Battleship(4);
		notPlacedShips[1] = new Cruiser(3);
		notPlacedShips[2] = new Cruiser(3);
		notPlacedShips[3] = new Destroyer(2);
		notPlacedShips[4] = new Destroyer(2);
		notPlacedShips[5] = new Destroyer(2);
		notPlacedShips[6] = new Submarine(1);
		notPlacedShips[7] = new Submarine(1);
		notPlacedShips[8] = new Submarine(1);
		notPlacedShips[9] = new Submarine(1);
		
		return notPlacedShips; 
	}
	
	/**
	 * Place all ten ships randomly on the (initially empty) ocean.
	 * Larger ships will be placed first
	 */
	public void placeAllShipsRandomly() {
		
		Random r = new Random();
		Ship[] shipsToBePlaced = createShips();
		boolean hasBeenPlaced;
		
		for(Ship s : shipsToBePlaced) {
			hasBeenPlaced = false;
			
			while (!hasBeenPlaced) {
				int tryRow = r.nextInt(10);
				int tryColumn = r.nextInt(10);
				boolean isHorizontal = r.nextBoolean();
				
				if (s.okToPlaceShipAt(tryRow, tryColumn, isHorizontal, this)) {	
					s.placeShipAt(tryRow, tryColumn, isHorizontal, this);
					hasBeenPlaced = true;
				}
			}
		}
	}
	
	/**
	 * Shoot the ocean at a certain square
	 * The counters also count 
	 * 
	 * @param row Row value of the square to be shot
	 * @param column Column value of the square to be shot
	 * @return If the square is shot successfully
	 */
	public boolean shootAt(int row, int column) {
		Ship squareHit = ships[row][column];
		shotsFired += 1;
		
		if (squareHit instanceof EmptySea) {
			squareHit.shootAt(row, column);
			return false;
		}
		else {
			if (squareHit.isSunk()) {
				return false;
			}
			else {
				squareHit.shootAt(row, column);
				hitCount += 1;
				if (squareHit.isSunk() == true) {
					shipsSunk += 1;
				}
				return true;
			}
		}
	}
	/**
	 * Check if a certain square in ocean is occupied
	 * 
	 * @param row Check the row of the square
	 * @param column Check the column of the square
	 * @return if the square is occupied
	 */
	public boolean isOccupied(int row, int column) {
		Ship s = this.ships[row][column];
		if (s instanceof EmptySea) {
			return false;
		}
		else return true;
	}
	
	/**
	 * set a ship to a certain square in ocean
	 * 
	 * @param row Row value of the square
	 * @param column Column value of the square
	 * @param ship Ship to be set in the ocean
	 */
	public void setShipArray(int row, int column, Ship ship) {
		this.ships[row][column] = ship;
	}

	/**
	 * Count the number of shots fired in a single game
	 * 
	 * @return the number of shots fired
	 */
	public int getShotsFired() {
		return shotsFired;
	}
		
	/**
	 * Count the number of hits
	 * 
	 * @return The number of hits
	 */
	public int getHitCount() {
		return hitCount;
	}
	
	/**
	 * Count the number of sunk ships
	 * 
	 * @return The number of sunk ships
	 */
	public int getShipsSunk() {
		return shipsSunk;
	}
	
	/**
	 * Check if all the ships are sunk
	 * 
	 * @return Returns true if all ships have been sunk, otherwise false.
	 */
	public boolean isGameOver() {
		if (shipsSunk == 10) {
			return true;
		}
		else return false;
	}

	/**
	 * 
	 * @return Returns the 10x10 array of ships. 
	 */
	public Ship[][] getShipArray() {
		return this.ships;
	}
	
	/**
	 * 
	 * @param row Row value of the ship on shipArray
	 * @param column Column value of the ship on shipArray
	 * @return Ship on the shipArray
	 */
	public Ship getShip(int row, int column) {
		return ships[row][column];
	}

	/**
	 * Print the ocean, which is actually printing the status of shipArray
	 * Print "x" if the ship is sunk
	 * Print "S" if the ship partly hit but not sunk
	 * Print "-" if the square shot is EmptySea
	 * Print "." if the square has not yet been shot
	 */
	public void print() {
		
		System.out.println(" ++ == The Battleship Game == ++ ");
		System.out.println("\n");
		System.out.print("   " + " ");
		for (int i = 0; i < 10; i++) {
			System.out.print(" " + i + " ");
			System.out.print(" ");
		}
		System.out.print("\n");
		// print the column numbers
		
		
		System.out.print("   ");
		for (int i = 0; i < 10; i++) {
			System.out.print(" " + "___");
		}
		System.out.print("\n");
		// print the upper frame
		
		for (int i = 0; i < 10; i++) {
			System.out.print(" " + i + " " + "|");
			for (int j = 0; j < 10; j++) {
				if (this.ships[i][j].isHorizontal()) {
					boolean hitPosition = ships[i][j].getHit(j - ships[i][j].getBowColumn());
					if (hitPosition) {
						System.out.print(" " + this.ships[i][j].toString() + " " + "|");
					}
					else {
						System.out.print(" " + "." + " " + "|");
					}
				}
				else {
					boolean hitPosition = ships[i][j].getHit(i - ships[i][j].getBowRow());
					if (hitPosition) {
						System.out.print(" " + this.ships[i][j].toString() + " " + "|");
					}
					else {
						System.out.print(" " + "." + " " + "|");
					}
				}
			}
			System.out.print("\n");
			
			System.out.print("   " + "|");
			for (int k = 0; k < 10; k++) {
				System.out.print("___" + "|");
			}
			System.out.print("\n");
		}
	}
}
