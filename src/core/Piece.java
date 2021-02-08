package core;

import java.util.ArrayList;

public class Piece {
	
	final int BOARD_SIZE = 8;
	private int x;
	private int y;
	private boolean color; // WHITE IS TRUE, BLACK IS FALSE
	private boolean pinned;
	private char symbol = 'x';
	private int moves;
	private ArrayList<Move> moveList;
	
	public Piece(int x, int y, boolean color) {
		this.setX(x);
		this.setY(y);
		this.color = color;
		pinned = false;
		moves = 0;
		moveList = new ArrayList<>();
	}
	
	public ArrayList<Move> getMoveList() {
		return moveList;
	}
	
	public boolean getColor() {
		return color;
	}
	
	public boolean getPinned() {
		return pinned;
	}
	
	public char getSymbol() {
		return symbol;
	}
	
	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public int getMoves() {
		return moves;
	}
	
	public void setMoves(int moves) {
		this.moves = moves;
	}
	
	public boolean withinBounds(int x, int y) {
		if (x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE) {
			return true;
		}
		return false;
	}
	
	public void placePiece(Piece[][] board, int x, int y) {
		board[x][y] = this;
		setX(x);
		setY(y);
		moves++;
	}
	
	public ArrayList<Move> directionalIterList(Piece[][] board, int addx, int addy) {
		ArrayList<Move> list = new ArrayList<>();
		int xx = getX();
		int yy = getY();
		while (withinBounds(xx, yy)) {
			xx += addx;
			yy += addy;
			if (!noRevealCheck(board, new Move(new Pair(getX(), getY()), new Pair(xx, yy)))) {
				return list;
			}
			if (board[xx][yy] == null) {
				list.add(new Move(new Pair(getX(), getY()), new Pair(xx, yy)));
			} else if (board[xx][yy].getColor() != color) {
				list.add(new Move(new Pair(getX(), getY()), new Pair(xx, yy)));
				break;
			} else {
				break;
			}
		}
		return list;
	}

	public Piece copyPiece() { // this method should be overridden in each child class
		return new Piece(getX(), getY(), getColor());
	}

	public void updateMoveList() {
		// function should be overridden in each child class
	}
	
	public boolean noRevealCheck(Piece[][] board, Move m) {
		Piece[][] copy = deepCopyBoard(board);
		Pair p1 = m.getStart();
		Pair p2 = m.getEnd();
		copy[p1.getX()][p1.getY()].placePiece(copy, p2.getX(), p2.getY());
		copy[p1.getX()][p1.getY()] = null;
		if (getKing(copy).isChecked(copy)) {
			return false;
		}
		return true;
	}
	
	public King getKing(Piece[][] board) {
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				if (board[i][j] != null && board[i][j].getSymbol() == 'K' && board[i][j].getColor() == getColor()) {
					return (King) board[i][j];
				}
			}
		}
		return null; // should be unreachable
	}
	
	public void reduceMoveList() {
		for (Move m : getMoveList()) {
			
		}
	}
	
	/*
	 * Function to return a deep copy of the board, so original object values aren't changed when checking future possible moves.
	 */
	public Piece[][] deepCopyBoard(Piece[][] board) {
		Piece[][] copy = new Piece[BOARD_SIZE][BOARD_SIZE];
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				if (board[i][j] != null) {
					copy[i][j] = board[i][j].copyPiece();
				}
			}
		}
		return copy;
	}
	

}
