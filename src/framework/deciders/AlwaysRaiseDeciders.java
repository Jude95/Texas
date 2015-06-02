package framework.deciders;

import bean.Incident;
import framework.record.ISceneReader;
import framework.translate.IActionPoster;

public class AlwaysRaiseDeciders extends Deciders {
	public AlwaysRaiseDeciders(IActionPoster actionPoster,
			ISceneReader sceneReader) {
		super(actionPoster, sceneReader);
	}

	@Override
	public void inquire(Incident[] actions, int total) {
		mActionPoster.raise(0);
	}
	

}
