package algorithms.skill;


import util.Log;
import config.Config;
import framework.record.ISceneReader;
import algorithms.statistics.HandStatistics;
import bean.*;

public class Skill {

	public Action getSkillAction(ISceneReader reader) {
		
		/*if(isContainAK(reader.hold())){
			Action action = Action.raise;
			action.setNum(300);
			return action;
		}
		
		if(isContainJJ(reader.hold())){
			  Incident[] incident = reader.preAction();
			if(isPreRaise(incident)){
				return Action.call;
			}else{
				if(callNum(incident) > incident.length - 2){
					return Action.call;
				}else{
					Action action = Action.raise;
					action.setNum(200);
					return action;
				}
			}
		}
		
		if(canCheck(reader.availableAction())){
			return Action.check;
		}else{
			float win = HandStatistics.getInstance().getProbability(reader.hold(),
					reader.person().length);
			Log.Log("log2",reader.hold()[0].getPoint()+","+reader.hold()[1].getPoint() + "----->"+win+"");
			Poker[] poker = { new Poker(Color.CLUBS, 14),
					new Poker(Color.DIAMONDS, 14) };
			float AAwin = HandStatistics.getInstance().getProbability(poker,
					reader.person().length);
			if (win >= Config.AlgorithmConfig.SHILL_CALL * AAwin) {
				int callJetton = reader.callJetton();
				Incident[] incident = reader.preAction();
				if(callNum(incident) < incident.length/2){//call或者raise的人数少于一半
					if(callJetton<Config.AlgorithmConfig.SHILL_CALLJETTON * reader.lastJetton()){
						return Action.call;
					}else{
						return Action.fold;
					}
				}else{
					if (win >= Config.AlgorithmConfig.SKILL_RAISE * AAwin) {
						if(callJetton<Config.AlgorithmConfig.SHILL_CALLJETTON * reader.lastJetton()){
							return Action.call;
						}else{
							return Action.fold;
						}
					}else{
						return Action.fold;
					}
				}
				
			}
		}
		*/
		return Action.fold;
		
	}

	boolean canCheck(Action[] actions){
		boolean flag =false;
		for(int i=0;i<actions.length;i++){
			if(actions[i].equals(Action.check)){
				flag = true;
			}
		}
		return flag;
	}
	
	private boolean isContainAK(Poker[] poker){
		boolean flag = false;
		Poker[][] pokers ={ {new Poker(Color.CLUBS, 14),new Poker(Color.DIAMONDS, 14) },
				{new Poker(Color.CLUBS, 13),new Poker(Color.DIAMONDS, 14)},
				{new Poker(Color.CLUBS, 14),new Poker(Color.DIAMONDS, 13)},
				{new Poker(Color.CLUBS, 13),new Poker(Color.DIAMONDS, 13)},
				{new Poker(Color.CLUBS, 12),new Poker(Color.DIAMONDS, 12)}};
		for(int i=0;i<pokers.length;i++){
			if(poker[0].getPoint() == pokers[i][0].getPoint()
					&& poker[1].getPoint() == pokers[i][1].getPoint()){
				flag = true;
			}
		}
		return flag;
	}
	
	private boolean isContainJJ(Poker[] poker){
		boolean flag = false;
		Poker[][] pokers ={ {new Poker(Color.CLUBS, 11),new Poker(Color.DIAMONDS, 11) },
				{new Poker(Color.CLUBS, 14),new Poker(Color.DIAMONDS, 12)},
				{new Poker(Color.CLUBS, 12),new Poker(Color.DIAMONDS, 14)},
				{new Poker(Color.CLUBS, 10),new Poker(Color.DIAMONDS, 10)},
				{new Poker(Color.CLUBS, 8),new Poker(Color.DIAMONDS, 8)},
				{new Poker(Color.CLUBS, 7),new Poker(Color.DIAMONDS, 7)},
				{new Poker(Color.CLUBS, 9),new Poker(Color.DIAMONDS, 9)},};
		for(int i=0;i<pokers.length;i++){
			if(poker[0].getPoint() == pokers[i][0].getPoint()
					&& poker[1].getPoint() == pokers[i][1].getPoint()){
				flag = true;
			}
		}
		return flag;
	}
	
	private boolean isPreRaise(Incident[] incident){
		boolean flag = false;
		for(int i=0;i<incident.length;i++){
			if(incident[i].getAction().equals(Action.raise)){
				flag = true;
			}
		}
		return flag;
	}
	
	private int callNum(Incident[] incident){
		int count=0;
		for(int i =0;i<incident.length;i++){
			if(incident[i].getAction().equals(Action.raise) || incident[i].getAction().equals(Action.call)){
				count++;
			}
		}
		return count;
	}
	
}
