package core;

public class Queen extends Piece {

	public Queen(int x, int y, boolean white) {
		super(x, y, white);
		super.setSymbol('Q');
	}
	
	@SuppressWarnings("unchecked")
	public Queen copyPiece() {
		Queen q = new Queen(getX(), getY(), getColor());
		q.setMoveList(getMoveList());
		q.setMoves(getMoves());
		return q;
	}

	public void updateMoveList(Piece[][] board) {
		super.getMoveList().clear();
		super.getMoveList().addAll(super.directionalIterList(board, 1, 1));
		super.getMoveList().addAll(super.directionalIterList(board, 1, -1));
		super.getMoveList().addAll(super.directionalIterList(board, -1, 1));
		super.getMoveList().addAll(super.directionalIterList(board, -1, -1));
		super.getMoveList().addAll(super.directionalIterList(board, 0, 1));
		super.getMoveList().addAll(super.directionalIterList(board, 1, 0));
		super.getMoveList().addAll(super.directionalIterList(board, -1, 0));
		super.getMoveList().addAll(super.directionalIterList(board, 0, -1));
	}
	
}
