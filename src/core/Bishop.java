package core;

public class Bishop extends Piece {

	public Bishop(int x, int y, boolean white) {
		super(x, y, white);
		super.setSymbol('B');
	}
	
	public Bishop copyPiece() {
		return new Bishop(getX(), getY(), getColor());
	}
	
	public void updateMoveList(Piece[][] board) {
		getMoveList().clear();
		getMoveList().addAll(super.directionalIterList(board, 1, 1));
		getMoveList().addAll(super.directionalIterList(board, 1, -1));
		getMoveList().addAll(super.directionalIterList(board, -1, 1));
		getMoveList().addAll(super.directionalIterList(board, -1, -1));
	}
	
	
}
