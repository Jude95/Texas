package algorithm;

import algorithms.probability.Probability;
import algorithms.skill.Skill;
import bean.Action;
import bean.Poker;
import framework.record.ISceneReader;

public class AlgorithmManager implements IAlgorithm{

	private Skill mSkill;
	private Probability mProbability;
	
	public AlgorithmManager() {
		init();
	}
	
	@Override
	public Action calculate(ISceneReader reader) {
		Poker[] poker = reader.common();
		if(poker.length == 2){
			return mSkill.getSkillAction(reader);
		}else{
			return mProbability.getProbabilityAction(reader);
		}
		
	}
	
	private void init(){
		mSkill = new Skill();
		mProbability = new Probability();
	}

}
