
public class Game {
	
	public Board fillBoard(Board b){
		b.locations = new int[30][30];
		for (int i = 0; i < 30; i++){
			for (int j = 0; j < 30; j++)
				b.locations[i][j] = 2;	
		}
		b.locations[23][15] = 0; // 0 - Pacman
		b.locations[13][15] = 1; // 1 - Ghost
		b.locations[16][14] = 1;
		b.locations[16][15] = 1;
		b.locations[16][16] = 1;
		return b;
	}
}
