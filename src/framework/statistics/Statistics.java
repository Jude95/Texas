package framework.statistics;

import java.util.HashMap;
import java.util.Map;

import util.Log;
import net.Client;
import bean.Combination;
import bean.Incident;
import bean.Person;
import bean.Poker;
import bean.Result;
import framework.translate.IActionObserver;

public class Statistics implements IActionObserver{
	private HashMap<String, Float> bag = new HashMap<String, Float>(); 
	Result result = new Result();
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
		result.setPoker1(poker[0]);
		result.setPoker2(poker[1]);
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
		boolean flag = false;
		for(Result r:results){
			if(r.getIndex() == 1 && r.getId().equals(Client.ID)){
				result = r;
				flag = true;
			}
		}
		StringBuilder key = new StringBuilder();
		key.append(result.getPoker1().compareTo(result.getPoker2())>0?result.getPoker1().getPointStr()+result.getPoker2().getPointStr():result.getPoker2().getPointStr()+result.getPoker1().getPointStr());
		key.append(result.getPoker1().getColor()==result.getPoker2().getColor()?"s":"o");
		Float PROB = bag.get(key.toString());
		float prob;
		if(PROB == null){
			prob = flag?1.1f:1.0f;
		}else{
			prob = PROB;
			prob=((flag?1f:0)+Math.round((prob-(int)prob)*10*(int)prob))/(((int)prob+1)*10)+(int)prob+1;
			
		}
		bag.put(key.toString(), prob);
		//Log.Log(key+"  :  "+prob);
		
	}
	
	public static void main(String[] args) {
		Statistics statistics = new Statistics();
		Result[] results = new Result[1];
		Client.ID = "8888";
		Poker[] pokers = new Poker[]{new Poker("J"),new Poker("K")};
		for(int i = 0;i<10;i++){
			results[0] = new Result(i%3, "8888", pokers[0],pokers[1], Combination.FLUSH);
			statistics.hold(pokers);
			statistics.showdown(results);
		}
	}

}
