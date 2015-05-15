package framework.record;

import java.util.Map;

import framework.translate.IActionObserver;
import bean.Combination;
import bean.Incident;
import bean.Person;
import bean.Poker;
import bean.Result;

public class SceneRecorder implements IActionObserver,ISceneReader{

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
	public void hold(Poker[] poker) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inquire(Incident[] action,int total) {
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
	public void blind(String smallId, int smallJetton) {
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
	public Poker[] hold() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int pot() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Person[] person() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int roundNum() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int seatNum() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Combination[] combinations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Poker[] common() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int callJetton() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int lastJetton() {
		// TODO Auto-generated method stub
		return 0;
	}

}
