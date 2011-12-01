package scrabble;

/** * Class representing a skilled AI player. Here is it's basic winning strategy:
 * 
 * @author Aaron Dobbe, Steven Beyer, Erik Paulson, and Andrew Meyer
 * @version To be implemented*/
public class ScrabbleComputerPlayerHard extends ScrabbleComputerPlayer {

    public ScrabbleComputerPlayerHard() {
        super();
    }
    
    /**  * Respond to a move request by making a move. */
    protected void doRequestMove () {
    	try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        game.applyAction(new ScrabbleDiscardAction(this, getHand()));
    }
    


}