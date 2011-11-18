package scrabble;

import javax.swing.*;
import java.awt.*;

/** A canvas that draws a Scrabble Game State
 * @author Aaron Dobbe, Steven Beyer, Erik Paulson, and Andrew Meyer
 * @version Yet to be implemented
 */
public class ScrabblePlayerUI extends JPanel 
{
    private final static int BOARD_SIZE = 15;
    private final static int TILE_SIZE = 35;
    private final static int UI_SIZE = BOARD_SIZE*TILE_SIZE+1;
    private ScrabbleGame model;
    private ScrabbleHumanPlayer player;
    private ScrabbleGameState state;

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
    }

    /** constructor
     * 
     * @param initPlayer The human player we are representing 
     */
    public ScrabblePlayerUI (ScrabbleHumanPlayer initPlayer) 
    {
        player = initPlayer;
        this.setSize(UI_SIZE, UI_SIZE);
        this.setPreferredSize(this.getSize());
        this.setMinimumSize(this.getSize());
        this.setBackground(Color.lightGray);
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

