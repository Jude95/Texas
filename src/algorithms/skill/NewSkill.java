package algorithms.skill;

import util.Log;
import config.Config;
import framework.record.ISceneReader;
import algorithms.statistics.HandStatistics;
import bean.Action;
import bean.Incident;

public class NewSkill  implements ISkill{
	public Action getSkillAction(ISceneReader reader){
		
		int count = reader.person().length;
		float mid = HandStatistics.getInstance().getMidProbability(count);
		float max = HandStatistics.getInstance().getNumProbability(count, 10);
		float win = HandStatistics.getInstance().getProbability(reader.hold(),reader.person().length);
		
		
		if(win <= mid){
			Log.Log("skill","第"+reader.timesNum()+"局"+"  小于中值，弃牌");
			return Action.fold;
		}
		if(win >= max){
			Log.Log("skill","第"+reader.timesNum()+"局"+"  大于前10，加注");
			return Action.raise;
		}
		
		
		
		//中间模糊值
		float danger = HandStatistics.getInstance().getNumProbability(count, Config.AlgorithmConfig.SKILL_DANGER);
		int callJetton = reader.callJetton();
		Incident[] incident = reader.preAction();
		
		
		if(callNum(incident) < incident.length/2){//call或者raise的人数少于一半
			if(callJetton<Config.AlgorithmConfig.SHILL_CALLJETTON * reader.lastJetton()){
				Log.Log("skill","第"+reader.timesNum()+"局"+"  生存者少于一半，下注小于30%，跟");
				return Action.call;
			}else{
				Log.Log("skill","第"+reader.timesNum()+"局"+"  生存者少于一半，下注大于30%，弃牌");
				return Action.fold;
			}
		}else{
			if (win >= danger) {
				Log.Log("skill","第"+reader.timesNum()+"局"+"  生存者大于一半，牌大于前危险值，跟牌");
				return Action.call;
			}else{
				Log.Log("skill","第"+reader.timesNum()+"局"+"  生存者大于一半，牌小于前危险值，弃牌");
				return Action.fold;
			}
		}
			
	}
	
	private int callNum(Incident[] incident){
		int count=0;
		for(int i =0;i<incident.length;i++){
			if(incident[i].getAction().equals(Action.raise)||incident[i].getAction().equals(Action.all_in) || incident[i].getAction().equals(Action.call)){
				count++;
			}
		}
		return count;
	}

}
