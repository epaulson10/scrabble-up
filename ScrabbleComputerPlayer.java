package scrabble;

import java.util.Vector;

import game.*;

/** * Abstract class representing a computer player. Should be
 * subclassed by classes representing each AI skill level
 *
 * @author Aaron Dobbe, Steven Beyer, Erik Paulson, and Andrew Meyer
 * @version To Be Implemented
*/
public abstract class ScrabbleComputerPlayer extends GameComputerPlayer implements ScrabblePlayer {

	
	protected Vector<ScrabbleTile> hand = new Vector<ScrabbleTile>();
	
	public ScrabbleComputerPlayer() {
		
	}
	@Override
	public Vector<ScrabbleTile> getHand() {
		return hand;
	}

	public void updateHand(Vector<ScrabbleTile> newHand) {
		hand = newHand;
	}

}