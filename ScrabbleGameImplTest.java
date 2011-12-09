package scrabble;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.Point;
import java.util.*;

import game.*;

public class ScrabbleGameImplTest
{
    private ScrabbleGameImpl testImpl;
    private ScrabbleHumanPlayer testPlayer0;
    private ScrabbleHumanPlayer testPlayer1;
    private ScrabbleBoard testBoard;
    private ScrabbleDiscardAction testDiscard;
    private ScrabbleResignAction testResign;
    
    
    
    
    

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
        testBoard = new ScrabbleBoard();        
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
    public void testInitializeGame()
    {
        testImpl.initializeGame();
        // get a state so we can make sure it worked
        ScrabbleGameState testState =
            (ScrabbleGameState)testImpl.getState(testPlayer0, 0);
        assertTrue("Player 0's score not initialized", testState.getScore(0) == 0);
        assertTrue("Player 1's score not initialized", testState.getScore(1) == 0);
        assertTrue("not Player 0's turn", testState.whoseMove() == 0);
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
    
    
    @Test
    public void makeMove()
    {
    	// Scrabble tiles
    	ScrabbleTile a = new ScrabbleTile('A', 1, false);
    	ScrabbleTile i = new ScrabbleTile('I', 1, false);
    	ScrabbleTile r = new ScrabbleTile('R', 1, false);
    	ScrabbleTile h = new ScrabbleTile('H', 1, false);
    	ScrabbleTile e = new ScrabbleTile('E', 1, false);
    	ScrabbleTile d = new ScrabbleTile('D', 1, false);
    	ScrabbleTile c = new ScrabbleTile('C', 1, false);
    	
    	// locations and tiles for a valid move
        Vector<Point> loc = new Vector<Point>();
        loc.add(new Point(7,7));
        loc.add(new Point(7,8));
        Vector<ScrabbleTile> tiles = new Vector<ScrabbleTile>();
        tiles.add(a);
        tiles.add(a);
        
    	ScrabbleMoveAction testMove = 
    		new ScrabbleMoveAction(testPlayer0, tiles, loc);
    	assertTrue("Player 0 valid move", 
    			testImpl.makeMove(testPlayer0, testMove));
        
        // locations and tiles for an valid word
        Vector<Point> loc2 = new Vector<Point>();
        loc2.add(new Point(0,7));
        loc2.add(new Point(1,7));
        loc2.add(new Point(2,7));
        loc2.add(new Point(3,7));
        loc2.add(new Point(4,7));
        loc2.add(new Point(5,7));
        loc2.add(new Point(6,7));
        Vector<ScrabbleTile> tiles2 = new Vector<ScrabbleTile>();
        tiles2.add(d);
        tiles2.add(i);
        tiles2.add(a);
        tiles2.add(r);
        tiles2.add(r);
        tiles2.add(h);
        tiles2.add(e);
        
    	ScrabbleMoveAction testMove2 =
    		new ScrabbleMoveAction(testPlayer1, tiles2, loc2);
    	assertTrue("Player 1 valid word",
    			testImpl.makeMove(testPlayer1, testMove2));
        
        // locations and tiles for a valid word the 0th column
        Vector<Point> loc3 = new Vector<Point>();
        loc3.add(new Point(0,8));
        loc3.add(new Point(0,9));
        loc3.add(new Point(0,10));
        
        Vector<ScrabbleTile> tiles3 = new Vector<ScrabbleTile>();
        tiles3.add(i);
        tiles3.add(r);
        tiles3.add(e);
        
    	ScrabbleMoveAction testMove3 = 
    		new ScrabbleMoveAction(testPlayer0, tiles3, loc3);
    	assertTrue("Player0 valid word",
    			testImpl.makeMove(testPlayer0, testMove3));
        
        // to test a word coming off the 0th column
        Vector<Point> loc4 = new Vector<Point>();
        loc4.add(new Point(1, 10));
        loc4.add(new Point(2, 10));
        
        Vector<ScrabbleTile> tiles4 = new Vector<ScrabbleTile>();
        tiles4.add(a);
        tiles4.add(r);
        
    	ScrabbleMoveAction testMove4 =
    		new ScrabbleMoveAction(testPlayer1, tiles4, loc4);
    	assertTrue("Player1 valid word",
    			testImpl.makeMove(testPlayer1, testMove4));
        
        // to test a move with different number of locations and tiles
        Vector<Point> loc5 = new Vector<Point>();
        loc5.add(new Point(0, 0));
        
        Vector<ScrabbleTile> tiles5 = new Vector<ScrabbleTile>();
        tiles5.add(r);
        tiles5.add(e);
        
    	ScrabbleMoveAction testMove5 =
    		new ScrabbleMoveAction(testPlayer0, tiles5, loc5);
    	assertTrue("Different number of locations and tiles",
    			!testImpl.makeMove(testPlayer0, testMove5));
        
        // test an invalid word in a col
        Vector<Point> loc6 = new Vector<Point>();
        loc6.add(new Point(7, 9));
        
        Vector<ScrabbleTile> tiles6 = new Vector<ScrabbleTile>();
        tiles6.add(a);
        
    	ScrabbleMoveAction testMove6 =
    		new ScrabbleMoveAction(testPlayer0, tiles6, loc6);
    	assertTrue("Invalid word in col",
    			!testImpl.makeMove(testPlayer0, testMove6));
    	
    	// to test two letters in different column & row
        Vector<Point> loc7 = new Vector<Point>();
        loc7.add(new Point(7, 9));
        loc7.add(new Point(8, 10));
        
        Vector<ScrabbleTile> tiles7 = new Vector<ScrabbleTile>();
    	tiles7.add(a);
    	tiles7.add(a);
    	
    	ScrabbleMoveAction testMove7 =
    		new ScrabbleMoveAction(testPlayer0, tiles7, loc7);
    	assertTrue("Two tiles on different row and col",
    			!testImpl.makeMove(testPlayer0, testMove7));
    	
    	// check word with two letters on the same row that are not connected
    	Vector<Point> loc8 = new Vector<Point>();
    	loc8.add(new Point(8,8));
    	loc8.add(new Point(10,8));
    	
    	Vector<ScrabbleTile> tiles8 = new Vector<ScrabbleTile>();
    	tiles8.add(a);
    	tiles8.add(a);

    	ScrabbleMoveAction testMove8 =
    		new ScrabbleMoveAction(testPlayer0, tiles8, loc8);
    	assertTrue("Two tiles not connected on row",
    			!testImpl.makeMove(testPlayer0, testMove8));
    	
    	// check word with two letters on the same col that are not connected
    	Vector<Point> loc9 = new Vector<Point>();
    	loc9.add(new Point(7,9));
    	loc9.add(new Point(7,11));
    	
    	Vector<ScrabbleTile> tiles9 = new Vector<ScrabbleTile>();
    	tiles9.add(h);
    	tiles9.add(a);

    	ScrabbleMoveAction testMove9 =
    		new ScrabbleMoveAction(testPlayer0, tiles9, loc9);
    	assertTrue("Two tiles not connected on col",
    			!testImpl.makeMove(testPlayer0, testMove9));
    	
    	// letters off the board
    	Vector<Point> loc10 = new Vector<Point>();
    	loc10.add(new Point(15,15));
    	loc10.add(new Point(16,16));
    	
    	Vector<ScrabbleTile> tiles10 = new Vector<ScrabbleTile>();
    	tiles10.add(a);
    	tiles10.add(a);

    	ScrabbleMoveAction testMove10 =
    		new ScrabbleMoveAction(testPlayer0, tiles10, loc10);
    	assertTrue("Letters off the board",
    			!testImpl.makeMove(testPlayer0, testMove10));
    	
    	// letters at the same spot as a different letter
    	Vector<Point> loc11 = new Vector<Point>();
    	loc11.add(new Point(7,8));
    	loc11.add(new Point(8,8));
    	
    	Vector<ScrabbleTile> tiles11 = new Vector<ScrabbleTile>();
    	tiles11.add(a);
    	tiles11.add(a);

    	ScrabbleMoveAction testMove11 =
    		new ScrabbleMoveAction(testPlayer0, tiles11, loc11);
    	assertTrue("Two tiles on the same spot",
    			!testImpl.makeMove(testPlayer0, testMove11));
    	
    	// test invalid word in row
    	Vector<Point> loc12 = new Vector<Point>();
    	loc12.add(new Point(8,8));
    	
    	Vector<ScrabbleTile> tiles12 = new Vector<ScrabbleTile>();
    	tiles12.add(c);

    	ScrabbleMoveAction testMove12 =
    		new ScrabbleMoveAction(testPlayer0, tiles12, loc12);
    	assertTrue("Invalid word in the row",
    			!testImpl.makeMove(testPlayer0, testMove12));
    	
    	// test a valid word that is added above another word
    	Vector<Point> loc13 = new Vector<Point>();
    	loc13.add(new Point(3,6));
    	
    	Vector<ScrabbleTile> tiles13 = new Vector<ScrabbleTile>();
    	tiles13.add(a);

    	ScrabbleMoveAction testMove13 =
    		new ScrabbleMoveAction(testPlayer0, tiles13, loc13);
    	assertTrue("Valid word placed above an existing word",
    			testImpl.makeMove(testPlayer0, testMove13));
    	
    	// Test discardAction
    	ScrabbleDiscardAction testDiscard =
    		new ScrabbleDiscardAction(testPlayer0, tiles13);
    	assertTrue("Valid discard",
    			testImpl.makeMove(testPlayer0, testDiscard));
    	
    	// Test resignAction
    	ScrabbleResignAction testResign =
    		new ScrabbleResignAction(testPlayer0);
    	assertTrue("Valid resign", 
    			testImpl.makeMove(testPlayer0, testResign));
    	
    }
    
}
