
public class Game{
	
	private Board topBoard, botBoard;
	
	boolean gameOn;
		
	public Game(){
		this(null); // prevents duplicate constructors and mistakes
	}
	
	public Game(final String[] b){
		this.gameOn = true;
				
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
	
	
	public void movePacman(Location l, Direction d){
		Location nL = changeLocationByDirection(l.getX(), l.getY(), d);
		
		switch ( getCreatureAt(nL) ){
		case Point:
			// score ++
		case Null:
			topBoard.set(l, Board.Creature.Null);
			topBoard.set(nL, Board.Creature.Pacman);
			botBoard.set(nL, Board.Creature.Null);
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
	
	
	public void moveGhost(Location l, Direction d, int ghostNum){
		Location nL = changeLocationByDirection(l.getX(), l.getY(), d);
		
		switch ( getCreatureAt(nL)){
			case Point:
			case Null:
				topBoard.set(l, Board.Creature.Null);
				topBoard.set( nL, getGhostCreatureByNum(ghostNum) );
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
		
		System.out.print("game stopped - ");
		if (gameStopper == Board.Creature.Pacman)
			System.out.println("pacman commited suicide");
		else
			System.out.println(gameStopper.toString() + " ate pacman");
		
		this.gameOn = false;
	}
	
	
	
}
