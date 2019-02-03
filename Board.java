package pacman;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Board {
	
	public Board(){
		this(null);
	}
	
	public Board(String[] rowsArray) {
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

	public static class Creature implements Moveable {
		public static Creature PACMAN = new Creature();
		public static Creature GHOST_1 = new Creature();
		public static Creature GHOST_2 = new Creature();
		public static Creature GHOST_3 = new Creature();
		public static Creature GHOST_4 = new Creature();
		public static Creature POINT = new Creature();
		public static Creature WALL = new Creature();
		public static Creature NULL = new Creature();
		
		private Location location;
		private Game.Direction direction;
		private Game.Direction goHere = Game.Direction.LEFT;
		private Type type;
		
		public enum Type {
			PACMAN,
			GHOST,
			POINT,
			WALL,
			NULL
		}
		
		public static void init() {
			PACMAN.type = Type.PACMAN;
			GHOST_1.type = Type.GHOST;
			GHOST_2.type = Type.GHOST;
			GHOST_3.type = Type.GHOST;
			GHOST_4.type = Type.GHOST;
			POINT.type = Type.POINT;
			WALL.type = Type.WALL;
			NULL.type = Type.NULL;
		}
		
		public Type getType() {
			return this.type;
		}
		
		public static Creature[] values(){
			return new Creature[] {
				PACMAN, GHOST_1, GHOST_2, GHOST_3, GHOST_4, POINT, WALL, NULL
			};
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

		@Override
		public Game.Direction getNextDirection() {
			return goHere;
		}

		@Override
		public void setNextDirection(Game.Direction goHere) {
			this.goHere = goHere;
		}
	}
	
	private Creature[][] board;
	private int ghostsNumber;
	
	public Creature[][] get() {
		return board.clone();
	}

	public Creature get(Location l) {
		return board[l.getX()][l.getY()];
	}
	
	public void set(Location l, Creature c) {
		board[l.getX()][l.getY()] = c;
		
		if (c == Creature.WALL || c == Creature.NULL || c == Creature.POINT)
			return;
		
		c.setLocation(l);
		
		if (c.getDirection() == null)
			c.setDirection(Game.defaultDirection);
	}
	
	public void limitToSpecificCreatures(Creature[] CreaturesForThisBoard) {
		for (int i = 0; i < board.length; i++){
			for (int j = 0; j < board[0].length; j++){
				if (!isCreatureAllowed( get(new Location(i, j) ), CreaturesForThisBoard ))
					set( new Location(i, j), Creature.NULL );
			}
		}
	}
	
	public int getDimensions() {
		if (board.length != board[0].length)
			return Integer.parseInt( new String( board.length + "" + board[0].length ) );
		
		return board.length;
	}
	
	public int getGhostNum() {
		return this.ghostsNumber;
	}
	
	private Creature[][] setBoard() throws FileNotFoundException {
		// I didn't find the relative path for the file Board.txt so i used this
		Scanner sc = new Scanner(new BufferedReader(new FileReader("E:\\Docs\\ωμεν\\A android project\\Pacman\\Board.txt")));
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
	
	private Creature StringToCreature(String st) {
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

	private boolean isCreatureAllowed(Board.Creature isAllowed, Board.Creature[] allowedArray){
		
		for (Board.Creature cre : allowedArray){
			if (isAllowed == cre)
				return true;
		}
		
		return false;
	}

	private void initialize(Location l, Creature c) {
		c.setLocation(null);
		c.setDirection(null);
		set( l, c );
	}
	
}
