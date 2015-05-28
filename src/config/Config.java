package config;

public class Config {
	public static String NAME = "Miracle";
	
	public static final String TempDir = "TempData";
	public static final String StandDir = "StandData";
	public static class AlgorithmConfig{
		//技巧的配置权重
		public static final double SKILL_WEIGHT = 1; //raise的时候的阈值
		public static final double SKILL_RAISE = 0.8;//当大于最大概率的0.8倍的时候raise
		public static final double SHILL_CALL = 0.65;//当大于最大概率的0.65倍的时候call
	}
	public static class Statistics{
		//概率记录时的比率。越高学习的越快，同时越不稳定。
		public static final int Gradient = 100 ;
	} 
}

