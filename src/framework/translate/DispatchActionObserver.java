package framework.translate;

import java.util.ArrayList;
import java.util.Map;

import bean.Incident;
import bean.Person;
import bean.Poker;
import bean.Result;

class DispatchActionObserver implements IActionObserver{
	private ArrayList<IActionObserver> mObservers = new ArrayList<IActionObserver>();
	
	public void registerObserver(IActionObserver observer){
		mObservers.add(observer);
	}
	
	public void unRegisterObserver(IActionObserver observer){
		mObservers.remove(observer);
	}
	
	
	@Override
	public void seat(Person[] person) {
		for(IActionObserver ob:mObservers){
			ob.seat(person);
		}
	}

	@Override
	public void blind(String smallId, int smallJetton, String bigId,
			int bigJetton) {
		for(IActionObserver ob:mObservers){
			ob.blind(smallId,smallJetton,bigId,bigJetton);
		}
	}

	@Override
	public void hold(Poker[] poker) {
		for(IActionObserver ob:mObservers){
			ob.hold(poker);
		}
	}

	@Override
	public void inquire(Incident[] action,int total) {
		for(IActionObserver ob:mObservers){
			ob.inquire(action,total);
		}
	}

	@Override
	public void flop(Poker[] poker) {
		for(IActionObserver ob:mObservers){
			ob.flop(poker);
		}
	}

	@Override
	public void turn(Poker poker) {
		for(IActionObserver ob:mObservers){
			ob.turn(poker);
		}
	}

	@Override
	public void river(Poker poker) {
		for(IActionObserver ob:mObservers){
			ob.river(poker);
		}
	}

	@Override
	public void blind(String smallId, int smallJetton) {
		for(IActionObserver ob:mObservers){
			ob.blind(smallId,smallJetton);
		}
	}

	@Override
	public void pot_win(Map<String, Integer> pot) {
		for(IActionObserver ob:mObservers){
			ob.pot_win(pot);
		}
	}

	@Override
	public void showdown(Result[] results) {
		for(IActionObserver ob:mObservers){
			ob.showdown(results);
		}
	}
	
}