package framework.deciders;

import bean.Incident;
import framework.record.ISceneReader;
import framework.translate.IActionPoster;

public class AlwaysFlodDeciders extends Deciders {

	public AlwaysFlodDeciders(IActionPoster actionPoster,
			ISceneReader sceneReader) {
		super(actionPoster, sceneReader);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void inquire(Incident[] actions, int total) {
		mActionPoster.fold();
	}
	
}
