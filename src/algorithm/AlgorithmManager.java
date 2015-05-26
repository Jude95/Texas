package algorithm;

import algorithms.probability.Probability;
import algorithms.skill.Skill;
import bean.Action;
import bean.Poker;
import framework.record.ISceneReader;

public class AlgorithmManager implements IAlgorithm{

	//private Skill mSkill;
	private Probability mProbability;
	Action action = null;
	public AlgorithmManager() {
		init();
	}
	
	@Override
	public Action calculate(ISceneReader reader) {
		
		Poker[] poker = reader.common();
		if(poker.length == 2){
			return Action.call;
		}else if(poker.length == 5){
			action = mProbability.getProbabilityAction(reader);
			return action;
		}
		if(action == Action.call){
			return action;
		}else{
			return Action.fold;
		}
	}
	
	private void init(){
		//mSkill = new Skill();
		mProbability = new Probability();
	}

}
