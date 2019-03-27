package pacman;

import java.util.Vector;

import pacman.Game.Direction;

public class DeadGhost extends Ghost {
	
	public Direction findPath(Game game, Location goal) {
				
		for (Direction d : Direction.values()) {
			Location nextLocation = game.getNextLocation(getLocation(), d);
			if ( nextLocation.toString().equals( goal.toString() ) )
				return d;
		}
		
		return game.defaultDirection;
	}
	
}
