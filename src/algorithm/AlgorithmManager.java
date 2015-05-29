package algorithm;

import util.Log;
import algorithms.probability.Probability;
import algorithms.skill.Skill;
import bean.Action;
import bean.Poker;
import framework.record.ISceneReader;

public class AlgorithmManager implements IAlgorithm {

	private Skill mSkill;
	private Probability mProbability;

	public AlgorithmManager() {
		init();
	}

	@Override
	public Action calculate(ISceneReader reader) {
		int length = 2 + reader.common().length;
		Log.Log("length", "length: " + length);
		if (length == 2) {
			Log.Log("algorithmManager", "mSkill.getSkillAction(reader): "
					+ mSkill.getSkillAction(reader));
			return mSkill.getSkillAction(reader);
		} else {
			Log.Log("algorithmManager",
					"mProbability.getProbabilityAction(reader): "
							+ mProbability.getProbabilityAction(reader));
			return mProbability.getProbabilityAction(reader);
		}

	}

	private void init() {
		mSkill = new Skill();
		mProbability = new Probability();
	}

}
