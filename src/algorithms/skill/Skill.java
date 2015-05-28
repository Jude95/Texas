package algorithms.skill;


import util.Log;
import config.Config;
import framework.record.ISceneReader;
import algorithms.statistics.HandStatistics;
import bean.*;

public class Skill {

	public Action getSkillAction(ISceneReader reader) {

		float win = HandStatistics.getInstance().getProbability(reader.hold(),
				reader.person().length);
		Log.Log("log2",reader.hold()[0].getPoint()+","+reader.hold()[1].getPoint() + "----->"+win+"");
		Poker[] poker = { new Poker(Color.CLUBS, 14),
				new Poker(Color.DIAMONDS, 14) };
		float AAwin = HandStatistics.getInstance().getProbability(poker,
				reader.person().length);
		
		// int callJetton = reader.callJetton();//跟注大小
		
		if (win >= Config.AlgorithmConfig.SKILL_RAISE * AAwin) {
			Action action = Action.raise;
			int money = (int) (Config.AlgorithmConfig.SKILL_WEIGHT
					* reader.lastJetton() * win);
			action.setNum(money);
			return action;
		}
		
		
		if (win >= Config.AlgorithmConfig.SHILL_CALL * AAwin) {
			return Action.call;
		}
		
		
		if (win < Config.AlgorithmConfig.SHILL_CALL * AAwin) {
			if(isContainCheck(reader.availableAction())){
				return Action.check;
			}else{
				return Action.fold;
			}
		}
		return null;
		
	}

	boolean isContainCheck(Action[] actions){
		boolean flag =false;
		for(int i=0;i<actions.length;i++){
			if(actions[i].equals(Action.check)){
				flag = true;
			}
		}
		return flag;
	}
	
	public static void main(String[] args) {
		Poker[] poker = { new Poker(Color.CLUBS, 14),
				new Poker(Color.DIAMONDS, 3) };
		float AAwin = HandStatistics.getInstance().getProbability(poker,
				8);
		System.out.println(AAwin);
	}
	
}
