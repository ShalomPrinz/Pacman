package pacman;

import pacman.Game.Direction;

public class DeadGhost extends Ghost {
	
	public void findPath(Game game, Location goal) {
				
		for (Direction d : Direction.values()) {
			Location nextLocation = game.getNextLocation(getLocation(), d);
			if ( nextLocation.equals(goal) )
				setDirection(d);
		}
		
	}
	
}
