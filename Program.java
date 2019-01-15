import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Program extends JFrame{
		
	Game game;
	
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
		
	}
	
	private String CreatureToString(Board.Creature c){
		switch(c){
			case Ghost1:
			case Ghost2:
			case Ghost3:
			case Ghost4:
				return "G";
			case Null:
				return "-";
			case Pacman:
				return "P";
			case Point:
				return ".";
			case Wall:
				return "W";
		}
		
		return ".";
	}
	
	private void showBoard() {
		for (int i = 0; i < 30; i++){
			for (int j = 0; j < 30; j++)
				System.out.print( this.CreatureToString( this.game.getCreatureAt(new Location(i, j)) ) );
			
			System.out.println();
		}
	}
	
	int round = 1;
	
	public static void main(String[] args){
		Program program = new Program();
		Timer show = new Timer();
		program.game.gameOn = true;
		program.setVisible(true);
		
		System.out.println("Game Starts.\nOriginal Game:");
		program.showBoard();		

		show.scheduleAtFixedRate(new TimerTask() {
						
			@Override
			public void run() {
				if (!program.game.gameOn)
					return;
								
				System.out.println("\nRound: " + program.round);
				program.round ++;
				program.game.move();
				program.showBoard();		
			}

		}, 0, 1000);
	}
	
	class DirectionChanger extends AbstractAction {

		Game game;
		
		public DirectionChanger(String str, Game game) {
			super(str);
			this.game = game;
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			for (Game.Direction d : Game.Direction.values()){
				if ( arg0.getActionCommand().equals(d.name()) ){
					this.game.changeDirection( d.valueOf( d.name() ), Board.Creature.Pacman );
				}
			}
		}
		
	}
}