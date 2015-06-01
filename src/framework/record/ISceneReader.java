package framework.record;

import bean.Action;
import bean.Combination;
import bean.Incident;
import bean.Person;
import bean.Poker;

public interface ISceneReader {
	//手牌
	public Poker[] hold();
	//奖池
	public int pot();
	//人员数组，庄家 小盲注 大盲注………依次
	public Person[] person();
	//当前第几圈
	public int roundNum();
	//座位序号
	public int seatNum();
	//当前可进行操作
	public Action[] availableAction();
	//取所有公牌
	public Poker[] common();
	//取当前跟注大小
	public int callJetton();
	//取剩余筹码
	public int lastJetton();
	//押注的总筹码
	public int totalCallJetton();
	//加注了几次
	public int raiseCount();
	//当前玩儿家之前的操作
	public Incident[] preAction();
	
	
}
