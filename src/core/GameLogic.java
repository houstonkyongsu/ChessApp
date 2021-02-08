package core;

import java.util.*;

public class GameLogic {
	
	final int BOARD_SIZE = 8;
	private Piece[][] board;
	private int numMoves = 0;
	private boolean gameOver;
	private King bK;
	private King wK;
	private ArrayList<Move> moves;
	private boolean inCheck;
	
	public GameLogic() {
		board = new Piece[BOARD_SIZE][BOARD_SIZE];
		gameOver = false;
		inCheck = false;
		moves = new ArrayList<>();
	}
	
	public Piece[][] getBoard() {
		return board;
	}
	
	public boolean isGameOver() {
		return gameOver;
	}
	
	public int getNumMoves() {
		return numMoves;
	}
	
	private boolean currentColour() {
		return numMoves % 2 == 0;
	}
	
	public void setupBoard() {
		for (int i = 0; i < BOARD_SIZE; i++) {
			board[1][i] = new Pawn(1, i, false);
			board[6][i] = new Pawn(6, i, true);
		}
		board[0][0] = new Rook(0, 0, false);
		board[0][7] = new Rook(0, 7, false);
		board[7][7] = new Rook(7, 7, true);
		board[7][0] = new Rook(7, 0, true);
		board[0][1] = new Knight(0, 1, false);
		board[0][6] = new Knight(0, 6, false);
		board[7][6] = new Knight(7, 6, true);
		board[7][1] = new Knight(7, 1, true);
		board[0][2] = new Bishop(0, 2, false);
		board[0][5] = new Bishop(0, 5, false);
		board[7][2] = new Bishop(7, 2, true);
		board[7][5] = new Bishop(7, 5, true);
		board[0][3] = new Queen(0, 3, false);
		board[7][3] = new Queen(7, 3, true);
		board[0][4] = new King(0, 4, false);
		board[7][4] = new King(7, 4, true);
		wK = (King) board[7][4];
		bK = (King) board[0][4];
	}
	
	public void updatePieceMoves() {
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				if (board[i][j] != null && board[i][j].getColor() == currentColour()) {
					board[i][j].updateMoveList();
				}
			}
		}
	}
	
	public int countPieceMoves() {
		int count = 0;
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				if (board[i][j] != null && board[i][j].getColor() == currentColour()) {
					count += board[i][j].getMoveList().size();
				}
			}
		}
		return count;
	}
	
	public void gameLoop() {
		printBoard();
	 	
		
	}
	
	public void checkGameNotOver() {
		King k = (currentColour()) ? wK : bK;
		if (k.isChecked(board)) {
			inCheck = true;
		} else {
			inCheck = false;
		}
		if (countPieceMoves() == 0) {
			gameOver = true;
		}
	}
	
	public boolean tryMove(int x1, int y1, int x2, int y2) {
		Piece p1 = board[x1][y1];
		Piece p2 = board[x2][y2];
		if (p1 != null && (p2 == null || p2.getColor() != p1.getColor())) {
			if (moveInMoveList(p1, new Move(new Pair(x1, y1),new Pair(x2, y2)))) {
				return true;
			}
		}
		return false;
	}
	
	public void makeUserMove(int x1, int y1, int x2, int y2) {
		board[x1][y1].placePiece(board, x2, y2);
		board[x1][y1] = null;
		numMoves++;
	}
	
	private boolean moveInMoveList(Piece p, Move move) {
		for (Move m : p.getMoveList()) {
			if (m.compareMove(move)) {
				return true;
			}
		}
		return false;
	}
	
	
	public void printBoard() {
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				if (board[i][j] == null) {
					System.out.print("-- ");
				} else if (board[i][j].getColor()) {
					System.out.print("W" + board[i][j].getSymbol() + " ");
				} else {
					System.out.print("B" + board[i][j].getSymbol() + " ");
				}
			}
		}
	}
	

}
