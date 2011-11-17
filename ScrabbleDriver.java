package scrabble;

import game.*;

/** Main driver class that sets up a game of Scrabble.
 * 
 * @author Aaron Dobbe, Steven Beyer, Erik Paulson, and Andrew Meyer
 * @version 11/12/2011
 */
class ScrabbleDriver implements GameDriver
{
	private String[] names = {"human", "AI player (easy)", "AI player (hard)"};

	public static void main(String[] args)
	{
	    // begin game
        DriverEngine.play(args, new ScrabbleDriver());
	}

	/**
	 * Creates a new game of Scrabble
     *
     * @param numPlayers the number of players (will be ignored, game always
     *        has two players).
     */
	public Game createGame(int numPlayers) {
		return new ScrabbleGameImpl();
	}

	/**
	 * Creates a new player.
     *
     * @param name A String representing the requested type of player
     * @return a new GamePlayer of the requested type, or null if player type
     *         was not found.
     */
	public GamePlayer createLocalPlayer (String name)
	{
		if (name.equals(names[0])) // Human player
		{
		    return new ScrabbleHumanPlayer();
		}
		else if (name.equals(names[1])) // AI (easy)
		{
		    return new ScrabbleComputerPlayerEasy();
		}
		else if (name.equals(names[2])) // AI (hard)
        {
            return new ScrabbleComputerPlayerHard();
        }
		else
		{
		    // Bad selection
		    return null;
		}
	}

	/**
	 * Creates a new proxy game.
     *
     * @param hostName the IP address of the host machine
     * @return a new ProxyGame
     */
	public ProxyGame createRemoteGame (String hostName)
	{
		return new ScrabbleProxyGame(hostName);
	}

	/**
     * Creates a new proxy player.
     *
     * @return a new ProxyPlayer
     */
	public ProxyPlayer createRemotePlayer ()
	{
		return new ScrabbleProxyPlayer();
	}

	/**
	 * Returns possible player type choices.
     */
	public String[] localPlayerChoices ()
	{
		return names;
	}

}

