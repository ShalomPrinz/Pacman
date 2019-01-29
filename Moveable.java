package pacman;

public interface Moveable {
	
	Location getLocation();

	void setLocation(Location location);

	Game.Direction getDirection();

	void setDirection(Game.Direction direction);
	
	Game.Direction getNextDirection();
	
	void setNextDirection(Game.Direction nextDirection);
	
	Location getInitialLocation();
	
	void setInitialLocation(Location initialLocation);
	
}
