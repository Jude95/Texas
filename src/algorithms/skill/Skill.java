package algorithms.skill;

import java.util.Arrays;
import config.Config;
import framework.record.ISceneReader;
import bean.*;

public class Skill {
	
	private Poker[][] rPoker= {{new Poker("A"),new Poker("A")},
			{new Poker("K"),new Poker("K")},
			{new Poker("Q"),new Poker("Q")},
			{new Poker("A"),new Poker("K")}};
	
	public Action getSkillAK(ISceneReader reader){
		Poker[] poker = reader.hold();
		Person[] person = reader.person();
		int lastJetton = reader.lastJetton();
		Action mAction = null;
		if(isAKMax(poker)){
			mAction = Action.raise;
			mAction.setNum(getRaiseNum(person,lastJetton));
		}else{
			mAction = Action.fold;
		}
		return mAction;
	}
	
	private boolean isAKMax(Poker[]poker){
		boolean flag = false;
		for(int i=0;i<rPoker.length;i++){
			if(equal(poker,rPoker[i])){
				flag = true;
			}
		}
		return flag;
	}
	
	
	private boolean equal(Poker[]sPoker,Poker[]rPoker ){
		return (sPoker[0].equals(rPoker[0]) &&sPoker[1].equals(rPoker[1]))||
				(sPoker[0].equals(rPoker[1]) &&sPoker[1].equals(rPoker[0]));
	}
	
	private int getRaiseNum(Person[] person,int lastJetton){
		if(lastJetton>2000){
			return (int)(Config.AlgorithmConfig.SKILL_WEIGHT*lastJetton);
		}else if(lastJetton<300){
			return lastJetton;
		}else{
			Arrays.sort(person);
			int s = (int) (person.length*Config.AlgorithmConfig.SKILL_PERSON_NUM);
			int jetton =  (int)(person[s].getJetton()*Config.AlgorithmConfig.SKILL_WEIGHT);
			if(lastJetton < jetton){
				return lastJetton;
			}else{
				return jetton;
			}
		}
	}
	
	
}
