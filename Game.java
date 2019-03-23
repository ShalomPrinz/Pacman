package pacman;

import java.util.Vector;

import pacman.Creature.Type;

public class Game{
	
	private Board topBoard, botBoard;
	private int score, pacmanLives;
	boolean gameOn, ghostsEating;
	Vector<Location> move;
	
	public static final Direction defaultDirection = Direction.LEFT;
		
	public Game() {
		this(null); // prevents duplicate constructors and mistakes
	}
	
	public Game( final String[] b ) {
		this.gameOn = true;
		this.move = new Vector<>(0, 2);		
		this.pacmanLives = 3;
		this.ghostsEating = false;
		
		this.topBoard = new Board(b);
		this.topBoard.limitToSpecificCreatures( new Type[]{
				Type.GHOST, Type.PACMAN });
		
		this.botBoard = new Board(b);
		this.botBoard.limitToSpecificCreatures(new Type[]{
				Type.WALL, Type.POINT, Type.NULL, Type.BIG_POINT } );
	
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
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getPacmanLives() {
		return this.pacmanLives;
	}
	
	public void stopGame() {
		
		if (ghostsEating)
			return;
		
		pacmanLives --;
		if (pacmanLives == 0)
			this.gameOn = false;
		
		for (MovingCreature mC : topBoard.getMovingCreatures()) {
			topBoard.set(mC.getLocation(), new Null());
			topBoard.set(mC.getInitialLocation(), mC);
		}
		
	}
	
	public Location getNextLocation( Location movingCreatureLocation, Direction d ) {
		Location nextLocation = changeLocationByDirection( movingCreatureLocation.getX(), movingCreatureLocation.getY(), d );
		
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
			
		int turnLives = getPacmanLives();
		
		for (MovingCreature mC : topBoard.getMovingCreatures()) {
			mC.move(this);
			if (turnLives != getPacmanLives())
				break;
		}
					
	}
	
	public void set( MovingCreature mC ) {
		Location nextLocation = getNextLocation(mC.getLocation(), mC.getDirection());
		
		move.add(mC.getLocation());
		move.add(nextLocation);
		
		topBoard.set( mC.getLocation(), new Null() );
		topBoard.set( nextLocation, mC );
		
		if (mC.getType() == Type.PACMAN) {
			if ( botBoard.get( nextLocation ).getType() == Type.BIG_POINT )
				this.ghostsEating = true;
			botBoard.set(nextLocation, new Null());
		}
		
	}

	public Location[] getLocationsToUpdate() {
		return move.toArray( new Location[move.size()] );
	}
	
}
