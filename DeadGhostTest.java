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
		
		dg.findPath(game, rev);
		dg.move(game);
		
		assertEquals(dg.getLocation(), rev);
	}
	
	@Test
	void oneStepLeft() {
		Location rev = new Location(0, 0), deadGhost = new Location(0, 1);
		setGameAndGhost( new String[] {"RDW"}, deadGhost);
		
		dg.findPath(game, rev);
		dg.move(game);
		
		assertEquals(dg.getLocation(), rev);
	}

	@Test
	void oneStepDown() {
		Location rev = new Location(1, 0), deadGhost = new Location(0, 0);
		setGameAndGhost( new String[] {"D", "R", "W"}, deadGhost);
		
		dg.findPath(game, rev);
		dg.move(game);
		
		assertEquals(dg.getLocation(), rev);
	}
	
	@Test
	void oneStepUp() {
		Location rev = new Location(0, 0), deadGhost = new Location(1, 0);
		setGameAndGhost( new String[] {"R", "D", "W"}, deadGhost);
		
		dg.findPath(game, rev);
		dg.move(game);
		
		assertEquals(dg.getLocation(), rev);
	}
		
	@Test
	void chooseTheOneStep() {
		Location rev = new Location(2, 1), deadGhost = new Location(1, 1);
		setGameAndGhost( new String[] {
			"W-W",
			"-D-",
			"WRW"
		}, deadGhost);
		
		dg.findPath(game, rev);
		dg.move(game);
		
		assertEquals(dg.getLocation(), rev);
	}
	
}
