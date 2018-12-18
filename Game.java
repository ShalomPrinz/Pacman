import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Game {
	
	public Game(){
		try{
			this.board = setBoard();
		}
		catch(FileNotFoundException e){
			// no such file
		}
	}
	
	enum Creatures {
		Pacman,
		Ghost1, Ghost2, Ghost3, Ghost4,
		Point,
		Wall,
		Null
	}
	
	enum Directions {
		RIGHT,
		LEFT,
		UP,
		DOWN
	}
	
	private Creatures[][] board = new Creatures[30][30];
	int ghostNum;
	
	public Creatures[][] getBoard() {
		return board;
	}

	public int getBoardDimensions(){
		return board.length * 100 + board[0].length;
	}
	
	public Creatures[][] setBoard() throws FileNotFoundException{
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

	public boolean move(Creatures c, Directions d){
		int line = find(c)[0];
		int column = find(c)[1];
		int Nline = changeLocationByDirection(d, line, column)[0];
		int Ncolumn = changeLocationByDirection(d, line, column)[1];

		if (this.board[Nline][Ncolumn] == Creatures.Wall)
			return false;
		
		Creatures temp = this.board[Nline][Ncolumn];
		this.board[Nline][Ncolumn] = c;
		this.board[line][column] = temp;
		return true;
	}
	
	private int[] changeLocationByDirection(Directions d, int line, int column){
		switch (d){
			case DOWN:
				line++;
				break;
			case UP:
				line--;
				break;
			case RIGHT:
				column++;
				break;
			case LEFT:
				column--;
				break;
		}
		return new int[]{line, column};
	}
	
	private int[] find(Creatures c){
		for (int i = 0; i < this.board.length; i++){
			for (int j = 0; j < this.board[0].length; j++){
				if (this.board[i][j] == c)
					return new int[]{i, j};
			}
		}
		return null;
	}

}
