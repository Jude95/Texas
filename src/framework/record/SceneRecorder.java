package framework.record;

import java.util.Map;

import bean.Action;
import bean.Incident;
import bean.Person;
import bean.Poker;
import bean.Result;
import framework.translate.IActionObserver;

public class SceneRecorder implements IActionObserver, ISceneReader {
	private Person[] person;
	private Poker[] holdPoker;// 手牌
	private Poker[] commonPocker;// 公牌
	private int total;// 奖池
	private int seatNum;// 座位序号，从0开始
	private boolean isFirststart = true;
	private boolean isPersonAlive[];// 本局是否存活
	private int roundNum;// 圈数，从1开始
	private Incident[] preIncident;// 当前玩家之前其他玩家的操作
	private Result[] results;
	@Override
	public void seat(Person[] person) {
		// TODO Auto-generated method stub
		this.person = person;
		roundNum = 1;
		seatNum = 0;
		isPersonAlive = new boolean[person.length];
		for (int i = 0; i < isPersonAlive.length; i++) {
			isPersonAlive[i] = true;
		}
	}

	@Override
	public void blind(String smallId, int smallJetton, String bigId,
			int bigJetton) {
	}

	@Override
	public void blind(String smallId, int smallJetton) {
		// TODO Auto-generated method stub
	}

	@Override
	public void hold(Poker[] poker) {
		// TODO Auto-generated method stub
		holdPoker = poker;
	}

	@Override
	public void inquire(Incident[] action, int total) {
		// TODO Auto-generated method stub
		preIncident = action;
		this.total = total;
		// 如果上一个人弃牌，则把他的isPersonAlive置为false
		if (preIncident[preIncident.length - 1].getAction().getNum() == Action
				.params("fold").getNum()) {
			isPersonAlive[seatNum] = false;// 现在位置还没有+1，所以是上一个人的
		}
		if (isFirststart) {// 起始时，不用改变位置
			isFirststart = false;
		} else {
			seatNum++;
			if (seatNum == person.length) {
				seatNum = 0;
				roundNum++;// 再次轮到庄家的时候，新的一圈开始
			}
			// 如果位置+1后，后面的人已经弃牌，则继续+1
			while (!isPersonAlive[seatNum]) {
				seatNum++;
				if (seatNum == person.length) {
					seatNum = 0;
					roundNum++;// 再次轮到庄家的时候，新的一圈开始
				}
			}

		}
	}

	@Override
	public void flop(Poker[] poker) {
		// TODO Auto-generated method stub
		commonPocker = new Poker[5];
		for (int i = 0; i < 3; i++) {
			commonPocker[i] = poker[i];
		}
	}

	@Override
	public void turn(Poker poker) {
		// TODO Auto-generated method stub
		commonPocker[3] = poker;
	}

	@Override
	public void river(Poker poker) {
		// TODO Auto-generated method stub
		commonPocker[4] = poker;
	}

	@Override
	public void pot_win(Map<String, Integer> pot) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showdown(Result[] results) {
		// TODO Auto-generated method stub
		this.results = results;
	}

	@Override
	public Poker[] hold() {
		// TODO Auto-generated method stub
		return holdPoker;
	}

	@Override
	public int pot() {
		// TODO Auto-generated method stub
		return total;
	}

	@Override
	public Person[] person() {
		// TODO Auto-generated method stub
		return person;
	}

	@Override
	public int roundNum() {
		// TODO Auto-generated method stub
		return roundNum;
	}

	@Override
	public int seatNum() {
		// TODO Auto-generated method stub
		return seatNum;
	}

	@Override
	public Action[] availableAction() {
		// TODO Auto-generated method stub
		Action[] availableAtion;
		// 开局，如果是庄家没有操作
		if (preIncident.length == 0) {
			return null;
		}

		// 如果剩余人数为2或者如果上一个人加注
		if (getAlivePersonNum() == 2
				|| preIncident[preIncident.length - 1].getAction().getNum() == Action
						.params("raise").getNum()) {
			availableAtion = new Action[4];
			availableAtion[0] = Action.call;
			availableAtion[1] = Action.raise;
			availableAtion[2] = Action.fold;
			availableAtion[3] = Action.all_in;
			return availableAtion;
		}

//。。。。。。。。。。。。未完待续。。。。。。。。
		return null;
	}

	@Override
	public Poker[] common() {
		// TODO Auto-generated method stub
		return commonPocker;
	}

	@Override
	public int callJetton() {
		// TODO Auto-generated method stub
		return preIncident[preIncident.length - 1].getBet();
	}

	@Override
	public int lastJetton() {
		// TODO Auto-generated method stub
		int tempJetton = preIncident[preIncident.length - 1].getPerson()
				.getJetton();
		return tempJetton - callJetton();
	}

	private int getAlivePersonNum() {
		int num = 0;
		for (int i = 0; i < isPersonAlive.length; i++) {
			if (isPersonAlive[i]) {
				num++;
			}
		}
		return num;
	}
}
