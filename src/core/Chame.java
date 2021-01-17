package core;

public class Chame {
	
	private GameLogic logic;
	
	public Chame() {
		logic = new GameLogic();
		logic.setupBoard();
		runGame();
	}
	
	private void runGame() {
		
//		while (!logic.isGameOver()) {
			logic.gameLoop();
//		}
		
		
	}

	public static void main(String[] args) {
		new Chame();

	}

}
