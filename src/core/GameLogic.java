package core;

import java.util.*;

import javax.swing.JOptionPane;

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
	
	public boolean currentColour() {
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
	
	public void updatePieceMoves(Piece[][] board, boolean col) {
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				if (board[i][j] != null) {
					if (board[i][j].getColor() == col) {
						getChildPiece(board[i][j]).updateMoveList(board);
					} else {
						board[i][j].clearMoveList();
					}
				}
			}
		}
	}
	
	public int countPieceMoves(Piece[][] board, boolean col) {
		int count = 0;
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				if (board[i][j] != null && board[i][j].getColor() == col) {
					
					count += getChildPiece(board[i][j]).getMoveList().size();
				}
			}
		}
		return count;
	}
	
	public void checkPawnPromotes(Piece[][] board, boolean col, boolean userMove) {
		for (int i = 0; i < BOARD_SIZE; i++) {
			int row = col ? 0 : BOARD_SIZE-1;
			if (board[row][i] != null && board[row][i].getColor() == col && board[row][i].getSymbol() == 'P') {
				if (userMove) {
					switch (pawnPromoteSelect()) {
					case 0:
						board[row][i] = new Queen(row, i, col);
						break;
					case 1:
						board[row][i] = new Rook(row, i, col);
						break;
					case 2:
						board[row][i] = new Bishop(row, i, col);
						break;
					case 3:
						board[row][i] = new Knight(row, i, col);
						break;
					default:
						System.out.println("invalid selection, promoting to queen by default");
						board[row][i] = new Queen(row, i, col);
					}
				} else {
					board[row][i] = new Queen(row, i, col);
				}
				break;
			}
		}
	}
	
	public int pawnPromoteSelect() {
		String[] options = {"Queen",
		                    "Rook",
		                    "Bishop",
		                    "Knight"};
		int returnVal = JOptionPane.showOptionDialog(null, "Choose a piece to promote to",
		    "Pawn Promote", JOptionPane.PLAIN_MESSAGE, 0, null, options, options[3]);
		return returnVal;
	}
	
	public void checkGameNotOver(Piece[][] board, boolean col) {
		King k = (col) ? wK : bK;
		if (k.isChecked(board)) {
			inCheck = true;
		} else {
			inCheck = false;
		}
		if (countPieceMoves(board, col) == 0) {
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
		checkPawnPromotes(board, getChildPiece(board[x2][y2]).getColor(), true);
		numMoves++;
		playerTurn = false;
	}
	
	/**
	 *  Function to return the given Piece object, after it has been casted to its specific child type 
	 * @param piece
	 * @return
	 */
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
	
	/**
	 *  Function to return true if the given move is found in the given piece's move list
	 * @param p
	 * @param move
	 * @return
	 */
	private boolean moveInMoveList(Piece p, Move move) {
		for (Move m : getChildPiece(p).getMoveList()) {
			if (m.compareMove(move)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 *  debug function to print board state
	 */
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
	
	public int alphaBeta(Piece[][] board, int depth, int maxDepth, boolean col, boolean max, int alpha, int beta, int feature) {
		updatePieceMoves(board, col);
        ArrayList<Move> moves = returnLegalMoves(col, board);
        Collections.shuffle(moves, new Random());
        //If max depth reached or game is over or if there are no legal moves
        if (depth == maxDepth || moves.size() == 0) {
            return 0;
        }
        int bestScore = -1;
        Move bestM = null;
        for (Move m : moves) {
            Piece[][] newBoard = deepCopyBoard(board);
//            placeMove(p.getX(), p.getY(), col, newBoard);
            int v = alphaBeta(newBoard, depth+1, maxDepth, !col, !max, alpha, beta, feature);
            if (max) {
                if (bestScore < v || bestM == null) {
                    bestScore = v;
                    bestM = m;
                    //set alpha to highest score so far
                    if (bestScore > alpha) {
                        alpha = bestScore;
                    }
                    //if beta less than alpha, stop searching child nodes
                    if (beta <= alpha) {
                        break;
                    }
                }
            } else {
                if (bestScore > v || bestM == null) {
                    bestScore = v;
                    bestM = m;
                    if (bestScore < beta) {
                        beta = bestScore;
                    }
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
        }

        return 0;
    }
	
	/**
	 *  Function to return a combined list of all the available legal moves for a given colour, by
	 *  searching the board for pieces of the given colour, and adding their move lists to the combined list
	 * @param col
	 * @param brd
	 * @return
	 */
	private ArrayList<Move> returnLegalMoves(boolean col, Piece[][] brd) {
		ArrayList<Move> fullList = new ArrayList<Move>();
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				if (board[i][j] != null && board[i][j].getColor() == col) {
					fullList.addAll(getChildPiece(board[i][j]).getMoveList());
				}
			}
		}
		return fullList;
	}

	/**
	 * Function to return a deep copy of the given board
	 * @param board
	 * @return
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
