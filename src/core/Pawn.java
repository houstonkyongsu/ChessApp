package core;

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

//	public boolean canMovePiece(Piece[][] board, int x, int y) {
//		Pair p = new Pair(x, y);
//		ArrayList<Move> list = updateMoveList(board);
//		for (Move move : list) {
//			if (move.getEnd().comparePair(p)) {
//				return true;
//			}
//		}
//		return false;
//	}
	
	public void updateMoveList(Piece[][] board) {
		getMoveList().clear();
		int xx = super.getX();
		int yy = super.getY();
		if (super.getColor()) {
			if (super.withinBounds(xx-1, yy) && board[xx-1][yy] == null) {
				getMoveList().add(new Move(new Pair(getX(), getY()), new Pair(xx-1, yy)));
			}
			if (super.withinBounds(xx-2, yy) && super.getMoves() == 0 && board[xx-2][yy] == null) {
				getMoveList().add(new Move(new Pair(getX(), getY()), new Pair(xx-2, yy)));
			}
			if (super.withinBounds(xx-1, yy+1) && board[xx-1][yy+1].getColor() != super.getColor()) {
				getMoveList().add(new Move(new Pair(getX(), getY()), new Pair(xx-1, yy+1)));
			}
			if (super.withinBounds(xx-1, yy-1) && board[xx-1][yy-1].getColor() != super.getColor()) {
				getMoveList().add(new Move(new Pair(getX(), getY()), new Pair(xx-1, yy-1)));
			}
		} else {
			if (super.withinBounds(xx+1, yy) && board[xx+1][yy] == null) {
				getMoveList().add(new Move(new Pair(getX(), getY()), new Pair(xx+1, yy)));
			}
			if (super.withinBounds(xx+2, yy) && super.getMoves() == 0 && board[xx+2][yy] == null) {
				getMoveList().add(new Move(new Pair(getX(), getY()), new Pair(xx+2, yy)));
			}
			if (super.withinBounds(xx+1, yy-1) && board[xx+1][yy-1].getColor() != super.getColor()) {
				getMoveList().add(new Move(new Pair(getX(), getY()), new Pair(xx+1, yy-1)));
			}
			if (super.withinBounds(xx+1, yy+1) && board[xx+1][yy+1].getColor() != super.getColor()) {
				getMoveList().add(new Move(new Pair(getX(), getY()), new Pair(xx+1, yy+1)));
			}
		}
	}

}
