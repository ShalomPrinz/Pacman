import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Game {
	
	enum Creatures {
		Pacman,
		Ghost,
		Point,
		Wall,
		Null
	}
	
	Creatures[][] board = new Creatures[30][30];
	
	public int getBoardDimensions(){
		return board.length * 100 + board[0].length;
	}
	
	public Creatures[][] setBoard() throws Exception{
		Scanner sc = new Scanner(new BufferedReader(new FileReader("D:/Users/Board.txt")));
	    while(sc.hasNextLine()) {
	    	for (int i = 0; i < board.length && sc.hasNextLine(); i++) {
    			String[] line = sc.nextLine().trim().split("	");
	        	for (int j = 0; j < line.length; j++) 
	        		board[i][j] = StringToCreature(line[j]); 
	        }
	    }
	    sc.close();
	    return board;
	}
	
	public int count(Creatures c) throws Exception{
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
	
	private Creatures StringToCreature(String st){
		switch(st){
		case "G":
			return Creatures.Ghost;
		case "P":
			return Creatures.Pacman;
		case "W":
			return Creatures.Wall;
		case "-":
			return Creatures.Point;
		}
		return Creatures.Null;
	}
	

}
