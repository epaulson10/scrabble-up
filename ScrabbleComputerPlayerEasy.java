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
    	try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        game.applyAction(new ScrabbleDiscardAction(this, hand));
    }

}