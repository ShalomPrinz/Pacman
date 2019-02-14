package pacman;

import java.util.Vector;

import pacman.Creature.Type;

public class Game{
	
	private Board topBoard, botBoard;
	
	boolean gameOn;
	
	public static final Direction defaultDirection = Direction.LEFT;
		
	public Game() {
		this(null); // prevents duplicate constructors and mistakes
	}
	
	public Game( final String[] b ) {
		this.gameOn = false;
				
		this.topBoard = new Board(b);
		this.topBoard.limitToSpecificCreatures( new Type[]{
				Type.GHOST, Type.PACMAN });
		
		this.botBoard = new Board(b);
		this.botBoard.limitToSpecificCreatures(new Type[]{
				Type.WALL, Type.POINT, Type.NULL } );
	
	}	
	
	public enum Direction {
		RIGHT,
		LEFT,
		UP,
		DOWN
	}
	
	private int setDimensionOutOfBoard( int location, int dimension ) {
		if (location >= dimension)
			return 0;
		
		if (location < 0)
			return dimension - 1;
		
		return location;
	}
	
	private void moveGhost( Ghost g ){
		
		Location currentLocation = g.getLocation();
		Location nextLocation = getNextLocation(g, g.getDirection());
		
		switch ( getCreatureAt(nextLocation).getType()){
			case POINT:
			case NULL:
				topBoard.set( currentLocation, new Null() );
				topBoard.set( nextLocation, g );
				break;
			case PACMAN:
				stopGame();
				break;

			default:
				break;					
		}
		
		
	}
	
	private Location changeLocationByDirection(int x, int y, Direction d){
		switch (d){
		case RIGHT:
			y ++;
			break;
		case LEFT:
			y --;
			break;
		case DOWN:
			x ++;
			break;
		case UP:
			x --;
			break;
		}
		return new Location(x, y);
	}
		
	private Vector<Direction> findNewPathForGhost( Ghost ghost ) {
		Vector<Direction> possibleDirections = new Vector<>(0, 1);
		
		Direction oppositeDirection = getOppositeDirection( ghost.getDirection() );
		
		for (Direction d : Direction.values()){
			if ( getCreatureAt( getNextLocation(ghost, d) ).getType() != Type.WALL &&
					d != oppositeDirection)
				possibleDirections.add(d);
		}
		
		if (possibleDirections.capacity() == 0)
			possibleDirections.add( getCreatureAt( getNextLocation( ghost, oppositeDirection ) ).getType()
					!= Type.WALL ? oppositeDirection : defaultDirection);
		
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
				return defaultDirection;
		}
	}
	
	public void stopGame() {
		this.gameOn = false;
	}
	
	public Location getNextLocation( MovingCreature Mc, Direction d ) {
		Location nextLocation = changeLocationByDirection(Mc.getLocation().getX(), Mc.getLocation().getY(), d);
		
		int Xdim = topBoard.getDimensions('X'), Ydim = topBoard.getDimensions('Y');
		
		nextLocation.setX( setDimensionOutOfBoard( nextLocation.getX(), Xdim ) );
		nextLocation.setY( setDimensionOutOfBoard( nextLocation.getY(), Ydim ) );
		
		return nextLocation;
	}
	
	public Creature getCreatureAt( Location l ) {
		
		// If the creature is null on the top, it will return the creature on the bottom board
		// Otherwise method returns pacman / ghost from top board
		
		Creature topBoardCreature = this.topBoard.get(l);
		
		if (topBoardCreature.getType() == Type.NULL) 
			return this.botBoard.get(l);
		else
			return topBoardCreature;
	}
	
	public void move() {
			
		Ghost[] ghosts = topBoard.getGhosts();
		Pacman pacman = topBoard.getPacman();
		
		if ( pacman != null ) 
			pacman.move(this);
		
		for ( int i = 0; i < ghosts.length; i++ ) {		
			
			int possibleWays = findNewPathForGhost( ghosts[i] ).capacity();
			
			if ( getCreatureAt( getNextLocation( ghosts[i], ghosts[i].getDirection() ) ).getType() == Type.WALL ) {
				int randomWay = (int) (Math.random() * possibleWays);
				ghosts[i].setNextDirection( findNewPathForGhost( ghosts[i] ).get( randomWay ) );
			}
			
			changeDirection( ghosts[i].getNextDirection(), ghosts[i] );
			moveGhost( ghosts[i] );
		}
		
	}
	
	public void changeDirection( Direction goThisDirection, MovingCreature wantsAnotherDirection ) {
		
		if (goThisDirection == null || wantsAnotherDirection.getLocation() == null)
			return;
		
		Location nextLocation = getNextLocation(wantsAnotherDirection, goThisDirection);
		
		if ( getCreatureAt( nextLocation ).getType() != Type.WALL ){
			wantsAnotherDirection.setDirection(goThisDirection);
			wantsAnotherDirection.setNextDirection(null);
		}
		else {
			wantsAnotherDirection.setNextDirection(goThisDirection);
		}
		
	}
	
	public void setByPacman( Location current, Location next ) {
		botBoard.set(next, new Null());
		topBoard.set(current, new Null());
		topBoard.set(next, topBoard.getPacman());
	}
}
