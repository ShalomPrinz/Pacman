import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Board {
	
	public Board(){
		this(null);
	}
	
	public Board(String[] rowsArray){
		this.ghostsNumber = 0;
		
		for (Creature c : Creature.values()){
			c.setLocation(null);
			c.setNextDirection(null);
		}
			
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

	enum Creature implements Moveable{
		Pacman,
		Ghost1, Ghost2, Ghost3, Ghost4,
		Point,
		Wall,
		Null;

		private Location location;
		private Game.Direction direction;
		private Game.Direction goHere = Game.defaultDirection;
		
		@Override
		public Location getLocation() {
			return this.location;
		}

		@Override
		public void setLocation(Location location) {
			this.location = location;
		}

		@Override
		public Game.Direction getDirection() {
			return this.direction;
		}

		@Override
		public void setDirection(Game.Direction direction) {
			this.direction = direction;
		}

		public Game.Direction getNextDirection() {
			return goHere;
		}

		public void setNextDirection(Game.Direction goHere) {
			this.goHere = goHere;
		}
	}
	
	private Creature[][] board;
	private int ghostsNumber;
	
	public Creature[][] get() {
		return board.clone();
	}

	public Creature get(Location l){
		return board[l.getX()][l.getY()];
	}
	
	public void set(Location l, Creature c){
		board[l.getX()][l.getY()] = c;
		
		if (c == Creature.Wall || c == Creature.Null || c == Creature.Point)
			return;
		
		c.setLocation(l);
		
		if (c.getDirection() == null)
			c.setDirection(Game.defaultDirection);
	}
	
	public void limitToSpecificCreatures(Creature[] CreaturesForThisBoard){
		for (int i = 0; i < board.length; i++){
			for (int j = 0; j < board[0].length; j++){
				if (!isCreatureAllowed( get(new Location(i, j) ), CreaturesForThisBoard ))
					set( new Location(i, j), Creature.Null );
			}
		}
	}
	
	public int getDimensions(){
		if (board.length != board[0].length)
			return Integer.parseInt( new String( board.length + "" + board[0].length ) );
		
		return board.length;
	}
	
	public int getGhostNum(){
		return this.ghostsNumber;
	}
	
	private Creature[][] setBoard() throws FileNotFoundException{
		Scanner sc = new Scanner(new BufferedReader(new FileReader("./Board.txt")));
	    while(sc.hasNextLine()) {
	    	for (int i = 0; i < board.length && sc.hasNextLine(); i++) {
    			String[] line = sc.nextLine().trim().split("	");
	        	for (int j = 0; j < line.length; j++)
					initialize( new Location(i, j), StringToCreature(line[j]) );
	        }
	    }
	    sc.close();
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
				this.ghostsNumber++;
				switch(this.ghostsNumber){
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
				initialize( new Location(i, j), StringToCreature(sts[i].charAt(j) + "") );
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

	private void initialize(Location l, Creature c){
		c.setLocation(null);
		c.setDirection(null);
		set( l, c );
	}

}
