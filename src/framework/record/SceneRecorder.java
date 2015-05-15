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
	private Poker[] holdPoker;//����
	private Poker[] commonPocker;//����
	private int total;//�ص�
	private Incident[] preIncident;//֮ǰ�Ĳ���
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
		commonPocker = new Poker[5];//��ת�ƴ���ʼ��������
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
		if(preIncident.length==0){//һ�ֵĿ�ʼ��ׯ�һ���Сäע
			return null;
		}
		if(preIncident[preIncident.length-1].getAction().getNum()==Action.params("raise").getNum()){//��һ���˼�ע
			availableAtion = new Action[4];
			availableAtion[0] = Action.call;
			availableAtion[1] = Action.raise;
			availableAtion[2] = Action.fold;
			availableAtion[3] = Action.all_in;
			return availableAtion;
		}
		
		/*
		 * ��ûд..
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
