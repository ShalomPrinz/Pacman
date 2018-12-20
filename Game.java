
public class Game {
	
	Board mBoard = new Board();
	Board.Creatures[][] board = mBoard.getBoard();
	
	public boolean movePacmanRight(){
		if (this.board[0][1] == Board.Creatures.Wall)
			return false;
		
		if (this.board[0][1] == Board.Creatures.Point){
			this.board[0][1] = Board.Creatures.Pacman;
			this.board[0][0] = Board.Creatures.Null;
		}
		
		for (Board.Creatures c : mBoard.ghosts){
			if (this.board[0][1] == c)
				this.board[0][0] = Board.Creatures.Null;
		}
		
		return true;
	}
}
