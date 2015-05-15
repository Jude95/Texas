package algorithm;

import bean.Action;
import framework.record.ISceneReader;

public interface Algorithm {
	public Action calculate(ISceneReader reader);
}
