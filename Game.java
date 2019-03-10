package pacman;

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
		
	public void stopGame() {
		this.gameOn = false;
	}
	
	public Location getNextLocation( MovingCreature mC, Direction d ) {
		Location nextLocation = changeLocationByDirection(mC.getLocation().getX(), mC.getLocation().getY(), d);
		
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
			
		for (MovingCreature mC : topBoard.getMovingCreatures())
			mC.move(this);		
	}
	
	public void set( Location current, Location next, MovingCreature mC ) {
		topBoard.set(current, new Null());
		topBoard.set(next, mC);
		
		if (mC.getType() == Type.PACMAN)
			botBoard.set(next, new Null());
	}

}
