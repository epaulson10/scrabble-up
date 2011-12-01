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
	
	public ScrabbleComputerPlayer() {
		super();
	}
	
	public Vector<ScrabbleTile> getHand() {
	    ScrabbleGameState curState = (ScrabbleGameState)game.getState(this, 0);
        return curState.getHand();
	}

}