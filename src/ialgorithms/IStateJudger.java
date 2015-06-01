package ialgorithms;

public interface IStateJudger {
	
	
	boolean isStraightFlush();//是不是同花顺
	boolean isFourKind();//是不是4条
	boolean isFullHouse();//是不是葫芦
	boolean isSameColor();//是不是同花
	boolean isStraight();//是不是顺子
	// 是不是三条
	boolean isThreeKind();
	// 是不是两对
	boolean isTwoPair();
	//是不是一对
	boolean isOnePair();
	
}
