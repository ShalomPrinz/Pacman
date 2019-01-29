package pacman;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;

import pacman.Creature.Type;

public class Board {
	
	public Board() {
		this(null);
	}
	
	public Board(String[] rowsArray) {
		this.ghostsNumber = 0;
		this.movingCreatures = new Vector<>(0, 1);
		
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

	private Vector< MovingCreature > movingCreatures;
	private Creature[][] board;
	private int ghostsNumber;
	
	public MovingCreature[] getMovingCreatures() {
		return this.movingCreatures.toArray( new MovingCreature[ this.movingCreatures.size() ] );
	}
	
	public Creature[][] get() {
		return board.clone();
	}

	public Creature get(Location l){
		return board[l.getX()][l.getY()];
	}
	
	public void set(Location l, Creature c){
		board[l.getX()][l.getY()] = c;
		
		if ( isStaticCreature(c) )
			return;
		
		MovingCreature Mc = (MovingCreature) c;
		
		Mc.setLocation(l);
		
		if (Mc.getDirection() == null)
			Mc.setDirection(Game.DEFAULT_DIRECTION);
	}
	
	public void limitToSpecificCreatures(Type[] TypesForThisBoard){
		for (int i = 0; i < board.length; i++){
			for (int j = 0; j < board[0].length; j++){
				if (! Arrays.stream( TypesForThisBoard ).anyMatch( get( new Location(i, j) ).getType() :: equals ) )
					set( new Location(i, j), new Null() );
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
			case "W":
				return Wall.getInstance();
			case "-":
				return new Point();
			case "P":
				return new Pacman();
			case "G":
				return new Ghost();
			default:
				return new Null();
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
		if ( !isStaticCreature( c ) ) {
			MovingCreature Mc = (MovingCreature) c;
			Mc.setInitialLocation( l );
			this.movingCreatures.add(Mc);
		}
		set( l, c );
	}

	private boolean isStaticCreature( Creature c ) {
		return ( c.getType() == Type.WALL || c.getType() == Type.NULL || c.getType() == Type.POINT );
	}
}
