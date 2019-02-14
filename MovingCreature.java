package pacman;

import pacman.Game.Direction;

public abstract class MovingCreature extends Creature implements Moveable{
	
	private Location location;
	private Game.Direction direction;
	private Game.Direction nextDirection;
		
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

	abstract void move(Game game);
	
}
