package pacman;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;

import pacman.Game.Direction;

public class Program extends JFrame{
		
	/**
	 * 
	 */
	Game game;
	static final int Xdim = 30, Ydim = 30;
	Timer show;
	
	public Program(){
				
		setTitle("Change Pacman Direction");
		setSize(300, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(new FlowLayout());
		
		game = new Game();
		
		for (Game.Direction d : Game.Direction.values()){
			DirectionChanger change = new DirectionChanger(d.name(), game);
			add(new JButton(change));
		}
		
		show = new Timer();
	}
	
	private String CreatureToString(Board.Creature c){
		switch(c){
			case GHOST_1:
			case GHOST_2:
			case GHOST_3:
			case GHOST_4:
				return "G";
			case NULL:
				return "-";
			case PACMAN:
				return "P";
			case POINT:
				return ".";
			case WALL:
				return "W";
		}
		
		return ".";
	}
	
	private void showBoard() {
		for (int i = 0; i < Xdim; i++){
			for (int j = 0; j < Ydim; j++)
				System.out.print( this.CreatureToString( this.game.getCreatureAt(new Location(i, j)) ) );
			
			System.out.println();
		}
	}
	
	int round = 1;
	
	public static void main(String[] args){
		Program program = new Program();
		program.game.gameOn = false;
		program.setVisible(true);
		
		System.out.println("Game Starts.\nOriginal Board:");
		program.showBoard();		

		program.startProgram();
	}
	
	class DirectionChanger extends AbstractAction {

		/**
		 * 
		 */
		Game game;
		
		public DirectionChanger(String str, Game game) {
			super(str);
			this.game = game;
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			for (Game.Direction d : Game.Direction.values()){
				if ( arg0.getActionCommand().equals(d.name()) ){
					this.game.changeDirection( Direction.valueOf( d.name() ), Board.Creature.PACMAN );
				}
			}
		}
		
	}

	private void startProgram() {
		
		if (game.gameOn)
			return;
		
		game.gameOn = true;
		
		show.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
								
				System.out.println( "\nRound: " + round + ", Score: " + game.getScore() );
				round ++;
				game.move();
				showBoard();
				
				if (game.getLives() <= 0) {
					System.out.println("\nGame Over! Well Played");
					System.exit(0);
				}
					
			}

		}, 0, 200);
	}
}