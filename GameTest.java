import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class GameTest {

	Game game;
	
	@Before
	public void setup(){
		game = new Game();
	}
	
	// In each test which checked the moving function, I asserted both changing places
	
	// - Pacman -
	// Right
	
	@Test
	public void PacmanRightWall(){
		// arrange
		this.game.board = new Board(new String[]{"PW"}).getBoard();
		Location l = new Location(0, 0);
		
		// action
		game.movePacman(l, Game.Directions.Right);
		
		// assert
		assertEquals(Board.Creatures.Pacman, this.game.board[l.getX()] [l.getY()]);
		assertEquals(Board.Creatures.Wall, this.game.board[l.getX()] [l.getY() + 1]);
	}

	@Test
	public void PacmanRightPoint(){
		// arrange
		this.game.board = new Board(new String[]{"P-"}).getBoard();
		Location l = new Location(0, 0);
		
		// action
		game.movePacman(l, Game.Directions.Right);
		
		// assert
		assertEquals(Board.Creatures.Null , this.game.board[l.getX()] [l.getY()]);
		assertEquals(Board.Creatures.Pacman , this.game.board[l.getX()] [l.getY() + 1]);
	}

	@Test
	public void PacmanRightGhost(){
		// arrange
		this.game.board = new Board(new String[]{"PG"}).getBoard();
		Location l = new Location(0, 0);

		// action
		game.movePacman(l, Game.Directions.Right);
		
		// assert
		assertEquals(Board.Creatures.Ghost1, this.game.board[l.getX()][l.getY() + 1]);
	}
	
	@Test
	public void PacmanRightNull(){
		// arrange
		this.game.board = new Board(new String[]{"P."}).getBoard();
		Location l = new Location(0, 0);
		
		// action
		game.movePacman(l, Game.Directions.Right);
		
		// assert
		assertEquals(Board.Creatures.Null, this.game.board[l.getX()] [l.getY()]);
		assertEquals(Board.Creatures.Pacman , this.game.board[l.getX()] [l.getY() + 1]);
	}

	// Left
	
	@Test
	public void PacmanLeftPoint(){
		// arrange
		this.game.board = new Board(new String[]{"-P"}).getBoard();
		Location l = new Location(0, 1);	
		
		// action
		game.movePacman(l, Game.Directions.Left);
		
		// assert
		assertEquals(Board.Creatures.Null , this.game.board[l.getX()] [l.getY()]);
		assertEquals(Board.Creatures.Pacman , this.game.board[l.getX()] [l.getY() - 1]);
	}

	// Up
	
	@Test
	public void PacmanUpGhost(){
		// arrange
		this.game.board = new Board(new String[]{"G-", "PW"}).getBoard();
		Location l = new Location(1, 0);
		
		// action
		game.movePacman(l, Game.Directions.Up);
		
		// assert
		assertEquals(Board.Creatures.Null , this.game.board[l.getX()] [l.getY()]);
		assertEquals(Board.Creatures.Ghost1 , this.game.board[l.getX() - 1] [l.getY()]);
	}

	// Down
	
	@Test
	public void PacmanDownWall(){
		// arrange
		this.game.board = new Board(new String[]{"PW", "WG"}).getBoard();
		Location l = new Location(0, 0);
		
		// action
		game.movePacman(l, Game.Directions.Down);
		
		// assert
		assertEquals(Board.Creatures.Pacman , this.game.board[l.getX()] [l.getY()]);
		assertEquals(Board.Creatures.Wall , this.game.board[l.getX() + 1] [l.getY()]);
	}
	
}
