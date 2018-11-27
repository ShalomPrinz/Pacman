
public class Game {
	
	enum Creatures {
		Pacman 	
	}
	
	Creatures[][] board = new Creatures[30][30];
	
	public int getBoardDimensions(){
		return board.length * 100 + board[0].length;
	}
	
	public Creatures[][] getBoard(){
		board[23][15] = Creatures.Pacman;
		return board;
	}
	
	public int count(Creatures c){
		int cNum = 0;
		for (int i = 0; i < board.length; i++){
			for (int j = 0; j < board[0].length; j++){
				if (board[i][j] == c)
					cNum ++;
			}
		}
		return cNum;
	}

}
