package scrabble;

import javax.swing.*;
import java.awt.*;

/** A canvas that draws a Scrabble Game State
 * @author Aaron Dobbe, Steven Beyer, Erik Paulson, and Andrew Meyer
 * @version Yet to be implemented
 */
public class ScrabblePlayerUI extends JPanel 
{
    private ScrabbleGame model;
    private ScrabbleHumanPlayer player;
    private ScrabbleGameState state;

    /** 
     * Paints the game state
     * @param g The graphics object to be used 
     */
    public void paint (Graphics g) 
    {
        g.fillRect(0,0, this.getSize().width, this.getSize().height);
    }

    /** constructor
     * 
     * @param initPlayer The human player we are representing 
     */
    public ScrabblePlayerUI (ScrabbleHumanPlayer initPlayer) 
    {
        player = initPlayer;
        this.setSize(player.initialWidth(), player.initialHeight()-100);
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

