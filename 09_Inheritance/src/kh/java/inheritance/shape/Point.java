package kh.java.inheritance.shape;

/**
 * 도형의 꼭지점 좌표를 나타내는 클래스
 *  (Shape과는 관계 없음)
 */
public class Point {
	private int x;
	private int y;
	
	
	public Point() {
		super();
	}

	public Point(int x, int y) {
		super();
		this.x = x;
		this.y = y;
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
	
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
	
}
