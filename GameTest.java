package pacman;

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
		Board.MovingCreature.PACMAN.setDirection(Game.Direction.RIGHT);
		
		// action
		game.move();
		
		// assert
		assertEquals(Board.MovingCreature.PACMAN, this.game.getCreatureAt(l1));
		assertEquals(Board.Creature.WALL, this.game.getCreatureAt(l2));
	}

	@Test
	public void PacmanRightPoint(){
		// arrange
		setGameBoardByStringArray(new String[]{"P-"});
		Location l1 = new Location(0, 0), l2 = new Location(0, 1);
		Board.MovingCreature.PACMAN.setDirection(Game.Direction.RIGHT);
		
		// action
		game.move();
		
		// assert
		assertEquals(Board.Creature.NULL , this.game.getCreatureAt(l1));
		assertEquals(Board.MovingCreature.PACMAN , this.game.getCreatureAt(l2));
	}

	@Test
	public void PacmanRightGhost(){
		// arrange
		setGameBoardByStringArray(new String[]{"PG"});
		Board.MovingCreature.PACMAN.setDirection(Game.Direction.RIGHT);
		
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
		Board.MovingCreature.PACMAN.setDirection(Game.Direction.RIGHT);
		
		// action
		game.move();
		
		// assert
		assertEquals(Board.Creature.NULL , this.game.getCreatureAt(l1));
		assertEquals(Board.MovingCreature.PACMAN , this.game.getCreatureAt(l2));
	}

	@Test
	public void PacmanOutOfBoard_Right(){
		// arrange
		setGameBoardByStringArray(new String[]{"--P", "...", "..."});
		Location l1 = new Location(0, 0), l2 = new Location(0, 2);
		Board.MovingCreature.PACMAN.setDirection(Game.Direction.RIGHT);
		
		// action
		game.move();
		
		// assert
		assertEquals(Board.MovingCreature.PACMAN , this.game.getCreatureAt(l1));
		assertEquals(Board.Creature.NULL , this.game.getCreatureAt(l2));
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
		assertEquals(Board.MovingCreature.PACMAN, this.game.getCreatureAt(l1));
		assertEquals(Board.Creature.NULL, this.game.getCreatureAt(l2));
	}

	@Test
	public void PacmanOutOfBoard_Left(){
		// arrange
		setGameBoardByStringArray(new String[]{"P--", "...", "..."});
		Location l1 = new Location(0, 0), l2 = new Location(0, 2);
		
		// action
		game.move();
		
		// assert
		assertEquals(Board.Creature.NULL , this.game.getCreatureAt(l1));
		assertEquals(Board.MovingCreature.PACMAN , this.game.getCreatureAt(l2));
	}
	
	// Up
	
	@Test
	public void PacmanUpGhost(){
		// arrange
		setGameBoardByStringArray(new String[]{"G-", "PW"});
		Board.MovingCreature.PACMAN.setDirection(Game.Direction.UP);
		
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
		Board.MovingCreature.PACMAN.setDirection(Game.Direction.DOWN);
		
		// action
		game.move();
				
		// assert
		assertEquals(Board.MovingCreature.PACMAN , this.game.getCreatureAt(l1));
		assertEquals(Board.Creature.WALL , this.game.getCreatureAt(l2));
	}
	
	// - Ghosts -
	// Right
	
	@Test
	public void GhostRightWall(){
		// arrange
		setGameBoardByStringArray(new String[]{"GW"});
		Location l1 = new Location(0, 0), l2 = new Location(0, 1);
		Board.MovingCreature.GHOST_1.setDirection(Game.Direction.RIGHT);
		
		// action
		game.move();
				
		// assert
		assertEquals(Board.MovingCreature.GHOST_1, this.game.getCreatureAt(l1));
		assertEquals(Board.Creature.WALL, this.game.getCreatureAt(l2));
	}

	@Test
	public void GhostRightPoint(){
		// arrange
		setGameBoardByStringArray(new String[]{"G-"});
		Location l1 = new Location(0, 0), l2 = new Location(0, 1);
		Board.MovingCreature.GHOST_1.setDirection(Game.Direction.RIGHT);
		
		// action
		game.move();
				
		// assert
		assertEquals(Board.Creature.NULL, this.game.getCreatureAt(l1));
		assertEquals(Board.MovingCreature.GHOST_1, this.game.getCreatureAt(l2));
	}

	@Test
	public void GhostRightPoint_SecondTurn() {
		// arrange
		setGameBoardByStringArray(new String[]{"G-."});
		Location l1 = new Location(0, 0), l2 = new Location(0, 1), l3 = new Location(0, 2);
		Board.MovingCreature.GHOST_1.setDirection(Game.Direction.RIGHT);
		
		// action
		game.move();
		game.move();
		
		// assert
		assertEquals(Board.Creature.NULL, this.game.getCreatureAt ( l1 ) );
		assertEquals(Board.Creature.POINT, this.game.getCreatureAt ( l2 ) );
		assertEquals(Board.MovingCreature.GHOST_1, this.game.getCreatureAt ( l3 ) );
	}

	@Test
	public void GhostRightPacman(){
		// arrange
		setGameBoardByStringArray(new String[]{"GP"});
		Board.MovingCreature.GHOST_1.setDirection(Game.Direction.RIGHT);
		
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
		Board.MovingCreature.GHOST_1.setDirection(Game.Direction.RIGHT);
		
		// action
		game.move();
				
		// assert
		assertEquals(Board.MovingCreature.GHOST_1, this.game.getCreatureAt(l1));
		assertEquals(Board.MovingCreature.GHOST_2, this.game.getCreatureAt(l2));
	}

	// Left
	
	@Test
	public void GhostLeftGhost(){
		// arrange
		setGameBoardByStringArray(new String[]{"GG"});
		Location l1 = new Location(0, 0), l2 = new Location(0, 1);
		Board.MovingCreature.GHOST_1.setDirection(Game.Direction.RIGHT);
		
		// action
		game.move();
				
		// assert
		assertEquals(Board.MovingCreature.GHOST_1, this.game.getCreatureAt(l1));
		assertEquals(Board.MovingCreature.GHOST_2, this.game.getCreatureAt(l2));
	}

	// Up
	
	@Test
	public void GhostUpNull(){
		// arrange
		setGameBoardByStringArray(new String[]{".", "G"});
		Location l1 = new Location(0, 0), l2 = new Location(1, 0);
		Board.MovingCreature.GHOST_1.setDirection(Game.Direction.UP);
		
		// action
		game.move();
				
		// assert
		assertEquals(Board.MovingCreature.GHOST_1, this.game.getCreatureAt(l1));
		assertEquals(Board.Creature.NULL, this.game.getCreatureAt(l2));
	}

	@Test
	public void GhostOutOfBoard_Up(){
		// arrange
		setGameBoardByStringArray(new String[]{"WGW", "...", "..."});
		Location l1 = new Location(2, 1), l2 = new Location(0, 1);
		Board.MovingCreature.GHOST_1.setDirection(Game.Direction.UP);
		
		// action
		game.move();
		
		// assert
		assertEquals(Board.MovingCreature.GHOST_1 , this.game.getCreatureAt(l1));
		assertEquals(Board.Creature.NULL , this.game.getCreatureAt(l2));
	}
	
	// Down

	@Test
	public void GhostDownPoint(){
		// arrange
		setGameBoardByStringArray(new String[]{"G", "-"});
		Location l1 = new Location(0, 0), l2 = new Location(1, 0);
		Board.MovingCreature.GHOST_1.setDirection(Game.Direction.DOWN);
		
		// action
		game.move();
				
		// assert
		assertEquals(Board.Creature.NULL, this.game.getCreatureAt(l1));
		assertEquals(Board.MovingCreature.GHOST_1, this.game.getCreatureAt(l2));
	}

	@Test
	public void GhostOutOfBoard_Down(){
		// arrange
		setGameBoardByStringArray(new String[]{"---", "-.-", ".G."});
		Location l1 = new Location(2, 1), l2 = new Location(0, 1);
		Board.MovingCreature.GHOST_1.setDirection(Game.Direction.DOWN);
		
		// action
		game.move();
		
		// assert
		assertEquals(Board.Creature.NULL , this.game.getCreatureAt(l1));
		assertEquals(Board.MovingCreature.GHOST_1 , this.game.getCreatureAt(l2));
	}

	// Change Direction
	// - Pacman -
	
	@Test
	public void PacmanLeft_Down_NotChanging(){
		// arrange
		setGameBoardByStringArray(new String[]{"-P", "-W"});
		Location l1 = new Location(0, 0), l2 = new Location(0, 1);
		
		// action
		game.changeDirection(Game.Direction.DOWN, Board.MovingCreature.PACMAN);
		game.move();
		
		// assert
		assertEquals(Board.Creature.NULL , this.game.getCreatureAt(l2));
		assertEquals(Board.MovingCreature.PACMAN , this.game.getCreatureAt(l1));
	}

	@Test
	public void PacmanLeft_Down_Changing(){
		// arrange
		setGameBoardByStringArray(new String[]{"P-", "-W"});
		Location l1 = new Location(0, 0), l2 = new Location(1, 0);
		
		// action
		game.changeDirection(Game.Direction.DOWN, Board.MovingCreature.PACMAN);
		game.move();
		
		// assert
		assertEquals(Board.Creature.NULL , this.game.getCreatureAt(l1));
		assertEquals(Board.MovingCreature.PACMAN , this.game.getCreatureAt(l2));
	}
	
	@Test
	public void PacmanLeft_Down_SaveChange(){
		// arrange
		setGameBoardByStringArray(new String[]{"-P", "-W"});
		Location l1 = new Location(0, 0), l2 = new Location(1, 0);
		
		// action
		game.changeDirection(Game.Direction.DOWN, Board.MovingCreature.PACMAN);
		game.move();
		game.move();
		
		// assert
		assertEquals(Board.Creature.NULL , this.game.getCreatureAt(l1));
		assertEquals(Board.MovingCreature.PACMAN , this.game.getCreatureAt(l2));
	}

	// - Ghost -
	
	@Test
	public void GhostLeft_Down_NotChanging(){
		// arrange
		setGameBoardByStringArray(new String[]{"-G", "-W"});
		Location l1 = new Location(0, 0), l2 = new Location(0, 1);
		
		// action
		game.changeDirection(Game.Direction.DOWN, Board.MovingCreature.GHOST_1);
		game.move();
		
		// assert
		assertEquals(Board.Creature.NULL , this.game.getCreatureAt(l2));
		assertEquals(Board.MovingCreature.GHOST_1 , this.game.getCreatureAt(l1));
	}
	
	@Test
	public void GhostLeft_Down_Changing(){
		// arrange
		setGameBoardByStringArray(new String[]{"G-", "-W"});
		Location l1 = new Location(0, 0), l2 = new Location(1, 0);
		
		// action
		game.changeDirection(Game.Direction.DOWN, Board.MovingCreature.GHOST_1);
		game.move();
		
		// assert
		assertEquals(Board.Creature.NULL , this.game.getCreatureAt(l1));
		assertEquals(Board.MovingCreature.GHOST_1 , this.game.getCreatureAt(l2));
	}
	
	@Test
	public void GhostLeft_Down_SaveChange(){
		// arrange
		setGameBoardByStringArray(new String[]{"-G", "-W"});
		Location l1 = new Location(0, 0), l2 = new Location(1, 0);
		
		// action
		game.changeDirection(Game.Direction.DOWN, Board.MovingCreature.GHOST_1);
		game.move();
		game.move();
		
		// assert
		assertEquals(Board.Creature.POINT , this.game.getCreatureAt(l1));
		assertEquals(Board.MovingCreature.GHOST_1 , this.game.getCreatureAt(l2));
	}
	
	@Test
	public void GhostLeftWall_SmartChange(){
		// arrange
		setGameBoardByStringArray(new String[]{"W-G", "W-W", "WWW"});
		Location l1 = new Location(0, 1), l2 = new Location(1, 1);
		
		// action
		game.move();
		game.move();
		
		// assert
		assertEquals(Board.MovingCreature.POINT , this.game.getCreatureAt(l1));
		assertEquals(Board.MovingCreature.GHOST_1 , this.game.getCreatureAt(l2));
	}

	@Test
	public void findNewPathForGhost_method() {
		// arrange
		setGameBoardByStringArray(new String[]{"W-G", "W-W", "WWW"});
		Game.Direction original  = Board.MovingCreature.GHOST_1.getDirection();
		Game.Direction designed;
		
		// action
		game.move();
		game.move();
		designed = Board.MovingCreature.GHOST_1.getDirection();
		
		// assert
		assertTrue(original != designed);
	}

}
