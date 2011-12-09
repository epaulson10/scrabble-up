package scrabble;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the encode/decoding algorithms within ScrabbleProxyPlayer
 * 
 * @author Erik Paulson
 *
 */
public class ScrabbleProxyPlayerTest
{
    ScrabbleBoard board;
    Vector<ScrabbleTile> hand;
    ScrabbleGameState state;
    ScrabbleProxyPlayer thePlayer;
    String encodedState = "[+A1+A1+A1+A1+A1+A1+Q10][||||||||||||||||||||||||||||" +
    		"||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||" +
    		"||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||" +
    		"||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||" +
    		"|||||||||||][12:99][0]";
    
    ScrabbleBoard board1;
    Vector<ScrabbleTile> hand1;
    ScrabbleGameState state1;
    ScrabbleProxyPlayer thePlayer1;
    String encodedState1 = "[-*0+A1+A1+A1+A1-X8+Q10][+A1+X8-E0||||||||||||||||||||||||||" +
            "||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||" +
            "||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||" +
            "|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||" +
            "|||||||||||][0:9][1]";
    
    ScrabbleMoveAction moveAction;
    
    @Before
    public void setup() throws Exception
    {
        thePlayer = new ScrabbleProxyPlayer();
        board = new ScrabbleBoard();
        hand = new Vector<ScrabbleTile>();
        for (int i = 0; i < 6; i++)
        {
            hand.add(new ScrabbleTile('A', 1, false));
        }
        hand.add(new ScrabbleTile('Q', 10, false));
        state = new ScrabbleGameState(board,hand,0,12,99);
        
        thePlayer1 = new ScrabbleProxyPlayer();
        board1 = new ScrabbleBoard();
        board1.putTileAt(0, 0, new ScrabbleTile('A',1,false));
        board1.putTileAt(0, 1, new ScrabbleTile('X',8,false));
        board1.putTileAt(0, 2, new ScrabbleTile('E',0,true));
        hand1 = new Vector<ScrabbleTile>();
        hand1.add(new ScrabbleBlankTile());
        for (int i = 1; i < 5; i++)
        {
            hand1.add(new ScrabbleTile('A', 1, false));
        }
        hand1.add(new ScrabbleTile('X', 8, true));
        hand1.add(new ScrabbleTile('Q', 10, false));
        state1 = new ScrabbleGameState(board1,hand1,1,0,9);
        
        Vector<ScrabbleTile> moveTiles = new Vector<ScrabbleTile>();
        moveTiles.add(new ScrabbleTile('A',1,false));
        moveTiles.add(new ScrabbleTile('X',8,false));
        moveTiles.add(new ScrabbleTile('E',0,true));
        Vector<Point> positions = new Vector<Point>();
        positions.add(new Point(0,0));
        positions.add(new Point(0,1));
        positions.add(new Point(0,2));
        
        moveAction = new ScrabbleMoveAction(thePlayer,moveTiles, positions);
    }
  
    @Test
    public void testEncodeState()
    {
        String stateString = thePlayer.encodeState(state);
        assertEquals(stateString,encodedState);
        
        String stateString1 = thePlayer.encodeState(state1);
        assertEquals(stateString1,encodedState1);
    }

    @Test
    public void testDecodeAction()
    {
        String moveString = "0|+A1+X8-E0|(0,0)(0,1)(0,2)";
        ScrabbleMoveAction decodedAction = (ScrabbleMoveAction) thePlayer.decodeAction(moveString);
        assertEquals(decodedAction.getSource(), moveAction.getSource());
        Vector<ScrabbleTile> tiles = decodedAction.getTiles();
        Vector<ScrabbleTile> compTiles = moveAction.getTiles();
        for (int i = 0; i < tiles.size(); i++)
        {
            assertTrue(tiles.get(i).getLetter() == compTiles.get(i).getLetter());
            assertTrue(tiles.get(i).getValue() == compTiles.get(i).getValue());
            assertTrue(tiles.get(i).isBlank() == compTiles.get(i).isBlank());
        }
        
        Vector<Point> positions = decodedAction.getPositions();
        Vector<Point> positionsToCompare = moveAction.getPositions();
        for (Point x : positions)
        {
            assertTrue(positionsToCompare.contains(x));
        }
        
        
    }

}
