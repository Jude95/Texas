package test;

import java.util.Arrays;
import java.util.HashMap;

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
			sum += poker[i].getPoint() * Math.pow(14,i);
		}
		if(isStraightFlush(poker)){
			sum +=a;
		}else if(isFourKind()){
			sum += b;
		}else if(isFullHouse()){
			sum += c;
		}else if(isSameColor(poker)){
			sum += d;
		}else if(isStraight(poker)){
			sum += e;
		}else if(isThreeKind()){
			sum += f;
		}else if(isTwoPair()){
			sum += g;
		}else if(isOnePair()){
			sum += h;
		}else{
			sum += l;
		}
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

	private boolean isStraightFlush(Poker[]poker){
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
	
	//是不是顺子
	private boolean isStraight(Poker[] poker){
		boolean flag = true;
		Arrays.sort(poker);
		int temp = poker[0].getPoint();
		for(int i=1;i<poker.length;i++){
			if(poker[i].getPoint()-temp == 1){
				temp = poker[i].getPoint();
			}else{
				flag = false;
				break;
			}
		}
		return flag;
	}
	
	//是不是三条
	private boolean isThreeKind(){
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
	
	
	//是不是两对
	private boolean isTwoPair(){
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
	
	//是不是一对
	private boolean isOnePair(){
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
	
	
	public int getResult(Poker[][] poker) {
		
		return 0;
	}

}
