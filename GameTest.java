import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BoardTest {
	
	Game game;
	Game.Creatures[][] board;
	
	@Before
	public void setup(){
		game = new Game();
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
		int pacmans = game.count(Game.Creatures.Pacman);
		
		// assert
		assertEquals(1, pacmans);
	}
	
	@Test
	public void testGhosts(){
		// arrange
		
		// action
		int ghosts = game.count(Game.Creatures.Ghost);
		
		// assert
		assertEquals(4, ghosts);
	}
	
}
