import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BoardTest {
	
	Game game;
	Game.Creatures[][] board;
	
	@Before
	public void setup(){
		game = new Game();
		board = game.getBoard();
	}
	
	@Test
	public void testDimensions(){
		// arrange
		
		// action
		int dimensions = game.getBoardDimensions();
		
		// assert
		assertEquals(3030, dimensions);
	}
	
	@Test
	public void testPacman(){
		// arrange
		
		// action
		int pacmans = count(Game.Creatures.Pacman);
		
		// assert
		assertEquals(1, pacmans);
	}
	
	@Test
	public void testGhosts(){
		// arrange
		
		// action
		int ghosts = count(Game.Creatures.Ghost1) + count(Game.Creatures.Ghost2) +
					 count(Game.Creatures.Ghost3) + count(Game.Creatures.Ghost4);
		
		// assert
		assertEquals(4, ghosts);
	}
	
	@Test
	public void testPoints(){
		// arrange
		
		// action
		int points = count(Game.Creatures.Point);
		
		// assert
		assertEquals(330, points);
	}
	
	@Test
	public void testNulls(){
		// arrange
		
		// action
		int nulls = count(Game.Creatures.Null);
		
		// assert
		assertEquals(148, nulls);
	}
	
	@Test
	public void testWalls(){
		// arrange
		
		// action
		int walls = count(Game.Creatures.Wall);
		
		// assert
		assertEquals(417, walls);
	}
	
	private int count(Game.Creatures c){
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
	public void moveCreature(){
		// arrange
		
		// action
		boolean result = game.move(Game.Creatures.Pacman, Game.Directions.RIGHT);
		
		// assert	
		assertTrue(result);	
	}
}
