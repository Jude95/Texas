package test;


import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import bean.Color;
import bean.Poker;
import framework.translate.IActionObserver;

public class StateProducer {
	
	private static final int PERSON_NUM  = 8;
	private static int SELF_POKER_ONE = 14;
	private static int SELF_POKER_TWO = 14;
	private static final boolean isSameColor = false;
	private IActionObserver mIActionObserver;
	private Random mRandom = new Random();
	private Poker fPoker;
	private Poker sPoker;
	private Poker[] cPoker = new Poker[5];
	private Set<Integer> set = new HashSet<Integer>();
	private Poker [][] pokers = new Poker[PERSON_NUM][7];
	private Poker [] allPoker = new Poker[52];
	
	public StateProducer(IActionObserver mIActionObserver){
		this.mIActionObserver = mIActionObserver;
		initAllPokers();
		initHand();
		
	}
	
	
	public static void main(String[] args) {
		StateProducer s = new StateProducer(null);
		int winer = 0;
		for(int i=0;i<10000000;i++){
			s.initCards();
			StateJudger sj = new StateJudger();
			int win = sj.getResult(s.pokers);
			if(win ==0){
				winer++;
			}
			s.reset();
		}
		System.out.println(winer);
		System.out.println(winer/1000000000);
	}
	
	
	public void reset(){
		set.clear();
	}
	
	public void initCards(){
		int temp ;
		for(int i=0;i<5;i++){
			while(true){
				temp = getNum();
				if(!set.contains(temp)){
					cPoker[i] = allPoker[temp];
					set.add(temp);
					break;
				}
			}
			
		}
		for(int i =0;i<pokers.length;i++){
			for(int j=0;j<pokers[i].length;j++){
				if(i == 0 && j<2){
					pokers[i][j] = fPoker;
					j++;
					pokers[i][j] = sPoker;
				}else if(i!= 0 && j<2){
					while(true){
						temp = getNum();
						if(!set.contains(temp)){
							pokers[i][j] = allPoker[temp];
							set.add(temp);
							break;
						}
					}
				}else{
					pokers[i][j] = cPoker[j-2];
				}
			}
		}
	}
	
	
	
	
	//初始化全套poker
	private void initAllPokers(){
		for(int i = 0;i<13;i++){
			for(int j= 0;j<4;j++){
				allPoker[i*4+j] = new Poker(getColor(j),i+2);
			}
		}
	}
	
	private void initHand(){
		if(isSameColor){
			fPoker = allPoker[(SELF_POKER_ONE-2)*4+1];
			sPoker = allPoker[(SELF_POKER_TWO-2)*4+1];
			set.add((SELF_POKER_ONE-2)*4+1);
			set.add((SELF_POKER_TWO-2)*4+1);
		}else{
			fPoker = allPoker[(SELF_POKER_ONE-2)*4+2];
			sPoker = allPoker[(SELF_POKER_TWO-2)*4+1];
			set.add((SELF_POKER_ONE-2)*4+2);
			set.add((SELF_POKER_TWO-2)*4+1);
		}
	}
	
	//得到color
	private Color getColor(int i){
		Color color;
		switch(i){
		case 0:
			color = Color.SPADES;
			break;
		case 1:
			color = Color.CLUBS;
			break;
		case 2:
			color = Color.DIAMONDS;
			break;
		case 3:
			color = Color.HEARTS;
			break;
		default:
			color = Color.NULL;
		}
		return color;
	}
	
	//得到一个在一定范围的随机数
	private int getNum(){
		return Math.abs(mRandom.nextInt())%52;
	}
	
	
}
