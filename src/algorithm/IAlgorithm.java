package algorithm;

import bean.Action;
import framework.record.ISceneReader;

public interface IAlgorithm {
	public Action calculate(ISceneReader reader);
}
