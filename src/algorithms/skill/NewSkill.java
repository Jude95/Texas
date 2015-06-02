package algorithms.skill;

import util.Log;
import config.Config;
import framework.record.ISceneReader;
import algorithms.statistics.HandStatistics;
import bean.Action;
import bean.Incident;
import bean.Poker;

public class NewSkill implements ISkill {
	private int totalCurrentJetton = 0;
	public Action getSkillAction(ISceneReader reader) {

		int count = reader.person().length;
		
		
		StringBuilder key = new StringBuilder();
		Poker[] poker = reader.hold();
		key.append(poker[0].compareTo(poker[1]) > 0 ? poker[0].getPointStr()
				+ poker[1].getPointStr() : poker[1].getPointStr()
				+ poker[0].getPointStr());
		key.append(poker[0].getColor() == poker[1].getColor() ? "s" : "o");
		String hand = key.toString();
		
		float change = 1;
		switch(count){
		case 2:
			change = 4.675f;break;
		case 3:
			change = 3.62f;break;
		case 4:
			change = 2.75f;break;
		case 5:
			change = 2.05f;break;
		case 6:
			change = 1.525f;break;
		case 7:
			change = 1.175f;break;
		case 8:
			change = 1;break;
		}
		
		totalCurrentJetton += reader.callJetton();
		float mid = HandStatistics.getInstance().getMidProbability(count);
		float max = HandStatistics.getInstance().getNumProbability(count, (int)(4*change));
		float better =  HandStatistics.getInstance().getNumProbability(count, (int)(10*change));
		float win = HandStatistics.getInstance().getProbability(reader.hold(),
				reader.person().length);

		if (reader.getAlivePersonCount() <= 1) {
			Log.Log("skill", "第" + reader.timesNum() + "局   "+" 人数"+count+" 投入"+reader.callJetton()+"  " +hand+ "  概率:"+win+"  无人存活,我跟牌");
			return Action.call;
		}
		if (win <= mid) {
			Log.Log("skill", "第" + reader.timesNum() + "局   " +" 人数"+count+" 投入"+reader.callJetton()+"  " +hand+ "  概率:"+win+ "  小于中值，弃牌");
			return Action.fold;
		}
		if (win >= max) {
			Log.Log("skill", "第" + reader.timesNum() + "局   " +" 人数"+count+" 投入"+reader.callJetton()+"  " +hand+ "  概率:"+win+ "  大于"+(int)(4*change)+"  加注");
			if(totalCurrentJetton >= 2000){
				return Action.call;
			}
			return Action.raise;
		}
		
		if (win >= better) {
			Log.Log("skill", "第" + reader.timesNum() + "局   " +" 人数"+count+" 投入"+reader.callJetton()+"  " +hand+ "  概率:"+win+ "  大于"+(int)(10*change)+ "  跟");
			return Action.call;
		}
		// 中间模糊值
		float danger = HandStatistics.getInstance().getNumProbability(count,
				Config.AlgorithmConfig.SKILL_DANGER);
		int callJetton = reader.callJetton();
		Incident[] incident = reader.preAction();

		if (callNum(incident) < incident.length / 2) {// call或者raise的人数少于一半
			if (callJetton < Config.AlgorithmConfig.SHILL_CALLJETTON
					* reader.lastJetton()) {
				Log.Log("skill", "第" + reader.timesNum() + "局   "+" 人数"+count+" 投入"+reader.callJetton()+"  " +hand+ "  概率:"+win
						+ "  生存者少于一半，下注小于30%，跟");
				return Action.call;
			} else {
				Log.Log("skill", "第" + reader.timesNum() + "局   "+" 人数"+count+" 投入"+reader.callJetton()+"  " +hand+ "  概率:"+win
						+ "  生存者少于一半，下注大于30%，弃牌");
				return Action.fold;
			}
		} else {
			if (win >= danger) {
				Log.Log("skill", "第" + reader.timesNum() + "局   "+" 人数"+count+" 投入"+reader.callJetton()+"  " +hand+ "  概率:"+win
						+ "  生存者大于一半，牌大于前危险值，跟牌");
				return Action.call;
			} else {
				Log.Log("skill", "第" + reader.timesNum() + "局   "+" 人数"+count+" 投入"+reader.callJetton()+"  " +hand+ "  概率:"+win
						+ "  生存者大于一半，牌小于前危险值，弃牌");
				return Action.fold;
			}
		}
	}
	private int callNum(Incident[] incident) {
		int count = 0;
		for (int i = 0; i < incident.length; i++) {
			if (incident[i].getAction().equals(Action.raise)
					|| incident[i].getAction().equals(Action.all_in)
					|| incident[i].getAction().equals(Action.call)) {
				count++;
			}
		}
		return count;
	}

}
