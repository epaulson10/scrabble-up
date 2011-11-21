package scrabble;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

/** A canvas that draws a Scrabble Game State
 * @author Aaron Dobbe, Steven Beyer, Erik Paulson, and Andrew Meyer
 * @version Yet to be implemented
 * 
 * NOTE: Find x,y values of where you clicked and divide by 15 = position.
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

    /** 
     * Paints the game state
     * @param g The graphics object to be used 
     */
    public void paint (Graphics g) 
    {
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
        
        g.drawImage(test.getPicture(), 0,0,TILE_SIZE, TILE_SIZE,null,null);
        drawHand(g, player.getHand());
    }
    
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
        test = new ScrabbleTile('A',1,false);
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

}

