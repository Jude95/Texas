package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import bean.Color;
import bean.Poker;

public class StateJudger {

	private final int s = 579194;
	private final int a = 10 * s;
	private final int b = 9 * s;
	private final int c = 8 * s;
	private final int d = 7 * s;
	private final int e = 6 * s;
	private final int f = 5 * s;
	private final int g = 4 * s;
	private final int h = 3 * s;
	private final int l = 2 * s;

	private HashMap<Integer, Integer> map;

	public StateJudger() {

	}

	private int getCode(Poker[] poker) {
		initMap(poker);
		Arrays.sort(poker);
		int sum = 0;
		for (int i = 0; i < poker.length; i++) {
			sum += poker[i].getPoint() * Math.pow(14, i);
		}
		if (isStraightFlush(poker)) {
			sum += a;
		} else if (isFourKind()) {
			sum += b;
		} else if (isFullHouse()) {
			sum += c;
		} else if (isSameColor(poker)) {
			sum += d;
		} else if (isStraight(poker)) {
			sum += e;
		} else if (isThreeKind()) {
			sum += f;
		} else if (isTwoPair()) {
			sum += g;
		} else if (isOnePair()) {
			sum += h;
		} else {
			sum += l;
		}
		resetMap();
		return sum;
	}

	private void resetMap() {
		map.clear();
	}

	private void initMap(Poker[] poker) {
		map = new HashMap<Integer, Integer>();
		for (int i = 0; i < poker.length; i++) {
			if (map.containsKey(poker[i].getPoint())) {
				map.put(poker[i].getPoint(), (map.get(poker[i].getPoint()) + 1));
			} else {
				map.put(poker[i].getPoint(), 1);
			}
		}
	}

	private boolean isStraightFlush(Poker[] poker) {
		return isSameColor(poker) && isStraight(poker);
	}

	// 是不是四条
	private boolean isFourKind() {
		boolean flag = false;
		if (map.size() == 2) {
			for (int i : map.keySet()) {
				if (map.get(i) == 4) {
					flag = true;
				}
			}
		}
		return flag;
	}

	// 是不是葫芦
	private boolean isFullHouse() {
		boolean flag = false;
		if (map.size() == 2) {
			for (int i : map.keySet()) {
				if (map.get(i) == 3) {
					flag = true;
				}
			}
		}
		return flag;
	}

	// 是不是同花
	private boolean isSameColor(Poker[] poker) {
		Color color = poker[0].getColor();
		boolean flag = true;
		for (int i = 0; i < poker.length; i++) {
			if (!color.equals(poker[i].getColor())) {
				flag = false;
				break;
			}
		}
		return flag;
	}

	// 是不是顺子
	private boolean isStraight(Poker[] poker) {
		boolean flag = true;
		Arrays.sort(poker);
		int temp = poker[0].getPoint();
		for (int i = 1; i < poker.length; i++) {
			if (poker[i].getPoint() - temp == 1) {
				temp = poker[i].getPoint();
			} else {
				flag = false;
				break;
			}
		}
		return flag;
	}

	// 是不是三条
	private boolean isThreeKind() {
		boolean flag = false;
		if (map.size() == 3) {
			for (int i : map.keySet()) {
				if (map.get(i) == 3) {
					flag = true;
				}
			}
		}
		return flag;
	}

	// 是不是两对
	private boolean isTwoPair() {
		boolean flag = false;
		if (map.size() == 3) {
			for (int i : map.keySet()) {
				if (map.get(i) == 2) {
					flag = true;
				}
			}
		}
		return flag;
	}

	// 是不是一对
	private boolean isOnePair() {
		boolean flag = false;
		if (map.size() == 4) {
			for (int i : map.keySet()) {
				if (map.get(i) == 2) {
					flag = true;
				}
			}
		}
		return flag;
	}

	private Poker[] printCombination(Poker[] array, byte[] bits) {
		List<Poker> list = new ArrayList<Poker>();
		for (int i = 0; i < bits.length; i++) {
			if (bits[i] == (byte) 1) {
				list.add(array[i]);
			}
		}
		Poker [] res = new Poker[list.size()];
		for(int i=0;i<list.size();i++){
			res[i] = list.get(i);
		}
		return res;
	}

	private Poker[][] combine(Poker[] poker, int n) {
		int t = 0;
		Poker[][] res = new Poker[21][n];
		boolean find = false;
		// 初始化移位法需要的数组
		byte[] bits = new byte[poker.length];
		for (int i = 0; i < bits.length; i++) {
			bits[i] = i < n ? (byte) 1 : (byte) 0;
		}

		do {
			// 找到10，换成01
			res[t] = printCombination(poker, bits);
			find = false;

			for (int i = 0; i < poker.length - 1; i++) {
				if (bits[i] == 1 && bits[i + 1] == 0) {
					find = true;
					bits[i] = 0;
					bits[i + 1] = 1;

					if (bits[0] == 0) // 如果第一位为0，则将第i位置之前的1移到最左边，如为1则第i位置之前的1就在最左边，无需移动
					{
						for (int k = 0, j = 0; k < i; k++) // O(n)复杂度使1在前0在后
						{
							if (bits[k] == 1) {
								byte temp = bits[k];
								bits[k] = bits[j];
								bits[j] = temp;
								j++;
							}
						}
					}

					break;
				}
			}
			t++;
		} while (find);
		
		return res;
	}

	public int getResult(Poker[][] poker) {
		Poker [][] res;
		int temp =0;
		int win = 0;
		int s = 0;
		for(int i=0;i<poker.length;i++){
			res = combine(poker[i], 5);
			for(int j=0;j<res.length;j++){
				s = getCode(res[j]);
				if(temp<s){
					temp = s;
					win =i;
				}
			}
		}
		return win;
	}

}
