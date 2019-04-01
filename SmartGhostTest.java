package pacman;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MediumGhostTest {

	Game game;
	
	private void setGameBoardByStringArray(final String[] board){
		this.game = new Game(board);
	}
	
	
	@Test
	void chance1outOf3() {
		MediumGhost sg = new MediumGhost();
		int counter = 0;
		
		for (int i = 0; i < 90; i++)
			counter += sg.chance() ? 1 : 0;
		
		// estimated. works
		assertEquals(30, counter);		
	}

	@Test
	public void findNewPathForGhost_method() {
		// arrange
		setGameBoardByStringArray(new String[]{"W-S", "W-W", "WWW"});
		MediumGhost sg = (MediumGhost) this.game.getCreatureAt( new Location(0, 2) );
		Game.Direction original = sg.getDirection();
		Game.Direction designed;
		
		// action
		game.move();
		game.move();
		designed = sg.getDirection();
		
		// assert
		assertTrue(original != designed);
	}

}
