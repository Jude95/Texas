package framework;

import java.util.Map;

import framework.record.ISceneReader;
import framework.translate.IActionObserver;
import framework.translate.IActionPoster;
import bean.Action;
import bean.Incident;
import bean.Person;
import bean.Poker;
import bean.Result;

public class Deciders implements IActionObserver{
	private IActionPoster mActionPoster;
	private ISceneReader mSceneReader;
	public Deciders(IActionPoster actionPoster,ISceneReader sceneReader) {
		mActionPoster = actionPoster;
		mSceneReader = sceneReader;
	}

	@Override
	public void inquire(Incident[] action, int total) {
		Action[] actions = new Action[]{Action.check,Action.call,Action.raise,Action.all_in,Action.fold};
		int i = (int) (Math.random()*actions.length);
		switch (actions[i]) {
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

}
