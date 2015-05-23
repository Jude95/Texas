package algorithms.statistics;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Random;

import javax.swing.text.html.parser.Entity;

import config.Config;
import util.Log;
import util.ObjectSave;
import net.Client;
import bean.Combination;
import bean.Incident;
import bean.Person;
import bean.Poker;
import bean.Result;
import framework.translate.IActionObserver;

public class Statistics implements IActionObserver{
	private HashMap<String, Float> bag = new HashMap<String, Float>(); 
	private static final boolean debug =true;
	
	Result result = new Result();
	int lastCount = 0;
	
	
	public float getProbability(Poker[] poker){
		StringBuilder key = new StringBuilder();
		key.append(poker[0].compareTo(poker[1])>0?poker[0].getPointStr()+poker[1].getPointStr():poker[1].getPointStr()+poker[0].getPointStr());
		key.append(poker[0].getColor()==poker[1].getColor()?"s":"o");
		Float PROB = bag.get(key.toString());
		if(PROB!=null){
			float prob = PROB;
			return (prob-(int)prob)*10;
		}else{
			return 0;
		}
	}
	
	@Override
	public void seat(Person[] person) {
		if(!(lastCount == person.length)){
			changeRecord( person.length);
		}
	}
	
	private void changeRecord(int curCount){
		File last = new File(String.format(Config.StandDir, lastCount));
		if(!last.exists()){
			last.mkdirs();
		}
		ObjectSave.witeObjectToFile(bag, new File(last,"hand"));
		File curFile = new File(String.format(Config.StandDir, curCount));
		bag = (HashMap<String, Float>) ObjectSave.readObjectFromFile(new File(curFile,"hand"));
		if(bag == null){
			 bag = new HashMap<String, Float>();
		}
		lastCount = curCount;
	}
	
	private void showBag(){
		for(int i = 2;i<9;i++){
			changeRecord(i);
			System.out.println("---------------"+lastCount+"---------------------");
			for(Entry<String,Float> entry:bag.entrySet()){
				if(debug){
					System.out.println(entry.getKey()+"  :  "+entry.getValue());
				}else{
					Log.Log(entry.getKey()+"  :  "+entry.getValue());
				}
			}
			System.out.println("----------------"+lastCount+"--------------------");
		}

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
	}
	
//	public static void main(String[] args) {
//		Statistics statistics = new Statistics();
//		
//		Client.ID = "1111";
//		Poker[] pokers = new Poker[]{new Poker("J"),new Poker("K")};
//		statistics.regist();
//		for(int i = 0;i<100;i++){
//			int l = ((int)(Math.random()*7+2));
//			Result[] results = new Result[l];
//			Person[] persons = new Person[l];
//			for(int p = 0 ; p < l ; p++){
//				results[p] = new Result(((int)(Math.random()*100))%l, "1111", pokers[0],pokers[1], Combination.FLUSH);
//				persons[p] = new Person(1111*p+"", 2000, 10000);
//			}
//			statistics.seat(persons);
//			statistics.hold(pokers);
//			statistics.showdown(results);
//		}
//		statistics.gameover();
//	}

	@Override
	public void regist() {
		showBag();
	}

	@Override
	public void gameover() {
		showBag();
	}


	@Override
	public void notifly(Incident[] action, int total) {
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
}
