
public class Game {
	
	Board.Creatures[][] board = new Board().getBoard();
	
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
		
		if (this.board[nX][nY] == Board.Creatures.Point){
			this.board[x][y] = Board.Creatures.Null;
			this.board[nX][nY] = Board.Creatures.Ghost1;
			// remember point at (nX, nY)
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
