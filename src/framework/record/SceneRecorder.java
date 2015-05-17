package framework.record;

import java.util.Map;

import bean.Action;
import bean.Incident;
import bean.Person;
import bean.Poker;
import bean.Result;
import framework.translate.IActionObserver;

public class SceneRecorder implements IActionObserver,ISceneReader{
	private Person[] person;
	private Poker[] holdPoker;//手牌
	private Poker[] commonPocker;//公牌
	private int total;//池底
	private Incident[] preIncident;//之前的操作
	@Override
	public void seat(Person[] person) {
		// TODO Auto-generated method stub
		this.person = person;
	}

	@Override
	public void blind(String smallId, int smallJetton, String bigId,
			int bigJetton) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hold(Poker[] poker) {
		// TODO Auto-generated method stub
		holdPoker = poker;
	}

	@Override
	public void inquire(Incident[] action,int total) {
		// TODO Auto-generated method stub
		preIncident = action;
		this.total = total;
	}

	@Override
	public void flop(Poker[] poker) {
		// TODO Auto-generated method stub
		commonPocker = new Poker[5];//在转牌处初始化公共牌
		for(int i=0; i<3; i++){
			commonPocker[i] = poker[i];
		}
		
	}

	@Override
	public void turn(Poker poker) {
		// TODO Auto-generated method stub
		commonPocker[3] = poker;
		
	}

	@Override
	public void river(Poker poker) {
		// TODO Auto-generated method stub
		commonPocker[4] = poker;
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
		return holdPoker;
	}

	@Override
	public int pot() {
		// TODO Auto-generated method stub
		return total;
	}

	@Override
	public Person[] person() {
		// TODO Auto-generated method stub
		return person;
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
	public Action[] availableAction() {
		// TODO Auto-generated method stub
		Action[] availableAtion;
		if(preIncident.length==0){//一局的开始，庄家或者小盲注
			return null;
		}
		if(preIncident[preIncident.length-1].getAction().getNum()==Action.params("raise").getNum()){//上一个人加注
			availableAtion = new Action[4];
			availableAtion[0] = Action.call;
			availableAtion[1] = Action.raise;
			availableAtion[2] = Action.fold;
			availableAtion[3] = Action.all_in;
			return availableAtion;
		}
		
		/*
		 * 还没写..
		 */
		return null;
	}

	@Override
	public Poker[] common() {
		// TODO Auto-generated method stub
		return commonPocker;
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
