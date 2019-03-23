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
		Location nextLoaction = game.getNextLocation( this.getLocation(), this.getDirection() );
		Creature nextCreature = game.getCreatureAt( nextLoaction );
		
		if ( nextCreature.getType() == Type.WALL ) {
			int randomWay = (int) (Math.random() * possibleWays);
			Direction nextDirection = findNewPath( game ).get( randomWay );
			this.setNextDirection(nextDirection);
		}
		
		changeDirection( game, this.getNextDirection(), this );	
		Location nextLocation = game.getNextLocation(this.getLocation(), this.getDirection());
		
		switch ( game.getCreatureAt(nextLocation).getType()){
			case POINT:
			case BIG_POINT:
			case NULL:
				game.set(this);
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
			Location nextLocation = game.getNextLocation(this.getLocation(), d);
			Creature nextCreature = game.getCreatureAt( nextLocation );
			
			if ( nextCreature.getType() != Type.WALL && d != oppositeDirection)
				possibleDirections.add(d);
		}
		
		if (possibleDirections.capacity() == 0) {
			Location nextLocation = game.getNextLocation( this.getLocation(), oppositeDirection );
			Creature nextCreature = game.getCreatureAt( nextLocation );
			
			possibleDirections.add( nextCreature.getType() == Type.WALL ? Game.defaultDirection : oppositeDirection);
		}
		
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
