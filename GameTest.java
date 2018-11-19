import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class GameTest {

	Board board;
	Game game;
	
	@Before // It's better be @BeforeClass, but then static needed...
	public void arrangement(){
		board = new Board();
		game = new Game();
		board = game.fillBoard(board);
	}
	
	@Test
	public void testBoardDimensions() {
		// arrange
		
		// action
		
		// assert
		assertEquals(3030, board.getDimensions());
		
	}
	
	@Test
	public void testBoardPacman(){
		// arrange
		boolean onePacman = false;
		
		// action
		for (int i = 0; i < 30; i++){
			for (int j = 0; j < 30; j++){
				if (board.locations[i][j] == 0){
					if (onePacman == true){
						onePacman = false;
						i = 30;
						break;
					}
					else
						onePacman = true;
				}
			}
		}
		
		// assert
		assertTrue(onePacman);
		
	}

	@Test
	public void testFourGhosts(){
		// arrange
		int ghostsNum = 0;
		
		// action
		for (int i = 0; i < 30; i++){
			for (int j = 0; j < 30; j++){
				if (board.locations[i][j] == 1)
					ghostsNum++;
			}
		}
		
		// assert
		assertEquals(4, ghostsNum);
	}

	@Test
	public void testPoints(){
		
	}
}
