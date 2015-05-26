package algorithms.probability;

import bean.Poker;

public class RateImpl implements IRate{

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
	
	private IStatisticsInfo mStatisticsInfo;
	private IStateJudger mIStateJudger;
	public RateImpl(Poker[] poker){
		mStatisticsInfo = new StatisticsInfoImpl(poker);
		mIStateJudger = new StateJugerImpl(mStatisticsInfo);
	}

	
	@Override
	public int getCode() {
		int sum = 0;
		for (int i = 0; i < mStatisticsInfo.getPoker().length; i++) {
			sum += mStatisticsInfo.getPoker()[i].getPoint() * Math.pow(14, i);
		}
		if (mIStateJudger.isStraightFlush()) {
			sum += a;
		} else if (mIStateJudger.isFourKind()) {
			sum += b;
		} else if (mIStateJudger.isFullHouse()) {
			sum += c;
		} else if (mIStateJudger.isSameColor()) {
			sum += d;
		} else if (mIStateJudger.isStraight()) {
			sum += e;
		} else if (mIStateJudger.isThreeKind()) {
			sum += f;
		} else if (mIStateJudger.isTwoPair()) {
			sum += g;
		} else if (mIStateJudger.isOnePair()) {
			sum += h;
		} else {
			sum += l;
		}
		return sum;
	}

	
}
