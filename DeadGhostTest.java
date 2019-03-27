package pacman;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import pacman.Game.Direction;

class DeadGhostTest {

	Game game;
	DeadGhost dg;
	
	void setGameAndGhost(String[] board, Location deadGhost) {
		game = new Game(board);
		dg = (DeadGhost) game.getCreatureAt( deadGhost );
	}
	
	@Test
	void oneStepRight() {
		Location rev = new Location(0, 1), deadGhost = new Location(0, 0);
		setGameAndGhost( new String[] {"DR"}, deadGhost);
		
		Direction d = dg.findPath(game, rev);
		
		assertEquals(Direction.RIGHT, d);
	}
	
	@Test
	void oneStepLeft() {
		Location rev = new Location(0, 0), deadGhost = new Location(0, 1);
		setGameAndGhost( new String[] {"RD"}, deadGhost);
		
		Direction d = dg.findPath(game, rev);
		
		assertEquals(Direction.LEFT, d);
	}

	@Test
	void oneStepDown() {
		Location rev = new Location(1, 0), deadGhost = new Location(0, 0);
		setGameAndGhost( new String[] {"D", "R"}, deadGhost);
		
		Direction d = dg.findPath(game, rev);
		
		assertEquals(Direction.DOWN, d);
	}
	
	@Test
	void oneStepUp() {
		Location rev = new Location(0, 0), deadGhost = new Location(1, 0);
		setGameAndGhost( new String[] {"R", "D"}, deadGhost);
		
		Direction d = dg.findPath(game, rev);
		
		assertEquals(Direction.UP, d);
	}
	
	@Test
	void stepDownStepRight() {
		Location rev = new Location(1, 1), deadGhost = new Location(0, 0);
		setGameAndGhost( new String[] {"DW", ".R"}, deadGhost);
		
		Direction first = dg.findPath(game, rev);
		dg.setDirection(first);
		dg.move(game);
		Direction second = dg.findPath(game, rev); 
		
		assertTrue( Direction.DOWN == first || Direction.UP == first  );
		assertTrue( Direction.LEFT == second || Direction.RIGHT == second );
	}
	
}
