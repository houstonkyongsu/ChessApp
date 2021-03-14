package core;

public class Pawn extends Piece {
	
	public Pawn(int x, int y, boolean white) {
		super(x, y, white);
		super.setSymbol('P');
	}
	
	@SuppressWarnings("unchecked")
	public Pawn copyPiece() {
		Pawn p = new Pawn(getX(), getY(), getColor());
		p.setMoves(getMoves());
		p.setMoveList(getMoveList());
		return p;
	}
	
	public void updateMoveList(Piece[][] board) {
		getMoveList().clear();
		int xx = super.getX();
		int yy = super.getY();
		if (super.getColor()) {
			if (super.withinBounds(xx-1, yy) && board[xx-1][yy] == null && noRevealCheck(board, new Move(new Pair(getX(), getY()), new Pair(xx-1, yy), false))) {
				super.getMoveList().add(new Move(new Pair(getX(), getY()), new Pair(xx-1, yy), false));
			}
			if (super.withinBounds(xx-2, yy) && super.getMoves() == 0 && board[xx-2][yy] == null && board[xx-1][yy] == null
					&& noRevealCheck(board, new Move(new Pair(getX(), getY()), new Pair(xx-2, yy), false))) {
				super.getMoveList().add(new Move(new Pair(getX(), getY()), new Pair(xx-2, yy), false));
			}
			if (super.withinBounds(xx-1, yy+1) && board[xx-1][yy+1] != null && board[xx-1][yy+1].getColor() != super.getColor() 
					&& noRevealCheck(board, new Move(new Pair(getX(), getY()), new Pair(xx-1, yy+1), true))) {
				super.getMoveList().add(new Move(new Pair(getX(), getY()), new Pair(xx-1, yy+1), true));
			}
			if (super.withinBounds(xx-1, yy-1) && board[xx-1][yy-1] != null && board[xx-1][yy-1].getColor() != super.getColor()
					&& noRevealCheck(board, new Move(new Pair(getX(), getY()), new Pair(xx-1, yy-1), true))) {
				super.getMoveList().add(new Move(new Pair(getX(), getY()), new Pair(xx-1, yy-1), true));
			}
		} else {
			if (super.withinBounds(xx+1, yy) && board[xx+1][yy] == null && noRevealCheck(board, new Move(new Pair(getX(), getY()), new Pair(xx+1, yy), false))) {
				getMoveList().add(new Move(new Pair(getX(), getY()), new Pair(xx+1, yy), false));
			}
			if (super.withinBounds(xx+2, yy) && super.getMoves() == 0 && board[xx+2][yy] == null && board[xx+1][yy] == null
					&& noRevealCheck(board, new Move(new Pair(getX(), getY()), new Pair(xx+2, yy), false))) {
				getMoveList().add(new Move(new Pair(getX(), getY()), new Pair(xx+2, yy), false));
			}
			if (super.withinBounds(xx+1, yy-1) && board[xx+1][yy-1] != null && board[xx+1][yy-1].getColor() != super.getColor()
					&& noRevealCheck(board, new Move(new Pair(getX(), getY()), new Pair(xx+1, yy-1), true))) {
				getMoveList().add(new Move(new Pair(getX(), getY()), new Pair(xx+1, yy-1), true));
			}
			if (super.withinBounds(xx+1, yy+1) && board[xx+1][yy+1] != null && board[xx+1][yy+1].getColor() != super.getColor()
					&& noRevealCheck(board, new Move(new Pair(getX(), getY()), new Pair(xx+1, yy+1), true))) {
				getMoveList().add(new Move(new Pair(getX(), getY()), new Pair(xx+1, yy+1), true));
			}
		}
	}

}
