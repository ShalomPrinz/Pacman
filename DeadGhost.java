package pacman;

import pacman.Game.Direction;

public class DeadGhost extends Ghost {
	
	public Direction findPath(Location goal) {
		
		int xDiff = goal.getX() - getLocation().getX();
		int yDiff = goal.getY() - getLocation().getY();
		
		if (yDiff > 0)
			return Direction.RIGHT;
		if (yDiff < 0)
			return Direction.LEFT;
		if (xDiff > 0)
			return Direction.DOWN;
		
		return Direction.UP;
	}
	
}
