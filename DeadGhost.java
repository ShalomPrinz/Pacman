package pacman;

import pacman.Game.Direction;

public class DeadGhost extends Ghost {
	
	public Direction findPath(Location goal) {
		
		return Direction.RIGHT;
		
	}
	
}
