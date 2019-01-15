import java.util.Timer;
import java.util.TimerTask;

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
	
	enum Direction{
		Right,
		Left,
		Up,
		Down
	}
	
	private void movePacman(Board.Creature c){
		Location currentLocation = c.getLocation();
		Location nextLocation = changeLocationByDirection(currentLocation.getX(), currentLocation.getY(), c.getDirection());
		
		nextLocation.setX( setDimensionOutOfBoard( nextLocation.getX(), topBoard.getDimensions() ) );
		nextLocation.setY( setDimensionOutOfBoard( nextLocation.getY(), topBoard.getDimensions() ) );
		
		switch ( getCreatureAt(nextLocation) ){
		case Point:
			// score ++
		case Null:
			topBoard.set(currentLocation, Board.Creature.Null);
			topBoard.set(nextLocation, Board.Creature.Pacman);
			botBoard.set(nextLocation, Board.Creature.Null);
			break;
			
		case Ghost1:
		case Ghost2:
		case Ghost3:
		case Ghost4:
			stopGame(Board.Creature.Pacman);
			break;
			
		default:
			break;
		}
	}

	public int setDimensionOutOfBoard(int location, int dimension) {
		// This method relies on the equality of the board dimensions
		if (location >= dimension)
			return 0;
		
		if (location < 0)
			return dimension - 1;
		
		return location;
	}
	
	private void moveGhost(Board.Creature c){
		
		int ghostNum = 1;
		try{
			ghostNum = Integer.parseInt( c.toString().substring( c.toString().length() - 1, c.toString().length() ) );
		} catch (NumberFormatException e){
			e.printStackTrace();
		}
		Location currentLocation = c.getLocation();
		Location nextLocation = changeLocationByDirection(currentLocation.getX(), currentLocation.getY(), c.getDirection());
		
		nextLocation.setX( setDimensionOutOfBoard( nextLocation.getX(), topBoard.getDimensions() ) );
		nextLocation.setY( setDimensionOutOfBoard( nextLocation.getY(), topBoard.getDimensions() ) );
		
		switch ( getCreatureAt(nextLocation)){
			case Point:
			case Null:
				topBoard.set( currentLocation, Board.Creature.Null );
				topBoard.set( nextLocation, getGhostCreatureByNum(ghostNum) );
				break;
			case Pacman:
				stopGame( getGhostCreatureByNum(ghostNum) );
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

	public Board.Creature getCreatureAt(Location l){
		
		// If the creature is null on the top, it will return the creature on the bottom board
		// Otherwise method returns pacman / ghost from top board
		
		Board.Creature topBoardCreature = this.topBoard.get(l);
		
		if (topBoardCreature == Board.Creature.Null) 
			return this.botBoard.get(l);
		else
			return topBoardCreature;
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
	
	public void move(){
				
		if (Board.Creature.Pacman.getLocation() != null)
			movePacman( Board.Creature.Pacman );
		
		for (int i = 0; i < this.topBoard.getGhostNum(); i++)
			moveGhost( getGhostCreatureByNum(i + 1) );
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

}
