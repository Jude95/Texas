package bean;

public class Poker implements Comparable<Poker>{
	private Color color;
	
	/*
	 * 2	3	4	5	6	7	8	9	T	J	Q	K	A
	 * 2	3	4	5	6	7	8	9	10	11	12	13	14
	 */
	private int point;
	private String pointStr;
	
	public Poker(String point){
		this(Color.NULL,point);
	}
	
	public Poker(Color color, String point) {
		this.color = color;
		this.pointStr = point;
		this.point = parsePoint(point);
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
	public void setPoint(String point) {
		this.pointStr = point;
		this.point = parsePoint(point);
	}
	public String getPointStr(){
		return pointStr;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if(this.point != ((Poker)obj).point){
			return false;
		}
		if(this.getColor() == Color.NULL || ((Poker)obj).getColor() == Color.NULL){
			return true;
		}
		if(this.getColor() == ((Poker)obj).getColor()){
			return true;
		}
		return false;
	}

	public int compareTo(Poker o) {
		return this.point>o.point?1:-1;
	}
	
	public int parsePoint(String point){
		if(point.equals("J")){
			return 11;
		}else if(point.equals("Q")){
			return 12;
		}else if(point.equals("K")){
			return 13;
		}else if(point.equals("A")){
			return 14;
		}else{
			return Integer.parseInt(point);
		}
	}
	
}

	
