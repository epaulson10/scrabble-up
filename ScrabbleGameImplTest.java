package scrabble;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import game.*;

public class ScrabbleGameImplTest
{
    private ScrabbleGameImpl testImpl;
    private ScrabbleHumanPlayer testPlayer0;
    private ScrabbleHumanPlayer testPlayer1;

    @Before
    public void setUp() throws Exception
    {
        testImpl = new ScrabbleGameImpl();
        testPlayer0 = new ScrabbleHumanPlayer();
        testPlayer0.setGame(testImpl, 0);
        testPlayer1 = new ScrabbleHumanPlayer();
        testPlayer1.setGame(testImpl, 1);
        GamePlayer[] players = {testPlayer0, testPlayer1}; 
        testImpl.setPlayers(players);
    }

    @After
    public void tearDown() throws Exception
    {
    }
    
    @Test
    public void testCanMove()
    {
        // Attempt out-of-turn move
        assertTrue("Player 1 can move out-of-turn", !testImpl.canMove(testPlayer1));
        // Attempt good move
        assertTrue("Player 0 can't move on his turn", testImpl.canMove(testPlayer0));
        // Make a move to change turn player
        ScrabbleDiscardAction testDisc =
            new ScrabbleDiscardAction(testPlayer0, new Vector<ScrabbleTile>());
        testImpl.makeMove(testPlayer0, testDisc);
        // Attempt out-of-turn move
        assertTrue("Player 0 can move out-of-turn", !testImpl.canMove(testPlayer0));
        // Attempt good move
        assertTrue("Player 1 can't move on his turn", testImpl.canMove(testPlayer1));
    }
    
    @Test
    public void testCanQuit()
    {
        assertTrue("Player 0 is blocked from quitting",
                   testImpl.canQuit(testPlayer0));
        assertTrue("Player 1 is blocked from quitting",
                   testImpl.canQuit(testPlayer1));
    }
    
    @Test
    public void testGameOver()
    {
        assertTrue("Game is over too early", !testImpl.gameOver());
    }
}
