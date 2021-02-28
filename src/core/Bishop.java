package core;

public class Bishop extends Piece {

	public Bishop(int x, int y, boolean white) {
		super(x, y, white);
		super.setSymbol('B');
	}
	
	@SuppressWarnings("unchecked")
	public Bishop copyPiece() {
		Bishop b = new Bishop(getX(), getY(), getColor());
		b.setMoveList(getMoveList());
		b.setMoves(getMoves());
		return b;
	}
	
	public void updateMoveList(Piece[][] board) {
		getMoveList().clear();
		getMoveList().addAll(super.directionalIterList(board, 1, 1));
		getMoveList().addAll(super.directionalIterList(board, 1, -1));
		getMoveList().addAll(super.directionalIterList(board, -1, 1));
		getMoveList().addAll(super.directionalIterList(board, -1, -1));
	}
	
	
}
