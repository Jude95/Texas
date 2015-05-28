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
		
		
		public static final double PRO_WEIGHT = 1; //raise的时候的阈值
		public static final double PRO_RAISE = 0.6;
		public static final double PRO_CALL = 0.3;//当大于最大概率的0.65倍的时候call
	}

}
