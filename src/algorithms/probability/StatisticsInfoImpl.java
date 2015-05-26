package algorithms.probability;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import bean.Color;
import bean.Poker;

public class StatisticsInfoImpl implements IStatisticsInfo{

	private Poker[] poker;
	public StatisticsInfoImpl(Poker[] poker){
		this.poker = poker;
	}
	
	@Override
	public Map<Integer, Integer> getPokerInfoMap() {
		Map<Integer,Integer> map = new HashMap<>();
		for (int i = 0; i < poker.length; i++) {
			if (map.containsKey(poker[i].getPoint())) {
				map.put(poker[i].getPoint(), (map.get(poker[i].getPoint()) + 1));
			} else {
				map.put(poker[i].getPoint(), 1);
			}
		}
		return map;
	}

	@Override
	public Set<Color> getPokerInfoSet() {
		Set<Color> set = new HashSet<Color>();
		for(int i=0;i<poker.length;i++){
			set.add(poker[i].getColor());
		}
		return set;
	}

	@Override
	public Poker[] getPoker() {
		return poker;
	}

}
