package core;

public class Rook extends Piece {

	public Rook(int x, int y, boolean white) {
		super(x, y, white);
		super.setSymbol('R');
	}
	
	@SuppressWarnings("unchecked")
	public Rook copyPiece() {
		Rook r = new Rook(getX(), getY(), getColor());
		r.setMoves(getMoves());
		r.setMoveList(getMoveList());
		return r;
	}

	public void updateMoveList(Piece[][] board) {
		getMoveList().clear();
		getMoveList().addAll(super.directionalIterList(board, 0, 1));
		getMoveList().addAll(super.directionalIterList(board, 1, 0));
		getMoveList().addAll(super.directionalIterList(board, -1, 0));
		getMoveList().addAll(super.directionalIterList(board, 0, -1));
	}
	
}
