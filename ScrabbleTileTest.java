package scrabble;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

public class ScrabbleTileTest {

	ScrabbleTile a;
	ScrabbleTile b;
	ScrabbleTile c;

	@Test
	public void testGetLetter() {
		// valid letter
		a = new ScrabbleTile('a', 1, false);
		assertTrue("Valid letter 'a'", a.getLetter() == 'A');
		
		// blank letter
		b = new ScrabbleTile(' ', 1, false);
		assertTrue("blank letter", b.getLetter() == ' ');
		
		// weird char
		c = new ScrabbleTile('%', 1, false);
		assertTrue("weird char", c.getLetter() == '%');
	}

	@Test
	public void testGetValue() {
		// valid value
		a = new ScrabbleTile('a', 5, false);
		assertTrue("valid value", a.getValue() == 5);
		
		// test 0
		b = new ScrabbleTile('b', 0, false);
		assertTrue("0", b.getValue() == 0);
		
		// test negative
		c = new ScrabbleTile('c', -5, false);
		assertTrue("negative", c.getValue() == 0);
	}

	@Test
	public void testIsBlank() {
		// blank
		a = new ScrabbleTile(' ', 0, true);
		assertTrue("blank", a.isBlank());
		
		// not blank
		b = new ScrabbleTile('b', 1, false);
		assertTrue("not blank", !b.isBlank());
	}

	@Test
	public void testGetFileName() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetLocationGetLocationPointInside() {
		// test 0, 0 (edge)
		a = new ScrabbleTile('a', 1, false);
		a.setLocation(0, 0);
		assertTrue("location 0, 0", a.getLocation().x == 0 && a.getLocation().y ==0);
		assertTrue("location 0, 0", a.pointInside(0, 0));
		
		// test invalid case of point inside
		b = new ScrabbleTile('b', 1, false);
		b.setLocation(5, 5);
		assertTrue("location 5, 5", b.getLocation().x == 5 && b.getLocation().y ==5);
		assertTrue("location 0, 0", !b.pointInside(0, 0));
	}

	@Test
	public void testGetPicture() {
		fail("Not yet implemented");
	}

	@Test
	public void testToString() {
//		// test blank
//		a = new ScrabbleTile(' ', 1, true);
//		String test = a.toString();
//		assertTrue("test blank", test.equals(null));
		
		// test valid letter
		b = new ScrabbleTile('b', 1, true);
		String test2 = b.toString();
		assertTrue("test b", test2.equals("B"));
	}

}
