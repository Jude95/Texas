package algorithms.probability;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import bean.*;


public class StateJugerImpl implements IStateJudger{
	
	
	private Map<Integer, Integer> map;
	private Set<Color> set;
	private Poker[] poker;
	
	public StateJugerImpl(IStatisticsInfo mIStatisticsInfo) {
		map = mIStatisticsInfo.getPokerInfoMap();
		set = mIStatisticsInfo.getPokerInfoSet();
		poker = mIStatisticsInfo.getPoker();
	}
	
	
	
	@Override
	public boolean isStraightFlush() {
		return isSameColor() && isStraight();
	}

	
	@Override
	public boolean isFourKind() {
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

	
	@Override
	public boolean isFullHouse() {
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

	
	@Override
	public boolean isSameColor() {
		
		return set.size() == 1;
	}

	@Override
	public boolean isStraight() {
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

	@Override
	public boolean isThreeKind() {
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

	@Override
	public boolean isTwoPair() {
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

	@Override
	public boolean isOnePair() {
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
	
}
