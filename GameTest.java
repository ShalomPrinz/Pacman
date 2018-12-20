import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class GameTest {

	Game game;
	
	@Before
	public void setup(){
		game = new Game();
	}
	
	public void movePacmanToWall(){
		// arrange
		this.game.board = game.mBoard.setBoard("PW");
		
		// action
		boolean result = game.movePacmanRight();
		
		// assert
		assertFalse(result);
	}

	@Test
	public void movePacmanToPoint(){
		// arrange
		this.game.board = game.mBoard.setBoard("P-");
		
		// action
		boolean result = game.movePacmanRight();
		
		// assert
		assertTrue(result);
	}

	@Test
	public void movePacmanToGhost(){
		// arrange
		this.game.board = game.mBoard.setBoard("PG");
		
		// action
		boolean result = game.movePacmanRight();
		
		// assert
		assertTrue(result);
	}


}
