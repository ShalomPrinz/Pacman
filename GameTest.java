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
	
}
