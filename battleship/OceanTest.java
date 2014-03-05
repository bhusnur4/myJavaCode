package battleship;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
/*
 * Created on Oct 13, 2007
 * Totally revised December 2010
 */

public class OceanTest {
    private final int OCEAN_SIZE = 10;

    Ocean ocean;

    @Before
    public void setUp() throws Exception {
        ocean = new Ocean();
        new Submarine(1).placeShipAt(0, 0, true, ocean);
        new Submarine(1).placeShipAt(2, 4, true, ocean);
    }

    @Test
    public final void testOcean() {
        ocean = new Ocean();
        assertEquals(0, numberOfSquaresOccupied());
        assertEquals(0, ocean.getShotsFired());
        assertEquals(0, ocean.getHitCount());
    }
    
    @Test(timeout=1000)
    public final void testPlaceAllShipsRandomly() {
        ocean = new Ocean();
        ocean.placeAllShipsRandomly();
        assertEquals(20, numberOfSquaresOccupied());
    }
    
    @Test(timeout=5000)
    public final void testPlaceAllShipsRandomlyManyTimes() {
        int count = 0;
        int limit = 1000;
        for (int i = 0; i < limit; i++) {
            ocean = new Ocean();
            ocean.placeAllShipsRandomly();
        }
    }
//    
//    @Test
//    public final void testIsLegalLocation() {
//        int size = OCEAN_SIZE;
//        assertTrue(ocean.isValidLocation(0, 0));
//        assertTrue(ocean.isValidLocation(size - 1, size - 1));
//        assertTrue(ocean.isValidLocation(size / 2, size / 2));
//        assertFalse(ocean.isValidLocation(0, size));
//        assertFalse(ocean.isValidLocation(size, 0));
//        assertFalse(ocean.isValidLocation(-1, 0));
//        assertFalse(ocean.isValidLocation(0, -1));
//    }

    @Test
    public final void testIsOccupied() {
        Ship[][] ships = ocean.getShipArray();
        ships[0][0] = new Submarine(1);
        ships[2][4] = new Submarine(1);
        for (int i = 0; i < OCEAN_SIZE; i++) {
            for (int j = 0; j < OCEAN_SIZE; j++) {
                if (i == 0 && j == 0) assertTrue(ocean.isOccupied(i, j));
                else if (i == 2 && j == 4) assertTrue(ocean.isOccupied(i, j));
                else assertFalse(ocean.isOccupied(i, j));
            }
        }
    }

    @Test
    public final void testShootAt() {
        assertTrue(ocean.shootAt(0, 0));
        assertTrue(ocean.shootAt(2, 4));
        assertFalse(ocean.shootAt(0, 0));
        assertFalse(ocean.shootAt(4, 4));
        assertFalse(ocean.shootAt(4, 2));
    }

    @Test
    public final void testGetShotsFired() {
        assertEquals(0, ocean.getShotsFired());
        ocean.shootAt(0, 0);
        assertEquals(1, ocean.getShotsFired());
        ocean.shootAt(9, 9);
        assertEquals(2, ocean.getShotsFired());
        ocean.shootAt(0, 0);
        assertEquals(3, ocean.getShotsFired());
    }

    @Test
    public final void testGetHitCount() {
        // submarines at 0,0 and 2,4
        assertEquals(0, ocean.getHitCount());
        ocean.shootAt(0, 1);
        assertEquals(0, ocean.getHitCount());
        ocean.shootAt(1, 0);
        assertEquals(0, ocean.getHitCount());
        ocean.shootAt(0, 0);
        assertEquals(1, ocean.getHitCount());
        ocean.shootAt(2, 4);
        assertEquals(2, ocean.getHitCount());
        ocean.shootAt(0, 0);
        assertEquals(2, ocean.getHitCount());
    }

    @Test
    public final void testIsGameOver() {
        assertFalse(ocean.isGameOver());
        ocean.shootAt(0, 0);
        assertFalse(ocean.isGameOver());
        ocean.shootAt(0, 0);
        assertFalse(ocean.isGameOver());
        ocean.shootAt(8, 8);
        assertFalse(ocean.isGameOver());
        ocean.shootAt(2, 4);
        assertTrue(ocean.isGameOver());
    }

    private int numberOfSquaresOccupied() {
        int count = 0;
        for (int i = 0; i < OCEAN_SIZE; i++) {
            for (int j = 0; j < OCEAN_SIZE; j++) {
                if (ocean.isOccupied(i, j)) count++;
            }
        }
        return count;
    }
}
