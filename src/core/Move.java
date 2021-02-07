package core;

public class Move {
	
	private Pair start;
	private Pair end;
	
	public Move(Pair p1, Pair p2) {
		start = p1;
		end = p2;
	}

	public Pair getStart() {
		return start;
	}
	
	public Pair getEnd() {
		return end;
	}
	
	public boolean compareMove(Move m) {
		if (m.getStart().comparePair(start) && m.getEnd().comparePair(end)) {
			return true;
		}
		return false;
	}
}
