package scrabble;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

public class ScrabbleHumanPlayerTest
{
    ScrabbleHumanPlayer thePlayer;
    ScrabbleGameImpl theGame;
    ScrabbleGameState theState;

    @Before
    public void setUp() throws Exception
    {
        theGame = new ScrabbleGameImpl();
        thePlayer = new ScrabbleHumanPlayer();
        thePlayer.setGame(theGame, 0);
        theState = (ScrabbleGameState)theGame.getState(thePlayer, 0);
        
    }


    @Test
    public void testStateChanged()
    {
        Vector<ScrabbleTile> oldHand = new Vector<ScrabbleTile>();
        for (ScrabbleTile x : thePlayer.getHand())
            oldHand.add(x);
        theGame.applyAction(new ScrabbleDiscardAction(thePlayer,thePlayer.getHand()));
        //See if the player still has a hand
        assertFalse(thePlayer.getHand().isEmpty());
        //Make sure the hand changed
        assertFalse(thePlayer.getHand() == oldHand);
        //Ensure the hand updated to the current state
        assertTrue(thePlayer.getHand().equals(theState.getHand()));  
    }

    @Test
    public void testGetHand()
    {
        Vector<ScrabbleTile> playerHand = thePlayer.getHand(); 
        for (ScrabbleTile x : playerHand)
            assertTrue(theState.getHand().contains(x));
       
    }

}
