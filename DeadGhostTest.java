package pacman;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import pacman.Game.Direction;

class DeadGhostTest {

	@Test
	void oneStepRight() {
		Game game = new Game( new String[] {"DR"} );
		Location rev = new Location(0, 1), ghost = new Location(0, 0);
		DeadGhost dg = (DeadGhost) game.getCreatureAt( ghost );
		
		Direction d = dg.findPath(rev);
		
		assertEquals(Direction.RIGHT, d);
	}
	
	@Test
	void oneStepLeft() {
		Game game = new Game( new String[] {"RD"} );
		Location rev = new Location(0, 0), ghost = new Location(0, 1);
		DeadGhost dg = (DeadGhost) game.getCreatureAt( ghost );
		
		Direction d = dg.findPath(rev);
		
		assertEquals(Direction.LEFT, d);
	}

}
