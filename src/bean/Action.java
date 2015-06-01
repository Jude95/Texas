package bean;

public enum Action {
	blind, check, call, raise, all_in, fold;

	private int num;

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public static Action params(String str) {
		if (str.equals(blind.name())) {
			return blind;
		} else if (str.equals(check.name())) {
			return check;
		} else if (str.equals(call.name())) {
			return call;
		} else if (str.equals(raise.name())) {
			return raise;
		} else if (str.equals(all_in.name())) {
			return all_in;
		} else {
			return fold;
		}
	}

}
