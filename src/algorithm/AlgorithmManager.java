package algorithm;

import algorithms.skill.Skill;
import bean.Action;
import framework.record.ISceneReader;

public class AlgorithmManager implements IAlgorithm{

	private Skill mSkill;
	
	public AlgorithmManager() {
		init();
	}
	
	@Override
	public Action calculate(ISceneReader reader) {
		
		return mSkill.getSkillAK(reader);
	}
	
	private void init(){
		mSkill = new Skill();
	}


	

}
