package pacman;

import pacman.Game.Direction;

public abstract class MovingCreature extends Creature implements Moveable{
	
	private Location location;
	private Game.Direction direction;
	private Location initialLocation;
	private Game.Direction nextDirection;
	
	// Implemented methods
	
	@Override
	public Location getLocation() {
		return location;
	}

	@Override
	public void setLocation(Location location) {
		this.location = location;
	}

	@Override
	public Direction getDirection() {
		return this.direction;
	}

	@Override
	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	@Override
	public Direction getNextDirection() {
		return this.nextDirection;
	}

	@Override
	public void setNextDirection(Direction nextDirection) {
		this.nextDirection = nextDirection;
	}

	@Override
	public Location getInitialLocation() {
		return this.initialLocation;
	}

	@Override
	public void setInitialLocation(Location initialLocation) {
		this.initialLocation = initialLocation;
	}
	
	public void changeDirection(Direction newDirection, Game game){
		
		if (newDirection == null || this.getLocation() == null)
			return;
		
		Location nextLocation = game.getNextLocation(this, newDirection);
		
		if ( game.getCreatureAt( nextLocation ).getType() != Type.WALL ){
			this.setDirection(newDirection);
			this.setNextDirection(null);
		}
		else 
			this.setNextDirection(newDirection);
	}
	
	// Not implemented methods 
	
	abstract void move(Game game);
	
}
