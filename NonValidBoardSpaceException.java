package scrabble;

@SuppressWarnings("serial")
public class NonValidBoardSpaceException extends Exception 
{
	String msg;
	
	NonValidBoardSpaceException(String s)
	{
		msg = s;
	}
	
	public String toString()
	{
		return msg;
	}
}
