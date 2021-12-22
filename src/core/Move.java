package core;

public class Move {
	
	private Pair start;
	private Pair end;
	private char take;
	private int moveValue;
	
	public Move(Pair p1, Pair p2, char take) {
		this.start = p1;
		this.end = p2;
		this.take = take;
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
		return moveValue;
	}
	
	public void setValue(int value) {
		this.moveValue = value;
	}
	
	public boolean compareMove(Move m) {
		if (m.getStart().comparePair(start) && m.getEnd().comparePair(end)) {
			return true;
		}
		return false;
	}
	
}
