package algorithms.statistics;

import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.MissingFormatArgumentException;
import java.util.Objects;
import java.util.Random;

import javax.swing.text.html.parser.Entity;

import config.Config;
import util.FileUtil;
import util.ObjectSave;
import bean.Incident;
import bean.Person;
import bean.Poker;
import bean.Result;
import framework.translate.IActionObserver;

public class HandStatistics implements IActionObserver {
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

	private HandStatistics() {
		dir = new File(FileUtil.getStandDir(), "Hand");
		if (!dir.exists()) {
			dir.mkdir();
		}
		init();
	}

	public static HandStatistics getInstance() {
		if (instance == null) {
			instance = new HandStatistics();
		}
		return instance;
	}

	private HashMap<String, Float> readMapByCount(int index) {
		Object obj = ObjectSave
				.readObjectFromFile(new File(dir, "hand" + index));
		if (obj != null)
			return (HashMap<String, Float>) obj;
		else
			return new HashMap<String, Float>();
	}

	private void init() {
		bag2 = readMapByCount(2);
		bag3 = readMapByCount(3);
		bag4 = readMapByCount(4);
		bag5 = readMapByCount(5);
		bag6 = readMapByCount(6);
		bag7 = readMapByCount(7);
		bag8 = readMapByCount(8);
	}

	private void saveBag() {
		for (int i = 2; i < 9; i++) {
			HashMap<String, Float> bag = getBagByCount(i);
			File last = new File(dir, "hand" + i);
			FileUtil.deleteFile(last);
			ObjectSave.witeObjectToFile(bag, last);
		}
	}

	private HashMap<String, Float> getBagByCount(int count) {
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
	
	private ArrayList<Entry<String,Float>> getArrayByCount(int count){
		ArrayList<Entry<String,Float>> arr = new ArrayList<Map.Entry<String,Float>>();
		arr.addAll(getBagByCount(count).entrySet());
		Collections.sort(arr,new Comparator<Entry<String,Float>>() {

			@Override
			public int compare(Entry<String,Float> o1, Entry<String,Float> o2) {
				float pro1 = (float)o1.getValue();
				float pro2 = (float)o2.getValue();
				return (int)( ((pro1-(int)pro1)-(pro2-(int)pro2))*10000);
			}
		});

		return arr;
	}
	
	

	public float getProbability(Poker[] poker, int count) {
		HashMap<String, Float> tempBag = getBagByCount(count);
		StringBuilder key = new StringBuilder();
		key.append(poker[0].compareTo(poker[1]) > 0 ? poker[0].getPointStr()
				+ poker[1].getPointStr() : poker[1].getPointStr()
				+ poker[0].getPointStr());
		key.append(poker[0].getColor() == poker[1].getColor() ? "s" : "o");
		Float PROB = tempBag.get(key.toString());
		if (PROB != null) {
			float prob = PROB;
			return (prob - (int) prob) * 10;
		} else {
			return 0;
		}
	}


	public float getMaxProbability(int count) {
		HashMap<String, Float> tempBag = getBagByCount(count);
		Float PROB = tempBag.get("AAo");
		if (PROB != null) {
			float prob = PROB;
			return (prob - (int) prob) * 10;
		} else {
			return 0;
		}
	}

	
	public float getMinProbability(int count){
		ArrayList<Entry<String,Float>> arr = getArrayByCount(count);
		if(arr.size()==0)return 0;
		Float PROB = arr.get(0).getValue();
		if(PROB!=null){
			float prob = PROB;
			return (prob-(int)prob)*10;
		}else{
			return 0;
		}
	}
	
	public float getAverageProbability(int count){
		ArrayList<Entry<String,Float>> arr = getArrayByCount(count);
		if(arr.size()==0)return 0;
		float total = 0;
		for(Entry<String,Float> e:arr){
			total +=(e.getValue()-(int)(float)e.getValue())*10;
		}
		return total/arr.size();
	}

	public float getMidProbability(int count){
		ArrayList<Entry<String,Float>> arr = getArrayByCount(count);
		if(arr.size()==0)return 0;
		Float PROB = arr.get(arr.size()/2).getValue();
		if(PROB!=null){
			float prob = PROB;
			return (prob-(int)prob)*10;
		}else{
			return 0;
		}
	}
	
	public float getNumProbability(int count,int index){
		ArrayList<Entry<String,Float>> arr = getArrayByCount(count);
		if(arr.size()==0)return 0;
		Float PROB;
		if(index>arr.size()-1){
			PROB = arr.get(arr.size()-1).getValue();
		}else{
			PROB = arr.get(arr.size()-1-index).getValue();
		}
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


	private void showBag() {
		File bagDir = new File(FileUtil.getUserDir(), "hand");
		if(!bagDir.exists())
			bagDir.mkdir();
		for (int i = 2; i < 9; i++) {
			HashMap<String, Float> bag = getBagByCount(i);
			for (Entry<String, Float> e : bag.entrySet()) {
				if (debug) {
					// System.out.println(e.getKey()+"  :  "+e.getValue());
				} else {
					File hand = new File(bagDir, "hand" + i);
					FileUtil.writeToFile(hand,
							e.getKey() + "  :  " + e.getValue() + "\n");
				}
			}
		}
	}

	@Override
	public void showdown(Result[] results) {
		boolean flag = false;
		for (Result r : results) {
			if (r.getIndex() == 1) {
				result = r;
				flag = true;
			}
		}
		StringBuilder key = new StringBuilder();
		key.append(result.getPoker1().compareTo(result.getPoker2()) > 0 ? result
				.getPoker1().getPointStr() + result.getPoker2().getPointStr()
				: result.getPoker2().getPointStr()
						+ result.getPoker1().getPointStr());
		key.append(result.getPoker1().getColor() == result.getPoker2()
				.getColor() ? "s" : "o");
		Float PROB = curBag.get(key.toString());
		float prob;
		if (PROB == null) {
			prob = flag ? 1.1f : 1.0f;
		} else {
			prob = PROB;
			int win = ((flag ? Config.Statistics.Gradient : 0) + Math
					.round((prob - (int) prob) * 10 * (int) prob));
			int total = (((int) prob + Config.Statistics.Gradient));
			prob = ((float)win/total/10+total);
		}
		curBag.put(key.toString(), prob);
	}
	
//	public static void main(String[] args) {
//		boolean flag = false;
//		FileUtil.init("1111");
//		Float PROB = HandStatistics.getInstance().readMapByCount(8).get("J2o");
//		float prob;
//		
//		
//		if (PROB == null) {
//			prob = flag ? 1.1f : 1.0f;
//		} else {
//			prob = PROB;
//
//			int win = ((flag ? Config.Statistics.Gradient : 0) + Math
//					.round((prob - (int) prob) * 10 * (int) prob));
//			int total = (((int) prob + Config.Statistics.Gradient));
//			prob = ((float)win/total/10+total);
//
//		}
//		
//	}

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
