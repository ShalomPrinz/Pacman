package pacman;

import static org.junit.Assert.*;
//import org.junit.jupiter.api.Test;

import org.junit.Test;

import pacman.Creature.Type;
import pacman.Game.Direction;

public class PacmanTest {

	Game game;
	Pacman pacman;
	
	private void setGameBoardByStringArray(final String[] board){
		this.game = new Game(board);
	}
	
	private void setBoardAndDirection(Location l, Direction d, String[] board) {
		setGameBoardByStringArray(board);
		MovingCreature mC = (MovingCreature) game.getCreatureAt(l);
		mC.setDirection(d);
	}	
	
	// Pacman: Move
	
	// Right
	
	@Test
	public void PacmanRightWall(){
		// arrange
		Location l1 = new Location(0, 0), l2 = new Location(0, 1);
		setBoardAndDirection( l1, Game.Direction.RIGHT, new String[]{"PW"} );
		
		// action
		game.move();
		
		// assert
		assertEquals( Type.PACMAN, this.game.getCreatureAt(l1).getType() );
		assertEquals( Type.WALL, this.game.getCreatureAt(l2).getType() );
	}

}
