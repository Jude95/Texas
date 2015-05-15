package framework.translate;

import java.util.ArrayList;
import java.util.Map;

import bean.Incident;
import bean.Person;
import bean.Poker;
import framework.IProgressObserver;

class DispatchProgressObserver implements IProgressObserver{
	private ArrayList<IProgressObserver> mObservers = new ArrayList<IProgressObserver>();
	
	public void registerObserver(IProgressObserver observer){
		mObservers.add(observer);
	}
	
	public void unRegisterObserver(IProgressObserver observer){
		mObservers.remove(observer);
	}
	
	
	@Override
	public void seat(Person[] person) {
		for(IProgressObserver ob:mObservers){
			ob.seat(person);
		}
	}

	@Override
	public void blind(String smallId, int smallJetton, String bigId,
			int bigJetton) {
		for(IProgressObserver ob:mObservers){
			ob.blind(smallId,smallJetton,bigId,bigJetton);
		}
	}

	@Override
	public void hold(Poker[] poker) {
		for(IProgressObserver ob:mObservers){
			ob.hold(poker);
		}
	}

	@Override
	public void inquire(Incident[] action,int total) {
		for(IProgressObserver ob:mObservers){
			ob.inquire(action,total);
		}
	}

	@Override
	public void flop(Poker[] poker) {
		for(IProgressObserver ob:mObservers){
			ob.flop(poker);
		}
	}

	@Override
	public void turn(Poker poker) {
		for(IProgressObserver ob:mObservers){
			ob.turn(poker);
		}
	}

	@Override
	public void river(Poker poker) {
		for(IProgressObserver ob:mObservers){
			ob.river(poker);
		}
	}

	@Override
	public void blind(String smallId, int smallJetton) {
		for(IProgressObserver ob:mObservers){
			ob.blind(smallId,smallJetton);
		}
	}

	@Override
	public void pot_win(Map<String, Integer> pot) {
		// TODO Auto-generated method stub
		
	}
	
}