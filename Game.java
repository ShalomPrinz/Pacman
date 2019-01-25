package pacman;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class Game{
	
	private Board topBoard, botBoard;
	
	private int score, lives;
	
	boolean gameOn;
	
	public static final Direction DEFAULT_DIRECTION = Direction.LEFT;
	public static final int POINT_SCORE = 10;
	
	Timer moveTimer;
	
	public Game(){
		this(null); // prevents duplicate constructors and mistakes
	}
	
	public Game(final String[] b){
		this.gameOn = false;
		this.score = 0;
		this.lives = 3;
		this.moveTimer = new Timer();
				
		this.topBoard = new Board(b);
		this.topBoard.limitToSpecificCreatures(new Board.Creature[]{
				Board.Creature.GHOST_1, Board.Creature.GHOST_2, Board.Creature.GHOST_3,
				Board.Creature.GHOST_4, Board.Creature.PACMAN});
		
		this.botBoard = new Board(b);
		this.botBoard.limitToSpecificCreatures(new Board.Creature[]{
				Board.Creature.WALL, Board.Creature.POINT, Board.Creature.NULL} );
	
	}	
	
	public enum Direction{
		RIGHT,
		LEFT,
		UP,
		DOWN
	}
	
	private void movePacman(){	
		Board.Creature p = Board.Creature.PACMAN;
		Location currentLocation = p.getLocation();
		Location nextLocation = getNextLocation(p, p.getDirection());
		
		switch ( getCreatureAt(nextLocation) ){
		case POINT:
			this.score += POINT_SCORE;
		case NULL:
			topBoard.set(currentLocation, Board.Creature.NULL);
			topBoard.set(nextLocation, p);
			botBoard.set(nextLocation, Board.Creature.NULL);
			break;
			
		case GHOST_1:
		case GHOST_2:
		case GHOST_3:
		case GHOST_4:
			pacmanDead();
			break;
			
		default:
			break;
		}
	}

	private Location getNextLocation(Board.Creature c, Direction d) {
		Location nextLocation = changeLocationByDirection(c.getLocation().getX(), c.getLocation().getY(), d);
		
		int Xdim = topBoard.getDimensions('X'), Ydim = topBoard.getDimensions('Y');
		
		nextLocation.setX( setDimensionOutOfBoard( nextLocation.getX(), Xdim ) );
		nextLocation.setY( setDimensionOutOfBoard( nextLocation.getY(), Ydim ) );
		
		return nextLocation;
	}

	private int setDimensionOutOfBoard(int location, int dimension) {
		if (location >= dimension)
			return 0;
		
		if (location < 0)
			return dimension - 1;
		
		return location;
	}
	
	private void moveGhost(Board.Creature c){
		
		Location currentLocation = c.getLocation();
		Location nextLocation = getNextLocation(c, c.getDirection());
		
		switch ( getCreatureAt(nextLocation)){
			case POINT:
			case NULL:
				topBoard.set( currentLocation, Board.Creature.NULL );
				topBoard.set( nextLocation, c );
				break;
			case PACMAN:
				pacmanDead();
				break;

			default:
				break;					
		}
		
		
	}

	private Board.Creature getGhostCreatureByNum(int num){
		switch (num){
			case 1:
				return Board.Creature.GHOST_1;
			case 2:
				return Board.Creature.GHOST_2;
			case 3:
				return Board.Creature.GHOST_3;
			case 4:
				return Board.Creature.GHOST_4;
				
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
	
	private void pacmanDead(){
		
		this.lives --;
		
		if (this.lives > 0) {
			restartBoard();
			return;
		}
		
		this.moveTimer.cancel();
		this.gameOn = false;
		// add score to highest_score list, with the player name
	}
	
	private void restartBoard() {
		
		// Assuming all the creatures in this array are in topBoard
		Board.Creature[] startAgain = {
			Board.Creature.GHOST_1, Board.Creature.GHOST_2, Board.Creature.GHOST_3,
			Board.Creature.GHOST_4, Board.Creature.PACMAN
		};
		
		for (Board.Creature c : startAgain) {
			topBoard.set( c.getLocation() , Board.Creature.NULL );
			topBoard.set( c.getInitialLocation() , c );
			c.setDirection( DEFAULT_DIRECTION );
		}
	}
	
	private Vector<Direction> findNewPathForGhost(Board.Creature ghost){
		Vector<Direction> possibleDirections = new Vector<>(0, 1);
		
		Direction oppositeDirection = getOppositeDirection( ghost.getDirection() );
		
		Board.Creature[] dontGoThere = {
			Board.Creature.GHOST_1, Board.Creature.GHOST_2, Board.Creature.WALL, 
			Board.Creature.GHOST_3, Board.Creature.GHOST_4,
		};
		
		for (Direction d : Direction.values()){			
			if ( ! Arrays.stream( dontGoThere ).anyMatch( getCreatureAt( getNextLocation(ghost, d) ) :: equals ) &&
					d != oppositeDirection)
				possibleDirections.add(d);
		}
		
		if (possibleDirections.capacity() == 0)
			possibleDirections.add(oppositeDirection);
		
		return possibleDirections;
	}
	
	private Direction getOppositeDirection(Direction d){
		switch (d) {
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
		return DEFAULT_DIRECTION;
	}
	
	public Board.Creature getCreatureAt(Location l){
		
		// If the creature is null on the top, it will return the creature on the bottom board
		// Otherwise method returns pacman / ghost from top board
		
		Board.Creature topBoardCreature = this.topBoard.get(l);
		
		if (topBoardCreature == Board.Creature.NULL) 
			return this.botBoard.get(l);
		else
			return topBoardCreature;
	}
	
	public void move(){
					
		if (Board.Creature.PACMAN.getLocation() != null){
			changeDirection(Board.Creature.PACMAN.getNextDirection(), Board.Creature.PACMAN);
			movePacman();
		}
		
		for (int i = 0; i < this.topBoard.getGhostNum(); i++){
			Board.Creature ghost = getGhostCreatureByNum(i + 1);
			
			int possibleWays = findNewPathForGhost( ghost ).capacity();
			ghost.setNextDirection( findNewPathForGhost( ghost ).get( (int) (Math.random() * possibleWays) ) );
			
			changeDirection( ghost.getNextDirection(), ghost );
			moveGhost( ghost );
		}
		
	}

	public void startGame(){
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

	public void changeDirection(Direction goThisDirection, Board.Creature wantsAnotherDirection){
		
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
	
	public int getScore() {
		return this.score;
	}

	public int getLives() {
		return this.lives;
	}
}
