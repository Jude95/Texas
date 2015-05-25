package framework.deciders;

import algorithms.statistics.HandStatistics;
import bean.Incident;
import framework.record.ISceneReader;
import framework.translate.IActionPoster;

public class HandAllinDeciders extends Deciders {

	public HandAllinDeciders(IActionPoster actionPoster,
			ISceneReader sceneReader) {
		super(actionPoster, sceneReader);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void inquire(Incident[] actions, int total) {
		if(HandStatistics.getInstance().getProbability(mSceneReader.hold(), mSceneReader.person().length)>HandStatistics.getInstance().getMaxProbability(mSceneReader.person().length)*0.78){
			mActionPoster.all_in();
		}else{
			mActionPoster.fold();
		}
		
	}
}
