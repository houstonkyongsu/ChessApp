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
	
	public Piece(int x, int y, boolean color) {
		this.setX(x);
		this.setY(y);
		this.color = color;
		pinned = false;
		moves = 0;
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
	

}
