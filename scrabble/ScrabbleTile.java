package scrabble;

/**Scrabble Tile

A scrabble tile used in playing the game.*/
class ScrabbleTile {
	private char letter;
	private int value;
	private String fileName;
	private boolean fromBlank;

	/** Initializes letter to initLetter and value to initValue.
@param initLetter the letter that the tile represents.
@param initValue the value of the tile. */
	public ScrabbleTile (char initLetter, int initValue) {
		
	}

	/** Gets the letter of the tile
@return the letter the tile represents */
	public char getLetter () {
		return ' ';
	}

	/** Gets the point value of the tile
@return the value of the tile in points */
	public int getValue () {
		return 0;
	}

	/** The location of the tile's image */
	public String getFileName () {
		return null;
	}

}