package core;

import java.util.ArrayList;

public class Rook extends Piece {

	public Rook(int x, int y, boolean white) {
		super(x, y, white);
		super.setSymbol('R');
	}
	
	public Rook copyPiece() {
		Rook r = new Rook(getX(), getY(), getColor());
		r.setMoves(getMoves());
		return r;
	}

	public ArrayList<Move> getMoveList(Piece[][] board) {
		ArrayList<Move> list = new ArrayList<>();
		list.addAll(super.directionalIterList(board, 0, 1));
		list.addAll(super.directionalIterList(board, 1, 0));
		list.addAll(super.directionalIterList(board, -1, 0));
		list.addAll(super.directionalIterList(board, 0, -1));
		
		return list;
	}
	
}
