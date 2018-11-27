
public class Game {
	
	enum Creatures {
		Pacman,
		Ghost
	}
	
	Creatures[][] board = new Creatures[30][30];
	
	public int getBoardDimensions(){
		return board.length * 100 + board[0].length;
	}
	
	public Creatures[][] getBoard(){
		board[23][15] = Creatures.Pacman;
		board[13][15] = Creatures.Ghost;
		board[16][14] = Creatures.Ghost;
		board[16][15] = Creatures.Ghost;
		board[16][16] = Creatures.Ghost;
		return board;
	}
	
	public int count(Creatures c){
		board = getBoard();
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
