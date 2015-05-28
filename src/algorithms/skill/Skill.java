package algorithms.skill;


import config.Config;
import framework.record.ISceneReader;
import algorithms.statistics.HandStatistics;
import bean.*;

public class Skill {

	public Action getSkillAction(ISceneReader reader) {

		float win = HandStatistics.getInstance().getProbability(reader.hold(),
				reader.person().length);
		// Log.Log("probability",
		// reader.hold()[0].getPoint()+" , "+reader.hold()[1].getPoint()+"----->"+win+"");
		// Log.Log("log2",
		// reader.hold()[0].getPoint()+","+reader.hold()[1].getPoint() +
		// "----->"+win+"");
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
			return Action.fold;
		}
		return null;
	}

}
