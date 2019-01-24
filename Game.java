package pacman;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class Game{
	
	private Board topBoard, botBoard;
	
	boolean gameOn;
	
	public static final Direction defaultDirection = Direction.Left;
	
	Timer moveTimer;
	
	public Game(){
		this(null); // prevents duplicate constructors and mistakes
	}
	
	public Game(final String[] b){
		this.gameOn = false;
		this.moveTimer = new Timer();
				
		this.topBoard = new Board(b);
		this.topBoard.limitToSpecificCreatures(new Board.Creature[]{
				Board.Creature.Ghost1, Board.Creature.Ghost2, Board.Creature.Ghost3,
				Board.Creature.Ghost4, Board.Creature.Pacman});
		
		this.botBoard = new Board(b);
		this.botBoard.limitToSpecificCreatures(new Board.Creature[]{
				Board.Creature.Wall, Board.Creature.Point, Board.Creature.Null} );
	
	}	
	
	public enum Direction{
		Right,
		Left,
		Up,
		Down
	}
	
	private void movePacman(){	
		Board.Creature p = Board.Creature.Pacman;
		Location currentLocation = p.getLocation();
		Location nextLocation = getNextLocation(p, p.getDirection());
		
		switch ( getCreatureAt(nextLocation) ){
		case Point:
			// score ++
		case Null:
			topBoard.set(currentLocation, Board.Creature.Null);
			topBoard.set(nextLocation, p);
			botBoard.set(nextLocation, Board.Creature.Null);
			break;
			
		case Ghost1:
		case Ghost2:
		case Ghost3:
		case Ghost4:
			stopGame( p );
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
		// This method relies on the equality of the board dimensions
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
			case Point:
			case Null:
				topBoard.set( currentLocation, Board.Creature.Null );
				topBoard.set( nextLocation, c );
				break;
			case Pacman:
				stopGame( c );
				break;

			default:
				break;					
		}
		
		
	}

	private Board.Creature getGhostCreatureByNum(int num){
		switch (num){
			case 1:
				return Board.Creature.Ghost1;
			case 2:
				return Board.Creature.Ghost2;
			case 3:
				return Board.Creature.Ghost3;
			case 4:
				return Board.Creature.Ghost4;
				
			default:
				return null; // might cause problems
		}
	}
	
	private Location changeLocationByDirection(int x, int y, Direction d){
		switch (d){
		case Right:
			y ++;
			break;
		case Left:
			y --;
			break;
		case Down:
			x ++;
			break;
		case Up:
			x --;
			break;
		}
		return new Location(x, y);
	}
	
	private void stopGame(Board.Creature gameStopper){
		// in actual game, I will stop the game using this function
		
		System.out.print("Game Stopped - ");
		if (gameStopper == Board.Creature.Pacman)
			System.out.println("Pacman Commited Suicide");
		else
			System.out.println(gameStopper.toString() + " Ate Pacman");
		
		this.moveTimer.cancel();
		this.gameOn = false;
	}
	
	private Vector<Direction> findNewPathForGhost(Board.Creature ghost){
		Vector<Direction> possibleDirections = new Vector<>(0, 1);
		
		Direction previousDirection = ghost.getDirection();
		
		for (Direction d : Direction.values()){
			if ( getCreatureAt( getNextLocation(ghost, d) ) != Board.Creature.Wall &&
					d != previousDirection)
				possibleDirections.add(d);
		}
		
		if (possibleDirections.capacity() == 0)
			possibleDirections.add( getCreatureAt( getNextLocation( ghost, previousDirection ) )
					!= Board.Creature.Wall ? previousDirection : defaultDirection);
		
		return possibleDirections;
		
	}
	
	public Board.Creature getCreatureAt(Location l){
		
		// If the creature is null on the top, it will return the creature on the bottom board
		// Otherwise method returns pacman / ghost from top board
		
		Board.Creature topBoardCreature = this.topBoard.get(l);
		
		if (topBoardCreature == Board.Creature.Null) 
			return this.botBoard.get(l);
		else
			return topBoardCreature;
	}
	
	public void move(){
					
		if (Board.Creature.Pacman.getLocation() != null){
			changeDirection(Board.Creature.Pacman.getNextDirection(), Board.Creature.Pacman);
			movePacman();
		}
		
		for (int i = 0; i < this.topBoard.getGhostNum(); i++){
			Board.Creature ghost = getGhostCreatureByNum(i + 1);
			
			int possibleWays = findNewPathForGhost( ghost ).capacity();
			if ( possibleWays > 1  && getCreatureAt( getNextLocation(ghost, ghost.getDirection()) ) == Board.Creature.Wall ) {
				ghost.setNextDirection( findNewPathForGhost( ghost ).get(possibleWays - 1) );
			}
			
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
		
		if ( getCreatureAt( nextLocation ) != Board.Creature.Wall ){
			wantsAnotherDirection.setDirection(goThisDirection);
			wantsAnotherDirection.setNextDirection(null);
		}
		else {
			wantsAnotherDirection.setNextDirection(goThisDirection);
		}
		
	}
	
}
