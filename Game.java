
public class Game {
	
	Board mBoard = new Board();
	Board.Creatures[][] board = mBoard.getBoard();
	
	enum Directions{
		Right,
		Left,
		Up,
		Down
	}

	public void movePacman(int[] location, Directions d){
		int x = location[0], y = location[1];
		int nX = changeLocationByDirection(location, d)[0], nY = changeLocationByDirection(location, d)[1];
		
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
	
	public int[] changeLocationByDirection(int[] location, Directions d){
		int[] newLocation = location.clone();
		switch (d){
		case Right:
			newLocation[1] ++;
			break;
		case Left:
			newLocation[1] --;
			break;
		case Down:
			newLocation[0] ++;
			break;
		case Up:
			newLocation[0] --;
			break;
		}
		return newLocation;
	}
}
