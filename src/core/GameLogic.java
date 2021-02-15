package core;

import java.util.*;

public class GameLogic {
	
	final int BOARD_SIZE = 8;
	private Piece[][] board;
	private int numMoves = 0;
	private boolean gameOver;
	private boolean playerTurn;
	private King bK;
	private King wK;
	private ArrayList<Move> moves;
	private boolean inCheck;
	
	public GameLogic() {
		board = new Piece[BOARD_SIZE][BOARD_SIZE];
		gameOver = false;
		inCheck = false;
		setPlayerTurn(true);
		moves = new ArrayList<>();
	}
	
	public boolean isPlayerTurn() {
		return playerTurn;
	}

	public void setPlayerTurn(boolean playerTurn) {
		this.playerTurn = playerTurn;
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
				if (board[i][j] != null) {
					if (board[i][j].getColor() == currentColour()) {
						getChildPiece(board[i][j]).updateMoveList(board);
					} else {
						board[i][j].clearMoveList();
					}
				}
			}
		}
	}
	
	public int countPieceMoves() {
		int count = 0;
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				if (board[i][j] != null && (board[i][j].getColor() == currentColour())) {
					
					count += getChildPiece(board[i][j]).getMoveList().size();
				}
			}
		}
		return count;
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
	
	public void makeGameMove(int x1, int y1, int x2, int y2) {
		getChildPiece(board[x1][y1]).placePiece(board, x2, y2);
		board[x1][y1] = null;
		if (board[x2][y2].getSymbol() == 'K' && board[x2][y2].getMoves() == 1 && Math.abs(y1-y2) > 1) {
			if (y2 > 4) {
				getChildPiece(board[x2][BOARD_SIZE-1]).placePiece(board, x2, y2-1);
				board[x2][BOARD_SIZE-1] = null;
			} else {
				getChildPiece(board[x2][0]).placePiece(board, x2, y2+1);
				board[x2][0] = null;
			}
		}
		numMoves++;
		playerTurn = false;
	}
	
	public Piece getChildPiece(Piece piece) {
		switch (piece.getSymbol()) {
			case 'P':
				return (Pawn) piece;
			case 'K':
				return (King) piece;
			case 'H':
				return (Knight) piece;
			case 'B':
				return (Bishop) piece;
			case 'R':
				return (Rook) piece;
			case 'Q':
				return (Queen) piece;
			default:
				System.out.println("superclass piece object on board, how did this happen");
		}
		return null;
	}
	
	private boolean moveInMoveList(Piece p, Move move) {
		for (Move m : getChildPiece(p).getMoveList()) {
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
