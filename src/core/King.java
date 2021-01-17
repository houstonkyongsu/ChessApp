package core;

import java.util.ArrayList;

public class King extends Piece {

	public King(int x, int y, boolean white) {
		super(x, y, white);
		super.setSymbol('K');
	}
	
	public King copyPiece() {
		King k = new King(getX(), getY(), getColor());
		k.setMoves(getMoves());
		return k;
	}
	
	public ArrayList<Move> getMoveList(Piece[][] board) {
		ArrayList<Move> list = new ArrayList<>();
		checkSafeMove(board, 0, 1, list);
		checkSafeMove(board, 1, 0, list);
		checkSafeMove(board, 0, -1, list);
		checkSafeMove(board, -1, 0, list);
		checkSafeMove(board, 1, 1, list);
		checkSafeMove(board, 1, -1, list);
		checkSafeMove(board, -1, 1, list);
		checkSafeMove(board, -1, -1, list);
		return list;
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
			return true;
		}
		return false;
	}
	
	private void checkSafeMove(Piece[][] board, int x, int y, ArrayList<Move> list) {
		int xx = getX() + x;
		int yy = getY() + y;
		if (withinBounds(xx, yy) && (board[xx][yy] == null || board[xx][yy].getColor() != getColor()) && !isChecked(board)) {
			list.add(new Move(new Pair(getX(), getY()), new Pair(xx, yy)));
		}
	}
	
	private boolean checkRookBishop(Piece[][] board, int addx, int addy, char c) {
		int xx = getX();
		int yy = getY();
		while (withinBounds(xx, yy)) {
			xx += addx;
			yy += addy;
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
			if (withinBounds(xx+1, yy-1) && board[xx+1][yy-1] != null && !board[xx+1][yy-1].getColor() && board[xx+1][yy-1].getSymbol() == 'P') {
				return true;
			}
			if (withinBounds(xx+1, yy+1) && board[xx+1][yy+1] != null && !board[xx+1][yy+1].getColor() && board[xx+1][yy+1].getSymbol() == 'P') {
				return true;
			}
		}
		return false;
	}
	
	private boolean checkKnight(Piece[][] board) {
		int xx = getX();
		int yy = getY();
		if (withinBounds(xx+2, yy+1) && board[xx+2][yy+1] != null && board[xx+2][yy+1].getColor() != getColor()
				 && board[xx+2][yy+1].getSymbol() == 'K') {
			return true;
		} else if (withinBounds(xx-2, yy+1) && board[xx-2][yy+1] != null && board[xx-2][yy+1].getColor() != getColor()
				&& board[xx-2][yy+1].getSymbol() == 'K') {
			return true;
		} else if (withinBounds(xx+2, yy-1) && board[xx+2][yy-1] != null && board[xx+2][yy-1].getColor() != getColor()
				&& board[xx+2][yy-1].getSymbol() == 'K') {
			return true;
		} else if (withinBounds(xx-2, yy-1) && board[xx-2][yy-1] != null && board[xx-2][yy-1].getColor() != getColor()
				&& board[xx-2][yy-1].getSymbol() == 'K') {
			return true;
		} else if (withinBounds(xx+1, yy+2) && board[xx+1][yy+2] != null && board[xx+1][yy+2].getColor() != getColor()
				&& board[xx+1][yy+2].getSymbol() == 'K') {
			return true;
		} else if (withinBounds(xx-1, yy+2) && board[xx-1][yy+2] != null && board[xx-1][yy+2].getColor() != getColor()
				&& board[xx-1][yy+2].getSymbol() == 'K') {
			return true;
		} else if (withinBounds(xx+1, yy-2) && board[xx+1][yy-2] != null && board[xx+1][yy-2].getColor() != getColor()
				&& board[xx+1][yy-2].getSymbol() == 'K') {
			return true;
		} else if (withinBounds(xx-1, yy-2) && board[xx-1][yy-2] != null && board[xx-1][yy-2].getColor() != getColor()
				&& board[xx-1][yy-2].getSymbol() == 'K') {
			return true;
		}
		
		return false;
	}
	
}
