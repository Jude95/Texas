package framework.deciders;

import util.Log;
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
		float mine = HandStatistics.getInstance().getProbability(mSceneReader.hold(), mSceneReader.person().length);
		//float max = HandStatistics.getInstance().getMaxProbability(mSceneReader.person().length);
		float avg = HandStatistics.getInstance().getAverageProbability(mSceneReader.person().length);
		//Log.Log("hand", "mine:"+mine+"    max"+max);

		if(mine>avg){
			mActionPoster.all_in();
		}else{
			mActionPoster.fold();
		}
		
	}
}
