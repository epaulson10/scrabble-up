package scrabble;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

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
        fail("Not yet implemented");
    }

}
