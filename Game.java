
public class Game {
	
	Board.Creatures[][] board;
	
	Location[] savePoints;
	
	int pointsSavedNum;
	
	public Game(){
		this.board = new Board().getBoard();
		this.savePoints = new Location[]{null, null, null, null}; // 4 - Ghosts number
		this.pointsSavedNum = 0;
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
			this.board[x][y] = Board.Creatures.Null;
			// pacman eaten - game over
			break;
			
		default:
			break;
		}

	}
	
	public void moveGhost(Location l, Directions d){
		// wall - nothing
		int x = l.getX(), y = l.getY();
		int nX = changeLocationByDirection(x, y, d).getX(),
				nY = changeLocationByDirection(x, y, d).getY();
		
		if (this.board[nX][nY] == Board.Creatures.Point || 
				this.board[nX][nY] == Board.Creatures.Null){
			
			this.board[x][y] = Board.Creatures.Null;
			this.board[nX][nY] = Board.Creatures.Ghost1;
			
			// if point - add to save array
			this.savePoints[pointsSavedNum] = new Location(nX, nY);
			pointsSavedNum ++;
		}
		
		relocatePoints();
		
		sortNullsOnArray();
	}

	public void sortNullsOnArray() {	
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

	public void relocatePoints() {
		int originalSaves = this.pointsSavedNum;
		
		for (int i = 0; i < originalSaves; i++){
			int x = savePoints[i].getX(), y = savePoints[i].getY();
			
			if (this.board[x][y] != Board.Creatures.Null) // checks if can relocate point
				continue;
			
			this.board[x][y] = Board.Creatures.Point;
			this.pointsSavedNum --;
		}
	}
	
	public Location changeLocationByDirection(int x, int y, Directions d){
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
}
