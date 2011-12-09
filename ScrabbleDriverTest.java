package scrabble;

import static org.junit.Assert.*;

import org.junit.*;
import game.*;

public class ScrabbleDriverTest
{

    ScrabbleDriver testDriver;
    @Before
    public void setUp() throws Exception
    {
        testDriver = new ScrabbleDriver();
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void testCreateGame()
    {
        Game testGame = testDriver.createGame(2);
        assertTrue("Didn't create a GameImpl",
                testGame instanceof ScrabbleGameImpl);
    }
    
    @Test
    public void testCreateLocalPlayer()
    {
        GamePlayer testPlayer = testDriver.createLocalPlayer("human");
        assertTrue("Didn't create a human player",
                testPlayer instanceof ScrabbleHumanPlayer);
        
        testPlayer = testDriver.createLocalPlayer("AI player (easy)");
        assertTrue("Didn't create an easy AI player",
                testPlayer instanceof ScrabbleComputerPlayerEasy);
        
        testPlayer = testDriver.createLocalPlayer("AI player (hard)");
        assertTrue("Didn't create a hard AI player",
                testPlayer instanceof ScrabbleComputerPlayerHard);
        
        testPlayer = testDriver.createLocalPlayer("incorrect player");
        assertTrue("Didn't return null for bad string", testPlayer == null);
    }
}
