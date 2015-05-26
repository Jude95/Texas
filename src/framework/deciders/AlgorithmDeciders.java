package framework.deciders;

import java.util.Map;

import algorithm.AlgorithmManager;
import algorithm.AlgorithmManager;
import bean.Action;
import bean.Incident;
import bean.Person;
import bean.Poker;
import bean.Result;
import framework.record.ISceneReader;
import framework.translate.IActionObserver;
import framework.translate.IActionPoster;

public class AlgorithmDeciders implements IActionObserver{
	protected IActionPoster mActionPoster;
	protected ISceneReader mSceneReader;
	protected AlgorithmManager mAlgorithmManager;
	
	public AlgorithmDeciders(IActionPoster actionPoster,ISceneReader sceneReader) {
		mActionPoster = actionPoster;
		mSceneReader = sceneReader;
		mAlgorithmManager = new AlgorithmManager();
	}

	@Override
	public void inquire(Incident[] actions, int total) {
		Action action = mAlgorithmManager.calculate(mSceneReader);
		switch (action) {
		case check:
			mActionPoster.check();
			break;
		case call:
			mActionPoster.call();
			break;
		case raise:
			mActionPoster.raise(200);
			break;
		case all_in:
			mActionPoster.all_in();
			break;
		default:
			mActionPoster.fold();
			break;
		}
	}
	
	
	
	
	
	
	
	
	
	@Override
	public void seat(Person[] person) {
		// TODO Auto-generated method stub
	}

	@Override
	public void blind(String smallId, int smallJetton, String bigId,
			int bigJetton) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void blind(String smallId, int smallJetton) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hold(Poker[] poker) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void flop(Poker[] poker) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void turn(Poker poker) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void river(Poker poker) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pot_win(Map<String, Integer> pot) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showdown(Result[] results) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void regist() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gameover() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void notifly(Incident[] action, int total) {
		// TODO Auto-generated method stub
		
	}

}
