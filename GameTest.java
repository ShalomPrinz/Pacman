package pacman;

import static org.junit.Assert.*;

import org.junit.Test;

import pacman.Creature.Type;

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
		Pacman p = (Pacman) this.game.getCreatureAt(l1);
		p.setDirection(Game.Direction.RIGHT);
		
		// action
		game.move();
		
		// assert
		assertEquals( Type.PACMAN, this.game.getCreatureAt(l1).getType() );
		assertEquals( Type.WALL, this.game.getCreatureAt(l2).getType() );
	}

	@Test
	public void PacmanRightPoint(){
		// arrange
		setGameBoardByStringArray(new String[]{"P-"});
		Location l1 = new Location(0, 0), l2 = new Location(0, 1);
		Pacman p = (Pacman) this.game.getCreatureAt(l1);
		p.setDirection(Game.Direction.RIGHT);
		
		// action
		game.move();
		
		// assert
		assertEquals( Type.NULL , this.game.getCreatureAt(l1).getType() );
		assertEquals( Type.PACMAN , this.game.getCreatureAt(l2).getType() );
	}

	@Test
	public void PacmanRightGhost(){
		// arrange
		setGameBoardByStringArray(new String[]{"PG"});
		Pacman p = (Pacman) this.game.getCreatureAt( new Location(0, 0) );
		p.setDirection(Game.Direction.RIGHT);
		
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
		Pacman p = (Pacman) this.game.getCreatureAt(l1);
		p.setDirection(Game.Direction.RIGHT);
		
		// action
		game.move();
		
		// assert
		assertEquals( Type.NULL, this.game.getCreatureAt(l1).getType() );
		assertEquals( Type.PACMAN , this.game.getCreatureAt(l2).getType() );
	}

	@Test
	public void PacmanOutOfBoard_Right(){
		// arrange
		setGameBoardByStringArray(new String[]{"--P", "...", "..."});
		Location l1 = new Location(0, 0), l2 = new Location(0, 2);
		Pacman p = (Pacman) this.game.getCreatureAt(l2);
		p.setDirection(Game.Direction.RIGHT);
		
		// action
		game.move();
		
		// assert
		assertEquals( Type.PACMAN , this.game.getCreatureAt(l1).getType() );
		assertEquals( Type.NULL , this.game.getCreatureAt(l2).getType() );
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
		assertEquals( Type.PACMAN, this.game.getCreatureAt(l1).getType() );
		assertEquals( Type.NULL, this.game.getCreatureAt(l2).getType() );
	}

	@Test
	public void PacmanOutOfBoard_Left(){
		// arrange
		setGameBoardByStringArray(new String[]{"P--", "...", "..."});
		Location l1 = new Location(0, 0), l2 = new Location(0, 2);
		
		// action
		game.move();
		
		// assert
		assertEquals( Type.NULL , this.game.getCreatureAt(l1).getType() );
		assertEquals( Type.PACMAN , this.game.getCreatureAt(l2).getType() );
	}
	
	// Up
	
	@Test
	public void PacmanUpGhost(){
		// arrange
		setGameBoardByStringArray(new String[]{"G-", "PW"});
		Pacman p = (Pacman) this.game.getCreatureAt( new Location(1, 0) );
		p.setDirection(Game.Direction.UP);
		
		// action
		game.move();
				
		// assert
		assertFalse(this.game.gameOn);
	}

	// Down
	
	@Test
	public void PacmanDownWall(){
		// arrange
		setGameBoardByStringArray(new String[]{"PW", "W-"});
		Location l1 = new Location(0, 0), l2 = new Location(1, 0);
		Pacman p = (Pacman) this.game.getCreatureAt(l1);
		p.setDirection(Game.Direction.DOWN);	
		
		// action
		game.move();
				
		// assert
		assertEquals( Type.PACMAN , this.game.getCreatureAt(l1).getType() );
		assertEquals( Type.WALL , this.game.getCreatureAt(l2).getType() );
	}
	
	// - Ghosts -
	// Right
	
	@Test
	public void GhostRightWall(){
		// arrange
		setGameBoardByStringArray(new String[]{"GW"});
		Location l1 = new Location(0, 0), l2 = new Location(0, 1);
		Ghost g = (Ghost) this.game.getCreatureAt(l1);
		g.setDirection(Game.Direction.RIGHT);	
		
		// action
		game.move();
				
		// assert
		assertEquals( Type.GHOST, this.game.getCreatureAt(l1).getType() );
		assertEquals( Type.WALL, this.game.getCreatureAt(l2).getType() );
	}

	@Test
	public void GhostRightPoint(){
		// arrange
		setGameBoardByStringArray(new String[]{"G-"});
		Location l1 = new Location(0, 0), l2 = new Location(0, 1);
		Ghost g = (Ghost) this.game.getCreatureAt(l1);
		g.setDirection(Game.Direction.RIGHT);	
		
		// action
		game.move();
				
		// assert
		assertEquals( Type.NULL, this.game.getCreatureAt(l1).getType() );
		assertEquals( Type.GHOST, this.game.getCreatureAt(l2).getType() );
	}

	@Test
	public void GhostRightPoint_SecondTurn() {
		// arrange
		setGameBoardByStringArray(new String[]{"G-."});
		Location l1 = new Location(0, 0), l2 = new Location(0, 1), l3 = new Location(0, 2);
		Ghost g = (Ghost) this.game.getCreatureAt(l1);
		g.setDirection(Game.Direction.RIGHT);
		
		// action
		game.move();
		game.move();
		
		// assert
		assertEquals( Type.NULL, this.game.getCreatureAt(l1).getType() );
		assertEquals( Type.POINT, this.game.getCreatureAt(l2).getType() );
		assertEquals( Type.GHOST, this.game.getCreatureAt(l3).getType() );
	}

	@Test
	public void GhostRightPacman(){
		// arrange
		setGameBoardByStringArray(new String[]{"GP"});
		Ghost g = (Ghost) this.game.getCreatureAt( new Location(0, 0) );
		g.setDirection(Game.Direction.RIGHT);
		
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
		Ghost g = (Ghost) this.game.getCreatureAt(l1);
		g.setDirection(Game.Direction.RIGHT);
		
		// action
		game.move();
				
		// assert
		assertEquals( Type.GHOST, this.game.getCreatureAt(l1).getType() );
		assertEquals( Type.GHOST, this.game.getCreatureAt(l2).getType() );
	}

	// Left
	
	@Test
	public void GhostLeftGhost(){
		// arrange
		setGameBoardByStringArray(new String[]{"GG"});
		Location l1 = new Location(0, 0), l2 = new Location(0, 1);
		Ghost g = (Ghost) this.game.getCreatureAt(l1);
		g.setDirection(Game.Direction.LEFT);
		
		// action
		game.move();
				
		// assert
		assertEquals( Type.GHOST, this.game.getCreatureAt(l1).getType() );
		assertEquals( Type.GHOST, this.game.getCreatureAt(l2).getType() );
	}

	// Up
	
	@Test
	public void GhostUpNull(){
		// arrange
		setGameBoardByStringArray(new String[]{".", "G"});
		Location l1 = new Location(0, 0), l2 = new Location(1, 0);
		Ghost g = (Ghost) this.game.getCreatureAt(l2);
		g.setDirection(Game.Direction.UP);
		
		// action
		game.move();
				
		// assert
		assertEquals( Type.GHOST, this.game.getCreatureAt(l1).getType() );
		assertEquals( Type.NULL, this.game.getCreatureAt(l2).getType() );
	}

	@Test
	public void GhostOutOfBoard_Up(){
		// arrange
		setGameBoardByStringArray(new String[]{"WGW", "...", "..."});
		Location l1 = new Location(2, 1), l2 = new Location(0, 1);
		Ghost g = (Ghost) this.game.getCreatureAt(l2);
		g.setDirection(Game.Direction.UP);
		
		// action
		game.move();
		
		// assert
		assertEquals( Type.GHOST , this.game.getCreatureAt(l1).getType() );
		assertEquals( Type.NULL , this.game.getCreatureAt(l2).getType() );
	}
	
	// Down

	@Test
	public void GhostDownPoint(){
		// arrange
		setGameBoardByStringArray(new String[]{"G", "-"});
		Location l1 = new Location(0, 0), l2 = new Location(1, 0);
		Ghost g = (Ghost) this.game.getCreatureAt(l1);
		g.setDirection(Game.Direction.DOWN);
		
		// action
		game.move();
				
		// assert
		assertEquals( Type.NULL, this.game.getCreatureAt(l1).getType() );
		assertEquals( Type.GHOST, this.game.getCreatureAt(l2).getType() );
	}

	@Test
	public void GhostOutOfBoard_Down(){
		// arrange
		setGameBoardByStringArray(new String[]{"---", "-.-", ".G."});
		Location l1 = new Location(2, 1), l2 = new Location(0, 1);
		Ghost g = (Ghost) this.game.getCreatureAt(l1);
		g.setDirection(Game.Direction.DOWN);
		
		// action
		game.move();
		
		// assert
		assertEquals( Type.NULL , this.game.getCreatureAt(l1).getType() );
		assertEquals( Type.GHOST , this.game.getCreatureAt(l2).getType() );
	}

	// Change Direction
	// - Pacman -
	
	@Test
	public void PacmanLeft_Down_NotChanging(){
		// arrange
		setGameBoardByStringArray(new String[]{"-P", "-W"});
		Location l1 = new Location(0, 0), l2 = new Location(0, 1);
		Pacman p = (Pacman) this.game.getCreatureAt(l2);
		
		// action
		p.changeDirection(game, Game.Direction.DOWN, p);
		game.move();
		
		// assert
		assertEquals( Type.NULL , this.game.getCreatureAt(l2).getType() );
		assertEquals( Type.PACMAN , this.game.getCreatureAt(l1).getType() );
	}

	@Test
	public void PacmanLeft_Down_Changing(){
		// arrange
		setGameBoardByStringArray(new String[]{"P-", "-W"});
		Location l1 = new Location(0, 0), l2 = new Location(1, 0);
		Pacman p = (Pacman) this.game.getCreatureAt(l1);
		p.setDirection(Game.Direction.DOWN);
		
		// action
		p.changeDirection(game, Game.Direction.DOWN, p);
		game.move();
		
		// assert
		assertEquals( Type.NULL , this.game.getCreatureAt(l1).getType() );
		assertEquals( Type.PACMAN , this.game.getCreatureAt(l2).getType() );
	}
	
	@Test
	public void PacmanLeft_Down_SaveChange(){
		// arrange
		setGameBoardByStringArray(new String[]{"-P", "-W"});
		Location l1 = new Location(0, 0), l2 = new Location(1, 0), l3 = new Location(0, 1);
		Pacman p = (Pacman) this.game.getCreatureAt(l3);
		
		// action
		p.changeDirection(game, Game.Direction.DOWN, p);
		game.move();
		game.move();
		
		// assert
		assertEquals( Type.NULL , this.game.getCreatureAt(l1).getType() );
		assertEquals( Type.PACMAN , this.game.getCreatureAt(l2).getType() );
	}

	// - Ghost -
	
	@Test
	public void GhostLeft_Down_NotChanging(){
		// arrange
		setGameBoardByStringArray(new String[]{"-G", "-W"});
		Location l1 = new Location(0, 0), l2 = new Location(0, 1);
		Ghost g = (Ghost) this.game.getCreatureAt(l2);
		g.setDirection(Game.Direction.DOWN);
		
		// action
		g.changeDirection(game, Game.Direction.DOWN, g);
		game.move();
		
		// assert
		assertEquals( Type.NULL , this.game.getCreatureAt(l2).getType() );
		assertEquals( Type.GHOST , this.game.getCreatureAt(l1).getType() );
	}
	
	@Test
	public void GhostLeft_Down_Changing(){
		// arrange
		setGameBoardByStringArray(new String[]{"G-", "-W"});
		Location l1 = new Location(0, 0), l2 = new Location(1, 0);
		Ghost g = (Ghost) this.game.getCreatureAt(l1);
		g.setDirection(Game.Direction.DOWN);
		
		// action
		g.changeDirection(game, Game.Direction.DOWN, g);
		game.move();
		
		// assert
		assertEquals( Type.NULL , this.game.getCreatureAt(l1).getType() );
		assertEquals( Type.GHOST , this.game.getCreatureAt(l2).getType() );
	}
	
	@Test
	public void GhostLeft_Down_SaveChange(){
		// arrange
		setGameBoardByStringArray(new String[]{"-G", "-W"});
		Location l1 = new Location(0, 0), l2 = new Location(1, 0), l3 = new Location(0, 1);
		Ghost g = (Ghost) this.game.getCreatureAt(l3);
		
		// action
		g.changeDirection(game, Game.Direction.DOWN, g);
		game.move();
		game.move();
		
		// assert
		assertEquals( Type.POINT , this.game.getCreatureAt(l1).getType() );
		assertEquals( Type.GHOST , this.game.getCreatureAt(l2).getType() );
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
		assertEquals( Type.POINT , this.game.getCreatureAt(l1).getType() );
		assertEquals( Type.GHOST , this.game.getCreatureAt(l2).getType() );
	}

	@Test
	public void findNewPathForGhost_method() {
		// arrange
		setGameBoardByStringArray(new String[]{"W-G", "W-W", "WWW"});
		Ghost g = (Ghost) this.game.getCreatureAt( new Location(0, 2) );
		Game.Direction original = g.getDirection();
		Game.Direction designed;
		
		// action
		game.move();
		game.move();
		designed = g.getDirection();
		
		// assert
		assertTrue(original != designed);
	}
	
}
