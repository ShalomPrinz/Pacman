import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class GameTest {
	
	@Test
	public void testDimensions(){
		// arrange
		Game game = new Game();

		// action
		int dimensions = game.getBoardDimensions();

		// assert
		assertEquals(3030, dimensions);
	}
	
	@Test
	public void testPacman(){
		// arrange
		Game game = new Game();
		Game.Creatures[][] board = game.getBoard();
		
		// action
		int pacmans = game.count(Game.Creatures.Pacman);
		
		// assert
		assertEquals(1, pacmans);
	}
	
}
