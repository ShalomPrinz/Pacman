import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class GameTest {

	Game game;
	
	@Before
	public void setup(){
		game = new Game();
	}
	
	// Right
	
	@Test
	public void PacmanRightWall(){
		// arrange
		this.game.board = game.mBoard.setBoardWithString("PW");
		int[] location = find(Board.Creatures.Pacman);
		
		// action
		game.movePacman(location, Game.Directions.Right);
		
		// assert
		assertEquals(Board.Creatures.Pacman, this.game.board[location[0]] [location[1]]);
	}

	@Test
	public void PacmanRightPoint(){
		// arrange
		this.game.board = game.mBoard.setBoardWithString("P-");
		int[] location = find(Board.Creatures.Pacman);
		
		// action
		game.movePacman(location, Game.Directions.Right);
		
		// assert
		assertEquals(Board.Creatures.Null , this.game.board[location[0]] [location[1]]);
	}

	@Test
	public void PacmanRightGhost(){
		// arrange
		this.game.board = game.mBoard.setBoardWithString("PG");
		int[] location = find(Board.Creatures.Pacman);

		// action
		game.movePacman(location, Game.Directions.Right);
		
		// assert
		assertEquals(Board.Creatures.Ghost1, this.game.board[location[0]][location[1] + 1]);
	}
	
	@Test
	public void PacmanRightNull(){
		// arrange
		this.game.board = game.mBoard.setBoardWithString("P.");
		int[] location = find(Board.Creatures.Pacman);

		// action
		game.movePacman(location, Game.Directions.Right);
		
		// assert
		assertEquals(Board.Creatures.Null, this.game.board[location[0]] [location[1]]);
	}

	public int[] find(Board.Creatures c){
		for (int i = 0; i < this.game.board.length; i++){
			for (int j = 0; j < this.game.board[i].length; j++){
				if (this.game.board[i][j] == c)
					return new int[]{i, j};
			}
		}
		
		return null; // not found
	}

	// Left
	
	@Test
	public void PacmanLeftPoint(){
		// arrange
		this.game.board = game.mBoard.setBoardWithString("-P");
		int[] location = find(Board.Creatures.Pacman);
		
		// action
		game.movePacman(location, Game.Directions.Left);
		
		// assert
		assertEquals(Board.Creatures.Null , this.game.board[location[0]] [location[1]]);
	}

	// Up
	
	@Test
	public void PacmanUpGhost(){
		// arrange
		this.game.board = game.mBoard.setBoardWithStringArray(new String[]{"G-", "PW"});
		int[] location = find(Board.Creatures.Pacman);
		
		// action
		game.movePacman(location, Game.Directions.Up);
		
		// assert
		assertEquals(Board.Creatures.Null , this.game.board[location[0]] [location[1]]);
	}

	// Down
	
	@Test
	public void PacmanDownWall(){
		// arrange
		this.game.board = game.mBoard.setBoardWithStringArray(new String[]{"PW", "WG"});
		int[] location = find(Board.Creatures.Pacman);
		
		// action
		game.movePacman(location, Game.Directions.Down);
		
		// assert
		assertEquals(Board.Creatures.Pacman , this.game.board[location[0]] [location[1]]);
	}
}
