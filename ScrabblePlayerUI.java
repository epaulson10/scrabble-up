package scrabble;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Vector;

/** A canvas that draws a Scrabble Game State
 * @author Aaron Dobbe, Steven Beyer, Erik Paulson, and Andrew Meyer
 * @version Yet to be implemented
 * 
 * NOTE: Find x,y values of where you clicked and divide by 15 = position
 */
public class ScrabblePlayerUI extends JPanel
{
    public final static int BOARD_SIZE = 15;
    public final static int TILE_SIZE = 35;
    public final static int UI_SIZE = BOARD_SIZE*TILE_SIZE+1;
    
    //The amount of space between the board and the hand.
    public final static int SPACE = 20;
    
    private ScrabbleGame model;
    private ScrabbleHumanPlayer player;
    private ScrabbleGameState state;
    private ScrabbleBoard board;


    /** constructor
     * 
     * @param initPlayer The human player we are representing 
     */
    public ScrabblePlayerUI (ScrabbleHumanPlayer initPlayer, ScrabbleGame game) 
    {
        //Tell the parent constructor to double buffer this
        super(true);
        player = initPlayer;
        this.setSize(UI_SIZE, UI_SIZE+TILE_SIZE+SPACE+1);
        this.setPreferredSize(this.getSize());
        this.setMinimumSize(this.getSize());
        this.setBackground(Color.lightGray);
        board = new ScrabbleBoard();
    }

    /**  * Updates the stored game state. */
    protected void updateState ()
    {
        state = (ScrabbleGameState)model.getState(player, 0);
        board = state.getBoard();
    }
    
    /**  * Set the canvas to model the given game.
     *
     * @param game The ScrabbleGame this canvas to model.
     */
    public void setModel (ScrabbleGame game) 
    {
        model = game;
    }
    /** 
     * Paints the game state
     * @param g The graphics object to be used 
     */
    public void paint (Graphics g) 
    {
        //Clear the board
        g.setColor(Color.white);
        g.fillRect(0, 0, UI_SIZE, UI_SIZE+SPACE+TILE_SIZE);
        
        g.setColor(Color.black);
        //Draw the vertical lines of the grid
        for (int i = 0; i <= BOARD_SIZE; i++)
        {
            g.drawLine(TILE_SIZE*i, 0, TILE_SIZE*i, UI_SIZE);
        }
        //Draw the horizontal lines of the grid
        for (int i = 0; i <= BOARD_SIZE; i++)
        {
            g.drawLine(0, TILE_SIZE*i, UI_SIZE, TILE_SIZE*i);
        }
        
        //Draw Hand rack
        g.drawLine(4*TILE_SIZE, TILE_SIZE*15+SPACE, TILE_SIZE*11, TILE_SIZE*15+SPACE);
        g.drawLine(4*TILE_SIZE, TILE_SIZE*16+SPACE, TILE_SIZE*11, TILE_SIZE*16+SPACE);
        for (int i = 4; i <= 11; i++)
        {
            g.drawLine(TILE_SIZE*i, TILE_SIZE*15+SPACE, TILE_SIZE*i, TILE_SIZE*16+SPACE);
        }
        g.fillOval((BOARD_SIZE/2)*TILE_SIZE + TILE_SIZE/4,(BOARD_SIZE/2)*TILE_SIZE + TILE_SIZE/4, TILE_SIZE/2, TILE_SIZE/2);
        
        drawBoard(g,board);
        drawHand(g,player.getHand());
        g.dispose();
    }
    
    /**
     * Draws the tiles on the board from the given ScrabbleBoard
     * @param g Graphics context that's being drawn to
     * @param board The scrabble board for a given player
     */
    public void drawBoard(Graphics g, ScrabbleBoard board)
    {
        for (int i = 0; i < BOARD_SIZE; i++)
        {
            for (int k = 0; k < BOARD_SIZE; k++)
            {
                ScrabbleTile temp = board.getTileAt(i,k);
                if (temp != null)
                {
                    g.drawImage(temp.getPicture(), k*TILE_SIZE, i*TILE_SIZE, TILE_SIZE, TILE_SIZE, null,null);
                }
            }
        }
    }
    
    /**
     * Gives a vector of tiles from the hand which are on the board
     * @return a vector of tiles from the player's hand which are now on the board
     */
    public Vector<ScrabbleTile> tilesToPlay()
    {
        Vector<ScrabbleTile> playedTiles = new Vector<ScrabbleTile>();
        for (ScrabbleTile tile : player.getHand())
        {
            Point p = tile.getLocation();
            if (p.x >= 0 && p.x < UI_SIZE && p.y >= 0 && p.y < UI_SIZE)
            {
                playedTiles.add(tile);
            }
        }
        return playedTiles;
    }
    
    /**
     * Draws the tiles in the players "hand"
     * 
     * @param g graphics object which we are drawing on
     * @param hand The vector containing the player's hand
     */
    public void drawHand(Graphics g, Vector<ScrabbleTile> hand)
    {
        ScrabbleTile tile = null;
        for (int i = 0; i < player.getHand().size(); i++)
        {
            tile = player.getHand().elementAt(i);
            g.drawImage(tile.getPicture(),tile.getLocation().x,tile.getLocation().y,TILE_SIZE, TILE_SIZE,null,null);
        }
    }
    
    /**
     * Assigns each tile in the hand a x,y coordinate
     * @param hand The player's hand
     */
    public static void putInHand(Vector<ScrabbleTile> hand)
    {
        int count = 4;
        for (ScrabbleTile tile : hand)
        {
            tile.setLocation(TILE_SIZE*count,TILE_SIZE*15+SPACE);
            count++;
        }
        
    }
    
    /**
     * Snaps a tile from the rack to its position on the rack
     * 
     * @param hand the players hand
     */
    public void snapToRack(Vector<ScrabbleTile> hand)
    {
        int count = 4;
        int rackLoc;
        for (ScrabbleTile tile : hand)
        {
            Point loc = tile.getLocation();
            rackLoc = TILE_SIZE*count;
            if (loc.x +TILE_SIZE/2 >= rackLoc && loc.x+TILE_SIZE/2 < rackLoc+TILE_SIZE &&
                    loc.y+TILE_SIZE/2 >= TILE_SIZE*15+SPACE && loc.y+TILE_SIZE/2 < TILE_SIZE*16+SPACE)
                tile.setLocation(rackLoc,TILE_SIZE*15+SPACE);
            count++;
            
        }
    }


    
    /**
     * Returns the ScrabbleTile that was clicked on
     * 
     * @param p the Point where the user clicked
     * @return the tile on the point or null if there is none
     */
    public ScrabbleTile tileOnPosition(Point p)
    {
        for (int i = 0; i < BOARD_SIZE; i++)
        {
            for (int k = 0; k < BOARD_SIZE; k++)
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
    
    /**
     * Determines if a point is on a tile that is in the hand
     * @param p the point being processed
     * @return the tile that the point was within
     */
    public ScrabbleTile tileInHand(Point p)
    {
        for (ScrabbleTile tile : player.getHand())
        {
            if (tile.pointInside(p.x, p.y))
                return tile;
        }
        return null;
    }
    /**
     * Determines if a player has placed tiles on the board
     * 
     * @return true if the player has played 1 or more tiles, false otherwise
     */
    public boolean tilesPlayed()
    {
        for (ScrabbleTile tile : player.getHand())
        {
            Point loc = tile.getLocation();
            if (loc.x >= 0 && loc.x < UI_SIZE && loc.y >= 0 && loc.y < UI_SIZE)
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Determines if tiles should be discarded
     * 
     * @return A vector containing the tiles to be discarded
     */
    public Vector<ScrabbleTile> tilesToDiscard()
    {
        Vector<ScrabbleTile> toDiscard = new Vector<ScrabbleTile>();
        for (ScrabbleTile tile : player.getHand())
        {
            Point loc = tile.getLocation();
            if ((loc.x >= 0 && loc.x < UI_SIZE && loc.y >= 0 && loc.y < UI_SIZE))
            {
                //Do Nothing, the tile is on the board
            }
            else if (loc.x >= TILE_SIZE*4 && loc.x < TILE_SIZE * 11 && loc.y >= TILE_SIZE*15+SPACE && loc.y <= TILE_SIZE*16+SPACE)
            {
                //Do nothing, the tile is in the player's hand
            }
            else
                toDiscard.add(tile); 
        }
        
        return toDiscard;
        
    }
    
    /**
     * "Snaps" the tile image to the board grid when released.
     * 
     * @param st the ScrabbleTile being moved
     */
    public void snapTileToGrid(ScrabbleTile st)
    {
        Point location = st.getLocation();
        int x = location.x+TILE_SIZE/2;
        int y = location.y+TILE_SIZE/2;
        
        for (int row = 0; row < BOARD_SIZE; row++)
        {
            for (int col = 0; col < BOARD_SIZE; col++)
            {
                if (x >= row*TILE_SIZE && x < row*TILE_SIZE + TILE_SIZE &&
                        y >= col*TILE_SIZE && y < col*TILE_SIZE + TILE_SIZE)
                {
                    st.setLocation(row*TILE_SIZE, col*TILE_SIZE);
                }
            }
        }

    }

}

