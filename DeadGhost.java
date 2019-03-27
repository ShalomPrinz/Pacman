package pacman;

import pacman.Game.Direction;

public class DeadGhost extends Ghost {
	
	public Direction findPath(Location goal) {
		
		if (goal.getY() > getLocation().getY())
			return Direction.RIGHT;
		
		return Direction.LEFT;
		
	}
	
}
