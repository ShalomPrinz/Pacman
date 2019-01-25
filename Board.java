package pacman;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
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

	public enum Creature implements Moveable{
		PACMAN,
		GHOST_1, GHOST_2, GHOST_3, GHOST_4,
		POINT,
		WALL,
		NULL;

		private Location initialLocation;
		private Location location;
		private Game.Direction direction;
		private Game.Direction goHere;
		
		public Location getInitialLocation() {
			return this.initialLocation;
		}
		
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
		
		if (c == Creature.WALL || c == Creature.NULL || c == Creature.POINT)
			return;
		
		c.setLocation(l);
		
		if (c.getDirection() == null)
			c.setDirection(Game.DEFAULT_DIRECTION);
	}
	
	public void limitToSpecificCreatures(Creature[] CreaturesForThisBoard){
		for (int i = 0; i < board.length; i++){
			for (int j = 0; j < board[0].length; j++){
				if (! Arrays.stream( CreaturesForThisBoard ).anyMatch( get(new Location(i, j)) :: equals ) )
					set( new Location(i, j), Creature.NULL );
			}
		}
	}
	
	public int getDimensions(char dim){
		int beginIndex, endIndex;
		beginIndex = (dim == 'X') ? 0 : 2;
		endIndex = (dim == 'X') ? 2 : 4;
		
		return Integer.parseInt( new String( String.format( "%02d", board.length )  + String.format( "%02d", board[0].length ) ).substring(beginIndex, endIndex) );
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
				return Creature.PACMAN;
			case "W":
				return Creature.WALL;
			case "-":
				return Creature.POINT;
			case "G":				
				this.ghostsNumber++;
				switch(this.ghostsNumber){
					case 1:
						return Creature.GHOST_1;
					case 2:
						return Creature.GHOST_2;
					case 3:
						return Creature.GHOST_3;
					case 4:
						return Creature.GHOST_4;
				}
				
			default:
				return Creature.NULL;
		}
	}

	private Creature[][] setBoardWithStringArray(String[] sts) {
		
		for (int i = 0; i < sts.length; i++){
			for (int j = 0; j < sts[i].length(); j++)
				initialize( new Location(i, j), StringToCreature(sts[i].charAt(j) + "") );
		}
		
		return this.board;
	}

	private void initialize(Location l, Creature c){
		c.setLocation(null);
		c.setDirection(null);
		c.initialLocation = l;
		set( l, c );
	}

}
