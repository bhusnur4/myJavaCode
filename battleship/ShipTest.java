package battleship;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class ShipTest {
    Ship ship;

    @Before
    public void setUp() throws Exception {
        ship = new Cruiser(3);
    }

    @Test
    public final void testSetAndGetBowRow() {
        ship.setBowRow(3);
        assertEquals(3, ship.getBowRow());
    }

    @Test
    public final void testSetAndGetBowColumn() {
        ship.setBowColumn(5);
        assertEquals(5, ship.getBowColumn());
    }

    @Test
    public final void testSetAndGetIsHorizontal() {
        ship.setHorizontal(true);
        assertTrue(ship.isHorizontal());
        ship.setHorizontal(false);
        assertFalse(ship.isHorizontal());
    }

//    @Test
//    public final void testGetShipType() {
//        fail("Not yet implemented");
//    }

    @Test
    public final void testOkToPlaceShipAt() {
        Ocean ocean = new Ocean();
        Ship[][] ships = ocean.getShipArray();
        Ship battleship = new Battleship(4);
        ships[3][3] = ships[3][4] = ships[3][5] = ships[3][6] = battleship;

        Cruiser cruiser = new Cruiser(4);
        assertTrue(cruiser.okToPlaceShipAt(1, 7, true, ocean));
        assertFalse(cruiser.okToPlaceShipAt(4, 8, true, ocean));
        assertTrue(cruiser.okToPlaceShipAt(5, 4, false, ocean));
        assertFalse(cruiser.okToPlaceShipAt(8, 6, false, ocean));
        assertFalse(cruiser.okToPlaceShipAt(4, 2, false, ocean));
        assertFalse(cruiser.okToPlaceShipAt(3, 4, true, ocean));
        
        Destroyer destroyer = new Destroyer(2);
        assertTrue(destroyer.okToPlaceShipAt(0, 0, true, ocean));
        assertFalse(destroyer.okToPlaceShipAt(9, 9, true, ocean));

        Submarine submarine = new Submarine(2);
        assertTrue(submarine.okToPlaceShipAt(0, 0, true, ocean));
        assertTrue(submarine.okToPlaceShipAt(9, 9, false, ocean));
        assertFalse(submarine.okToPlaceShipAt(3, 6, true, ocean));
    }

    @Test
    public final void testPlaceShipAt() {
        Ocean ocean = new Ocean();
        
        Ship battleship = new Battleship(4);
        Ship cruiser = new Cruiser(3);
        Ship destroyer = new Destroyer(2);
        Ship submarine = new Submarine(1);

        battleship.placeShipAt(3, 3, true, ocean);
        cruiser.placeShipAt(0, 1, false, ocean);
        destroyer.placeShipAt(7, 8, true, ocean);
        submarine.placeShipAt(9, 0, false, ocean);
        
        int[][] battleshipLocations = {{3, 3}, {3, 4}, {3, 5}, {3, 6}};
        int[][] cruiserLocations = {{0, 1}, {1, 1}, {2, 1}};
        int[][] destroyerLocations = {{7, 8}, {7, 9}};
        int[][] submarineLocations = {{9, 0}};
        
        Ship[][] ships = ocean.getShipArray();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (battleship.equals(ships[i][j])) {
                    assertTrue(in(i, j, battleshipLocations));
                } else if (cruiser.equals(ships[i][j])) {
                    assertTrue(in(i, j, cruiserLocations));
                } else if (destroyer.equals(ships[i][j])) {
                    assertTrue(in(i, j, destroyerLocations));
                } else if (submarine.equals(ships[i][j])) {
                    assertTrue(in(i, j, submarineLocations));
                } else {
                    assertTrue("Location " + i + ", " + j + " contains " + ships[i][j],
                               ships[i][j] instanceof EmptySea);
                }
            }
        }
        
    }
    
    private boolean in(int row, int column, int[][] locations) {
        for (int[] location : locations) {
            if (row == location[0] && column == location[1]) {
                return true;
            }
        }
        return false;
    }

    @Test
    public final void testShootAt() {
        Ocean ocean = new Ocean();
        Ship[][] ships = ocean.getShipArray();
        Ship destroyer = new Destroyer(2);
        ships[3][3] = ships[3][4] = destroyer;

        assertEquals(0, ocean.getShotsFired());
        assertEquals(0, ocean.getHitCount());
    }

    @Test
    public final void testIsSunk() {
        Ocean ocean = new Ocean();
        Ship cruiser = new Destroyer(2);
        cruiser.placeShipAt(1, 3, true, ocean);
        assertFalse(cruiser.isSunk());
        cruiser.shootAt(1, 3);
        assertFalse(cruiser.isSunk());
        cruiser.shootAt(1, 3);
        assertFalse(cruiser.isSunk());
        cruiser.shootAt(1, 4);
        assertTrue(cruiser.isSunk());
    }

    @Test
    public final void testGetShipType() {
        assertEquals("battleship", new Battleship(4).getShipType());
        assertEquals("cruiser", new Cruiser(3).getShipType());
        assertEquals("destroyer", new Destroyer(2).getShipType());
        assertEquals("submarine", new Submarine(1).getShipType());
    }

    @Test
    public final void testToString() {
        Ocean ocean = new Ocean();
        Ship submarine = new Submarine(1);
        submarine.placeShipAt(1, 3, true, ocean);
        assertEquals("S", submarine.toString());
        submarine.shootAt(1, 3);
        assertEquals("x", submarine.toString());
    }
}
