package pacman;

import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;

class PacmanTest {

	Game game;
	Pacman pacman;
	
	public void setGameBoardByStringArray(final String[] board){
		this.game = new Game(board);
	}
	
	// Pacman: Move
	
	// Right
	
		@Test
	public void PacmanRightWall(){
		// arrange
		Location l1 = new Location(0, 0), l2 = new Location(0, 1);
		setGameBoardByStringArray( new String[]{"PW"} );
		Board.MovingCreature.PACMAN.setDirection(Game.Direction.RIGHT);
		
		// action
		game.move();
		
		// assert
		assertEquals(Board.MovingCreature.PACMAN, this.game.getCreatureAt(l1));
		assertEquals(Board.Creature.WALL, this.game.getCreatureAt(l2));
	}

//	@Test
//	public void PacmanRightPoint(){
//		// arrange
//		setGameBoardByStringArray(new String[]{"P-"});
//		Location l1 = new Location(0, 0), l2 = new Location(0, 1);
//		Board.Creature.PACMAN.setDirection(Game.Direction.RIGHT);
//		
//		// action
//		game.move();
//		
//		// assert
//		assertEquals(Board.Creature.NULL , this.game.getCreatureAt(l1));
//		assertEquals(Board.Creature.PACMAN , this.game.getCreatureAt(l2));
//	}
//
//	@Test
//	public void PacmanRightGhost(){
//		// arrange
//		setGameBoardByStringArray(new String[]{"PG"});
//		Board.Creature.PACMAN.setDirection(Game.Direction.RIGHT);
//		
//		// action
//		game.move();
//		
//		// assert
//		assertFalse(this.game.gameOn);
//	}
//	
//	@Test
//	public void PacmanRightNull(){
//		// arrange
//		setGameBoardByStringArray(new String[]{"P."});
//		Location l1 = new Location(0, 0), l2 = new Location(0, 1);
//		Board.Creature.PACMAN.setDirection(Game.Direction.RIGHT);
//		
//		// action
//		game.move();
//		
//		// assert
//		assertEquals(Board.Creature.NULL , this.game.getCreatureAt(l1));
//		assertEquals(Board.Creature.PACMAN , this.game.getCreatureAt(l2));
//	}
//
//	@Test
//	public void PacmanOutOfBoard_Right(){
//		// arrange
//		setGameBoardByStringArray(new String[]{"--P", "...", "..."});
//		Location l1 = new Location(0, 0), l2 = new Location(0, 2);
//		Board.Creature.PACMAN.setDirection(Game.Direction.RIGHT);
//		
//		// action
//		game.move();
//		
//		// assert
//		assertEquals(Board.Creature.PACMAN , this.game.getCreatureAt(l1));
//		assertEquals(Board.Creature.NULL , this.game.getCreatureAt(l2));
//	}
//	
//	// Left
//	
//	@Test
//	public void PacmanLeftPoint(){
//		// arrange
//		setGameBoardByStringArray(new String[]{"-P"});
//		Location l1 = new Location(0, 0), l2 = new Location(0, 1);
//		
//		// action
//		game.move();
//				
//		// assert
//		assertEquals(Board.Creature.PACMAN, this.game.getCreatureAt(l1));
//		assertEquals(Board.Creature.NULL, this.game.getCreatureAt(l2));
//	}
//
//	@Test
//	public void PacmanOutOfBoard_Left(){
//		// arrange
//		setGameBoardByStringArray(new String[]{"P--", "...", "..."});
//		Location l1 = new Location(0, 0), l2 = new Location(0, 2);
//		
//		// action
//		game.move();
//		
//		// assert
//		assertEquals(Board.Creature.NULL , this.game.getCreatureAt(l1));
//		assertEquals(Board.Creature.PACMAN , this.game.getCreatureAt(l2));
//	}
//	
//	// Up
//	
//	@Test
//	public void PacmanUpGhost(){
//		// arrange
//		setGameBoardByStringArray(new String[]{"G-", "PW"});
//		Board.Creature.PACMAN.setDirection(Game.Direction.UP);
//		
//		// action
//		game.move();
//				
//		// assert
//		assertFalse(this.game.gameOn);
//	}
//
//	// Down
//	
//	@Test
//	public void PacmanDownWall(){
//		// arrange
//		setGameBoardByStringArray(new String[]{"PW", "WG"});
//		Location l1 = new Location(0, 0), l2 = new Location(1, 0);
//		Board.Creature.PACMAN.setDirection(Game.Direction.DOWN);
//		
//		// action
//		game.move();
//				
//		// assert
//		assertEquals(Board.Creature.PACMAN , this.game.getCreatureAt(l1));
//		assertEquals(Board.Creature.WALL , this.game.getCreatureAt(l2));
//	}
//	

}
