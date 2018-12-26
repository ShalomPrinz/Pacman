
public class Game{
	
	private Board.Creatures[][] board;
	
	private Location[] savePoints;
	
	private int pointsSavedNum;
	
	boolean gameOn;
		
	public Game(){
		this(null); // prevents duplicate constructors and mistakes
	}
	
	public Game(final String[] b){
		this.savePoints = new Location[]{null, null, null, null}; // 4 - Ghosts number
		this.pointsSavedNum = 0;
		this.gameOn = true;
		
		if (b == null) // no specific board required
			this.board = new Board().getBoard();
		else
			this.board = new Board(b).getBoard();
	}
	
	enum Directions{
		Right,
		Left,
		Up,
		Down
	}
	
	public void movePacman(Location l, Directions d){
		int x = l.getX(), y = l.getY();
		int nX = changeLocationByDirection(x, y, d).getX(), 
				nY = changeLocationByDirection(x, y, d).getY();
		
		switch (this.board[nX][nY]){
		case Point:
			// score ++
		case Null:
			this.board[x][y] = Board.Creatures.Null;
			this.board[nX][nY] = Board.Creatures.Pacman;
			break;
			
		case Ghost1:
		case Ghost2:
		case Ghost3:
		case Ghost4:
			stopGame(Board.Creatures.Pacman);
			break;
			
		default:
			break;
		}
	}
	
	public void moveGhost(Location l, Directions d, int ghostNum){
		int x = l.getX(), y = l.getY();
		int nX = changeLocationByDirection(x, y, d).getX(),
				nY = changeLocationByDirection(x, y, d).getY();
		
		switch (this.board[nX][nY]){
			case Point:
				this.savePoints[pointsSavedNum] = new Location(nX, nY);
				pointsSavedNum ++;
			case Null:
				this.board[x][y] = Board.Creatures.Null;
				this.board[nX][nY] = getGhostCreatureByNum(ghostNum);
				break;
			case Pacman:
				stopGame(getGhostCreatureByNum(ghostNum));
				break;

			default:
				break;					
		}
		
		relocatePoints();
		
		sortNullsOnArray();
	}

	private Board.Creatures getGhostCreatureByNum(int num){
		switch (num){
			case 1:
				return Board.Creatures.Ghost1;
			case 2:
				return Board.Creatures.Ghost2;
			case 3:
				return Board.Creatures.Ghost3;
			case 4:
				return Board.Creatures.Ghost4;
				
			default:
				return null; // might cause problems
		}
	}
	
	private void sortNullsOnArray() {
		for (int i = 0; i < savePoints.length; i++){
			for (int j = savePoints.length - 1; j > i; j--){
				if (savePoints[i] == null){
					savePoints[i] = savePoints[j];
					savePoints[j] = null;
				}
				else
					break;
			}
		}
	}

	private void relocatePoints() {
		int originalSaves = this.pointsSavedNum;
		
		for (int i = 0; i < originalSaves; i++){
			int x = savePoints[i].getX(), y = savePoints[i].getY();
			
			if (this.board[x][y] != Board.Creatures.Null) // checks if can relocate point
				continue;
			
			this.savePoints[i] = null;
			this.board[x][y] = Board.Creatures.Point;
			this.pointsSavedNum --;
		}
	}
	
	private Location changeLocationByDirection(int x, int y, Directions d){
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

	public Board.Creatures getCreatureAt(Location l){
		return this.board[l.getX()][l.getY()];
	}

	private void stopGame(Board.Creatures gameStopper){
		// in actual game, I will stop the game using this function
		
		System.out.print("game stopped - ");
		if (gameStopper == Board.Creatures.Pacman)
			System.out.println("pacman commited suicide");
		else
			System.out.println(gameStopper.toString() + " ate pacman");
		
		this.gameOn = false;
	}

}
