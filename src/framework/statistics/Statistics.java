package framework.statistics;

import java.util.HashMap;
import java.util.Map;

import util.Log;
import net.Client;
import bean.Incident;
import bean.Person;
import bean.Poker;
import bean.Result;
import framework.translate.IActionObserver;

public class Statistics implements IActionObserver{
	private HashMap<String, Float> bag = new HashMap<String, Float>(); 
	int count;
	@Override
	public void seat(Person[] person) {
		count = person.length;
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
	public void inquire(Incident[] action, int total) {
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
		Result result = null;
		if(results.length == 1){
			result = results[0];
		}else{
			for(Result r:results){
				if(r.getIndex() == 1 && r.getId().equals(Client.ID)){
					result = r;
				}
			}
		}
		if(result==null)return;
		StringBuilder key = new StringBuilder();
		key.append(result.getPoker1().getPointStr()+result.getPoker2().getPointStr());
		key.append(result.getPoker1().getColor()==result.getPoker2().getColor()?"s":"o");
		Float PROB = bag.get(key.toString());
		float prob = PROB == null?0:PROB;
		if(PROB == null){
			prob = 1.1f;
		}else{
			prob = PROB;
			prob=(1+(prob-(int)prob)*(int)prob)/(int)prob+1;
		}
		Log.Log(key+"  :  "+prob);
	}

}
