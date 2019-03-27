package pacman;

import java.util.Vector;

import pacman.Game.Direction;

public class DeadGhost extends Ghost {
	
	public Direction findPath(Game game, Location goal) {
		
		Vector<Direction> directions = super.findNewPath(game);			
		
		int xDiff = goal.getX() - getLocation().getX();
		int yDiff = goal.getY() - getLocation().getY();
		
		if (xDiff != 0 && yDiff != 0)
			return directions.firstElement();
		
		if (yDiff > 0)
			return Direction.RIGHT;
		if (yDiff < 0)
			return Direction.LEFT;
		if (xDiff > 0)
			return Direction.DOWN;
	
		return Direction.UP;
	}
	
}
