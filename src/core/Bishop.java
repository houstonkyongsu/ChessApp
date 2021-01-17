package core;

import java.util.ArrayList;

public class Bishop extends Piece {

	public Bishop(int x, int y, boolean white) {
		super(x, y, white);
		super.setSymbol('B');
	}
	
	public Bishop copyPiece() {
		return new Bishop(getX(), getY(), getColor());
	}
	
	public ArrayList<Move> getMoveList(Piece[][] board) {
		ArrayList<Move> list = new ArrayList<>();
		list.addAll(super.directionalIterList(board, 1, 1));
		list.addAll(super.directionalIterList(board, 1, -1));
		list.addAll(super.directionalIterList(board, -1, 1));
		list.addAll(super.directionalIterList(board, -1, -1));
		
		return list;
	}
	
	
}
