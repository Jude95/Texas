package algorithms.skill;


import config.Config;
import framework.record.ISceneReader;
import algorithms.statistics.HandStatistics;
import bean.*;

public class Skill {
	
	public Action getSkillAction(ISceneReader reader){
		Action action = null;
		float win = HandStatistics.getInstance().getProbability(reader.hold(),reader.person().length);
		if(win>=0.39){
			action = Action.raise;
			int money = (int)(Config.AlgorithmConfig.SKILL_WEIGHT*reader.lastJetton()*win);
			action.setNum(money);
		}else if(win>=0.32 && win<0.39){
			action = Action.call;
		}else{
			action = Action.fold;
		}
		return action;
	}
	
}
