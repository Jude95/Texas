package bean;

public class Person {
	private String id;
	private int jetton;
	private int money;
	
	
	public Person(String name, int jetton, int money) {
		this.id = name;
		this.jetton = jetton;
		this.money = money;
	}
	
	public String getName() {
		return id;
	}
	public void setName(String name) {
		this.id = name;
	}
	public int getJetton() {
		return jetton;
	}
	public void setJetton(int jetton) {
		this.jetton = jetton;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}

}
