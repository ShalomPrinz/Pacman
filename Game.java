import java.io.BufferedReader;
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
		// Change Board.txt path on another computer.
		// Home path: E:/Docs/שלום/A android project 2018/Pacman/Board.txt
		Scanner sc = new Scanner(new BufferedReader(new FileReader("E:/Docs/שלום/A android project 2018/Pacman/Board.txt")));
	    while(sc.hasNextLine()) {
	    	for (int i = 0; i < board.length && sc.hasNextLine(); i++) {
    			String[] line = sc.nextLine().trim().split("	");
    			line = reverseLine(line);
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
		default:
			return Creatures.Null;
		}
	}
	
	private String[] reverseLine(String[] line){
		String newLine[] = new String[line.length];
		for (int i = 0; i < line.length; i++)
			newLine[i] = line[line.length - 1 - i];
		return newLine;
	}
}
