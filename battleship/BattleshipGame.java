package battleship;

import java.util.Scanner;

public class BattleshipGame {

	Ocean ocean = new Ocean();
	private static Scanner s;
	
	
	public BattleshipGame() {};
	
	public static void main(String[] args) {
		boolean play = true;
		
		while (play == true) {
		
		BattleshipGame b = new BattleshipGame();
		boolean gameOver = false;
		s = new Scanner(System.in);
		
		//The welcome interface
		System.out.println(" ++ == Welcome to Battleship! == ++ ");
		Ocean o = new Ocean();
		o.placeAllShipsRandomly();
		
			while (gameOver == false) {
				b.shoot(o);
				//show the results of the shots
				o.print();  //print the ocean
				gameOver = true; //if any of the non-sunk ships turn this boolean false, we know the game is not over
				//if all are sunk, the boolean gameOver will stay true
				for (Ship[] shipArray : o.getShipArray()) {
					for (Ship ship : shipArray) {
						if (!ship.isSunk() &&  !(ship instanceof EmptySea)) {
							gameOver = false;
					}
				}
			}
		}
			
		boolean invalidInput = true;
		//if we reach here, the game is over	
		System.out.println("Congratulations! The game is over");
		System.out.println("Shots fired were: " + o.getShotsFired()); 
		System.out.println("Would you like to play again?");
		System.out.println("Enter 'y' for yes, 'n' for no");
		String playAgain = s.next();
		
		while (invalidInput) {
			try {
				if (playAgain.equals("y")) {
					play = true;
				}
				else {
					play = false;
					invalidInput = false;
				}
			}
			catch (Exception e) {
				invalidInput = true;
				System.out.println("woops.. try again");
			}
			
		}
		
			
			}
		}
	
	
	public void shoot(Ocean ocean) {
		s = new Scanner(System.in);

		//accept shots while game is not over
		try {
		System.out.println("");
		System.out.println("Where would you like to shoot?");
		System.out.println("Enter a row, then a column (both integers 0-9): ");
		int row = s.nextInt();
		int column = s.nextInt();	
		ocean.shootAt(row, column); 
		if (ocean.isOccupied(row, column)) {
			System.out.println("");
			System.out.println("(*^_^*) Hit!");
			System.out.println("");
		}
		else {
			System.out.println("");
			System.out.println("(T_T) Miss!");
			System.out.println("");
		}
		System.out.println("++++++++++++++++++");
		System.out.println("Battle Status:");
		System.out.println("Shots fired: " + ocean.getShotsFired());
		System.out.println("Hits: " + ocean.getHitCount());
		System.out.println("Sunk ships: " + ocean.getShipsSunk());
		System.out.println("++++++++++++++++++");
		System.out.println("\n");
	}
	
	catch (Exception e) {
		System.out.println("Woops! Out of Range. Try again please.");
		this.shoot(ocean);
	}
	}
}
	
	
	
	

	

	
	

	
