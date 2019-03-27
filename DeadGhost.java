package pacman;

import java.util.Vector;

import pacman.Game.Direction;

public class DeadGhost extends Ghost {
	
	public Direction findPath(Game game, Location goal) {
		
		Vector<Direction> directions = super.findNewPath(game);			
		
		return directions.firstElement();
	}
	
}
