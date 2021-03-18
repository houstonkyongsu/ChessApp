package core;

import java.util.ArrayList;

public abstract class Piece {
	
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
	
	public void clearMoveList() {
		moveList.clear();
	}
	
	public void setMoveList(ArrayList<Move> moves) {
		moveList.clear();
		moveList.addAll(moves);
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
		while (true) {
			xx += addx;
			yy += addy;
			if (!withinBounds(xx, yy)) {
				break;
			}
			if (board[xx][yy] == null && noRevealCheck(board, new Move(new Pair(getX(), getY()), new Pair(xx, yy), false))) {
				list.add(new Move(new Pair(getX(), getY()), new Pair(xx, yy), false));
			} else if (board[xx][yy] != null && board[xx][yy].getColor() != color && noRevealCheck(board, new Move(new Pair(getX(), getY()), new Pair(xx, yy), true))) {
				list.add(new Move(new Pair(getX(), getY()), new Pair(xx, yy), true));
				break;
			} else {
				break;
			}
		}
		return list;
	}

	abstract public <T> T copyPiece();

	abstract public void updateMoveList(Piece[][] board);
	
	public boolean noRevealCheck(Piece[][] board, Move m) {
		Piece[][] copy = deepCopyBoard(board);
		Pair p1 = m.getStart();
		Pair p2 = m.getEnd();
//		if (board[][]) {
//			
//		}
		copy[p1.getX()][p1.getY()].placePiece(copy, p2.getX(), p2.getY());
		boolean col = copy[p2.getX()][p2.getY()].getColor();
		copy[p1.getX()][p1.getY()] = null;
		if (getKing(copy, col).isChecked(copy)) {
			return false;
		}
		return true;
	}
	
	public King getKing(Piece[][] board, boolean col) {
		int count = 0;
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				if (board[i][j] != null && board[i][j].getSymbol() == 'K' && board[i][j].getColor() == col) {
					return (King) board[i][j];
				}
				if (board[i][j] != null && board[i][j].getSymbol() == 'K') {
					count++;
				}
			}
		}
		if (count == 2) {
			System.out.println("found");			
		}
		System.out.println("null pointer about to be thrown, deep copy method not working");
		return null; // should be unreachable
	}
	
	
	/*
	 * Function to return a deep copy of the board, so original object values aren't changed when checking future possible moves.
	 */
	public Piece[][] deepCopyBoard(Piece[][] board) {
		Piece[][] copy = new Piece[BOARD_SIZE][BOARD_SIZE];
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				if (board[i][j] != null) {
					Piece piece = null;
					switch (board[i][j].getSymbol()) {
						case 'P':
							piece = (Pawn) board[i][j];
							break;
						case 'K':
							piece = (King) board[i][j];
							break;
						case 'H':
							piece = (Knight) board[i][j];
							break;
						case 'B':
							piece = (Bishop) board[i][j];
							break;
						case 'R':
							piece = (Rook) board[i][j];
							break;
						case 'Q':
							piece = (Queen) board[i][j];
							break;
						default:
							System.out.println("superclass piece object on board, how did this happen");
					}
					copy[i][j] = piece.copyPiece();
				}
			}
		}
		return copy;
	}
	

}
