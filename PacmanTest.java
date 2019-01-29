package pacman;

import static org.junit.Assert.*;

import org.junit.Test;

public class PacmanTest {

	@Test
	public void PacmanEatGhost() {
		// arrange
		Game game = new Game( new String[] {"PBG"} );
		Pacman pacman = findPacman( game, 1, 3 );
		pacman.setDirection(Game.Direction.RIGHT);
		
		// action
		game.move( new MovingCreature[] {pacman} );
		game.move( new MovingCreature[] {pacman} );
		
		// assert
		assertEquals( Creature.Type.PACMAN, game.getCreatureAt( new Location(0, 2) ).getType() );
	}
	
	@Test
	public void PacmanDieByGhost() {
		// arrange
		Game game = new Game( new String[] {"P.G"} );
		Pacman pacman = findPacman( game, 1, 3 );
		pacman.setDirection(Game.Direction.RIGHT);
		
		// action
		game.move( new MovingCreature[] {pacman} );
		game.move( new MovingCreature[] {pacman} );
		
		// assert
		assertEquals( Creature.Type.PACMAN, game.getCreatureAt( new Location(0, 0) ).getType() );
	}
	
	@Test
	public void PacmanTriesEatGhostTooLate() {
		// arrange
		Game game = new Game( new String[] {"PBG"} );
		Pacman pacman = findPacman( game, 1, 3 );
		pacman.setDirection(Game.Direction.RIGHT);
		long end = System.currentTimeMillis() + Game.GHOST_EATING_PERIOD + 100; 
		
		// action
		game.move( new MovingCreature[] {pacman} );
		while (System.currentTimeMillis() < end) ;
		game.move( new MovingCreature[] {pacman} );
		
		// assert
		assertEquals( Creature.Type.GHOST, game.getCreatureAt( new Location(0, 2) ).getType() );
	}
	
	private Pacman findPacman(Game game, int Xdim, int Ydim) {
		for (int i = 0; i < Xdim; i++) {
			for (int j = 0; j < Ydim; j++) {
				if (game.getCreatureAt( new Location(i, j) ).getType() == Creature.Type.PACMAN)
					return (Pacman) game.getCreatureAt( new Location(i, j));
			}
		}
		
		return null;
	}

}
