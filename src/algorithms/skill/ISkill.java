package algorithms.skill;

import bean.Action;
import framework.record.ISceneReader;

public interface ISkill {
	public Action getSkillAction(ISceneReader reader);
}
