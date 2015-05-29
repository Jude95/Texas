package algorithms.probability;

import java.util.ArrayList;
import java.util.List;

import config.Config;
import test.StateJudger;
import util.Log;
import framework.record.ISceneReader;
import algorithms.statistics.HandStatistics;
import bean.*;


public class Probability {

	private IAllPoker mIAllPoker;
	private StateJudger mStateJudger;

	public Probability() {
		mIAllPoker = new AllPokerImpl();
		mStateJudger = new StateJudger();
	}

	public Action getProbabilityAction(ISceneReader reader) {
		
		Action mAction = Action.fold;
		Poker[] holdPoker = reader.hold();
		Poker[] commonPoker = reader.common();
		Poker[] poker = new Poker[2+commonPoker.length];
		System.arraycopy(holdPoker, 0, poker, 0, 2);
		System.arraycopy(commonPoker, 0, poker, 2, commonPoker.length);
		if (poker.length == 5) {
			float win = getWins(poker);
			mAction = getDecideAction(win, reader);

		} else {
			Poker[][] pokers = null;
			if (poker.length == 6) {
				pokers = mStateJudger.combine(poker, 5, 6);// 6选5 
			}
			if (poker.length == 7) {
				pokers = mStateJudger.combine(poker, 5, 21);// 7选5
			}
			int temp = 0;
			int max = 0;
			if (pokers != null) {
				for (int i = 0; i < pokers.length; i++) {
					IRate mIRate = new RateImpl(pokers[i]);
					int tempCode = mIRate.getCode();
					if (temp < tempCode) {
						temp = mStateJudger.getCode(pokers[i]);
						max = i;
					}
				}
				float win = getWins(pokers[max]);
				mAction = getDecideAction(win, reader);
			}
		}
		return mAction;
	}

	private float getWins(Poker[] poker) {
		IRate mIRate = new RateImpl(poker);
		return combine(mIAllPoker.getAllPoker(poker), poker, 2,
				mIRate.getCode());
	}

	private Poker[] printCombination(List<Poker> list, byte[] bits) {
		List<Poker> mList = new ArrayList<Poker>();
		for (int i = 0; i < bits.length; i++) {
			if (bits[i] == (byte) 1) {
				mList.add(list.get(i));
			}
		}
		Poker[] res = new Poker[mList.size()];
		for (int i = 0; i < mList.size(); i++) {
			res[i] = mList.get(i);
		}
		return res;
	}

	private float combine(List<Poker> list, Poker[] poker, int n, int code) {
		int t = 0;
		IRate mIRate;
		float win = 0;
		float fail = 0;
		boolean find = false;
		Poker[] otherPoker = new Poker[poker.length];
		// 初始化移位法需要的数组
		byte[] bits = new byte[list.size()];
		for (int i = 0; i < bits.length; i++) {
			bits[i] = i < n ? (byte) 1 : (byte) 0;
		}

		do {
			Poker[] printTemp = printCombination(list, bits);
			for (int k = 0; k < otherPoker.length; k++) {
				if (k < printTemp.length) {
					otherPoker[k] = printTemp[k];
				} else {
					otherPoker[k] = poker[k];
				}
			}
			// 找到10，换成01
			mIRate = new RateImpl(otherPoker);
			if (mIRate.getCode() <= code) {
				win++;
			} else {
				fail++;
			}
			find = false;
			for (int i = 0; i < list.size() - 1; i++) {
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

		return win / (fail + win);
	}

	
	private Action getDecideAction(float win,ISceneReader reader){
		//牌的胜利的可能性 -------win
		//下注的大小
		//剩余的人数
		//自己的已经下注的多少以及池子里有的钱数
		int callJetton = reader.callJetton();
		int lastJetton = reader.lastJetton();
		int pot = reader.pot();
		int roundNum = reader.roundNum();
		boolean canCheck = canCheck(reader.availableAction());
		Person[] person = reader.person();
		int callNum = callNum(reader.preAction());
		if (win >= Config.AlgorithmConfig.PRO_RAISE*(roundNum/10 + 1)) {//如果这时候胜利的可能性已经超过了80%
			Action action = Action.raise;
			action.setNum(300);
			return action;
		}else{
			if(canCheck){//可以check吗
				return Action.check;
			}
			if(win >= Config.AlgorithmConfig.PRO_CALL * (roundNum/10 + 1)){//胜利的可能性大于40%
				if(callNum < person.length/2){
					if(callJetton < pot*win  && callJetton < lastJetton * win){
						return Action.call;
					}
				}
				if(lastJetton > callJetton*10){
					if(callJetton < pot*win  && callJetton < lastJetton * win){
						return Action.call;
					}
				}
			}	
		}
		if(true){
			//投入很多了，就一定要坚持到最后开牌，不惜all_in
			return Action.call;
		}
		return Action.fold;
	}
	private int callNum(Incident[] incident){
		int count=0;
		for(int i =0;i<incident.length;i++){
			if(incident[i].getAction().equals(Action.raise) || incident[i].getAction().equals(Action.call)){
				count++;
			}
		}
		return count;
	}
	boolean canCheck(Action[] actions){
		boolean flag =false;
		for(int i=0;i<actions.length;i++){
			if(actions[i].equals(Action.check)){
				flag = true;
			}
		}
		return flag;
	}
}
