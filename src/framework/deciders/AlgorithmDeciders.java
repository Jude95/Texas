package framework.deciders;

import algorithm.AlgorithmManager;
import bean.Action;
import bean.Incident;
import framework.record.ISceneReader;
import framework.translate.IActionPoster;

public class AlgorithmDeciders extends Deciders {
	private AlgorithmManager algorithmManager;
	public AlgorithmDeciders(IActionPoster actionPoster,
			ISceneReader sceneReader,int style) {
		super(actionPoster, sceneReader);
		algorithmManager = new AlgorithmManager(style);
	}

	@Override
	public void inquire(Incident[] actions, int total) {
		// TODO Auto-generated method stub
		Action action = algorithmManager.calculate(mSceneReader);
		switch (action) {
		case check:
			mActionPoster.check();
			break;
		case call:
			mActionPoster.call();
			break;
		case raise:

			mActionPoster.raise(action.getNum());

			break;
		case all_in:
			mActionPoster.all_in();
			break;
		default:
			mActionPoster.fold();
			break;
		}

	}

}
