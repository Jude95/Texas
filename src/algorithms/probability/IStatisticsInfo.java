package algorithms.probability;

import java.util.Map;
import java.util.Set;

import bean.Color;
import bean.Poker;

public interface IStatisticsInfo {
	
	Map<Integer,Integer> getPokerInfoMap();//判断对子
	Set<Color> getPokerInfoSet();//判断同花
	Poker[] getPoker();
}
