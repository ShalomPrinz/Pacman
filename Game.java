package pacman;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class Game{
	
	private Board topBoard, botBoard;
	
	boolean gameOn;
	
	public static final Direction defaultDirection = Direction.LEFT;
	
	Timer moveTimer;
	
	public Game() {
		this(null); // prevents duplicate constructors and mistakes
	}
	
	public Game( final String[] b ) {
		this.gameOn = false;
		this.moveTimer = new Timer();
		Board.Creature.init();
				
		this.topBoard = new Board(b);
		this.topBoard.limitToSpecificCreatures( new Board.Creature[]{
				Board.MovingCreature.GHOST_1, Board.MovingCreature.GHOST_2, Board.MovingCreature.GHOST_3,
				Board.MovingCreature.GHOST_4, Board.MovingCreature.PACMAN});
		
		this.botBoard = new Board(b);
		this.botBoard.limitToSpecificCreatures(new Board.Creature[]{
				Board.Creature.WALL, Board.Creature.POINT, Board.Creature.NULL} );
	
	}	
	
	public enum Direction {
		RIGHT,
		LEFT,
		UP,
		DOWN
	}
	
	private void movePacman() {	
		Board.MovingCreature p = Board.MovingCreature.PACMAN;
		Location currentLocation = p.getLocation();
		Location nextLocation = getNextLocation(p, p.getDirection());
		
		switch ( getCreatureAt(nextLocation).getType() ){
		case POINT:
			// score ++
		case NULL:
			topBoard.set(currentLocation, Board.Creature.NULL);
			topBoard.set(nextLocation, p);
			botBoard.set(nextLocation, Board.Creature.NULL);
			break;
			
		case GHOST:
			stopGame( p );
			break;
			
		default:
			break;
		}
	}

	private Location getNextLocation( Board.MovingCreature Mc, Direction d ) {
		Location nextLocation = changeLocationByDirection(Mc.getLocation().getX(), Mc.getLocation().getY(), d);
		
		int Xdim = topBoard.getDimensions(), Ydim = Xdim;
		
		if (topBoard.get().length != topBoard.get()[0].length){
			Xdim = topBoard.get().length;
			Ydim = topBoard.get()[0].length;
		}
		
		nextLocation.setX( setDimensionOutOfBoard( nextLocation.getX(), Xdim ) );
		nextLocation.setY( setDimensionOutOfBoard( nextLocation.getY(), Ydim ) );
		
		return nextLocation;
	}

	private int setDimensionOutOfBoard(int location, int dimension) {
		// This method relies on the equality of the board dimensions
		if (location >= dimension)
			return 0;
		
		if (location < 0)
			return dimension - 1;
		
		return location;
	}
	
	private void moveGhost( Board.MovingCreature g ){
		
		Location currentLocation = g.getLocation();
		Location nextLocation = getNextLocation(g, g.getDirection());
		
		switch ( getCreatureAt(nextLocation).getType()){
			case POINT:
			case NULL:
				topBoard.set( currentLocation, Board.Creature.NULL );
				topBoard.set( nextLocation, g );
				break;
			case PACMAN:
				stopGame( g );
				break;

			default:
				break;					
		}
		
		
	}

	private Board.MovingCreature getGhostCreatureByNum( int num ) {
		switch (num){
			case 1:
				return Board.MovingCreature.GHOST_1;
			case 2:
				return Board.MovingCreature.GHOST_2;
			case 3:
				return Board.MovingCreature.GHOST_3;
			case 4:
				return Board.MovingCreature.GHOST_4;
				
			default:
				return null; // might cause problems
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
	
	private void stopGame( Board.MovingCreature gameStopper ) {
		// in actual game, I will stop the game using this function
		
		System.out.print("Game Stopped - ");
		if (gameStopper == Board.MovingCreature.PACMAN)
			System.out.println("Pacman Commited Suicide");
		else
			System.out.println(gameStopper.toString() + " Ate Pacman");
		
		this.moveTimer.cancel();
		this.gameOn = false;
	}
	
	private Vector<Direction> findNewPathForGhost( Board.MovingCreature ghost ) {
		Vector<Direction> possibleDirections = new Vector<>(0, 1);
		
		Direction oppositeDirection = getOppositeDirection( ghost.getDirection() );
		
		for (Direction d : Direction.values()){
			if ( getCreatureAt( getNextLocation(ghost, d) ) != Board.Creature.WALL &&
					d != oppositeDirection)
				possibleDirections.add(d);
		}
		
		if (possibleDirections.capacity() == 0)
			possibleDirections.add( getCreatureAt( getNextLocation( ghost, oppositeDirection ) )
					!= Board.Creature.WALL ? oppositeDirection : defaultDirection);
		
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
	
	public Board.Creature getCreatureAt( Location l ) {
		
		// If the creature is null on the top, it will return the creature on the bottom board
		// Otherwise method returns pacman / ghost from top board
		
		Board.Creature topBoardCreature = this.topBoard.get(l);
		
		if (topBoardCreature == Board.Creature.NULL) 
			return this.botBoard.get(l);
		else
			return topBoardCreature;
	}
	
	public void move() {
					
		if (Board.MovingCreature.PACMAN.getLocation() != null){
			changeDirection(Board.MovingCreature.PACMAN.getNextDirection(), Board.MovingCreature.PACMAN);
			movePacman();
		}
		
		for (int i = 0; i < this.topBoard.getGhostNum(); i++){
			Board.MovingCreature ghost = getGhostCreatureByNum(i + 1);
			
			int possibleWays = findNewPathForGhost( ghost ).capacity();
			if ( getCreatureAt( getNextLocation(ghost, ghost.getDirection()) ) == Board.Creature.WALL ) {
				int randomWay = (int) (Math.random() * possibleWays);
				ghost.setNextDirection( findNewPathForGhost( ghost ).get( randomWay ) );
			}
			
			changeDirection( ghost.getNextDirection(), ghost );
			moveGhost( ghost );
		}
		
	}

	public void startGame() {
		if (this.gameOn)
			return;
		
		this.gameOn = true;
		
		this.moveTimer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				move();
			}
			
		}, 0, 200);
		
	}

	public void changeDirection( Direction goThisDirection, Board.MovingCreature wantsAnotherDirection ) {
		
		if (goThisDirection == null || wantsAnotherDirection.getLocation() == null)
			return;
		
		Location nextLocation = getNextLocation(wantsAnotherDirection, goThisDirection);
		
		if ( getCreatureAt( nextLocation ) != Board.Creature.WALL ){
			wantsAnotherDirection.setDirection(goThisDirection);
			wantsAnotherDirection.setNextDirection(null);
		}
		else {
			wantsAnotherDirection.setNextDirection(goThisDirection);
		}
		
	}
	
}
