package pacman;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import pacman.Creature.Type;

public class BoardTest {
	
	Board board;
	
	@Before
	public void setup(){
		board = new Board();
	}
	
	@Test
	public void testDimensions_Equal(){
		// arrange
		
		// action
		int Xdim = new Board().getDimensions('X');
		int Ydim = new Board().getDimensions('Y');

		// assert
		assertEquals(30, Xdim);
		assertEquals(30, Ydim);
	}
	
	@Test
	public void testDimensions_Different(){
		// arrange
		
		// action
		int Xdim = new Board( new String[] {"---", "---"} ).getDimensions('X');
		int Ydim = new Board( new String[] {"---", "---"} ).getDimensions('Y');
		
		// assert
		assertEquals(2, Xdim);
		assertEquals(3, Ydim);
	}
	
	@Test
	public void testPacman(){
		// arrange
		
		// action
		int pacmans = count( Type.PACMAN );
		
		// assert
		assertEquals(1, pacmans);
	}
	
	@Test
	public void testGhosts(){
		// arrange
		
		// action
		int ghosts = count( Type.GHOST );
		
		// assert
		assertEquals(4, ghosts);
	}
	
	@Test
	public void testPoints(){
		// arrange
		
		// action
		int points = count( Type.POINT );
		
		// assert
		assertEquals(330, points);
	}
	
	@Test
	public void testNulls(){
		// arrange
		
		// action
		int nulls = count( Type.NULL );
		
		// assert
		assertEquals(148, nulls);
	}
	
	@Test
	public void testWalls(){
		// arrange
		
		// action
		int walls = count( Type.WALL );
		
		// assert
		assertEquals(417, walls);
	}
	
	private int count( Type t){
		int cNum = 0;
		for ( int i = 0; i < board.get().length; i++ ) {
			for ( int j = 0; j < board.get()[i].length; j++ ) {
				if ( board.get( new Location(i, j) ).getType() == t )
					cNum ++;
			}
		}
		return cNum;
	}	
	
	@Test
	public void setBoardWithSingleString(){
		// arrange
		Type[] expected = {Type.PACMAN, Type.WALL,
				Type.GHOST, Type.POINT, Type.NULL};
		
		// action
		this.board = new Board( new String[]{"PWG-."} );
		
		// assert
		for (int i = 0; i < this.board.get()[0].length; i++)
			assertEquals( expected[i], this.board.get( new Location(0, i) ).getType() );
	}

	@Test
	public void setBoardWithArrayString(){
		// arrange
		Type[][] expected = {
			{Type.PACMAN, Type.WALL},
			{Type.POINT, Type.GHOST},
			{Type.WALL, Type.NULL} 
		};
		
		// action
		this.board = new Board( new String[] {"PW", "-G", "W."} );
		
		// assert
		for ( int i = 0; i < this.board.get().length; i++ ) {
			for ( int j = 0; j < this.board.get()[i].length; j++ ) 
				assertEquals( expected[i][j], this.board.get( new Location(i, j) ).getType() );
		}
	}
	
}
