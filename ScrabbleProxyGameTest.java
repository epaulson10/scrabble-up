package scrabble;

import static org.junit.Assert.*;

import game.GameAction;
import game.GameState;

import java.awt.Point;
import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

/**
 * Test the ScrabbleProxyGame encoding/decoding methods. note, I had to pluck
 * the methods from the class because trying to create a proxy game object
 * crashes since there is no network play in the test.
 * 
 * @author Erik Paulson
 */
public class ScrabbleProxyGameTest
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
    String encodedMove = "0|+A1+X8-E0|(0,0)(0,1)(0,2)";

    @Before
    public void setUp() throws Exception
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
    public void testDecodeState()
    {
        
        ScrabbleGameState decodedState = (ScrabbleGameState) decodeState(encodedState1);
        
        ScrabbleBoard board = decodedState.getBoard();
        Vector<ScrabbleTile> hand = decodedState.getHand();
        int p1score = decodedState.getScore(0);
        int p2score = decodedState.getScore(1);
        int whoseMove = decodedState.whoseMove();
        
        ScrabbleBoard boardComp = state.getBoard();
        Vector<ScrabbleTile> handComp = state.getHand();
        int p1scoreComp = state.getScore(0);
        int p2scoreComp = state.getScore(1);
        int whoseMoveComp = state.whoseMove();
        
        for(int i = 0; i < 15; i++)
        {
            for (int k = 0; k < 15; k++)
            {
                if (board.getTileAt(i, k) != null && boardComp.getTileAt(i,k)!= null)
                {
                    assertEquals(board.getTileAt(i,k).getLetter(), boardComp.getTileAt(i,k).getLetter());
                    assertEquals(board.getTileAt(i,k).getValue(), boardComp.getTileAt(i,k).getValue());
                    assertEquals(board.getTileAt(i,k).isBlank(),boardComp.getTileAt(i,k).isBlank());
                }
            }
        }
        for (int i = 0; i < hand.size(); i++)
        {
            assertEquals(hand.get(i).getLetter(), hand1.get(i).getLetter());
            assertEquals(hand.get(i).getValue(), hand1.get(i).getValue());
            assertEquals(hand.get(i).isBlank(), hand1.get(i).isBlank());
        }
    }

    @Test
    public void testEncodeAction()
    {
        String encodeTest = encodeAction(moveAction);
        assertEquals(encodeTest, encodedMove);
    }
    
    protected GameState decodeState (String str) 
    {
        Vector<ScrabbleTile> hand = new Vector<ScrabbleTile>();
        ScrabbleBoard theBoard = new ScrabbleBoard();
        int p0score;
        int p1score;
        int whoseMove;
        //Start at the first character of data
        int i = 1;
        while (str.charAt(i) != ']')
        {
            Boolean isFromBlank;
            int value;

            //Check to see if it came from a blank
            if (str.charAt(i) == '+')
                isFromBlank = false;
            else
                isFromBlank = true;
            //check to see if the value is 1 or 2 digits, then take the appropriate substring
            if (str.charAt(i+3) != '+' && str.charAt(i+3) != '-' && str.charAt(i+3) != ']')
            {
                value = Integer.parseInt(str.substring(i+2, i+4));
            }
            else
                value = Integer.parseInt(str.substring(i+2, i +3));
            if (str.charAt(i+1) == '*')
                hand.add(new ScrabbleBlankTile());
            else
                hand.add(new ScrabbleTile(str.charAt(i+1),value,isFromBlank));
            if (value > 9)
                i += 4;
            else
                i += 3;
        }
        i+=2;//go past end and beginning brackets. Start at raw data.

        //Create a copy of the board
        for (int q = 0; q < ScrabblePlayerUI.BOARD_SIZE; q++)
        {
            for (int k = 0; k < ScrabblePlayerUI.BOARD_SIZE; k++)
            {
                char start = str.charAt(i);
                //Skip blank tiles
                if (start == '|')
                {
                    i++;
                    continue;
                }
                Boolean isFromBlank;
                if (str.charAt(i) == '+')
                    isFromBlank = false;
                else
                    isFromBlank = true;
                int value;
                if (str.charAt(i+3) != '+' && str.charAt(i+3) != '-' && str.charAt(i+3)!= '|' && str.charAt(i+3) != ']')
                {
                    value = Integer.parseInt(str.substring(i+2, i+4));
                }
                else
                    value = Integer.parseInt(str.substring(i+2, i +3));
                ScrabbleTile theTile = new ScrabbleTile(str.charAt(i+1),value,isFromBlank);
                theBoard.putTileAt(q, k, theTile);
                if (value > 9)
                    i += 4;
                else
                    i += 3;
            }
        }

        i += 2;//Go past end and beginning brackets. Start at data

        int scoreLocation = i;
        while (str.charAt(scoreLocation) != ':')
        {
            scoreLocation++;
        }
        p0score = Integer.parseInt(str.substring(i,scoreLocation));
        scoreLocation++;
        i = scoreLocation;//Move past the delimiting colon
        while (str.charAt(scoreLocation) != ']')
        {
            scoreLocation++;
        }
        p1score = Integer.parseInt(str.substring(i, scoreLocation));
        i = scoreLocation+2; //move past end and beginning brackets
        whoseMove = Integer.parseInt(str.substring(i,i+1));

        return new ScrabbleGameState(theBoard,hand,whoseMove,p0score,p1score);
    }
    
    protected String encodeAction (GameAction ga) 
    {
        String str = "";
        //This doesn't work since I had to pluck this from ScrabbleProxyGame
       // int player = ga.getSource().getId();
        str += 0;
        str += "|"; //Delimit

        if (ga instanceof ScrabbleMoveAction)
        {
            Vector<ScrabbleTile> playedTiles = ((ScrabbleMoveAction)ga).getTiles();
            for (ScrabbleTile tile : playedTiles)
            {
                if (tile.isBlank())
                    str += "-";
                else
                    str += "+";
                str += tile.getLetter();
                str += tile.getValue();
            }
            str += "|";//delimit
            Vector<Point> positions = ((ScrabbleMoveAction) ga).getPositions();
            for (Point p : positions)
            {
                str += "(";
                str = str + p.x + "," + p.y + ")";
            }
            return str;
        }
        else if (ga instanceof ScrabbleDiscardAction)
        {
            Vector<ScrabbleTile> playedTiles = ((ScrabbleDiscardAction)ga).getTiles();
            for (ScrabbleTile tile : playedTiles)
            {
                if (tile.isBlank())
                    str += "-";
                else
                    str += "+";
                if (tile instanceof ScrabbleBlankTile)
                    str += "*";
                else
                    str += tile.getLetter();
                str += tile.getValue();
            }

            return str;

        }
        else if (ga instanceof ScrabbleResignAction)
        {
            return "!!!";
        }
        else
            return null;

    }
}
