package core;

import java.util.*;

import javax.swing.JOptionPane;

public class GameLogic {
	
	final int BOARD_SIZE = 8;
	private Piece[][] board;
	private int numMoves = 0;
	private boolean gameOver;
	private boolean playerTurn;
	private boolean inCheck;
	private Move bestMove;
	private ArrayList<Move> moveHistory;
	private int historyIndex;
	
	public GameLogic() {
		board = new Piece[BOARD_SIZE][BOARD_SIZE];
		gameOver = false;
		inCheck = false;
		setPlayerTurn(true);
		moveHistory = new ArrayList<>();
		bestMove = null;
		historyIndex = 0;
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
	
	public Move getBestMove() {
		return bestMove;
	}
	
	public boolean currentColour() {
		return numMoves % 2 == 0;
	}
	
	public void moveBack() {
		if (moveHistory.size() > 0 && historyIndex > 0) {
			
			historyIndex--;
		}
	}
	
	public void moveForward() {
		if (moveHistory.size() > 0 && historyIndex < moveHistory.size()) {
			Move m = moveHistory.get(historyIndex);
			
			historyIndex++;
		}
	}
	
	/**
	 *  Function to clear all the pieces off the board
	 */
	public void clearBoard() {
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				board[i][j] = null;
			}
		}
		historyIndex = 0;
	}
	
	/**
	 *  Function to set up the pieces on the board
	 */
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
	}
	
	public void updatePieceMoves(Piece[][] board, boolean col) {
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				if (board[i][j] != null) {
					if (board[i][j].getColor() == col) {
						getChildPiece(board[i][j]).updateMoveList(board);
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
	
	/**
	 *  Function to open a pop-up dialog to let a user select which piece to promote their pawn to.
	 * @return
	 */
	public int pawnPromoteSelect() {
		String[] options = {"Queen",
		                    "Rook",
		                    "Bishop",
		                    "Knight"};
		int returnVal = JOptionPane.showOptionDialog(null, "Choose a piece to promote to",
		    "Pawn Promote", JOptionPane.PLAIN_MESSAGE, 0, null, options, options[3]);
		return returnVal;
	}
	
	/**
	 *  Function to check if the game is over, by checking if there are no moves left, or checking for a draw
	 *  by insufficient material.
	 * @param board
	 * @param col
	 * @return
	 */
	public boolean checkGameNotOver(Piece[][] board, boolean col) {
		King k = getKing(board, col);
		if (k.isChecked(board)) {
			inCheck = true;
		} else {
			inCheck = false;
		}
		if (countPieceMoves(board, col) == 0 || checkNotDraw(board)) {
			gameOver = true;
			return true;
		}
		return false;
	}
	
	/**
	 *  Function to return true if the game is drawn (either only kings left, or one side has a king and knight
	 *  or king and bishop, which is a draw by insufficient material) 
	 * @param board
	 * @return
	 */
	public boolean checkNotDraw(Piece[][] board) {
		int count = 0;
		int pieces = 0;
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				if (board[i][j] != null) {
					count++;
				} else if (board[i][j] != null && (board[i][j].getSymbol() == 'H' || board[i][j].getSymbol() == 'B')) {
					pieces++;
				}
			}
		}
		return count == 2 || (count == 3 && pieces == 1);
	}
	
	/**
	 *  Function to test if a given move is possible, first by checking that it designates a piece position on the board
	 *  and the new position is either empty or to a piece of the opposite colour. If these are true, then the function
	 *  calls the moveInMoveList function.
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	public boolean tryMove(int x1, int y1, int x2, int y2) {
		Piece p1 = board[x1][y1];
		Piece p2 = board[x2][y2];
		if (p1 != null && p2 == null) {
			if (moveInMoveList(p1, new Move(new Pair(x1, y1),new Pair(x2, y2), false))) {
				return true;
			}
		} else if (p1 != null && p2.getColor() != p1.getColor()) {
			if (moveInMoveList(p1, new Move(new Pair(x1, y1),new Pair(x2, y2), true))) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 *  Function to apply the given move to the board, update the move count and check for any pawn promotions.
	 *  The move is also added to the move history.
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public void makeGameMove(int x1, int y1, int x2, int y2, boolean user) {
		Move move = new Move(new Pair(x1, y1), new Pair(x2, y2), board[x2][y2] == null ? false : true);
		moveHistory.add(move);
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
		checkPawnPromotes(board, getChildPiece(board[x2][y2]).getColor(), user);
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
	 *  Debug function to print board state
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
	
	
	public void makeSmartBotMove() {
		int score = alphaBeta(getBoard(), 0, 4, currentColour(), true, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
		Move m = getBestMove();
		makeGameMove(m.getStart().getX(), m.getStart().getY(), m.getEnd().getX(), m.getEnd().getY(), false);
		System.out.println("move score: " + score);
	}
	
	public void makeRandomBotMove() {
		Move m = chooseRandomMove(getBoard(), currentColour());
		makeGameMove(m.getStart().getX(), m.getStart().getY(), m.getEnd().getX(), m.getEnd().getY(), false);
	}
	
	/**
	 *  This is the evaluation function to be used by the alpha-beta pruning function. It is a linear combination
	 *  of multiple heuristics.
	 * @param brd
	 * @param col
	 * @return
	 */
	private int linearCombination(Piece[][] brd, boolean col) {
		return 1 * evalBoardStatic(brd, col) + evalMobilityGeneral(brd, col);
	}
	
	/**
	 *  Simple static board evaluation heuristic which returns the total of all the players pieces
	 *  values, minus the total of the opponents pieces values.
	 * @param brd
	 * @param col
	 * @return
	 */
	private int evalBoardStatic(Piece[][] brd, boolean col) {
		int players = 0, opponent = 0;
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				if (brd[i][j] != null && brd[i][j].getColor() == col) {
					players += getPieceValue(brd[i][j].getSymbol());
				} else if (brd[i][j] != null) {
					opponent += getPieceValue(brd[i][j].getSymbol());
				}
			}
		}
		return players - opponent;
	}
	
	/**
	 *  General mobility heuristic which seek to maximise player mobility and minimise opponent mobility,
	 *  where mobility is the number of currently available legal moves.
	 * @param brd
	 * @param col
	 * @return
	 */
	private int evalMobilityGeneral(Piece[][] brd, boolean col) {
		int players = 0, opponent = 0;
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				if (brd[i][j] != null && brd[i][j].getColor() == col) {
					players += brd[i][j].getMoveList().size();
				} else if (brd[i][j] != null) {
					opponent += brd[i][j].getMoveList().size();
				}
			}
		}
		return players - opponent;
	}
	
	/**
	 *  Function to return a piece's static point value.
	 * @param c
	 * @return
	 */
	private int getPieceValue(char c) {
		switch (c) {
			case 'Q':
				return 100;
			case 'R':
				return 50;
			case 'B':
				return 30;
			case 'H':
				return 30;
			case 'P':
				return 10;
			default:
				return 0;
		}
	}
	
	/**
	 *  Function to randomly return one of the moves from the move list
	 * @param board
	 * @param col
	 * @return
	 */
	public Move chooseRandomMove(Piece[][] board, boolean col) {
		ArrayList<Move> moves = returnLegalMoves(col, board);
		Random random = new Random();
		return moves.get(random.nextInt(moves.size()));
	}
	
	/**
	 *  Minimax search with alpha-beta pruning. This function explores the game tree and for each possible move, evaluates the
	 *  board using one of the heuristic board evaluation functions, so moves can be compared to each other. The best move is
	 *  stored as alpha or beta depending on who's move is being explored, and this can be used to prune other branches in the
	 *  search tree, if they are too high / low. If a leaf node is a take, then quiescence search is used to evaluate the board.
	 * @param board
	 * @param depth
	 * @param maxDepth
	 * @param col
	 * @param max
	 * @param alpha
	 * @param beta
	 * @param isTake
	 * @return
	 */
	public int alphaBeta(Piece[][] board, int depth, int maxDepth, boolean col, boolean max, int alpha, int beta, boolean isTake) {
		updatePieceMoves(board, col);
        ArrayList<Move> moves = returnLegalMoves(col, board);
        Collections.shuffle(moves, new Random()); // should implemented function to sort list of possible moves 
        //If max depth reached or game is over or if there are no legal moves
        if (moves.size() == 0) {
        	if (depth % 2 == 0) {
        		return -1000;
        	} else {
        		return 1000;
        	}
        } else if (depth == maxDepth) {
        	if (isTake) {
        		Piece[][] newBoard = deepCopyBoard(board);
        		return quiescenceSearch(newBoard, 0, 1, col, max, alpha, beta);
        	}
            return linearCombination(board, col);
        }
        int bestScore = Integer.MIN_VALUE;
        Move bestM = null;
        for (Move m : moves) {
            Piece[][] newBoard = deepCopyBoard(board);
            Pair p1 = m.getStart();
            Pair p2 = m.getEnd();
            newBoard[p1.getX()][p1.getY()].placePiece(newBoard, p2.getX(), p2.getY());
            newBoard[p1.getX()][p1.getY()] = null;
            int v = alphaBeta(newBoard, depth+1, maxDepth, !col, !max, alpha, beta, m.isTake());
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
        bestMove = bestM;
        return bestScore;
    }
	
	/**
	 *  Quiescence search function, similar to the alpha-beta search function although this one only searches moves
	 *  which are takes, to a given max depth. This function will be called if a terminal node in the alpha-beta search
	 *  function is unstable (it represents a take).
	 * @param board
	 * @param depth
	 * @param maxDepth
	 * @param col
	 * @param max
	 * @param alpha
	 * @param beta
	 * @return
	 */
	public int quiescenceSearch(Piece[][] board, int depth, int maxDepth, boolean col, boolean max, int alpha, int beta) {
		updatePieceMoves(board, col);
        ArrayList<Move> moves = returnLegalMoves(col, board);
        removeNonTakeMoves(moves);
        Collections.shuffle(moves, new Random()); // should implemented function to sort list of possible moves 
        if (returnLegalMoves(col, board).size() == 0) {
        	if (depth % 2 == 0) {
        		return -1000;
        	} else {
        		return 1000;
        	}
        } else if (depth == maxDepth || moves.size() == 0) {
            return linearCombination(board, col);
        }
        int bestScore = Integer.MIN_VALUE;
        Move bestM = null;
        for (Move m : moves) {
            Piece[][] newBoard = deepCopyBoard(board);
            Pair p1 = m.getStart();
            Pair p2 = m.getEnd();
            newBoard[p1.getX()][p1.getY()].placePiece(newBoard, p2.getX(), p2.getY());
            newBoard[p1.getX()][p1.getY()] = null;
            int v = quiescenceSearch(newBoard, depth+1, maxDepth, !col, !max, alpha, beta);
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
        bestMove = bestM;
        return bestScore;
    }
	
	/**
	 *  Function to remove any moves from the given move list which are not takes. This function is used in
	 *  the quiescence search function, so only the 'unstable' moves will be explored.
	 * @param list
	 */
	private void removeNonTakeMoves(ArrayList<Move> list) {
		Iterator<Move> iter = list.iterator();
		while (iter.hasNext()) {
			if (!iter.next().isTake()) {
				iter.remove();
			}
		}
		
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
				if (brd[i][j] != null && brd[i][j].getColor() == col) {
					fullList.addAll(getChildPiece(brd[i][j]).getMoveList());
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
	
	private King getKing(Piece[][] board, boolean col) {
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				if (board[i][j] != null && board[i][j].getSymbol() == 'K' && board[i][j].getColor() == col) {
					return (King) board[i][j];
				}
			}
		}
		System.out.println("null pointer about to be thrown, deep copy method not working");
		return null; // should be unreachable
	}

}
