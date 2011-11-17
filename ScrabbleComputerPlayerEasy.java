package scrabble;

/** * Class representing an unskilled AI player.
 * 
 * @author Aaron Dobbe, Steven Beyer, Erik Paulson, and Andrew Meyer
 * @version To be implemented*/
public class ScrabbleComputerPlayerEasy extends ScrabbleComputerPlayer {

    public ScrabbleComputerPlayerEasy() {
        
    }

    /**  * Respond to a move request by making a move. */
    protected void doRequestMove () {
        game.applyAction(new ScrabbleDiscardAction(this, hand));
    }

}