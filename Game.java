
public class Game {
	
	enum Creatures {
		Pacman,
		Ghost
	}
	
	Creatures[][] board = new Creatures[30][30];
	
	public int getBoardDimensions(){
		return board.length * 100 + board[0].length;
	}
	
	public Creatures[][] setBoard(){
		board[23][15] = Creatures.Pacman;
		board[13][15] = Creatures.Ghost;
		fillBoardRaw(16, 14, 16, Creatures.Ghost);
		
		return board;
	}
	
	public int count(Creatures c){
		board = setBoard();
		int cNum = 0;
		for (int i = 0; i < board.length; i++){
			for (int j = 0; j < board[0].length; j++){
				if (board[i][j] == c)
					cNum ++;
			}
		}
		return cNum;
	}
	
	private void fillBoardRaw(int raw, int start, int end, Creatures c){
		if (end < start || raw < 0 || raw > 30)
			return;
		for (int i = start; i <= end; i++)
			board[raw][i] = c;
	}
	
	

}
