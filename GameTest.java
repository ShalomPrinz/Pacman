import static org.junit.Assert.*;

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
		Board.Creature.Pacman.setDirection(Game.Direction.Right);
		
		// action
		game.move();
		
		// assert
		assertEquals(Board.Creature.Pacman, this.game.getCreatureAt(l1));
		assertEquals(Board.Creature.Wall, this.game.getCreatureAt(l2));
	}

	@Test
	public void PacmanRightPoint(){
		// arrange
		setGameBoardByStringArray(new String[]{"P-"});
		Location l1 = new Location(0, 0), l2 = new Location(0, 1);
		Board.Creature.Pacman.setDirection(Game.Direction.Right);
		
		// action
		game.move();
		
		// assert
		assertEquals(Board.Creature.Null , this.game.getCreatureAt(l1));
		assertEquals(Board.Creature.Pacman , this.game.getCreatureAt(l2));
	}

	@Test
	public void PacmanRightGhost(){
		// arrange
		setGameBoardByStringArray(new String[]{"PG"});
		Board.Creature.Pacman.setDirection(Game.Direction.Right);
		
		// action
		game.move();
		
		// assert
		assertFalse(this.game.gameOn);
	}
	
	@Test
	public void PacmanRightNull(){
		// arrange
		setGameBoardByStringArray(new String[]{"P."});
		Location l1 = new Location(0, 0), l2 = new Location(0, 1);
		Board.Creature.Pacman.setDirection(Game.Direction.Right);
		
		// action
		game.move();
		
		// assert
		assertEquals(Board.Creature.Null , this.game.getCreatureAt(l1));
		assertEquals(Board.Creature.Pacman , this.game.getCreatureAt(l2));
	}

	@Test
	public void PacmanOutOfBoard_Right(){
		// arrange
		setGameBoardByStringArray(new String[]{"--P", "...", "..."});
		Location l1 = new Location(0, 0), l2 = new Location(0, 2);
		Board.Creature.Pacman.setDirection(Game.Direction.Right);
		
		// action
		game.move();
		
		// assert
		assertEquals(Board.Creature.Pacman , this.game.getCreatureAt(l1));
		assertEquals(Board.Creature.Null , this.game.getCreatureAt(l2));
	}
	
	// Left
	
	@Test
	public void PacmanLeftPoint(){
		// arrange
		setGameBoardByStringArray(new String[]{"-P"});
		Location l1 = new Location(0, 0), l2 = new Location(0, 1);
		
		// action
		game.move();
				
		// assert
		assertEquals(Board.Creature.Pacman, this.game.getCreatureAt(l1));
		assertEquals(Board.Creature.Null, this.game.getCreatureAt(l2));
	}

	@Test
	public void PacmanOutOfBoard_Left(){
		// arrange
		setGameBoardByStringArray(new String[]{"P--", "...", "..."});
		Location l1 = new Location(0, 0), l2 = new Location(0, 2);
		
		// action
		game.move();
		
		// assert
		assertEquals(Board.Creature.Null , this.game.getCreatureAt(l1));
		assertEquals(Board.Creature.Pacman , this.game.getCreatureAt(l2));
	}
	
	// Up
	
	@Test
	public void PacmanUpGhost(){
		// arrange
		setGameBoardByStringArray(new String[]{"G-", "PW"});
		Board.Creature.Pacman.setDirection(Game.Direction.Up);
		
		// action
		game.move();
				
		// assert
		assertFalse(this.game.gameOn);
	}

	// Down
	
	@Test
	public void PacmanDownWall(){
		// arrange
		setGameBoardByStringArray(new String[]{"PW", "WG"});
		Location l1 = new Location(0, 0), l2 = new Location(1, 0);
		Board.Creature.Pacman.setDirection(Game.Direction.Down);
		
		// action
		game.move();
				
		// assert
		assertEquals(Board.Creature.Pacman , this.game.getCreatureAt(l1));
		assertEquals(Board.Creature.Wall , this.game.getCreatureAt(l2));
	}
	
	// - Ghosts -
	// Right
	
	@Test
	public void GhostRightWall(){
		// arrange
		setGameBoardByStringArray(new String[]{"GW"});
		Location l1 = new Location(0, 0), l2 = new Location(0, 1);
		Board.Creature.Ghost1.setDirection(Game.Direction.Right);
		
		// action
		game.move();
				
		// assert
		assertEquals(Board.Creature.Ghost1, this.game.getCreatureAt(l1));
		assertEquals(Board.Creature.Wall, this.game.getCreatureAt(l2));
	}

	@Test
	public void GhostRightPoint(){
		// arrange
		setGameBoardByStringArray(new String[]{"G-"});
		Location l1 = new Location(0, 0), l2 = new Location(0, 1);
		Board.Creature.Ghost1.setDirection(Game.Direction.Right);
		
		// action
		game.move();
				
		// assert
		assertEquals(Board.Creature.Null, this.game.getCreatureAt(l1));
		assertEquals(Board.Creature.Ghost1, this.game.getCreatureAt(l2));
	}

	@Test
	public void GhostRightPoint_SecondTurn() {
		// arrange
		setGameBoardByStringArray(new String[]{"G-."});
		Location l1 = new Location(0, 0), l2 = new Location(0, 1), l3 = new Location(0, 2);
		Board.Creature.Ghost1.setDirection(Game.Direction.Right);
		
		// action
		game.move();
		game.move();
		
		// assert
		assertEquals(Board.Creature.Null, this.game.getCreatureAt ( l1 ) );
		assertEquals(Board.Creature.Point, this.game.getCreatureAt ( l2 ) );
		assertEquals(Board.Creature.Ghost1, this.game.getCreatureAt ( l3 ) );
	}

	@Test
	public void GhostRightPacman(){
		// arrange
		setGameBoardByStringArray(new String[]{"GP"});
		Board.Creature.Ghost1.setDirection(Game.Direction.Right);
		
		// action
		game.move();
				
		// assert
		assertFalse(this.game.gameOn);
	}

	@Test
	public void GhostRightGhost(){
		// arrange
		setGameBoardByStringArray(new String[]{"GG"});
		Location l1 = new Location(0, 0), l2 = new Location(0, 1);
		Board.Creature.Ghost1.setDirection(Game.Direction.Right);
		
		// action
		game.move();
				
		// assert
		assertEquals(Board.Creature.Ghost1, this.game.getCreatureAt(l1));
		assertEquals(Board.Creature.Ghost2, this.game.getCreatureAt(l2));
	}

	// Left
	
	@Test
	public void GhostLeftGhost(){
		// arrange
		setGameBoardByStringArray(new String[]{"GG"});
		Location l1 = new Location(0, 0), l2 = new Location(0, 1);
		Board.Creature.Ghost1.setDirection(Game.Direction.Right);
		
		// action
		game.move();
				
		// assert
		assertEquals(Board.Creature.Ghost1, this.game.getCreatureAt(l1));
		assertEquals(Board.Creature.Ghost2, this.game.getCreatureAt(l2));
	}

	// Up
	
	@Test
	public void GhostUpNull(){
		// arrange
		setGameBoardByStringArray(new String[]{".", "G"});
		Location l1 = new Location(0, 0), l2 = new Location(1, 0);
		Board.Creature.Ghost1.setDirection(Game.Direction.Up);
		
		// action
		game.move();
				
		// assert
		assertEquals(Board.Creature.Ghost1, this.game.getCreatureAt(l1));
		assertEquals(Board.Creature.Null, this.game.getCreatureAt(l2));
	}

	@Test
	public void GhostOutOfBoard_Up(){
		// arrange
		setGameBoardByStringArray(new String[]{"-G-", "...", "..."});
		Location l1 = new Location(2, 1), l2 = new Location(0, 1);
		Board.Creature.Ghost1.setDirection(Game.Direction.Up);
		
		// action
		game.move();
		
		// assert
		assertEquals(Board.Creature.Ghost1 , this.game.getCreatureAt(l1));
		assertEquals(Board.Creature.Null , this.game.getCreatureAt(l2));
	}
	
	// Down

	@Test
	public void GhostDownPoint(){
		// arrange
		setGameBoardByStringArray(new String[]{"G", "-"});
		Location l1 = new Location(0, 0), l2 = new Location(1, 0);
		Board.Creature.Ghost1.setDirection(Game.Direction.Down);
		
		// action
		game.move();
				
		// assert
		assertEquals(Board.Creature.Null, this.game.getCreatureAt(l1));
		assertEquals(Board.Creature.Ghost1, this.game.getCreatureAt(l2));
	}

	@Test
	public void GhostOutOfBoard_Down(){
		// arrange
		setGameBoardByStringArray(new String[]{"---", "-.-", ".G."});
		Location l1 = new Location(2, 1), l2 = new Location(0, 1);
		Board.Creature.Ghost1.setDirection(Game.Direction.Down);
		
		// action
		game.move();
		
		// assert
		assertEquals(Board.Creature.Null , this.game.getCreatureAt(l1));
		assertEquals(Board.Creature.Ghost1 , this.game.getCreatureAt(l2));
	}
}
