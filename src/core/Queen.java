package core;

import java.util.*;

public class Queen extends Piece {

	public Queen(int x, int y, boolean white) {
		super(x, y, white);
		super.setSymbol('Q');
	}
	
	public Queen copyPiece() {
		return new Queen(getX(), getY(), getColor());
	}

	public ArrayList<Move> getMoveList(Piece[][] board) {
		ArrayList<Move> list = new ArrayList<>();
		list.addAll(super.directionalIterList(board, 1, 1));
		list.addAll(super.directionalIterList(board, 1, -1));
		list.addAll(super.directionalIterList(board, -1, 1));
		list.addAll(super.directionalIterList(board, -1, -1));
		list.addAll(super.directionalIterList(board, 0, 1));
		list.addAll(super.directionalIterList(board, 1, 0));
		list.addAll(super.directionalIterList(board, -1, 0));
		list.addAll(super.directionalIterList(board, 0, -1));
		
		return list;
	}
	
}
