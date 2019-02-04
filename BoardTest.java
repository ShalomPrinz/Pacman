package pacman;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BoardTest {
	
	Board.Creature[][] board;
	
	@Before
	public void setup(){
		board = new Board().get();
	}
	
	@Test
	public void testDimensions_Equal(){
		// arrange
		
		// action
		int dimensions = new Board().getDimensions();
		
		// assert
		assertEquals(30, dimensions);
	}
	
	@Test
	public void testDimensions_Different(){
		// arrange
		
		// action
		int dimensions = new Board(new String[]{"---", "---"}).getDimensions();
		
		// assert
		assertEquals(23, dimensions);
	}
	
	@Test
	public void testPacman(){
		// arrange
		
		// action
		int pacmans = count(Board.MovingCreature.PACMAN);
		
		// assert
		assertEquals(1, pacmans);
	}
	
	@Test
	public void testGhosts(){
		// arrange
		
		// action
		int Ghost1 = count(Board.MovingCreature.GHOST_1);
		int Ghost2 = count(Board.MovingCreature.GHOST_2);
		int Ghost3 = count(Board.MovingCreature.GHOST_3);
		int Ghost4 = count(Board.MovingCreature.GHOST_4);
		
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
		for (int i = 0; i < board.length; i++){
			for (int j = 0; j < board[0].length; j++){
				if (board[i][j] == c)
					cNum ++;
			}
		}
		return cNum;
	}	
	
	@SuppressWarnings("deprecation")
	@Test
	public void setBoardWithSingleString(){
		// arrange
		Board.Creature[] expected = {Board.MovingCreature.PACMAN, Board.Creature.WALL,
				Board.MovingCreature.GHOST_1, Board.Creature.POINT, Board.Creature.NULL};
		
		// action
		this.board = new Board(new String[]{"PWG-."}).get();
		
		// assert
		assertEquals(expected, this.board[0]);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void setBoardWithArrayString(){
		// arrange
		Board.Creature[][] expected = {
			{Board.MovingCreature.PACMAN, Board.Creature.WALL},
			{Board.Creature.POINT, Board.MovingCreature.GHOST_1},
			{Board.Creature.WALL, Board.Creature.NULL} 
		};
		
		// action
		this.board = new Board(new String[] {"PW", "-G", "W."}).get();
		
		// assert
		assertEquals(expected, this.board);
	}
	
}
