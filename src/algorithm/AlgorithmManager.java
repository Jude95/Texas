package algorithm;

import util.Log;
import algorithms.probability.Probability;
import algorithms.skill.ISkill;
import algorithms.skill.NewSkill;
import algorithms.skill.Skill;
import algorithms.skill.TestSkill;
import bean.Action;
import bean.Poker;
import framework.record.ISceneReader;

public class AlgorithmManager implements IAlgorithm {
	private int style;
	private ISkill mSkill;
	
	private Probability mProbability;
	
	
	public AlgorithmManager(int style) {
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
		switch (style) {
		case 1:
			mSkill = new Skill();
			break;
		case 2:
			mSkill = new NewSkill();
			break;
		default:
			mSkill = new TestSkill();
			break;
		}
		mSkill = new NewSkill();
		mProbability = new Probability();
	}

}
