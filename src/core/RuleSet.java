package core;

public class RuleSet {

	final int BOARD_SIZE = 8;
	
	public RuleSet() {
		
	}
	
//	public boolean checkPossible(Piece[][] board, int x, int y, boolean col) {
//		if (board[x][y] == null || board[x][y].getColor() != col) {
//			return true;
//		}
//		return false;
//	}
	
	/*
	 * Function to return a deep copy of the board, so original object values aren't changed when checking future possible moves.
	 */
	public Piece[][] deepCopyBoard(Piece[][] board) {
		Piece[][] copy = new Piece[BOARD_SIZE][BOARD_SIZE];
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				if (board[i][j] != null) {
					copy[i][j] = board[i][j].copyPiece();
				}
			}
		}
		return copy;
	}
	
	
	
}
