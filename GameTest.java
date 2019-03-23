package pacman;

import static org.junit.Assert.*;

import org.junit.Test;

import pacman.Creature.Type;
import pacman.Game.Direction;

public class GameTest {

	Game game;
	
	private void setGameBoardByStringArray(final String[] board){
		this.game = new Game(board);
	}
	
	private void setBoardAndDirection(Location l, Direction d, String[] board) {
		setGameBoardByStringArray(board);
		MovingCreature mC = (MovingCreature) game.getCreatureAt(l);
		mC.setDirection(d);
	}
	
	private void setBoard_Direction_Change(Location l, Direction d, String[] board, Direction next) {
		setGameBoardByStringArray(board);
		MovingCreature mC = (MovingCreature) game.getCreatureAt(l);
		mC.setDirection(d);
		mC.changeDirection(game, next, mC);
	}
	
	// In each test which checked the moving function, I asserted both changing places
	
	// - Pacman -
	// Right
	
	@Test
	public void PacmanRightWall(){
		// arrange
		Location l1 = new Location(0, 0), l2 = new Location(0, 1);
		setBoardAndDirection( l1, Game.Direction.RIGHT, new String[] {"PW"} );
		
		// action
		game.move();
		
		// assert
		assertEquals( Type.PACMAN, this.game.getCreatureAt(l1).getType() );
		assertEquals( Type.WALL, this.game.getCreatureAt(l2).getType() );
	}

	@Test
	public void PacmanRightPoint(){
		// arrange
		Location l1 = new Location(0, 0), l2 = new Location(0, 1);
		setBoardAndDirection( l1, Game.Direction.RIGHT, new String[] {"P-"} );
		
		// action
		game.move();
		
		// assert
		assertEquals( Type.NULL , this.game.getCreatureAt(l1).getType() );
		assertEquals( Type.PACMAN , this.game.getCreatureAt(l2).getType() );
	}

	@Test
	public void PacmanRightGhost(){
		// arrange
		setBoardAndDirection( new Location(0, 0), Game.Direction.RIGHT, new String[] {"PG"} );
		
		// action
		game.move();
		
		// assert
		assertEquals(2, game.getPacmanLives());
	}
	
	@Test
	public void PacmanRightNull(){
		// arrange
		Location l1 = new Location(0, 0), l2 = new Location(0, 1);
		setBoardAndDirection( l1, Game.Direction.RIGHT, new String[] {"P."} );
		
		// action
		game.move();
		
		// assert
		assertEquals( Type.NULL, this.game.getCreatureAt(l1).getType() );
		assertEquals( Type.PACMAN , this.game.getCreatureAt(l2).getType() );
	}

	@Test
	public void PacmanOutOfBoard_Right(){
		// arrange
		Location l1 = new Location(0, 0), l2 = new Location(0, 1);
		setBoardAndDirection( l2, Game.Direction.RIGHT, new String[] {"-P"} );		
		
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
		Location l1 = new Location(0, 0), l2 = new Location(0, 1);
		setBoardAndDirection( l2, Game.Direction.LEFT, new String[] {"-P"} );
		
		// action
		game.move();
				
		// assert
		assertEquals( Type.PACMAN, this.game.getCreatureAt(l1).getType() );
		assertEquals( Type.NULL, this.game.getCreatureAt(l2).getType() );
	}

	@Test
	public void PacmanOutOfBoard_Left(){
		// arrange
		Location l1 = new Location(0, 0), l2 = new Location(0, 1);
		setBoardAndDirection( l1, Game.Direction.LEFT, new String[] {"P."} );
		
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
		setBoardAndDirection( new Location(1, 1), Game.Direction.UP, new String[] {"G-", "WP"} );
		
		// action
		game.move();
				
		// assert
		assertEquals(2, game.getPacmanLives());
	}

	// Down
	
	@Test
	public void PacmanDownWall(){
		// arrange
		Location l1 = new Location(0, 0), l2 = new Location(1, 0);
		setBoardAndDirection( l1, Game.Direction.DOWN, new String[] {"PW", "W-"} );
		
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
		Location l1 = new Location(0, 0), l2 = new Location(0, 1);
		setBoardAndDirection( l1, Game.Direction.RIGHT, new String[] {"GW"} );
		
		// action
		game.move();
				
		// assert
		assertEquals( Type.GHOST, this.game.getCreatureAt(l1).getType() );
		assertEquals( Type.WALL, this.game.getCreatureAt(l2).getType() );
	}

	@Test
	public void GhostRightPoint(){
		// arrange
		Location l1 = new Location(0, 0), l2 = new Location(0, 1);
		setBoardAndDirection( l1, Game.Direction.RIGHT, new String[] {"G-"} );
		
		// action
		game.move();
				
		// assert
		assertEquals( Type.NULL, this.game.getCreatureAt(l1).getType() );
		assertEquals( Type.GHOST, this.game.getCreatureAt(l2).getType() );
	}

	@Test
	public void GhostRightPoint_SecondTurn() {
		// arrange
		Location l1 = new Location(0, 0), l2 = new Location(0, 1), l3 = new Location(0, 2);
		setBoardAndDirection( l1, Game.Direction.RIGHT, new String[] {"G-."} );
		
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
		setBoardAndDirection( new Location(0, 0), Game.Direction.RIGHT, new String[] {"GP"} );
		
		// action
		game.move();
				
		// assert
		assertEquals(2, game.getPacmanLives());
	}

	@Test
	public void GhostRightGhost(){
		// arrange
		Location l1 = new Location(0, 0), l2 = new Location(0, 1);
		setBoardAndDirection( l1, Game.Direction.RIGHT, new String[] {"GG"} );
		
		// action
		game.move();
				
		// assert
		assertEquals( Type.GHOST, this.game.getCreatureAt(l1).getType() );
		assertEquals( Type.GHOST, this.game.getCreatureAt(l2).getType() );
	}

	// Left
	
	@Test
	public void GhostLeftBigPoint(){
		// arrange
		Location l1 = new Location(0, 2), l2 = new Location(0, 1), l3 = new Location(0, 0);
		setBoardAndDirection( l1, Game.Direction.LEFT, new String[] {".BG"} );
		
		// action
		game.move();
		game.move();
				
		// assert
		assertEquals( Type.GHOST, this.game.getCreatureAt(l3).getType() );
		assertEquals( Type.BIG_POINT, this.game.getCreatureAt(l2).getType() );
	}

	@Test
	public void GhostLeftGhost(){
		// arrange
		Location l1 = new Location(0, 0), l2 = new Location(0, 1);
		setBoardAndDirection( l1, Game.Direction.LEFT, new String[] {"GG"} );
		
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
		Location l1 = new Location(0, 0), l2 = new Location(1, 0);
		setBoardAndDirection( l2, Game.Direction.UP, new String[] {".", "G"} );
		
		// action
		game.move();
				
		// assert
		assertEquals( Type.GHOST, this.game.getCreatureAt(l1).getType() );
		assertEquals( Type.NULL, this.game.getCreatureAt(l2).getType() );
	}

	@Test
	public void GhostOutOfBoard_Up(){
		// arrange
		Location l1 = new Location(0, 0), l2 = new Location(1, 0);
		setBoardAndDirection( l1, Game.Direction.UP, new String[] {"G", "."} );
		
		// action
		game.move();
		
		// assert
		assertEquals( Type.GHOST , this.game.getCreatureAt(l2).getType() );
		assertEquals( Type.NULL , this.game.getCreatureAt(l1).getType() );
	}
	
	// Down

	@Test
	public void GhostDownPoint(){
		// arrange
		Location l1 = new Location(0, 0), l2 = new Location(1, 0);
		setBoardAndDirection( l1, Game.Direction.DOWN, new String[] {"G", "-"} );
		
		// action
		game.move();
				
		// assert
		assertEquals( Type.NULL, this.game.getCreatureAt(l1).getType() );
		assertEquals( Type.GHOST, this.game.getCreatureAt(l2).getType() );
	}

	@Test
	public void GhostOutOfBoard_Down(){
		// arrange
		Location l1 = new Location(1, 0), l2 = new Location(0, 0);
		setBoardAndDirection( l1, Game.Direction.DOWN, new String[] {"-", "G"} );
		
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
		Location l1 = new Location(0, 0), l2 = new Location(0, 1);
		setBoard_Direction_Change( l2, Game.Direction.LEFT, new String[] {"-P", "-W"}, Game.Direction.DOWN );
		
		// action
		game.move();
		
		// assert
		assertEquals( Type.NULL , this.game.getCreatureAt(l2).getType() );
		assertEquals( Type.PACMAN , this.game.getCreatureAt(l1).getType() );
	}

	@Test
	public void PacmanLeft_Down_Changing(){
		// arrange
		Location l1 = new Location(0, 0), l2 = new Location(1, 0);
		setBoard_Direction_Change( l1, Game.Direction.LEFT, new String[] {"P-", "-W"}, Game.Direction.DOWN );

		// action
		game.move();
		
		// assert
		assertEquals( Type.NULL , this.game.getCreatureAt(l1).getType() );
		assertEquals( Type.PACMAN , this.game.getCreatureAt(l2).getType() );
	}
	
	@Test
	public void PacmanLeft_Down_SaveChange(){
		// arrange
		Location l1 = new Location(0, 0), l2 = new Location(1, 0), l3 = new Location(0, 1);
		setBoard_Direction_Change( l3, Game.Direction.LEFT, new String[] {"-P", "-W"}, Game.Direction.DOWN );
		
		// action
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
		Location l1 = new Location(0, 0), l2 = new Location(0, 1);
		setBoard_Direction_Change( l2, Game.Direction.LEFT, new String[] {"-G", "-W"}, Game.Direction.DOWN );
		
		// action
		game.move();
		
		// assert
		assertEquals( Type.NULL , this.game.getCreatureAt(l2).getType() );
		assertEquals( Type.GHOST , this.game.getCreatureAt(l1).getType() );
	}
	
	@Test
	public void GhostLeft_Down_Changing(){
		// arrange
		Location l1 = new Location(0, 0), l2 = new Location(1, 0);
		setBoard_Direction_Change( l1, Game.Direction.LEFT, new String[] {"G-", "-W"}, Game.Direction.DOWN );
		
		// action
		game.move();
		
		// assert
		assertEquals( Type.NULL , this.game.getCreatureAt(l1).getType() );
		assertEquals( Type.GHOST , this.game.getCreatureAt(l2).getType() );
	}
	
	@Test
	public void GhostLeft_Down_SaveChange(){
		// arrange
		Location l1 = new Location(0, 0), l2 = new Location(1, 0), l3 = new Location(0, 1);
		setBoard_Direction_Change( l3, Game.Direction.LEFT, new String[] {"-G", "-W"}, Game.Direction.DOWN );
		
		// action
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
		Location l1 = new Location(0, 1), l2 = new Location(1, 1), l3 = new Location(0, 2);
		setBoardAndDirection( l3, Game.Direction.LEFT, new String[] {"W-G", "W-W", "WWW"} );
		
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
	
	// game locations to update
	
	@Test
	public void updatedLocations_2() {
		// arrange
		Location l1 = new Location(0, 1), l2 = new Location(0, 0);
		setGameBoardByStringArray( new String[] {"-P"} );
		Location[] expected = new Location[] {
				l1, l2
		};
		
		// action
		game.move();
		Location[] actual = game.getLocationsToUpdate();
		
		// assert
		for (int i = 0; i < expected.length; i++)
			assertEquals( expected[i].toString(), actual[i].toString() );
		
		assertEquals(expected.length, actual.length);
	}

	@Test
	public void updatedLocations_4() {
		// arrange
		setGameBoardByStringArray( new String[] {"-P", "-G"} );
		Location[] expected = new Location[] {
				new Location(0, 1), new Location(0, 0),
				new Location(1, 1), new Location(1, 0)
		};
		
		// action
		game.move();
		Location[] actual = game.getLocationsToUpdate();
		
		// assert
		for (int i = 0; i < expected.length; i++)
			assertEquals( expected[i].toString(), actual[i].toString() );
		
		assertEquals(expected.length, actual.length);
	}
	
	@Test
	public void updatedLocations_8() {
		// arrange
		setGameBoardByStringArray( new String[] {"-P", "-G", "WW", "WG", "-G", ".G"} );
		Location[] expected = new Location[] {
				new Location(0, 1), new Location(0, 0),
				new Location(1, 1), new Location(1, 0),
				new Location(4, 1), new Location(4, 0),
				new Location(5, 1), new Location(5, 0)
		};
		
		// action
		game.move();
		Location[] actual = game.getLocationsToUpdate();
		
		// assert
		for (int i = 0; i < expected.length; i++)
			assertEquals( expected[i].toString(), actual[i].toString() );
		
		assertEquals(expected.length, actual.length);
	}

	// Pacman lives
	
	@Test
	public void pacmanDeadOnce() {
		// arrange
		setGameBoardByStringArray( new String[] {"G.P"} );
		Location l1 = new Location(0, 0), l2 = new Location(0, 1), l3 = new Location(0, 2);
		Ghost g = (Ghost) game.getCreatureAt(l1);
		g.setDirection(Game.Direction.RIGHT);
		
		// action
		game.move();
		
		// assert
		assertEquals( Type.PACMAN, game.getCreatureAt(l3).getType() );
		assertEquals( Type.NULL, game.getCreatureAt(l2).getType() );
		assertEquals( Type.GHOST, game.getCreatureAt(l1).getType() );
	}
	
	@Test
	public void pacmanDeadThreeTimes() {
		// arrange
		setGameBoardByStringArray( new String[] {"G.P"} );
		Location l1 = new Location(0, 0);
		Ghost g = (Ghost) game.getCreatureAt(l1);
		g.setDirection(Game.Direction.RIGHT);
		
		// action
		for (int i = 0; i < 3; i++)
			game.move();
		
		// assert
		assertFalse( game.gameOn );
	}
		
	// Pacman vs Ghost
	
	@Test
	public void PacmanEatsGhost() {
		// arrange
		Location l1 = new Location(0, 0);
		setBoardAndDirection( l1, Game.Direction.RIGHT, new String[] {"G.BP"} );
		
		// action
		game.move();
		game.move();

		// assert
		assertEquals( 3, game.getPacmanLives() );
	}

}
