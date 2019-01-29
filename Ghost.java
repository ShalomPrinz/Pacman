package pacman;

import java.util.Arrays;
import java.util.Vector;

import pacman.Game.Direction;

public class Ghost extends MovingCreature implements ScoreIncrement{

	private final static int VALUE = 200;
	
	public Mode currentMode = Mode.ALIVE;
	
	public enum Mode{
		ALIVE,
		EATABLE,
		DEAD
	}
	
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
			case BIG_POINT:
			case POINT:
			case NULL:
				game.set( currentLocation, nextLocation, this );
				break;
			case PACMAN:
				switch (this.currentMode) {
					case EATABLE:
						game.setScore( game.getScore() + this.getValue() );
						this.currentMode = Mode.DEAD;
						break;
					case ALIVE:
						game.pacmanDead();
						break;
					default:
						break;
				}
				break;
			case REVIVOR:
				if (this.currentMode == Mode.DEAD)
					this.currentMode = Mode.ALIVE;
				game.set( currentLocation, nextLocation, this );
				break;
			default:
				break;					
		}
	}
	
	@Override
	public int getValue() {
		return VALUE;
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
		
//	@Override
//	public picture getPicture() {
//		switch ( this.currentMode ) {
//			choose picture by mode
//		}	
//	}
}
