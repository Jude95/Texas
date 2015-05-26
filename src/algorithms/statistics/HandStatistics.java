package algorithms.statistics;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Random;

import javax.swing.text.html.parser.Entity;

import config.Config;
import util.FileUtil;
import util.Log;
import util.ObjectSave;
import net.Client;
import bean.Combination;
import bean.Incident;
import bean.Person;
import bean.Poker;
import bean.Result;
import framework.translate.IActionObserver;

public class HandStatistics implements IActionObserver{
	private static HandStatistics instance;
	
	private HashMap<String, Float> bag8 = new HashMap<String, Float>(); 
	private HashMap<String, Float> bag7 = new HashMap<String, Float>(); 
	private HashMap<String, Float> bag6 = new HashMap<String, Float>(); 
	private HashMap<String, Float> bag5 = new HashMap<String, Float>(); 
	private HashMap<String, Float> bag4 = new HashMap<String, Float>(); 
	private HashMap<String, Float> bag3 = new HashMap<String, Float>(); 
	private HashMap<String, Float> bag2 = new HashMap<String, Float>(); 
	
	private HashMap<String, Float> curBag;
	private static final boolean debug = false;
	
	private File dir;
	
	Result result = new Result();
	int lastCount = 0;
	
	private  HandStatistics(){
		 dir = new File(FileUtil.getStandDir(),"Hand");
		 if(!dir.exists()){
			 dir.mkdir();
		 }
		 init();
	}
	
	public static HandStatistics getInstance(){
		if(instance == null){
			instance = new HandStatistics();
		}
		return instance;
	}
	
	private HashMap<String, Float> readMapByCount(int index){
		Object obj = ObjectSave.readObjectFromFile(new File(dir, "hand"+index));
		if(obj!=null)
			return (HashMap<String, Float>)obj;
		else
			return new HashMap<String, Float>();
	}
	
	private void init(){
		bag2 = readMapByCount(2);
		bag3 = readMapByCount(3);
		bag4 = readMapByCount(4);
		bag5 = readMapByCount(5);
		bag6 = readMapByCount(6);
		bag7 = readMapByCount(7);
		bag8 = readMapByCount(8);
	}
	
	private void saveBag(){
		for(int i = 2;i<9;i++){
			HashMap<String, Float> bag = getBagByCount(i);
			File last = new File(dir,"hand"+i);
			FileUtil.deleteFile(last);
			ObjectSave.witeObjectToFile(bag, last);
		}
	}

	
	private HashMap<String, Float> getBagByCount(int count){
		switch (count) {
		case 2:
			return bag2;
		case 3:
			return bag3;
		case 4:
			return bag4;
		case 5:
			return bag5;
		case 6:
			return bag6;
		case 7:
			return bag7;
		default:
			return bag8;
		}
	}
	
	
	public float getProbability(Poker[] poker,int count){
		HashMap<String, Float> tempBag = getBagByCount(count);
		StringBuilder key = new StringBuilder();
		key.append(poker[0].compareTo(poker[1])>0?poker[0].getPointStr()+poker[1].getPointStr():poker[1].getPointStr()+poker[0].getPointStr());
		key.append(poker[0].getColor()==poker[1].getColor()?"s":"o");
		Float PROB = tempBag.get(key.toString());
		if(PROB!=null){
			float prob = PROB;
			return (prob-(int)prob)*10;
		}else{
			return 0;
		}
	}
	
	public float getMaxProbability(int count){
		HashMap<String, Float> tempBag = getBagByCount(count);
		Float PROB = tempBag.get("AAo");
		if(PROB!=null){
			float prob = PROB;
			return (prob-(int)prob)*10;
		}else{
			return 0;
		}
	}
	
	
	@Override
	public void seat(Person[] person) {
		curBag = getBagByCount(person.length);
	}
	
	
	private void showBag(){
		for(int i = 2;i<9;i++){
			HashMap<String, Float> bag = getBagByCount(i);
			for(Entry<String,Float> entry:bag.entrySet()){
				if(debug){
					System.out.println(entry.getKey()+"  :  "+entry.getValue());
				}else{
					Log.Log("statistics",entry.getKey()+"  :  "+entry.getValue());
				}
			}
		}
	}
	


	@Override
	public void showdown(Result[] results) {
		boolean flag = false;
		for(Result r:results){
			if(r.getIndex() == 1){
				result = r;
				flag = true;
			}
		}
		StringBuilder key = new StringBuilder();
		key.append(result.getPoker1().compareTo(result.getPoker2())>0?result.getPoker1().getPointStr()+result.getPoker2().getPointStr():result.getPoker2().getPointStr()+result.getPoker1().getPointStr());
		key.append(result.getPoker1().getColor()==result.getPoker2().getColor()?"s":"o");
		Float PROB = curBag.get(key.toString());
		float prob;
		if(PROB == null){
			prob = flag?1.1f:1.0f;
		}else{
			prob = PROB;
			prob=((flag?1f:0)+Math.round((prob-(int)prob)*10*(int)prob))/(((int)prob+1)*10)+(int)prob+1;
		}
		curBag.put(key.toString(), prob);
	}

	@Override
	public void regist() {
		showBag();
		saveBag();
	}

	@Override
	public void gameover() {
		showBag();
		saveBag();
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
