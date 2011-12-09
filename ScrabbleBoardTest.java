package scrabble;

import static org.junit.Assert.*;

import org.junit.Test;

public class ScrabbleBoardTest {

	private ScrabbleBoard testBoard;
	public static final int BOARD_HEIGHT = 15;
	public static final int BOARD_WIDTH = 15;

	/**
	 * Just tests the constructor
	 * testPutTileAt() tests edge cases
	 */
	@Test
	public void testGetTileAt() {
		testBoard = new ScrabbleBoard();
		for(int col = 0; col < BOARD_WIDTH; col++)
		{
			for(int row = 0; row < BOARD_HEIGHT; row++)
			{
				assertTrue("Board initializes to null",
						testBoard.getTileAt(row, col) == null); 
			}
		}
	}

	/**
	 * Just tests the constructor
	 */
	@Test
	public void testGetBonusAt() {
		testBoard = new ScrabbleBoard();
		for(int col = 0; col < BOARD_WIDTH; col++)
		{
			for(int row = 0; row < BOARD_HEIGHT; row++)
			{
				assertTrue("Bonus initialize to 0",
						testBoard.getBonusAt(row, col) == 0); 
			}
		}
	}

	// tests putTileAt and getTileAt
	@Test
	public void testPutTileAt() {
		testBoard = new ScrabbleBoard();
		ScrabbleTile a = new ScrabbleTile('A', 1, false);
		ScrabbleTile b = new ScrabbleTile('B', 1, false);
		ScrabbleTile c = new ScrabbleTile('C', 1, false);
		ScrabbleTile d = new ScrabbleTile('D', 1, false);
		ScrabbleTile e = new ScrabbleTile('E', 1, false);
		
		// place a tile at 0,0 (edge case)
		testBoard.putTileAt(0, 0, a);
		assertTrue("Tile placed at 0,0", testBoard.getTileAt(0,0)==a);
		
		// place a tile at 14, 14 (edge case)
		testBoard.putTileAt(14,14,b);
		assertTrue("Tile placed at 14,14", testBoard.getTileAt(14,14)==b);
		
		// place a tile at an invalid location
		testBoard.putTileAt(16,16,c);
		assertTrue("Tile placed at 16, 16", testBoard.getTileAt(16,16) == null);
	}

	@Test
	public void testGetTileImages() {
		fail("Not yet implemented");
	}

}
