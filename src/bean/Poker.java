package bean;

public class Poker {
	private Color color;
	
	/*
	 * 5	6	7	8	9	T	J	Q	K	A
	 * 5	6	7	8	9	10	11	12	13	14
	 */
	private int point;
	
	public Poker(Color color, int point) {
		this.color = color;
		this.point = point;
	}
	
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
}

	