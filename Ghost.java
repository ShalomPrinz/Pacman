package pacman;

import java.util.Arrays;
import java.util.Vector;

import pacman.Game.Direction;

public class Ghost extends MovingCreature{

	@Override
	Type getType() {
		return Type.GHOST;
	}
	
	@Override
	void move(Game game) {
		
		int possibleWays = findNewPath( game ).capacity();
		setNextDirection( findNewPath( game ).get( (int) (Math.random() * possibleWays) ) );
		super.changeDirection( this.getNextDirection(), game );
		
		Location currentLocation = this.getLocation();
		Location nextLocation = game.getNextLocation(this, this.getDirection());
		
		switch ( game.getCreatureAt(nextLocation).getType() ){
			case POINT:
			case NULL:
				game.set( currentLocation, nextLocation, this );
				break;
			case PACMAN:
				game.pacmanDead();
			default:
				break;					
		}
	}
	
	private Vector<Direction> findNewPath(Game game){
		
		Vector<Direction> possibleDirections = new Vector<>(0, 1);
		
		Direction oppositeDirection = getOppositeDirection();
		
		Type[] dontGoThere = {
			Type.GHOST, Type.WALL
		};
		
		for (Direction d : Direction.values()){			
			if ( ! Arrays.stream( dontGoThere ).anyMatch( game.getCreatureAt( game.getNextLocation(this, d) ).getType() :: equals ) &&
					d != oppositeDirection)
				possibleDirections.add(d);
		}
		
		if (possibleDirections.capacity() == 0)
			possibleDirections.add(oppositeDirection);
		
		return possibleDirections;
	}
	
	private Direction getOppositeDirection(){
		switch ( this.getDirection() ) {
	        case UP: 
	        	return Direction.DOWN;
	        case DOWN:  
	        	return Direction.UP;
	        case RIGHT:
	        	return Direction.LEFT;
	        case LEFT:
	        	return Direction.RIGHT;
		}
		
		// Impossible to get here, but java forces me to write it
		return Game.DEFAULT_DIRECTION;
	}
	
}
