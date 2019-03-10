package pacman;

import pacman.Game.Direction;

public abstract class MovingCreature extends Creature implements Moveable{
	
	private Location location;
	private Game.Direction direction;
	private Game.Direction nextDirection;
	
	public MovingCreature() {
		this.location = null;
		this.direction = null;
		this.nextDirection = null;
	}
	
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
	
	public void changeDirection( Game game, Direction newDirection, MovingCreature mC ) {
		
		if (newDirection == null)
			return;
		
		Location nextLocation = game.getNextLocation(mC.getLocation(), newDirection);
		Creature nextCreature = game.getCreatureAt( nextLocation );
		
		if ( nextCreature.getType() == Type.WALL )
			mC.setNextDirection(newDirection);
		else {
			mC.setDirection(newDirection);
			mC.setNextDirection(null);
		}	
	}
	
}
