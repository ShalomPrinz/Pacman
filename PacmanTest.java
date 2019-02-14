package pacman;

import static org.junit.Assert.*;
//import org.junit.jupiter.api.Test;

import org.junit.Test;

import pacman.Creature.Type;

public class PacmanTest {

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
		pacman = (Pacman) this.game.getCreatureAt(l1);
		pacman.setDirection(Game.Direction.RIGHT);
		
		// action
		game.move( new MovingCreature[]{pacman} );
		
		// assert
		assertEquals( Type.PACMAN, this.game.getCreatureAt(l1).getType() );
		assertEquals( Type.WALL, this.game.getCreatureAt(l2).getType() );
	}

}
