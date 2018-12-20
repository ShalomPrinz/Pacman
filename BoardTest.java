import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BoardTest {
	
	Board mBoard;
	Board.Creatures[][] board;
	
	@Before
	public void setup(){
		mBoard = new Board();
		board = mBoard.getBoard();
	}
	
	@Test
	public void testDimensions(){
		// arrange
		
		// action
		int dimensions = mBoard.getBoardDimensions();
		
		// assert
		assertEquals(3030, dimensions);
	}
	
	@Test
	public void testPacman(){
		// arrange
		
		// action
		int pacmans = count(Board.Creatures.Pacman);
		
		// assert
		assertEquals(1, pacmans);
	}
	
	@Test
	public void testGhosts(){
		// arrange
		
		// action
		int Ghost1 = count(Board.Creatures.Ghost1);
		int Ghost2 = count(Board.Creatures.Ghost2);
		int Ghost3 = count(Board.Creatures.Ghost3);
		int Ghost4 = count(Board.Creatures.Ghost4);
		
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
		int points = count(Board.Creatures.Point);
		
		// assert
		assertEquals(330, points);
	}
	
	@Test
	public void testNulls(){
		// arrange
		
		// action
		int nulls = count(Board.Creatures.Null);
		
		// assert
		assertEquals(148, nulls);
	}
	
	@Test
	public void testWalls(){
		// arrange
		
		// action
		int walls = count(Board.Creatures.Wall);
		
		// assert
		assertEquals(417, walls);
	}
	
	private int count(Board.Creatures c){
		int cNum = 0;
		for (int i = 0; i < board.length; i++){
			for (int j = 0; j < board[0].length; j++){
				if (board[i][j] == c)
					cNum ++;
			}
		}
		return cNum;
	}	
	
	@Test
	public void movePacmanToWall(){
		// arrange
		board = mBoard.setBoard("PW");
		
		// action
		boolean result = mBoard.movePacmanRight();
		
		// assert
		assertFalse(result);
	}

	@Test
	public void movePacmanToPoint(){
		// arrange
		board = mBoard.setBoard("P-");
		
		// action
		boolean result = mBoard.movePacmanRight();
		
		// assert
		assertTrue(result);
	}

	@Test
	public void movePacmanToGhost(){
		// arrange
		board = mBoard.setBoard("PG");
		
		// action
		boolean result = mBoard.movePacmanRight();
		
		// assert
		assertTrue(result);
	}
}
