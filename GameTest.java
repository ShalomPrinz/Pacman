import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class GameTest {

	Game game;
	
	public void setGameBoardByStringArray(final String[] board){
		this.game = new Game(board);
	}
	
	// In each test which checked the moving function, I asserted both changing places
	
	// - Pacman -
	// Right
	
	@Test
	public void PacmanRightWall(){
		// arrange
		setGameBoardByStringArray(new String[]{"PW"});
		Location l1 = new Location(0, 0), l2 = new Location(0, 1);
		
		// action
		game.movePacman(l1, Game.Directions.Right);
		
		// assert
		assertEquals(Board.Creatures.Pacman, this.game.getCreatureAt(l1));
		assertEquals(Board.Creatures.Wall, this.game.getCreatureAt(l2));
	}

	@Test
	public void PacmanRightPoint(){
		// arrange
		setGameBoardByStringArray(new String[]{"P-"});
		Location l1 = new Location(0, 0), l2 = new Location(0, 1);
		
		// action
		game.movePacman(l1, Game.Directions.Right);
		
		// assert
		assertEquals(Board.Creatures.Null , this.game.getCreatureAt(l1));
		assertEquals(Board.Creatures.Pacman , this.game.getCreatureAt(l2));
	}

	@Test
	public void PacmanRightGhost(){
		// arrange
		setGameBoardByStringArray(new String[]{"PG"});
		Location l1 = new Location(0, 0);
		
		// action
		game.movePacman(l1, Game.Directions.Right);
		
		// assert
		assertFalse(this.game.gameOn);
	}
	
	@Test
	public void PacmanRightNull(){
		// arrange
		setGameBoardByStringArray(new String[]{"P."});
		Location l1 = new Location(0, 0), l2 = new Location(0, 1);
		
		// action
		game.movePacman(l1, Game.Directions.Right);
		
		// assert
		assertEquals(Board.Creatures.Null , this.game.getCreatureAt(l1));
		assertEquals(Board.Creatures.Pacman , this.game.getCreatureAt(l2));
	}

	// Left
	
	@Test
	public void PacmanLeftPoint(){
		// arrange
		setGameBoardByStringArray(new String[]{"-P"});
		Location l1 = new Location(0, 0), l2 = new Location(0, 1);
		
		// action
		game.movePacman(l2, Game.Directions.Left);
		
		// assert
		assertEquals(Board.Creatures.Pacman, this.game.getCreatureAt(l1));
		assertEquals(Board.Creatures.Null, this.game.getCreatureAt(l2));
	}

	// Up
	
	@Test
	public void PacmanUpGhost(){
		// arrange
		setGameBoardByStringArray(new String[]{"G-", "PW"});
		Location l1 = new Location(1, 0);
		
		// action
		game.movePacman(l1, Game.Directions.Up);
		
		// assert
		assertFalse(this.game.gameOn);
	}

	// Down
	
	@Test
	public void PacmanDownWall(){
		// arrange
		setGameBoardByStringArray(new String[]{"PW", "WG"});
		Location l1 = new Location(0, 0), l2 = new Location(1, 0);
		
		// action
		game.movePacman(l1, Game.Directions.Down);
		
		// assert
		assertEquals(Board.Creatures.Pacman , this.game.getCreatureAt(l1));
		assertEquals(Board.Creatures.Wall , this.game.getCreatureAt(l2));
	}
	
	// - Ghosts -
	// Right
	
	@Test
	public void GhostRightWall(){
		// arrange
		setGameBoardByStringArray(new String[]{"GW"});
		Location l1 = new Location(0, 0), l2 = new Location(0, 1);
		
		// action
		game.moveGhost(l1, Game.Directions.Right, 1);
		
		// assert
		assertEquals(Board.Creatures.Ghost1, this.game.getCreatureAt(l1));
		assertEquals(Board.Creatures.Wall, this.game.getCreatureAt(l2));
	}

	@Test
	public void GhostRightPoint(){
		// arrange
		setGameBoardByStringArray(new String[]{"G-"});
		Location l1 = new Location(0, 0), l2 = new Location(0, 1);
		
		// action
		game.moveGhost(l1, Game.Directions.Right, 1);
		
		// assert
		assertEquals(Board.Creatures.Null, this.game.getCreatureAt(l1));
		assertEquals(Board.Creatures.Ghost1, this.game.getCreatureAt(l2));
	}

	@Test
	public void GhostRightPoint_SecondTurn() {
		// arrange
		setGameBoardByStringArray(new String[]{"G-."});
		Location l1 = new Location(0, 0), l2 = new Location(0, 1), l3 = new Location(0, 2);
		
		// action
		game.moveGhost(l1, Game.Directions.Right, 1);
		game.moveGhost(l2, Game.Directions.Right, 1);
		
		// assert
		assertEquals(Board.Creatures.Null, this.game.getCreatureAt ( l1 ) );
		assertEquals(Board.Creatures.Point, this.game.getCreatureAt ( l2 ) );
		assertEquals(Board.Creatures.Ghost1, this.game.getCreatureAt ( l3 ) );
	}

	@Test
	public void GhostRightPacman(){
		// arrange
		setGameBoardByStringArray(new String[]{"GP"});
		Location l1 = new Location(0, 0);

		// action
		game.moveGhost(l1, Game.Directions.Right, 1);
		
		// assert
		assertFalse(this.game.gameOn);
	}

	@Test
	public void GhostRightGhost(){
		// arrange
		setGameBoardByStringArray(new String[]{"GG"});
		Location l1 = new Location(0, 0), l2 = new Location(0, 1);
		
		// action
		game.moveGhost(l1, Game.Directions.Right, 1);
		
		// assert
		assertEquals(Board.Creatures.Ghost1, this.game.getCreatureAt(l1));
		assertEquals(Board.Creatures.Ghost2, this.game.getCreatureAt(l2));
	}

	// Left
	
	@Test
	public void GhostLeftGhost(){
		// arrange
		setGameBoardByStringArray(new String[]{"GG"});
		Location l1 = new Location(0, 0), l2 = new Location(0, 1);
		
		// action
		game.moveGhost(l2, Game.Directions.Left, 2);
		
		// assert
		assertEquals(Board.Creatures.Ghost1, this.game.getCreatureAt(l1));
		assertEquals(Board.Creatures.Ghost2, this.game.getCreatureAt(l2));
	}

	// Up
	
	@Test
	public void GhostUpNull(){
		// arrange
		setGameBoardByStringArray(new String[]{".", "G"});
		Location l1 = new Location(0, 0), l2 = new Location(1, 0);
		
		// action
		game.moveGhost(l2, Game.Directions.Up, 1);
		
		// assert
		assertEquals(Board.Creatures.Ghost1, this.game.getCreatureAt(l1));
		assertEquals(Board.Creatures.Null, this.game.getCreatureAt(l2));
	}

	// Down

	@Test
	public void GhostDownPoint(){
		// arrange
		setGameBoardByStringArray(new String[]{"G", "-"});
		Location l1 = new Location(0, 0), l2 = new Location(1, 0);
		
		// action
		game.moveGhost(l1, Game.Directions.Down, 1);
		
		// assert
		assertEquals(Board.Creatures.Null, this.game.getCreatureAt(l1));
		assertEquals(Board.Creatures.Ghost1, this.game.getCreatureAt(l2));
	}
}
