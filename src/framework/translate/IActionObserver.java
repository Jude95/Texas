package framework.translate;

import java.util.Map;

import bean.Action;
import bean.Incident;
import bean.Person;
import bean.Poker;
import bean.Result;

public interface IActionObserver {
	public void regist();
	public void gameover();
	public void notifly(Incident[] action,int total);
	//座次信息。按顺序排，依次为庄家，小盲注，大盲注
	public void seat(Person[] person);
	//小盲注，大盲注下注
	public void blind(String smallId,int smallJetton,String bigId,int bigJetton);
	//有时没有大盲注
	public void blind(String smallId,int smallJetton);
	//手牌，只有2张
	public void hold(Poker[] poker);
	//本轮在你之前的操作
	public void inquire(Incident[] action,int total);
	//公牌消息,3张poker
	public void flop(Poker[] poker);
	//转牌消息,1张poker
	public void turn(Poker poker);
	//河牌消息,1张poker
	public void river(Poker poker);
	//奖池分配
	public void pot_win(Map<String,Integer> pot);
	//摊牌
	public void showdown(Result[] results);
	
}
