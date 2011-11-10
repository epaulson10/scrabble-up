package scrabble;

/**Scrabble Blank Tile

A blank Scrabble Tile. Can represent any letter in game.*/
public class ScrabbleBlankTile extends ScrabbleTile {

	/** Initailizes variables. Sets letter to null and value to 0. */
	public ScrabbleBlankTile () {
		super(' ',0);
	}

	/** Sets the letter when the word is played.
@param newLetter - The letter the blank tile will now represent
@return Returns a ScrabbleTile that has letter newLetter and value 0. */
	public ScrabbleTile setLetter (char newLetter) {
		return null;
	}

}