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
		Location rev = new Location(0, 2), deadGhost = new Location(0, 1);
		setGameAndGhost( new String[] {"WDR"}, deadGhost);
		
		Direction d = dg.findPath(game, rev);
		
		assertEquals(Direction.RIGHT, d);
	}
	
	@Test
	void oneStepLeft() {
		Location rev = new Location(0, 0), deadGhost = new Location(0, 1);
		setGameAndGhost( new String[] {"RDW"}, deadGhost);
		
		Direction d = dg.findPath(game, rev);
		
		assertEquals(Direction.LEFT, d);
	}

	@Test
	void oneStepDown() {
		Location rev = new Location(1, 0), deadGhost = new Location(0, 0);
		setGameAndGhost( new String[] {"D", "R", "W"}, deadGhost);
		
		Direction d = dg.findPath(game, rev);
		
		assertEquals(Direction.DOWN, d);
	}
	
	@Test
	void oneStepUp() {
		Location rev = new Location(0, 0), deadGhost = new Location(1, 0);
		setGameAndGhost( new String[] {"R", "D", "W"}, deadGhost);
		
		Direction d = dg.findPath(game, rev);
		
		assertEquals(Direction.UP, d);
	}
		
	@Test
	void chooseStepDown() {
		Location rev = new Location(2, 1), deadGhost = new Location(1, 1);
		setGameAndGhost( new String[] {
			"W-W",
			"-D-",
			"WRW"
		}, deadGhost);
		
		Direction d = dg.findPath(game, rev);
		
		assertEquals(Direction.DOWN, d);
	}
	
}
