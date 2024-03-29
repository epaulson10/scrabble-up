package scrabble;

import game.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Collections;
import java.util.Random;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * Class representing a human Scrabble player.
 * 
 * @author Erik Paulson (Primary), Aaaron Dobbe, Steven Beyer, Andrew Meyer
 * @version 12-9-11
 */

public class ScrabbleHumanPlayer extends GameHumanPlayer implements ScrabblePlayer, MouseMotionListener
{
    private final static int GUI_HEIGHT = 720;
    private final static int GUI_WIDTH = 700;

    private String buttonNames[] = {"Quit", "Discard", "Pass", "Shuffle"};
    //A reference to the tile that is being dragged
    private ScrabbleTile moveTile;

    //Labels representing the score
    private JLabel p0score;
    private JLabel p1score;
    
    //JLabel describing whose turn it is
    private JLabel whoseTurn;

    // Variables for use in case this is a network client:
    // Copy of the last detected hand
    private Vector<ScrabbleTile> proxyHand;
    // Whether the hand has changed since we last updated proxyHand
    private boolean proxyHandChanged;

    // Player's hand
    //private Vector<ScrabbleTile> hand;

    //The UI that the game is drawn on
    private ScrabblePlayerUI ui;

    //A reference to the play/pass button
    private JButton playPassButton;

    /** Returns the initial height of the GUI.
     * @return the initial height of the GUI, in pixels 
     */
    protected int initialHeight () 
    {
        return GUI_HEIGHT;
    }

    /** Returns the initial width of the GUI.
     * @return the initial width of the GUI, in pixels 
     **/
    protected int initialWidth () 
    {
        return GUI_WIDTH;
    }

    /** 
     * Constructor 
     */
    public ScrabbleHumanPlayer () 
    {
        super();
    }

    /** Actions to be taken after the game is initialized */
    protected void setGameMore () 
    {
        JOptionPane.showMessageDialog(this, "Welcome to Scrabble! \n Standard rules apply, minus tile bonuses. " +
                "\n To discard, drag tiles so that they are not on the tile rack or board and click \"Discard\"." +
                "\n Enjoy!");
        
        //Proxy hand items below eliminate a race condition
        proxyHand = new Vector<ScrabbleTile>();
        ui.setModel((ScrabbleGame)this.game);
        if (!(game instanceof ScrabbleProxyGame))
            ScrabblePlayerUI.putInHand(this.getHand());
        proxyHandChanged = true;
    }

    /** Gets the default title of the game window.
     * @return the initial window title 
     */
    protected String defaultTitle () 
    {
        return "Scrabble";
    }

    /** Creates a GUI object for this player.
     * @return a new ScrabblePlayerUI (the board graphics)
     */ 
    protected ScrabblePlayerUI createUI () 
    {
        return new ScrabblePlayerUI(this,(ScrabbleGame) game);
    }

    /** Actions to be taken after we're notified of a state change. */
    public void stateChanged () 
    {
        super.stateChanged();
        ScrabbleGameState state = (ScrabbleGameState)game.getState(this, 0);
        //Update scores
        p0score.setText("Player 1: "+state.getScore(0));
        p1score.setText("Player 2: "+ state.getScore(1));
        
        //Update whoseTurn label
        if (state.whoseMove() == getId())
            whoseTurn.setText("It's your turn!");
        else
            whoseTurn.setText("It's your opponent's turn!");
        
        ui.updateState();
        ui.repaint();
        proxyHandChanged = true;
        ScrabblePlayerUI.putInHand(getHand());
        repaint();
    }

    /**
     * Get the player's hand
     * 
     * @return the player's current hand
     */
    public Vector<ScrabbleTile> getHand()
    {
        // Get tiles from game state
        ScrabbleGameState curState = (ScrabbleGameState)game.getState(this, 0);
        //The Proxy game elements here help eliminate a race condition
        if (game instanceof ScrabbleProxyGame && proxyHandChanged)
        {
            proxyHandChanged = false;
            proxyHand = curState.getHand();
            return proxyHand;
        }
        else if (!(game instanceof ScrabbleProxyGame))
        {
            return curState.getHand();
        }
        else return proxyHand;
    }

    //unimplemented
    public void mouseClicked (MouseEvent me) {}

    /**
     * When the mouse is dragged and there is a tile being pressed on
     * move the tile relative to the mouse. The mouse will be on the center of the tile.
     * @param me the Mouse
     */
    public void mouseDragged (MouseEvent me) 
    {
        //The conditions inside the if statement do not allow a player to drag the tile
        //outside of the board
        if (moveTile != null && me.getX() >= 0 && me.getX() < ScrabblePlayerUI.UI_SIZE 
                && me.getY() >= 0 && me.getY() < ScrabblePlayerUI.UI_SIZE +
                ScrabblePlayerUI.SPACE +ScrabblePlayerUI.TILE_SIZE)
        {
            moveTile.setLocation(me.getX()-ScrabblePlayerUI.TILE_SIZE/2, me.getY()-ScrabblePlayerUI.TILE_SIZE/2);
            ui.repaint();
        }

    }

    public void mouseMoved (MouseEvent me) {}

    /**
     * When the mouse is pressed, get a reference to the tile it was pressed on
     * @param me the MouseEvent being processed
     */
    public void mousePressed (MouseEvent me)
    {
        //If you press on a tile in your hand, get a reference to it
        if (moveTile == null)
            moveTile = ui.tileInHand(me.getPoint());
    }
    /**
     * When the mouse is released, snaps the tile moved to the grid
     * and nulls the reference
     * 
     * @param me the MouseEvent being processed
     */
    public void mouseReleased (MouseEvent me)
    {
        if (moveTile != null)
        {
            //make the gui look pretty
            ui.snapTileToGrid(moveTile);
            ui.snapToRack(getHand());
            
            //assign values to blank tiles
            if (moveTile instanceof ScrabbleBlankTile &&
                    ScrabblePlayerUI.onBoard(me.getX(), me.getY()))
                assignBlankValue();
            else if (moveTile.isBlank())
            {
                if (ScrabblePlayerUI.onBoard(me.getX(), me.getY()))
                {
                    assignBlankValue();
                }
                else
                {
                    //When returned from the board (i.e. wasn't played)
                    //convert tiles from blanks back into blanks
                    int x = moveTile.getLocation().x;
                    int y = moveTile.getLocation().y;
                    ScrabbleBlankTile temp = new ScrabbleBlankTile();
                    temp.setLocation(x, y);
                    this.getHand().remove(moveTile);
                    this.getHand().add(temp);
                }
            }
            ui.repaint();
            moveTile = null;
        }
        //Change the text of the play/pass button
        if (ui.tilesPlayed())
        {
            playPassButton.setText("Play");
        }
        else
            playPassButton.setText("Pass");
    }

    /**
     * Asks the user what a blank tile should be played as and changes the moveTile
     * to that letter.
     */
    private void assignBlankValue()
    { 
        String answer;
        do
        {
            answer = JOptionPane.showInputDialog("What letter should this tile be?");
            if (answer == null)
                return;
        }
        while (answer.length() > 1 || !Character.isLetter(answer.charAt(0)));

        int x = moveTile.getLocation().x;
        int y = moveTile.getLocation().y;
        ScrabbleTile temp = new ScrabbleTile(answer.charAt(0),0,true);
        temp.setLocation(x, y);
        this.getHand().remove(moveTile);
        this.getHand().add(temp);
    }

    /** Creates the graphical component of the application.
     * 
     * @return a JPanel with the game's GUI 
     */
    protected Component createApplComponent () 
    {
        p0score = new JLabel("Player 1: 0");
        p1score = new JLabel("Player 2: 0");
        whoseTurn = new JLabel("Welcome to Scrabble!");
        
        JButton resignButton = new JButton("Resign");
        JButton discardButton = new JButton("Discard");
        JButton passButton = new JButton("Pass");
        JButton shuffleButton = new JButton("Shuffle");

        resignButton.setSize(300, 100);
        resignButton.setMinimumSize(resignButton.getSize());
        resignButton.setMaximumSize(resignButton.getSize());
        resignButton.addActionListener(this);

        discardButton.setSize(300, 100);
        discardButton.setMinimumSize(discardButton.getSize());
        discardButton.setMaximumSize(discardButton.getSize());
        discardButton.addActionListener(this);

        passButton.setSize(300, 100);
        passButton.setMinimumSize(passButton.getSize());
        passButton.setMaximumSize(passButton.getSize());
        passButton.addActionListener(this);

        shuffleButton.setSize(300, 100);
        shuffleButton.setMinimumSize(shuffleButton.getSize());
        shuffleButton.setMaximumSize(shuffleButton.getSize());
        shuffleButton.addActionListener(this);

        playPassButton = passButton;
        Panel uiPanel = new Panel();

        ui = createUI();
        ui.addMouseListener(this);
        ui.addMouseMotionListener(this);
        uiPanel.setSize(ui.getSize());
        Box vBox = Box.createVerticalBox();
        uiPanel.add(vBox);
        Box hBoxTop = Box.createHorizontalBox();
        Box hBoxBot = Box.createHorizontalBox();
        Box hBoxScore = Box.createHorizontalBox();
        vBox.add(hBoxTop);
        vBox.add(Box.createVerticalGlue());
        vBox.add(hBoxBot);
        vBox.add(Box.createVerticalGlue());
        vBox.add(hBoxScore);

        //Add UI buttons
        hBoxBot.add(Box.createHorizontalGlue());
        hBoxBot.add(resignButton);
        hBoxBot.add(Box.createHorizontalGlue());
        hBoxBot.add(discardButton);
        hBoxBot.add(Box.createHorizontalGlue());
        hBoxBot.add(passButton);
        hBoxBot.add(Box.createHorizontalGlue());
        hBoxBot.add(shuffleButton);
        hBoxBot.add(Box.createHorizontalGlue());
        hBoxScore.add(p0score);
        hBoxScore.add(Box.createHorizontalGlue());
        hBoxScore.add(whoseTurn);
        hBoxScore.add(Box.createHorizontalGlue());
        hBoxScore.add(p1score);

        hBoxTop.add(ui);
        uiPanel.setVisible(true);

        return uiPanel;
    }

    /**
     * 
     * This handles button press events.
     * 
     * @param ae The ActionEvent being passed in
     */
    protected void moreActionPerformed(ActionEvent ae)
    {
        String cmd = ae.getActionCommand();
        
        //submit a move
        if (cmd.equals("Play"))
        {
            //Get tiles played on board
            Vector<ScrabbleTile> tiles = ui.tilesToPlay();
            
            Vector<Point> positions = new Vector<Point>();
            //Find the locations of played tiles
            for (ScrabbleTile tile : tiles)
            {
                Point p = tile.getLocation();
                // CHANGED: use new point instead of pointer to previous one
                Point newPoint = new Point((p.x)/ScrabblePlayerUI.TILE_SIZE, (p.y)/ScrabblePlayerUI.TILE_SIZE);
                positions.add(newPoint);

            }
            game.applyAction(new ScrabbleMoveAction(this,tiles, positions));
            playPassButton.setText("Pass");
            ui.repaint();
        }
        //Discard some tiles
        else if (cmd.equals("Discard"))
        {
            game.applyAction(new ScrabbleDiscardAction(this,ui.tilesToDiscard()));
            ScrabblePlayerUI.putInHand(this.getHand());
            ui.repaint();

        }
        //Give up play to the other player
        else if (cmd.equals("Pass"))
        {
            Vector<ScrabbleTile> blankHand = new Vector<ScrabbleTile>();
            game.applyAction(new ScrabbleDiscardAction(this, blankHand));
            ui.repaint();
        }
        //Give up
        else if (cmd.equals("Resign"))
        {
            game.applyAction(new ScrabbleResignAction(this));
            ui.repaint();
        }
        //Shufle the hand
        else if (cmd.equals("Shuffle"))
        {
            Vector<ScrabbleTile> myHand = getHand();
            Collections.shuffle(myHand);
            ScrabblePlayerUI.putInHand(myHand);
            playPassButton.setText("Pass");
            ui.repaint();
        }
    }



}