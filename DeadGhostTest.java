package pacman;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import pacman.Game.Direction;

class DeadGhostTest {

	@Test
	void oneStepRight() {
		Game game = new Game( new String[] {"GR"} );
		Location rev = new Location(0, 1);
		DeadGhost dg = new DeadGhost();
		
		Direction d = dg.findPath(rev);
		
		assertEquals(Direction.RIGHT, d);
	}

}
