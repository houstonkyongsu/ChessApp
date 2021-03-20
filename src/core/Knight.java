package core;

public class Knight extends Piece {

	public Knight(int x, int y, boolean white) {
		super(x, y, white);
		super.setSymbol('H');
	}
	
	@SuppressWarnings("unchecked")
	public Knight copyPiece() {
		Knight k = new Knight(getX(), getY(), getColor());
		k.setMoveList(getMoveList());
		k.setMoves(getMoves());
		return k;
	}
	
	public void updateMoveList(Piece[][] board) {
		getMoveList().clear();
		int xx = super.getX();
		int yy = super.getY();
		if (super.withinBounds(xx+2, yy+1) && (board[xx+2][yy+1] == null || board[xx+2][yy+1].getColor() != super.getColor())
				&& noRevealCheck(board, new Move(new Pair(getX(), getY()), new Pair(xx+2, yy+1), 'X'))) {
			if (board[xx+2][yy+1] == null) {
				super.getMoveList().add(new Move(new Pair(getX(), getY()), new Pair(xx+2, yy+1), 'X'));
			} else {
				super.getMoveList().add(new Move(new Pair(getX(), getY()), new Pair(xx+2, yy+1), board[xx][yy].getSymbol()));
			}
		}
		if (super.withinBounds(xx-2, yy+1) && (board[xx-2][yy+1] == null || board[xx-2][yy+1].getColor() != super.getColor())
				&& noRevealCheck(board, new Move(new Pair(getX(), getY()), new Pair(xx-2, yy+1), 'X'))) {
			if (board[xx-2][yy+1] == null) {
				super.getMoveList().add(new Move(new Pair(getX(), getY()), new Pair(xx-2, yy+1), 'X'));
			} else {
				super.getMoveList().add(new Move(new Pair(getX(), getY()), new Pair(xx-2, yy+1), board[xx][yy].getSymbol()));
			}
		}
		if (super.withinBounds(xx+2, yy-1) && (board[xx+2][yy-1] == null || board[xx+2][yy-1].getColor() != super.getColor())
				&& noRevealCheck(board, new Move(new Pair(getX(), getY()), new Pair(xx+2, yy-1), 'X'))) {
			if (board[xx+2][yy-1] == null) {
				super.getMoveList().add(new Move(new Pair(getX(), getY()), new Pair(xx+2, yy-1), 'X'));
			} else {
				super.getMoveList().add(new Move(new Pair(getX(), getY()), new Pair(xx+2, yy-1), board[xx][yy].getSymbol()));
			}
		}
		if (super.withinBounds(xx-2, yy-1) && (board[xx-2][yy-1] == null || board[xx-2][yy-1].getColor() != super.getColor())
				&& noRevealCheck(board, new Move(new Pair(getX(), getY()), new Pair(xx-2, yy-1), 'X'))) {
			if (board[xx-2][yy-1] == null) {
				super.getMoveList().add(new Move(new Pair(getX(), getY()), new Pair(xx-2, yy-1), 'X'));
			} else {
				super.getMoveList().add(new Move(new Pair(getX(), getY()), new Pair(xx-2, yy-1), board[xx][yy].getSymbol()));
			}
		}
		if (super.withinBounds(xx+1, yy+2) && (board[xx+1][yy+2] == null || board[xx+1][yy+2].getColor() != super.getColor())
				&& noRevealCheck(board, new Move(new Pair(getX(), getY()), new Pair(xx+1, yy+2), 'X'))) {
			if (board[xx+1][yy+2] == null) {
				super.getMoveList().add(new Move(new Pair(getX(), getY()), new Pair(xx+1, yy+2), 'X'));
			} else {
				super.getMoveList().add(new Move(new Pair(getX(), getY()), new Pair(xx+1, yy+2), board[xx][yy].getSymbol()));
			}
		}
		if (super.withinBounds(xx-1, yy+2) && (board[xx-1][yy+2] == null || board[xx-1][yy+2].getColor() != super.getColor())
				&& noRevealCheck(board, new Move(new Pair(getX(), getY()), new Pair(xx-1, yy+2), 'X'))) {
			if (board[xx-1][yy+2] == null) {
				super.getMoveList().add(new Move(new Pair(getX(), getY()), new Pair(xx-1, yy+2), 'X'));
			} else {
				super.getMoveList().add(new Move(new Pair(getX(), getY()), new Pair(xx-1, yy+2), board[xx][yy].getSymbol()));
			}
		}
		if (super.withinBounds(xx+1, yy-2) && (board[xx+1][yy-2] == null || board[xx+1][yy-2].getColor() != super.getColor())
				&& noRevealCheck(board, new Move(new Pair(getX(), getY()), new Pair(xx+1, yy-2), 'X'))) {
			if (board[xx+1][yy-2] == null) {
				super.getMoveList().add(new Move(new Pair(getX(), getY()), new Pair(xx+1, yy-2), 'X'));
			} else {
				super.getMoveList().add(new Move(new Pair(getX(), getY()), new Pair(xx+1, yy-2), board[xx][yy].getSymbol()));
			}
		}
		if (super.withinBounds(xx-1, yy-2) && (board[xx-1][yy-2] == null || board[xx-1][yy-2].getColor() != super.getColor())
				&& noRevealCheck(board, new Move(new Pair(getX(), getY()), new Pair(xx-1, yy-2), 'X'))) {
			if (board[xx-1][yy-2] == null) {
				super.getMoveList().add(new Move(new Pair(getX(), getY()), new Pair(xx-1, yy-2), 'X'));
			} else {
				super.getMoveList().add(new Move(new Pair(getX(), getY()), new Pair(xx-1, yy-2), board[xx][yy].getSymbol()));
			}
		}
	}

}
