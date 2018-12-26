import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Board {
	
	public Board(){
		try{
			this.board = setBoard();
		}
		catch(FileNotFoundException e){
			// no such file
		}
	}
	
	public Board(String[] strArr){
		this.board = setBoardWithStringArray(strArr);
	}

	enum Creatures {
		Pacman,
		Ghost1, Ghost2, Ghost3, Ghost4,
		Point,
		Wall,
		Null
	}
	
	private Creatures[][] board = new Creatures[30][30];
	int ghostNum;
	
	public Creatures[][] getBoard() {
		return board;
	}

	public int getBoardDimensions(){
		return board.length * 100 + board[0].length;
	}
	
	private Creatures[][] setBoard() throws FileNotFoundException{
		this.ghostNum = 0;
		Scanner sc = new Scanner(new BufferedReader(new FileReader("./Board.txt")));
	    while(sc.hasNextLine()) {
	    	for (int i = 0; i < board.length && sc.hasNextLine(); i++) {
    			String[] line = sc.nextLine().trim().split("	");
	        	for (int j = 0; j < line.length; j++) 
	        		board[i][j] = StringToCreature(line[j]); 
	        }
	    }
	    sc.close();
	    this.ghostNum = 0;
	    return board;
	}
	
	private Creatures StringToCreature(String st){
		switch(st){
			case "P":
				return Creatures.Pacman;
			case "W":
				return Creatures.Wall;
			case "-":
				return Creatures.Point;
			case "G":
				this.ghostNum++;
				switch(this.ghostNum){
					case 1:
						return Creatures.Ghost1;
					case 2:
						return Creatures.Ghost2;
					case 3:
						return Creatures.Ghost3;
					case 4:
						return Creatures.Ghost4;
				}
			default:
				return Creatures.Null;
		}
	}

	private Creatures[][] setBoardWithStringArray(String[] sts) {
		this.board = new Creatures[sts.length][sts[0].length()];
		
		for (int i = 0; i < sts.length; i++){
			for (int j = 0; j < sts[i].length(); j++)
				this.board[i][j] = StringToCreature(sts[i].charAt(j) + "");
		}
		
		return this.board;
	}

	
}
