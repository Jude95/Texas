package test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import net.Client;
import algorithms.statistics.Statistics;
import bean.Color;
import bean.Person;
import bean.Poker;
import bean.Result;
import framework.translate.IActionObserver;

public class StateProducer {

	private final int PERSON_NUM = 8;
	public  static final float TIMES = 10;// 每手牌统计次数
	public  static  int BEGIN = 2;// 牌最小
	public 	static final int END = 14;// 牌最大
	public  static final int KIND = 2;// 两种是否同花
	private IActionObserver mIActionObserver;
	private Random mRandom = new Random();
	private Poker fPoker;
	private Poker sPoker;
	private Poker[] cPoker = new Poker[5];
	private Set<Integer> set = new HashSet<Integer>();
	private Poker[][] pokers = new Poker[PERSON_NUM][7];
	private Poker[] hold = new Poker[2];
	private Poker[] allPoker = new Poker[52];
	private Person[] person = new Person[PERSON_NUM];
	private Result[] results = { new Result(1, 0 + "", null, null, null) };

	public StateProducer(IActionObserver mIActionObserver) {
		this.mIActionObserver = mIActionObserver;
		initPerson();
		mIActionObserver.seat(person);
		Client.ID = 0 + "";
		initAllPokers();
	}

	public Poker[][] getPokers() {
		return pokers;
	}

	public void initHold(int a, int b, int isSameColor) {
		initHand(a, b, isSameColor);
		mIActionObserver.hold(hold);
		Result r = new Result();
	}

	public void setResult(int index) {
		results[0].setIndex(index);
		results[0].setPoker1(fPoker);
		results[0].setPoker2(sPoker);
		mIActionObserver.showdown(results);
	}

	public void gameover(){
		mIActionObserver.gameover();
	}
	

	public void reset() {
		set.clear();
	}

	private void initPerson() {
		for (int i = 0; i < person.length; i++) {
			person[i] = new Person(i + "", 0, 0);
		}
	}

	public void initCards() {
		int temp;
		for (int i = 0; i < 5; i++) {
			while (true) {
				temp = getNum();
				if (!set.contains(temp)) {
					cPoker[i] = allPoker[temp];
					set.add(temp);
					break;
				}
			}

		}
		for (int i = 0; i < pokers.length; i++) {
			for (int j = 0; j < pokers[i].length; j++) {
				if (i == 0 && j < 2) {
					pokers[i][j] = fPoker;
					j++;
					pokers[i][j] = sPoker;
				} else if (i != 0 && j < 2) {
					while (true) {
						temp = getNum();
						if (!set.contains(temp)) {
							pokers[i][j] = allPoker[temp];
							set.add(temp);
							break;
						}
					}
				} else {
					pokers[i][j] = cPoker[j - 2];
				}
			}
		}
	}

	// 初始化全套poker
	private void initAllPokers() {
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 4; j++) {
				allPoker[i * 4 + j] = new Poker(getColor(j), i + 2);
			}
		}
	}

	private void initHand(int a, int b, int isSameColor) {
		if (isSameColor == 1) {
			fPoker = allPoker[(a - 2) * 4 + 1];
			sPoker = allPoker[(b - 2) * 4 + 1];
			set.add((a - 2) * 4 + 1);
			set.add((b - 2) * 4 + 1);
		} else {
			fPoker = allPoker[(a - 2) * 4 + 2];
			sPoker = allPoker[(b - 2) * 4 + 1];
			set.add((a - 2) * 4 + 2);
			set.add((b - 2) * 4 + 1);
		}
		hold[0] = fPoker;
		hold[1] = sPoker;
	}

	// 得到color
	private Color getColor(int i) {
		Color color;
		switch (i) {
		case 0:
			color = Color.SPADES;
			break;
		case 1:
			color = Color.CLUBS;
			break;
		case 2:
			color = Color.DIAMONDS;
			break;
		case 3:
			color = Color.HEARTS;
			break;
		default:
			color = Color.NULL;
		}
		return color;
	}

	// 得到一个在一定范围的随机数
	private int getNum() {
		return Math.abs(mRandom.nextInt()) % 52;
	}

}
