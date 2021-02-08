package core;

public class Knight extends Piece {

	public Knight(int x, int y, boolean white) {
		super(x, y, white);
		super.setSymbol('H');
	}
	
	public Knight copyPiece() {
		return new Knight(getX(), getY(), getColor());
	}
	
	public void updateMoveList(Piece[][] board) {
		getMoveList().clear();
		int xx = super.getX();
		int yy = super.getY();
		if (super.withinBounds(xx+2, yy+1) && (board[xx+2][yy+1] == null || board[xx+2][yy+1].getColor() != super.getColor())
				&& noRevealCheck(board, new Move(new Pair(getX(), getY()), new Pair(xx+2, yy+1)))) {
			getMoveList().add(new Move(new Pair(getX(), getY()), new Pair(xx+2, yy+1)));
		}
		if (super.withinBounds(xx-2, yy+1) && (board[xx-2][yy+1] == null || board[xx-2][yy+1].getColor() != super.getColor())
				&& noRevealCheck(board, new Move(new Pair(getX(), getY()), new Pair(xx-2, yy+1)))) {
			getMoveList().add(new Move(new Pair(getX(), getY()), new Pair(xx-2, yy+1)));
		}
		if (super.withinBounds(xx+2, yy-1) && (board[xx+2][yy-1] == null || board[xx+2][yy-1].getColor() != super.getColor())
				&& noRevealCheck(board, new Move(new Pair(getX(), getY()), new Pair(xx+2, yy-1)))) {
			getMoveList().add(new Move(new Pair(getX(), getY()), new Pair(xx+2, yy-1)));
		}
		if (super.withinBounds(xx-2, yy-1) && (board[xx-2][yy-1] == null || board[xx-2][yy-1].getColor() != super.getColor())
				&& noRevealCheck(board, new Move(new Pair(getX(), getY()), new Pair(xx-2, yy-1)))) {
			getMoveList().add(new Move(new Pair(getX(), getY()), new Pair(xx-2, yy-1)));
		}
		if (super.withinBounds(xx+1, yy+2) && (board[xx+1][yy+2] == null || board[xx+1][yy+2].getColor() != super.getColor())
				&& noRevealCheck(board, new Move(new Pair(getX(), getY()), new Pair(xx+1, yy+2)))) {
			getMoveList().add(new Move(new Pair(getX(), getY()), new Pair(xx+1, yy+2)));
		}
		if (super.withinBounds(xx-1, yy+2) && (board[xx-1][yy+2] == null || board[xx-1][yy+2].getColor() != super.getColor())
				&& noRevealCheck(board, new Pair(getX(), getY()), new Pair(xx-1, yy+2))) {
			getMoveList().add(new Move(new Pair(getX(), getY()), new Pair(xx-1, yy+2)));
		}
		if (super.withinBounds(xx+1, yy-2) && (board[xx+1][yy-2] == null || board[xx+1][yy-2].getColor() != super.getColor())
				&& noRevealCheck(board, new Move(new Pair(getX(), getY()), new Pair(xx+1, yy-2)))) {
			getMoveList().add(new Move(new Pair(getX(), getY()), new Pair(xx+1, yy-2)));
		}
		if (super.withinBounds(xx-1, yy-2) && (board[xx-1][yy-2] == null || board[xx-1][yy-2].getColor() != super.getColor())
				&& noRevealCheck(board, new Move(new Pair(getX(), getY()), new Pair(xx-1, yy-2)))) {
			getMoveList().add(new Move(new Pair(getX(), getY()), new Pair(xx-1, yy-2)));
		}
	}

}
