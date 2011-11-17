package scrabble;

/**Scrabble Blank Tile

A blank Scrabble Tile. Can represent any letter in game.*/
public class ScrabbleBlankTile extends ScrabbleTile {

	/** Initializes variables. Sets letter to null and value to 0. */
	public ScrabbleBlankTile () {
		super(' ',0, false);
	}

	/** Sets the letter when the word is played.
@param newLetter - The letter the blank tile will now represent
@return Returns a ScrabbleTile that has letter newLetter and value 0. */
	public ScrabbleTile setLetter (char newLetter) {
		return new ScrabbleTile(newLetter, 0, true);
	}
	
	/** The location of the tile's image */
	public String getFileName ()
	{
		return "Tiles/ScrabbleTileBlank.jpg";
	}

}