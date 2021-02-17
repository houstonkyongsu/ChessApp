package core;

public class King extends Piece {

	public King(int x, int y, boolean white) {
		super(x, y, white);
		super.setSymbol('K');
	}
	
	@SuppressWarnings("unchecked")
	public King copyPiece() {
		King k = new King(getX(), getY(), getColor());
		k.setMoves(getMoves());
		k.setMoveList(getMoveList());
		return k;
	}
	
	public void updateMoveList(Piece[][] board) {
		getMoveList().clear();
		checkSafeMove(board, 0, 1);
		checkSafeMove(board, 1, 0);
		checkSafeMove(board, 0, -1);
		checkSafeMove(board, -1, 0);
		checkSafeMove(board, 1, 1);
		checkSafeMove(board, 1, -1);
		checkSafeMove(board, -1, 1);
		checkSafeMove(board, -1, -1);
		if (!isChecked(board)) {
			checkCastling(board);
		}
	}
	
	public boolean isChecked(Piece[][] board) {
		if (checkPawn(board) || checkKnight(board)
				|| checkRookBishop(board, 0, 1, 'R')
				|| checkRookBishop(board, 1, 0, 'R')
				|| checkRookBishop(board, 0, -1, 'R')
				|| checkRookBishop(board, -1, 0, 'R')
				|| checkRookBishop(board, 1, 1, 'B')
				|| checkRookBishop(board, -1, -1, 'B')
				|| checkRookBishop(board, 1, -1, 'B')
				|| checkRookBishop(board, -1, 1, 'B')) {
			System.out.println("king is checked");
			return true;
		}
		return false;
	}
	
	public boolean squareNotAttacked(Piece[][] board, Pair p1) {
		Piece[][] copy = deepCopyBoard(board);
		copy[getX()][getY()].placePiece(copy, p1.getX(), p1.getY());;
		copy[getX()][getY()] = null;
		if (((King) copy[p1.getX()][p1.getY()]).isChecked(copy)) {
			return false;
		}
		return true;
	}
	
	private void checkCastling(Piece[][] board) {
		if (getMoves() == 0 && board[getX()][getY()+3] != null && board[getX()][getY()+3].getMoves() == 0 && board[getX()][getY()+1] == null && board[getX()][getY()+2] == null
				 && board[getX()][getY()+3].getSymbol() == 'R') {
			if (squareNotAttacked(board, new Pair(getX(), getY()+1)) && squareNotAttacked(board, new Pair(getX(), getY()+2))) {
				getMoveList().add(new Move(new Pair(getX(), getY()), new Pair(getX(), getY()+2)));
			}
		} else if (getMoves() == 0 && board[getX()][getY()-4] != null && board[getX()][getY()-4].getMoves() == 0 && board[getX()][getY()-1] == null && board[getX()][getY()-2] == null
				 && board[getX()][getY()-3] == null && board[getX()][getY()-4].getSymbol() == 'R') {
			if (squareNotAttacked(board, new Pair(getX(), getY()-1)) && squareNotAttacked(board, new Pair(getX(), getY()-2))) {
				getMoveList().add(new Move(new Pair(getX(), getY()), new Pair(getX(), getY()-2)));
			}
		}
	}
	
	private void checkSafeMove(Piece[][] board, int x, int y) {
		int xx = getX() + x;
		int yy = getY() + y;
		if (withinBounds(xx, yy) && (board[xx][yy] == null || board[xx][yy].getColor() != getColor()) && squareNotAttacked(board, new Pair(xx, yy))
				&& noKingNear(board, xx, yy)) {
			getMoveList().add(new Move(new Pair(getX(), getY()), new Pair(xx, yy)));
		}
	}
	
	private boolean noKingNear(Piece[][] board, int x, int y) {
		Piece[][] copy = deepCopyBoard(board);
		copy[getX()][getY()].placePiece(copy, x, y);;
		copy[getX()][getY()] = null;
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (x + i < 0 || y + j < 0 || x + i == BOARD_SIZE || y + j == BOARD_SIZE || (i == 0 && j == 0)) {
					continue;
				}
				if (board[x + i][y + j] != null && board[x + i][y + j].getSymbol() == 'K' && board[x + i][y + j].getColor() != getColor()) {
					return false;
				}
			}
		}
		return true;
	}
	
	private boolean checkRookBishop(Piece[][] board, int addx, int addy, char c) {
		int xx = getX();
		int yy = getY();
		while (true) {
			xx += addx;
			yy += addy;
			if (!withinBounds(xx, yy)) {
				break;
			}
			if (board[xx][yy] != null) {
				if (board[xx][yy].getColor() != this.getColor() && (board[xx][yy].getSymbol() == c || board[xx][yy].getSymbol() == 'Q')) {
					return true;
				}
				return false;
			}
		}
		return false;
	}
	
	private boolean checkPawn(Piece[][] board) {
		int xx = getX();
		int yy = getY();
		if (getColor()) {
			if (withinBounds(xx-1, yy-1) && board[xx-1][yy-1] != null && !board[xx-1][yy-1].getColor() && board[xx-1][yy-1].getSymbol() == 'P') {
				return true;
			}
			if (withinBounds(xx-1, yy+1) && board[xx-1][yy+1] != null && !board[xx-1][yy+1].getColor() && board[xx-1][yy+1].getSymbol() == 'P') {
				return true;
			}
		} else {
			if (withinBounds(xx+1, yy-1) && board[xx+1][yy-1] != null && board[xx+1][yy-1].getColor() && board[xx+1][yy-1].getSymbol() == 'P') {
				return true;
			}
			if (withinBounds(xx+1, yy+1) && board[xx+1][yy+1] != null && board[xx+1][yy+1].getColor() && board[xx+1][yy+1].getSymbol() == 'P') {
				return true;
			}
		}
		return false;
	}
	
	private boolean checkKnight(Piece[][] board) {
		int xx = getX();
		int yy = getY();
		if (withinBounds(xx+2, yy+1) && board[xx+2][yy+1] != null && board[xx+2][yy+1].getColor() != getColor()
				 && board[xx+2][yy+1].getSymbol() == 'H') {
			return true;
		} else if (withinBounds(xx-2, yy+1) && board[xx-2][yy+1] != null && board[xx-2][yy+1].getColor() != getColor()
				&& board[xx-2][yy+1].getSymbol() == 'H') {
			return true;
		} else if (withinBounds(xx+2, yy-1) && board[xx+2][yy-1] != null && board[xx+2][yy-1].getColor() != getColor()
				&& board[xx+2][yy-1].getSymbol() == 'H') {
			return true;
		} else if (withinBounds(xx-2, yy-1) && board[xx-2][yy-1] != null && board[xx-2][yy-1].getColor() != getColor()
				&& board[xx-2][yy-1].getSymbol() == 'H') {
			return true;
		} else if (withinBounds(xx+1, yy+2) && board[xx+1][yy+2] != null && board[xx+1][yy+2].getColor() != getColor()
				&& board[xx+1][yy+2].getSymbol() == 'H') {
			return true;
		} else if (withinBounds(xx-1, yy+2) && board[xx-1][yy+2] != null && board[xx-1][yy+2].getColor() != getColor()
				&& board[xx-1][yy+2].getSymbol() == 'H') {
			return true;
		} else if (withinBounds(xx+1, yy-2) && board[xx+1][yy-2] != null && board[xx+1][yy-2].getColor() != getColor()
				&& board[xx+1][yy-2].getSymbol() == 'H') {
			return true;
		} else if (withinBounds(xx-1, yy-2) && board[xx-1][yy-2] != null && board[xx-1][yy-2].getColor() != getColor()
				&& board[xx-1][yy-2].getSymbol() == 'H') {
			return true;
		}
		
		return false;
	}
	
}
