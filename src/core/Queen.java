package core;

public class Queen extends Piece {

	public Queen(int x, int y, boolean white) {
		super(x, y, white);
		super.setSymbol('Q');
	}
	
	public Queen copyPiece() {
		return new Queen(getX(), getY(), getColor());
	}

	public void updateMoveList(Piece[][] board) {
		getMoveList().clear();
		getMoveList().addAll(super.directionalIterList(board, 1, 1));
		getMoveList().addAll(super.directionalIterList(board, 1, -1));
		getMoveList().addAll(super.directionalIterList(board, -1, 1));
		getMoveList().addAll(super.directionalIterList(board, -1, -1));
		getMoveList().addAll(super.directionalIterList(board, 0, 1));
		getMoveList().addAll(super.directionalIterList(board, 1, 0));
		getMoveList().addAll(super.directionalIterList(board, -1, 0));
		getMoveList().addAll(super.directionalIterList(board, 0, -1));
	}
	
}
