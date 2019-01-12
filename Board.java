import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Board {
	
	public Board(){
		this(null);
	}
	
	public Board(String[] rowsArray){
		this.ghostNum = 0;
		
		if (rowsArray == null){
			this.board = new Creature[30][30];
			
			try {
				this.board = setBoard();
			} catch (FileNotFoundException e) {
				// no such file
			}
		}
		else {
			this.board = new Creature[rowsArray.length][rowsArray[0].length()];
			this.board = setBoardWithStringArray(rowsArray);
		}
		
	}

	enum Creature {
		Pacman,
		Ghost1, Ghost2, Ghost3, Ghost4,
		Point,
		Wall,
		Null
	}
	
	private Creature[][] board;
	int ghostNum;
	
	public Creature[][] get() {
		return board.clone();
	}

	public Creature get(Location l){
		return board[l.getX()][l.getY()];
	}
	
	public void set(Location l, Creature c){
		board[l.getX()][l.getY()] = c;
	}
	
	public void limitToSpecificCreatures(Creature[] CreaturesForThisBoard){
		for (int i = 0; i < board.length; i++){
			for (int j = 0; j < board[0].length; j++){
				if (!isCreatureAllowed( get(new Location(i, j) ), CreaturesForThisBoard ))
					set( new Location(i, j), Creature.Null );
			}
		}
	}
	
	public int getBoardDimensions(){
		return board.length * 100 + board[0].length;
	}
	
	private Creature[][] setBoard() throws FileNotFoundException{
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
	
	private Creature StringToCreature(String st){
		switch(st){
			case "P":
				return Creature.Pacman;
			case "W":
				return Creature.Wall;
			case "-":
				return Creature.Point;
			case "G":
				this.ghostNum++;
				switch(this.ghostNum){
					case 1:
						return Creature.Ghost1;
					case 2:
						return Creature.Ghost2;
					case 3:
						return Creature.Ghost3;
					case 4:
						return Creature.Ghost4;
				}
			default:
				return Creature.Null;
		}
	}

	private Creature[][] setBoardWithStringArray(String[] sts) {
		
		for (int i = 0; i < sts.length; i++){
			for (int j = 0; j < sts[i].length(); j++)
				this.board[i][j] = StringToCreature(sts[i].charAt(j) + "");
		}
		
		return this.board;
	}

	private boolean isCreatureAllowed(Board.Creature isAllowed, Board.Creature[] allowedArray){
		
		for (Board.Creature cre : allowedArray){
			if (isAllowed == cre)
				return true;
		}
		
		return false;
	}

}
