package framework.record;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.Log;
import util.MyLog;
import bean.Action;
import bean.Incident;
import bean.Person;
import bean.Poker;
import bean.Result;
import framework.translate.IActionObserver;

public class SceneRecorder implements IActionObserver, ISceneReader {
	private Person[] person;
	private Poker[] holdPoker;// 手牌

	private List<Poker> commonPocker;// 公牌
	private int total;// 奖池

	private int roundNum;// 圈数，从1开始
	private boolean isRoundNumInited;
	private int seatNum;// 座位序号，从0开始
	private boolean isPersonAlive[];// 本局是否存活
	private boolean isPersonCanInquire[];// 本局中是否可以

	private boolean isInPreflop;
	private boolean isInFlop;
	private boolean isInTurn;
	private boolean isInRiver;

	private int currentJetton;
	private int betJetton[];
	private int totalCallJetton[];
	private int raisedCount[];

	private String smallId;
	private String bigId;
	private int smallJetton;
	private int bigJetton;

	private Incident[] inquireIncident;// 当前玩家之前其他玩家的操作
	private Incident[] notifyIncident;
	private Result[] results;
	private Map<String, Integer> pot_win_map;
	private Map<String, Integer> seatMap;

	@Override
	public void seat(Person[] person) {
		// TODO Auto-generated method stub
		this.person = person;
		seatMap = new HashMap<String, Integer>();
		for (int i = 0; i < person.length; i++) {
			seatMap.put(person[i].getName(), i);
		}
		MyLog.d("three", "seat");
		isRoundNumInited = false;

		isPersonAlive = new boolean[person.length];
		isPersonCanInquire = new boolean[person.length];

		isInPreflop = true;
		isInFlop = false;
		isInTurn = false;
		isInRiver = false;

		betJetton = new int[person.length];
		totalCallJetton = new int[person.length];
		raisedCount = new int[person.length];
		for (int i = 0; i < person.length; i++) {
			isPersonAlive[i] = true;
			isPersonCanInquire[i] = true;
			betJetton[i] = 0;
			totalCallJetton[i] = 0;
			raisedCount[i] = 0;
		}
		commonPocker = new ArrayList<Poker>();

	}

	@Override
	public void blind(String smallId, int smallJetton, String bigId,
			int bigJetton) {
		this.smallId = smallId;
		this.smallJetton = smallJetton;
		this.bigId = bigId;
		this.bigJetton = bigJetton;
		currentJetton = bigJetton;
	}

	@Override
	public void blind(String smallId, int smallJetton) {
		// TODO Auto-generated method stub
		this.smallId = smallId;
		this.smallJetton = smallJetton;
		currentJetton = smallJetton;
	}

	@Override
	public void hold(Poker[] poker) {
		// TODO Auto-generated method stub
		holdPoker = poker;
	}

	@Override
	public void inquire(Incident[] action, int total) {
		// TODO Auto-generated method stub
		inquireIncident = action;
		this.total = total;

		seatNum = seatMap.get(inquireIncident[0].getPerson().getName()) + 1;
		for (int i = 0; i < inquireIncident.length; i++) {
			int currentSeat = seatMap.get(inquireIncident[i].getPerson()
					.getName());
			betJetton[currentSeat] = inquireIncident[i].getBet();
			if (inquireIncident[i].getAction().equals(Action.fold)) {
				isPersonAlive[currentSeat] = false;
			}

			if (inquireIncident[i].getAction().equals(Action.fold)
					|| inquireIncident[i].getAction().equals(Action.all_in)) {
				isPersonCanInquire[currentSeat] = false;
			}

			if (inquireIncident[i].getAction().equals(Action.raise)) {
				raisedCount[currentSeat]++;
			}

			if (inquireIncident[i].getBet() > currentJetton) {
				currentJetton = inquireIncident[i].getBet();
			}

		}

		if (seatNum == person.length) {
			seatNum = 0;
		}
		// 如果位置+1后，后面的人已经弃牌或all in，则继续+1
		while (!isPersonCanInquire[seatNum]) {
			seatNum++;
			if (seatNum == person.length) {
				seatNum = 0;
			}
		}
		if (!isRoundNumInited) {
			if (person.length > 3 && seatNum < 3) {//如果是庄家、小盲注、大盲注
				roundNum = 1;
			}else{
				roundNum = 0;
			}
			isRoundNumInited = true;
		}
		roundNum++;
		MyLog.d("three", "inquire " + person[seatNum].getName()
				+ "     roundNum: " + roundNum);
		MyLog.d(person[seatNum].getName(),
				"inquire " + person[seatNum].getName() + "     roundNum: "
						+ roundNum);
		String flag = "default";
		if (isInPreflop)
			flag = "isInPreflop";
		if (isInFlop)
			flag = "isInFlop";
		if (isInTurn)
			flag = "isInTurn";
		if (isInRiver)
			flag = "isInRiver";
		MyLog.d(person[seatNum].getName(), flag + "     seatNum: " + seatNum
				+ "       person.length: " + person.length + "  alived person:"
				+ getAlivePersonCount() + "  canInquirePersonCount:"
				+ getCanInquirePersonCount() + "  action.length: "
				+ action.length);
		MyLog.d("three", flag + "     seatNum: " + seatNum
				+ "       person.length: " + person.length + "  alived person:"
				+ getAlivePersonCount() + "  canInquirePersonCount:"
				+ getCanInquirePersonCount() + "  action.length: "
				+ action.length);
		for (int i = 0; i < action.length; i++) {
			String content = "" + action[i].getPerson().getName() + " "
					+ action[i].getPerson().getJetton() + " "
					+ action[i].getPerson().getMoney() + " "
					+ action[i].getBet() + " " + action[i].getAction();
			MyLog.d(person[seatNum].getName(), content);
			MyLog.d("three", content);

		}
		MyLog.d(person[seatNum].getName(), " \n");
		MyLog.d("three", " \n");

	}

	public int getAlivePersonCount() {
		int count = 0;
		for (int i = 0; i < isPersonAlive.length; i++) {
			if (isPersonAlive[i]) {
				count++;
			}
		}
		return count;
	}

	public int getCanInquirePersonCount() {
		int count = 0;
		for (int i = 0; i < isPersonCanInquire.length; i++) {
			if (isPersonCanInquire[i]) {
				count++;
			}
		}
		return count;
	}

	@Override
	public void flop(Poker[] poker) {
		// TODO Auto-generated method stub
		isInPreflop = false;
		isInFlop = true;
		for (int i = 0; i < poker.length; i++) {
			commonPocker.add(poker[i]);
		}

	}

	@Override
	public void turn(Poker poker) {
		// TODO Auto-generated method stub
		isInFlop = false;
		isInTurn = true;
		commonPocker.add(poker);

	}

	@Override
	public void river(Poker poker) {
		// TODO Auto-generated method stub
		isInTurn = false;
		isInRiver = true;
		commonPocker.add(poker);
	}

	@Override
	public void pot_win(Map<String, Integer> pot) {
		// TODO Auto-generated method stub
		this.pot_win_map = pot;
		inquireIncident = null;
		notifyIncident = null;

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

	// 圈数读取有问题
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
		if (betJetton[seatNum] >= currentJetton) {
			availableAtion = new Action[5];
			availableAtion[0] = Action.call;
			availableAtion[1] = Action.raise;
			availableAtion[2] = Action.all_in;
			availableAtion[3] = Action.check;
			availableAtion[4] = Action.fold;
			return availableAtion;

		}

		availableAtion = new Action[4];
		availableAtion[0] = Action.call;
		availableAtion[1] = Action.raise;
		availableAtion[2] = Action.fold;
		availableAtion[3] = Action.all_in;
		return availableAtion;

	}

	@Override
	public Poker[] common() {
		// TODO Auto-generated method stub
		Poker poker[] = new Poker[commonPocker.size()];
		commonPocker.toArray(poker);
		return poker;
	}

	@Override
	public int callJetton() {
		// TODO Auto-generated method stub
		return currentJetton;
	}

	@Override
	public int lastJetton() {
		// TODO Auto-generated method stub
		return person[seatNum].getJetton();
	}

	@Override
	public int totalCallJetton() {
		// TODO Auto-generated method stub
		return totalCallJetton[seatNum];
	}

	@Override
	public void regist() {
		// TODO Auto-generated method stub

	}

	@Override
	public void gameover() {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifly(Incident[] action, int total) {
		// TODO Auto-generated method stub
		this.notifyIncident = action;
		

	}

	@Override
	public Incident[] preAction() {
		// TODO Auto-generated method stub
		return inquireIncident;
	}

	@Override
	public int raiseCount() {
		// TODO Auto-generated method stub
		return raisedCount[seatNum];
	}
}
