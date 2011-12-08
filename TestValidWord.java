package scrabble;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

public class TestValidWord {
	
	private ScrabbleBoard board;
	private ScrabbleGameImpl game;
	Vector<ScrabbleTile> tiles;
	Vector<ScrabbleTile> tiles2;
	Vector<ScrabbleTile> tiles3;
	Vector<ScrabbleTile> tiles4;
	Vector<ScrabbleTile> tiles5;
	Vector<ScrabbleTile> tiles6;
	Vector<Point> loc;
	Vector<Point> loc2;
	Vector<Point> loc3;
	Vector<Point> loc4;
	Vector<Point> loc5;
	Vector<Point> loc6;
	
	private ScrabbleTile a;
	private ScrabbleTile b;
	private ScrabbleTile g;
	private ScrabbleTile t;
	private ScrabbleTile r;
	private ScrabbleTile s;
	private ScrabbleTile d;
	private ScrabbleTile e;
	
	@Before
    public void setUp() {
		board = new ScrabbleBoard();
		game = new ScrabbleGameImpl();
		
		a = new ScrabbleTile('A', 1, false);
		b = new ScrabbleTile('B', 1, false);
		g = new ScrabbleTile('G', 1, false);
		t = new ScrabbleTile('T', 1, false);
		r = new ScrabbleTile('R', 1, false);
		s = new ScrabbleTile('S', 1, false);
		d = new ScrabbleTile('D', 1, false);
		e = new ScrabbleTile('E', 1, false);
		
		tiles = new Vector<ScrabbleTile>();
		tiles.add(t);
		tiles.add(r);
		tiles.add(e);
		tiles.add(a);
		tiles.add(d);
		
		loc = new Vector<Point>();
		loc.add(new Point(7,7));
		loc.add(new Point(7,8));
		loc.add(new Point(7,9));
		loc.add(new Point(7,10));
		loc.add(new Point(7,11));
		
		tiles2 = new Vector<ScrabbleTile>();
		tiles2.add(r);
		tiles2.add(a);
		tiles2.add(g);
		
		loc2 = new Vector<Point>();
		loc2.add(new Point(8,11));
		loc2.add(new Point(9,11));
		loc2.add(new Point(10,11));
		
		
		tiles3 = new Vector<ScrabbleTile>();
		tiles3.add(b);
		tiles3.add(a);
		
		loc3 = new Vector<Point>();
		loc3.add(new Point(10, 9));
		loc3.add(new Point(10,10));
		
		
		
		tiles4 = new Vector<ScrabbleTile>();
		tiles4.add(a);
		
		loc4 = new Vector<Point>();
		loc4.add(new Point(9,10));
		
		tiles5 = new Vector<ScrabbleTile>();
		tiles5.add(s);
		
		loc5 = new Vector<Point>();
		loc5.add(new Point(11, 10));
		
		
		tiles6 = new Vector<ScrabbleTile>();
		tiles6.add(t);
		tiles6.add(a);
		tiles6.add(r);
		
		loc6 = new Vector<Point>();
		loc6.add(new Point(0,0));
		loc6.add(new Point(0,1));
		loc6.add(new Point(0,2));
		

	}
	
	//@Test
	public void testCheckValidMove() {
		boolean check = game.checkValidMove(loc, tiles);
		boolean check2 = game.checkValidMove(loc2, tiles2);
		boolean check3 = game.checkValidMove(loc3, tiles3);
		boolean check4 = game.checkValidMove(loc4, tiles4);
		boolean check5 = game.checkValidMove(loc5, tiles5);
		boolean check6 = game.checkValidMove(loc6, tiles6);
		boolean expected = true;
		boolean expected6 = false;
		
		assertTrue(expected == check);
		assertTrue(expected == check2);
		assertTrue(expected == check3);
		assertTrue(expected == check4);
		assertTrue(expected == check5);
		assertTrue(expected6 == check6);
		
	}
	
	@Test
	public void testGameMoveAction()
	{	
		ScrabbleHumanPlayer plr = new ScrabbleHumanPlayer();
		ScrabbleMoveAction mov = new ScrabbleMoveAction(plr, tiles, loc);
		ScrabbleMoveAction mov2 = new ScrabbleMoveAction(plr, tiles2, loc2);
		ScrabbleMoveAction mov3 = new ScrabbleMoveAction(plr, tiles3, loc3);
		ScrabbleMoveAction mov4 = new ScrabbleMoveAction(plr, tiles4, loc4);
		ScrabbleMoveAction mov5 = new ScrabbleMoveAction(plr, tiles5, loc5);
		ScrabbleMoveAction mov6 = new ScrabbleMoveAction(plr, tiles6, loc6);
		Boolean check = game.makeMove(plr, mov);
		Boolean check2 = game.makeMove(plr, mov2);
		Boolean check3 = game.makeMove(plr, mov3);
		Boolean check4 = game.makeMove(plr, mov4);
		Boolean check5 = game.makeMove(plr, mov5);
		Boolean check6 = game.makeMove(plr, mov6);
		Boolean expected = true;
		Boolean expected6 = false;
		assertTrue(expected == check);
		assertTrue(expected == check2);
		assertTrue(expected == check3);
		assertTrue(expected == check4);
		assertTrue(expected == check5);
		assertTrue(expected == check6);
	}

}
