package core;

import java.util.*;

public class Pawn extends Piece {
	
	public Pawn(int x, int y, boolean white) {
		super(x, y, white);
		super.setSymbol('P');
	}
	
	public Pawn copyPiece() {
		Pawn p = new Pawn(getX(), getY(), getColor());
		p.setMoves(getMoves());
		return p;
	}

	public boolean canMovePiece(Piece[][] board, int x, int y) {
		Pair p = new Pair(x, y);
		ArrayList<Pair> list = getMoveList(board);
		for (Pair pair : list) {
			if (pair.comparePair(p)) {
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<Move> getMoveList(Piece[][] board) {
		ArrayList<Move> list = new ArrayList<>();
		int xx = super.getX();
		int yy = super.getY();
		if (super.getColor()) {
			if (super.withinBounds(xx-1, yy) && board[xx-1][yy] == null) {
				list.add(new Move(new Pair(getX(), getY()), new Pair(xx-1, yy)));
			}
			if (super.withinBounds(xx-2, yy) && super.getMoves() == 0 && board[xx-2][yy] == null) {
				list.add(new Move(new Pair(getX(), getY()), new Pair(xx-2, yy)));
			}
			if (super.withinBounds(xx-1, yy+1) && board[xx-1][yy+1].getColor() != super.getColor()) {
				list.add(new Move(new Pair(getX(), getY()), new Pair(xx-1, yy+1)));
			}
			if (super.withinBounds(xx-1, yy-1) && board[xx-1][yy-1].getColor() != super.getColor()) {
				list.add(new Move(new Pair(getX(), getY()), new Pair(xx-1, yy-1)));
			}
		} else {
			if (super.withinBounds(xx+1, yy) && board[xx+1][yy] == null) {
				list.add(new Move(new Pair(getX(), getY()), new Pair(xx+1, yy)));
			}
			if (super.withinBounds(xx+2, yy) && super.getMoves() == 0 && board[xx+2][yy] == null) {
				list.add(new Move(new Pair(getX(), getY()), new Pair(xx+2, yy)));
			}
			if (super.withinBounds(xx+1, yy-1) && board[xx+1][yy-1].getColor() != super.getColor()) {
				list.add(new Move(new Pair(getX(), getY()), new Pair(xx+1, yy-1)));
			}
			if (super.withinBounds(xx+1, yy+1) && board[xx+1][yy+1].getColor() != super.getColor()) {
				list.add(new Move(new Pair(getX(), getY()), new Pair(xx+1, yy+1)));
			}
		}
		return list;
	}

}
