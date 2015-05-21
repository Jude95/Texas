
package test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import bean.Color;
import bean.Poker;
import framework.translate.IActionObserver;

public class StateProducer {
	
	private static final int PERSON_NUM  = 8;
	private static final float TIMES = 1000000;//每手牌统计次数
	private static final int BEGIN = 2;//牌最小
	private static final int END = 14;//牌最大
	private static final int KIND =2;//两种是否同花
	
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
	}
	
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		StateProducer s = new StateProducer(null);
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("oos.txt")));
		 BufferedWriter bw = new BufferedWriter(new FileWriter(new File("test.txt")));
		String str = "";
		for(int k =BEGIN;k<=END;k++){
			for(int t = BEGIN;t<=END;t++){
				for(int h = 0;h<KIND;h++){
					if(t==k){
						if(h ==1){
							continue;
						}
					}
					s.initHand(k,t,h);
					int winer = 0;
					for(int i=0;i<TIMES;i++){
						s.initCards();
						StateJudger sj = new StateJudger();
						int win = sj.getResult(s.pokers);
						if(win ==0){
							winer++;
						}
						s.reset();
					}
					Recoder r = new Recoder();
					r.a = k;
					r.b = t;
					r.c = h;
					r.d = winer;
					r.e = (float) (winer/TIMES);
					str = k + " "+t+" "+h+" "+winer+" "+r.e+"\n";
					oos.writeObject(r);
					bw.write(str);
				}
			}
		}
		bw.close();
		oos.close();
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
	
	public void initHand(int a,int b,int isSameColor){
		if(isSameColor==1){
			fPoker = allPoker[(a-2)*4+1];
			sPoker = allPoker[(b-2)*4+1];
			set.add((a-2)*4+1);
			set.add((b-2)*4+1);
		}else{
			fPoker = allPoker[(a-2)*4+2];
			sPoker = allPoker[(b-2)*4+1];
			set.add((a-2)*4+2);
			set.add((b-2)*4+1);
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
class Recoder implements Serializable{
	int a;
	int b;
	int c;
	int d;
	float e;
}

