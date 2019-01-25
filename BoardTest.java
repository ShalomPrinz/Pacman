package pacman;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import pacman.Board;

public class BoardTest {
	
	Board board;
	
	@Before
	public void setup(){
		board = new Board();
	}
	
	@Test
	public void testDimensions_Equal(){
		// arrange
		Board b = new Board();
		
		// action
		int Xdim = b.getDimensions('X'), Ydim = b.getDimensions('Y');
		
		// assert
		assertEquals(30, Xdim);
		assertEquals(30, Ydim);
	}
	
	@Test
	public void testDimensions_Different(){
		// arrange
		Board b = new Board(new String[]{"---", "---"});
		
		// action
		int Xdim = b.getDimensions('X'), Ydim = b.getDimensions('Y');
		
		// assert
		assertEquals(02, Xdim);
		assertEquals(03, Ydim);
	}
	
	@Test
	public void testPacman(){
		// arrange
		
		// action
		int pacmans = count(Board.Creature.PACMAN);
		
		// assert
		assertEquals(1, pacmans);
	}
	
	@Test
	public void testGhosts(){
		// arrange
		
		// action
		int Ghost1 = count(Board.Creature.GHOST_1);
		int Ghost2 = count(Board.Creature.GHOST_2);
		int Ghost3 = count(Board.Creature.GHOST_3);
		int Ghost4 = count(Board.Creature.GHOST_4);
		
		// assert
		assertEquals(1, Ghost1);
		assertEquals(1, Ghost2);
		assertEquals(1, Ghost3);
		assertEquals(1, Ghost4);
	}
	
	@Test
	public void testPoints(){
		// arrange
		
		// action
		int points = count(Board.Creature.POINT);
		
		// assert
		assertEquals(330, points);
	}
	
	@Test
	public void testNulls(){
		// arrange
		
		// action
		int nulls = count(Board.Creature.NULL);
		
		// assert
		assertEquals(148, nulls);
	}
	
	@Test
	public void testWalls(){
		// arrange
		
		// action
		int walls = count(Board.Creature.WALL);
		
		// assert
		assertEquals(417, walls);
	}
	
	private int count(Board.Creature c){
		int cNum = 0;
		for (int i = 0; i < board.get().length; i++){
			for (int j = 0; j < board.get()[0].length; j++){
				if (board.get()[i][j] == c)
					cNum ++;
			}
		}
		return cNum;
	}	
	
	@SuppressWarnings("deprecation")
	@Test
	public void setBoardWithSingleString(){
		// arrange
		Board.Creature[] expected = {Board.Creature.PACMAN, Board.Creature.WALL,
				Board.Creature.GHOST_1, Board.Creature.POINT, Board.Creature.NULL};
		
		// action
		this.board = new Board(new String[]{"PWG-."});
		
		// assert
		assertEquals(expected, this.board.get()[0]);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void setBoardWithArrayString(){
		// arrange
		Board.Creature[][] expected = {
			{Board.Creature.PACMAN, Board.Creature.WALL},
			{Board.Creature.POINT, Board.Creature.GHOST_1},
			{Board.Creature.WALL, Board.Creature.NULL} 
		};
		
		// action
		this.board = new Board(new String[] {"PW", "-G", "W."});
		
		// assert
		assertEquals(expected, this.board.get());
	}
	
}
