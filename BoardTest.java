import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BoardTest {
	
	Game game;
	
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
	public void testPacman() throws Exception{
		// arrange
		
		// action
		int pacmans = game.count(Game.Creatures.Pacman);
		
		// assert
		assertEquals(1, pacmans);
	}
	
	@Test
	public void testGhosts() throws Exception{
		// arrange
		
		// action
		int ghosts = game.count(Game.Creatures.Ghost);
		
		// assert
		assertEquals(4, ghosts);
	}
	
	@Test
	public void testPoints() throws Exception{
		// arrange
		
		// action
		int points = game.count(Game.Creatures.Point);
		
		// assert
		assertEquals(330, points);
	}
	
	@Test
	public void testNulls() throws Exception{
		// arrange
		
		// action
		int nulls = game.count(Game.Creatures.Null);
		
		// assert
		assertEquals(148, nulls);
	}
	
	@Test
	public void testWalls() throws Exception{
		// arrange
		
		// action
		int walls = game.count(Game.Creatures.Wall);
		
		// assert
		assertEquals(417, walls);
	}
	
}
