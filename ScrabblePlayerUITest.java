package scrabble;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

public class ScrabblePlayerUITest
{
    ScrabbleHumanPlayer thePlayer;
    ScrabbleGameImpl theGame;
    ScrabblePlayerUI ui;
    ScrabbleBoard board;

    @Before
    public void setUp() throws Exception
    {
        thePlayer = new ScrabbleHumanPlayer();
        theGame = new ScrabbleGameImpl();
        thePlayer.setGame(theGame, 0);
        ui = thePlayer.createUI();
        board = new ScrabbleBoard();
        
        Vector<ScrabbleTile> tiles = new Vector<ScrabbleTile>();
        for (int i = 0; i < 7; i++)
            tiles.add(new ScrabbleTile('a',1,false));
        
        ScrabblePlayerUI.putInHand(tiles);
        for (int i = 0; i < 5; i++)
            board.putTileAt(i, 0, tiles.get(i));
    }

    @Test
    public void testTilesToPlay()
    {
        ScrabbleGameState theState = (ScrabbleGameState)theGame.getState(thePlayer, 0);
        Vector<ScrabbleTile> playerHand = theState.getHand();
        for (int i = 0; i < 7; i++)
        {
            playerHand.get(i).setLocation(i*ScrabblePlayerUI.TILE_SIZE, 0);
        }
        Vector<ScrabbleTile> playedTiles = ui.tilesToPlay();
        assertTrue(playedTiles.equals(playerHand));
        
      
    }

    @Test
    public void testPutInHand()
    {
        Vector<ScrabbleTile> tiles = new Vector<ScrabbleTile>();
        Vector<ScrabbleTile> compTiles = new Vector<ScrabbleTile>();
        for (int i = 0; i < 7; i++)
        {
            compTiles.add(new ScrabbleTile('a',1,false));
        }
        for (int i = 0; i < 7; i++)
        {
            compTiles.get(i).setLocation((i+4)*(ScrabblePlayerUI.TILE_SIZE),
                    ScrabblePlayerUI.SPACE+ScrabblePlayerUI.TILE_SIZE*15);
        }
        
        for (int i = 0; i < 7; i++)
            tiles.add(new ScrabbleTile('a',1,false));
        ScrabblePlayerUI.putInHand(tiles);
        
        for (int i = 0; i < 7; i++)
        {
            assertEquals(compTiles.get(i).getLocation().x, tiles.get(i).getLocation().x);
            assertEquals(compTiles.get(i).getLocation().y, tiles.get(i).getLocation().y);
        }
        
    }

    @Test
    public void testTileOnPosition()
    {
        ScrabbleTile testTile = tileOnPosition(new Point(20,12));
        assertTrue(testTile.getLetter() == 'A');
        assertTrue(testTile.getValue() == 1);
        assertTrue(testTile.isBlank() == false);
     
    }

    @Test
    public void testTileInHand()
    {
        ScrabblePlayerUI.putInHand(thePlayer.getHand());
        assertTrue(ui.tileInHand(new Point(ScrabblePlayerUI.TILE_SIZE*4,
                ScrabblePlayerUI.TILE_SIZE*ScrabblePlayerUI.BOARD_SIZE + 
                ScrabblePlayerUI.SPACE)) == thePlayer.getHand().get(0));
    }

    @Test
    public void testTilesPlayed()
    {
        //Set location of 1 tile to the board;
        thePlayer.getHand().get(1).setLocation(200,200);
        
        assertTrue(ui.tilesPlayed());
    }

    @Test
    public void testTilesToDiscard()
    {
        //Set location to a discard location
       thePlayer.getHand().get(0).setLocation(10, ScrabblePlayerUI.UI_SIZE+20);
        
        Vector<ScrabbleTile> discards = ui.tilesToDiscard();
        assertEquals(discards.get(0), thePlayer.getHand().get(0));
    }

    @Test
    public void testOnBoard()
    {
        assertTrue(ScrabblePlayerUI.onBoard(30,400));
        assertFalse(ScrabblePlayerUI.onBoard(-1, 200));
        
    }

    public ScrabbleTile tileOnPosition(Point p)
    {
        for (int i = 0; i < ScrabblePlayerUI.BOARD_SIZE; i++)
        {
            for (int k = 0; k < ScrabblePlayerUI.BOARD_SIZE; k++)
            {
                if (board.getTileAt(i, k) != null)
                {
                    if (board.getTileAt(i,k).pointInside(p.x, p.y))
                    {
                        return board.getTileAt(i,k);
                    }
                }
            }
        }
        return null;
    }
}
