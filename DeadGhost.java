package pacman;

import pacman.Game.Direction;

public class DeadGhost extends Ghost {
	
	public Direction findPath(Location goal) {
		
		if (goal.getY() > getLocation().getY())
			return Direction.RIGHT;
		if (goal.getY() < getLocation().getY())
			return Direction.LEFT;
		if (goal.getX() > getLocation().getX())
			return Direction.DOWN;
		
		return Direction.UP;
	}
	
}
