package pacman;

import java.util.Vector;

import pacman.Game.Direction;

public class Ghost extends MovingCreature {

	@Override
	Type getType() {
		return Type.GHOST;
	}
	
	@Override
	void move(Game game) {
		
		int possibleWays = findNewPath( game ).capacity();
		
		if ( game.getCreatureAt( game.getNextLocation( this, this.getDirection() ) ).getType() == Type.WALL ) {
			int randomWay = (int) (Math.random() * possibleWays);
			this.setNextDirection( findNewPath( game ).get( randomWay ) );
		}
		
		changeDirection( game, this.getNextDirection(), this );
		
		Location currentLocation = this.getLocation();
		Location nextLocation = game.getNextLocation(this, this.getDirection());
		
		switch ( game.getCreatureAt(nextLocation).getType()){
			case POINT:
			case NULL:
				game.set(currentLocation, nextLocation, this);
				break;
			case PACMAN:
				game.stopGame();
				break;

			default:
				break;					
		}
	}
	
	private Vector<Direction> findNewPath(Game game) {
		Vector<Direction> possibleDirections = new Vector<>(0, 1);
		
		Direction oppositeDirection = getOppositeDirection( this.getDirection() );
		
		for (Direction d : Direction.values()){
			if ( game.getCreatureAt( game.getNextLocation(this, d) ).getType() != Type.WALL &&
					d != oppositeDirection)
				possibleDirections.add(d);
		}
		
		if (possibleDirections.capacity() == 0)
			possibleDirections.add( game.getCreatureAt( game.getNextLocation( this, oppositeDirection ) ).getType()
					!= Type.WALL ? oppositeDirection : Game.defaultDirection);
		
		return possibleDirections;
		
	}
	
	private Direction getOppositeDirection( Direction d ) {
		switch(d) {
			case DOWN:
				return Direction.UP;
			case LEFT:
				return Direction.RIGHT;
			case RIGHT:
				return Direction.LEFT;
			case UP:
				return Direction.DOWN;
			default:
				return Game.defaultDirection;
		}
	}
	
}
