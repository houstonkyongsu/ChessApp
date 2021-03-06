package core;

public class Pair {
	
	private int x;
	private int y;
	
	public Pair(int x, int y) {
		this.setX(x);
		this.setY(y);
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
	
	public boolean comparePair(Pair p) {
		if (p.getX() == x && p.getY() == y) {
			return true;
		}
		return false;
	}
	
	public int getCommonCoord(Pair p) {
		if (p.getX() == x) {
			return 0;
		} else if (p.getY() == y) {
			return 1;
		}
		return -1;
	}
}
