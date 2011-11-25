package scrabble;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

/** A canvas that draws a Scrabble Game State
 * @author Aaron Dobbe, Steven Beyer, Erik Paulson, and Andrew Meyer
 * @version Yet to be implemented
 * 
 * 
 */
public class ScrabblePlayerUI extends JPanel 
{
    public final static int BOARD_SIZE = 15;
    public final static int TILE_SIZE = 35;
    public final static int UI_SIZE = BOARD_SIZE*TILE_SIZE+1;
    
    //The amount of space between the board and the hand.
    private final static int SPACE = 20;
    
    private ScrabbleGame model;
    private ScrabbleHumanPlayer player;
    private ScrabbleGameState state;
    private ScrabbleTile test;
    private ScrabbleBoard board;
    

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
        
        drawBoard(g,board);
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
                    g.drawImage(temp.getPicture(), temp.getLocation().x, temp.getLocation().y, TILE_SIZE, TILE_SIZE, null,null);
                }
            }
        }
    }
    
    /**
     * Draws the tiles in the players "hand"
     * 
     * @param g graphics object which we are drawing on
     * @param hand The vector containing the player's hand
     */
    public void drawHand(Graphics g, Vector<ScrabbleTile> hand)
    {
        int count = 4;
        for (ScrabbleTile tile : hand)
        {
            g.drawImage(tile.getPicture(),TILE_SIZE*count,TILE_SIZE*15+SPACE,TILE_SIZE, TILE_SIZE,null,null);
            count++;
        }
    }

    /** constructor
     * 
     * @param initPlayer The human player we are representing 
     */
    public ScrabblePlayerUI (ScrabbleHumanPlayer initPlayer) 
    {
        player = initPlayer;
        this.setSize(UI_SIZE, UI_SIZE+TILE_SIZE+SPACE+1);
        this.setPreferredSize(this.getSize());
        this.setMinimumSize(this.getSize());
        this.setBackground(Color.lightGray);
        board = new ScrabbleBoard();
        test = new ScrabbleTile('A',1,false);
        board.putTileAt(0, 0, test);
    }

    /**  * Updates the stored game state. */
    protected void updateState ()
    {
        state = (ScrabbleGameState)model.getState(player, 0);
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
     * "Snaps" the tile image to the board grid when released.
     * This works by using integer division by 15 to truncate the decimal
     * and then multiplying by 15 to restore the location.
     * 
     * NOTE: Find x,y values of where you clicked and divide by 15 = position
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

