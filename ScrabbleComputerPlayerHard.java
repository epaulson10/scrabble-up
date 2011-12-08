package scrabble;

import game.*;

import java.awt.Point;
import java.util.Random;
import java.util.Vector;

/** * Class representing a skilled AI player. Here is it's basic winning strategy:
 * 
 * @author Aaron Dobbe, Steven Beyer, Erik Paulson, and Andrew Meyer
 * @version To be implemented*/
public class ScrabbleComputerPlayerHard extends ScrabbleComputerPlayer {

    public ScrabbleComputerPlayerHard() {
        
    }
    
    /**  * Respond to a move request by making a move. */
    protected void doRequestMove () {
    	ScrabbleGameState myState = (ScrabbleGameState)game.getState(this, 0);
    	Vector<ScrabbleTile> hand = myState.getHand();
    	
    	
    	ScrabbleTile[] handContainer = new ScrabbleTile[7];
        hand.toArray(handContainer);
        
        
        Vector<GameMoveAction> moves = new Vector<GameMoveAction>();
        
        ScrabbleBoard boardState = myState.getBoard();
        
        
        for(int i = 2; i < ScrabbleBoard.BOARD_HEIGHT-3; i++)
        {
        	for(int j = 2; j < ScrabbleBoard.BOARD_WIDTH-3; j++)
        	{
        		if(boardState.getTileAt(i, j) != null)
        		{
        			
        		}
        	}
        }
        
        Vector<ScrabbleTile> discard = new Vector<ScrabbleTile>();
		for(ScrabbleTile s: myState.getHand()){
			discard.add(s);
		}
		discard.remove(5);
		discard.remove(3);
		ScrabbleDiscardAction move = new ScrabbleDiscardAction(this, discard);
		moves.add(move);
        
       
        GameAction[] ga = new GameAction[0];
        ga = moves.toArray(ga);
        Random rand = new Random();
        game.applyAction(ga[rand.nextInt(ga.length)]);
    }
    
    
    
    


}