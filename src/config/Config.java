package config;

public class Config {
	public static final String NAME = "Miracle";
	
	public static final String LogDir = "PlayerLog/PlayerLog%s";
	public static final String StandDir = "StandData/hand%d";
	public static class AlgorithmConfig{
		//技巧的配置权重
		public static final double SKILL_WEIGHT = 1/15; 
		public static final double SKILL_PERSON_NUM = 0.5;
		
		
		//概率算法中的各个点数的权值
		private static final int PROBABILITY_RoyalFlush = 500;//皇家同花顺
		private static final int PROBABILITY_StraightFlush = 400;//同花顺
		private static final int PROBABILITY_FOURKIND = 300;//四条
		private static final int PROBABILITY_FULLHOUSE = 200;//葫芦
		private static final int PROBABILITY_FLUSH = 100;//同花
		private static final int PROBABILITY_STAIGHT = 100;//顺子
		private static final int PROBABILITY_THREEKIND = 100;//三条
		private static final int PROBABILITY_TWOPAIR = 100;//两对
		private static final int PROBABILITY_ONTPAIR = 100;//一对
		private static final int PROBABILITY_HIGHCARD = 100;//高牌
		
		public static final int[] PROBABILITY_WEIGHT ={PROBABILITY_RoyalFlush,PROBABILITY_StraightFlush,PROBABILITY_FOURKIND,
			PROBABILITY_FULLHOUSE,PROBABILITY_FLUSH,PROBABILITY_STAIGHT,
			PROBABILITY_THREEKIND,PROBABILITY_TWOPAIR,PROBABILITY_ONTPAIR,
			PROBABILITY_HIGHCARD};
	}
	
}
