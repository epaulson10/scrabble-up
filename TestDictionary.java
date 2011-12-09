package scrabble;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

/**
 * JUnit Test that tests the dictionary.
 * Makes sure that the isValidWord() method works for
 * valid and invalid moves and makes sure the wordList
 * is created correctly.
 */
public class TestDictionary {

	String[] words = new String[10];
	private Dictionary test;
	int numWords = 113809;
	
	/**
	 * Sets up variables
	 */
	@Before
	public void setUp() {
		// longest word
		words[0] = "conceptualizing";
		// null string
		words[1] = null;
		// invalid characters
		words[2] = "ba11";
		// empty string
		words[3] = "";
		// shortest word
		words[4] = "aa";
		// word is too short
		words[5] = "a";
		// words is invalid
		words[6] = "korn";
		// valid word
		words[7] = "aal";
		// valid word
		words[8] = "dog";
		// valid word
		words[9] = "ball";
	}
	
	/**
	 * tests isValidWord for completeness
	 */
	@Test
	public void testIsValidWord() {
		try {
			test = new Dictionary();
		} catch (IOException e) {
			System.err.println("File not present");
		}
		// test that the hashSet actually got all of the words
		assertEquals(test.wordList.size(), numWords);
		
		assertEquals(test.isValidWord(words[0]), true);
		assertEquals(!test.isValidWord(words[1]), true);
		assertEquals(!test.isValidWord(words[2]), true);
		assertEquals(!test.isValidWord(words[3]), true);
		assertEquals(test.isValidWord(words[4]), true);
		assertEquals(!test.isValidWord(words[5]), true);
		assertEquals(!test.isValidWord(words[6]), true);
		assertEquals(test.isValidWord(words[7]), true);
		assertEquals(test.isValidWord(words[8]), true);
		assertEquals(test.isValidWord(words[9]), true);
	}

}
