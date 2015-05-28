package framework.deciders;

import bean.Incident;
import framework.record.ISceneReader;
import framework.translate.IActionPoster;

public class AlwaysAllinDeciders extends Deciders {

	public AlwaysAllinDeciders(IActionPoster actionPoster,
			ISceneReader sceneReader) {
		super(actionPoster, sceneReader);
	}

	@Override
	public void inquire(Incident[] actions, int total) {
		mActionPoster.all_in();
	}
	

}
