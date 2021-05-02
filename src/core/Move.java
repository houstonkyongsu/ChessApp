package core;

public class Move {
	
	private Pair start;
	private Pair end;
	private char take;
	private int value;
	
	public Move(Pair p1, Pair p2, char take) {
		this.start = p1;
		this.end = p2;
		this.take = take;
		this.value = getPieceValue(take);
	}
	
	public char getTake() {
		return take;
	}

	public Pair getStart() {
		return start;
	}
	
	public Pair getEnd() {
		return end;
	}
	
	public int getValue() {
		return value;
	}
	
	public boolean compareMove(Move m) {
		if (m.getStart().comparePair(start) && m.getEnd().comparePair(end)) {
			return true;
		}
		return false;
	}
	
	private int getPieceValue(char c) {
		switch (c) {
			case 'Q':
				return 10;
			case 'R':
				return 5;
			case 'B':
				return 3;
			case 'H':
				return 3;
			case 'P':
				return 1;
			default:
				return 0;
		}
	}
	
}
