package bean;

public class Result {
	private int index;
	private String id;
	private Poker poker1;
	private Poker poker2;
	public Result() {
		super();
	}
	private Combination combination;
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Poker getPoker1() {
		return poker1;
	}
	public void setPoker1(Poker poker1) {
		this.poker1 = poker1;
	}
	public Poker getPoker2() {
		return poker2;
	}
	public void setPoker2(Poker poker2) {
		this.poker2 = poker2;
	}
	public Combination getCombination() {
		return combination;
	}
	public void setCombination(Combination combination) {
		this.combination = combination;
	}
	public Result(int index, String id, Poker poker1, Poker poker2,
			Combination combination) {
		super();
		this.index = index;
		this.id = id;
		this.poker1 = poker1;
		this.poker2 = poker2;
		this.combination = combination;
	}
}
