package algorithms.know_enemy;

import java.util.HashMap;

import bean.Action;
import bean.Incident;
import framework.record.ISceneReader;

public class KnowEnemy {

	private KnowEnemy instance;
	private HashMap<String,Integer[]> map; 
	private Action action;
	private String id;
	private ISceneReader reader;
	
	
	private KnowEnemy(){
	}
	
	public KnowEnemy getInstance(ISceneReader reader){
		if(instance == null){
			instance = new KnowEnemy();
			map =  new HashMap<String,Integer[]>();
			map.put("1111",new Integer[5]);
			map.put("2222",new Integer[5]);
			map.put("3333",new Integer[5]);
			map.put("4444",new Integer[5]);
			map.put("5555",new Integer[5]);
			map.put("6666",new Integer[5]);
			map.put("7777",new Integer[5]);
			map.put("8888",new Integer[5]);
		}
		this.reader = reader;
		return instance;
	}
	
	public Type getEnemyType(String id){
		Integer[] s = map.get(id);
		int k = getTypeMax(id);
		return Type.AllInBoss;
	}
	
	
	public void statistics(){
		Incident[] incident = reader.preAction();
		for(int i=0;i<incident.length;i++){
			id = incident[i].getPerson().getName();
			action = incident[i].getAction();
			switch(action){
			case all_in:
				map.get(id)[4] ++;
				break;
			case raise:
				map.get(id)[3] ++;
				break;
			case call:
				map.get(id)[2] ++;
				break;
			case check:
				map.get(id)[1] ++;
				break;
			case fold:
				map.get(id)[0] ++;
				break;
			default:
				break;
			}
		}
	}
	
	private int getTypeMax(String key){
		int temp =-1;
		int k = 0;
		for(int i=0;i<map.get(key).length;i++){
			if(temp<map.get(key)[i]){
				temp = map.get(key)[i];
				k = i;
			}
		}
		return k;
	}
	
	
	
	
	enum Type{
		
		AllInBoss,//全压
		HandDog,//手牌狗
		FoldDOg,//弃牌狗
		RaiseFather,//加注
		CallGod,//跟注帝
		KeepGhost,//保守鬼
		RadicalLord,//激进爷
		
	}
	
}
