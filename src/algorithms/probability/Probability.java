package algorithms.probability;

import framework.record.ISceneReader;
import bean.*;

/*算法中的计算并不完全精确
 * 有很多需要多加1，少减1的东西对整体影响很小
 * 都不做考虑
 * */

public class Probability{
	
	
//	public static void main(String[] args) {
//		Probability p = new Probability();
//		Poker[] poker = {new Poker("A"),new Poker("K")};
//		System.out.println(p.get2HighCard(poker));
//		System.out.println(p.get2OnePair(poker));
//		System.out.println(p.get2TwoPair(poker));
//	}
	
	public Action getProbabilityAction(ISceneReader reader){
		Action mAction= null;
		
		
		return mAction;
	}
	
	
	private double[] get2Probability(Poker[] poker){
		double [] probability = new double[1];//10种牌形
		int[] s = new int[10];//每种牌形的样本数
		
		
		return null;
	}
	
	
	private double[] get5Probability(Poker[] poker){
		
		return null;
	}
	
	private double[] get6Probability(Poker[] poker){
		
		return null;
	}
	
	private double[] get7Probability(Poker[] poker){
		
		return null;
	}
	
	
	//初始手中有两张牌
	
	//最终为高牌的样本数------未完成，没减同花顺等
	private int get2HighCard(Poker[] poker){
		if(is2OnePair(poker)){
			return 0;
		}else{
			//c 11 5 * 4^5
			return (int) (arrangementor(5,11)*Math.pow(4, 5));
		}
	}
	
	//最终是一对的样本数------未完成，没减同花顺等
	private int get2OnePair(Poker[] poker){
		if(is2OnePair(poker)){
			//c 12 5 * 4^5
			return (int) (arrangementor(5,12)*Math.pow(4, 5));
		}else{
			//c 10 3  * 4^3 * c 11 1 * c 4 2
			return (int) (arrangementor(3,10)*Math.pow(4, 3))*11*6; 
		}
	}
	
	//最终是两对的样本数
	private int get2TwoPair(Poker[] poker){
		if(is2OnePair(poker)){
			//c 11 3 * 4^3 * c 12 1 *c 4 2
			return (int) (arrangementor(3,11)*Math.pow(4, 3))*12*6;
		}else{
			//c 9 1 * 4^1 * c 12 1 *c 4 2 (不称底牌)
			//c 10 2 * 4^2 * c 2 1 * c 3 1 * c 11 1 * c 4 2 (称一张底牌)
			//c 11 3 * 4^3 * c 3 1 *c 3 1 (称两张底牌)
			return (int)( (arrangementor(1,9)*Math.pow(4, 1))*12*6 +
					(arrangementor(2,10)*Math.pow(4, 2))*2*3*11*6+
					(arrangementor(3,11)*Math.pow(4, 3))*3*3); 
		}
	}
	
	//最终是三条的样本数
	private int get2ThreeKind(Poker[] poker){
		if(is2OnePair(poker)){
			// c 12 4 * 4^4 * c 2 1
			return (int) (arrangementor(4, 12)*Math.pow(4, 4)*2);
		}else{
			//c 10 2 * 4^2 * c 11 1 * c 4 3(不称底牌)
			//c 11 3 * 4^3 * c 2 1 * c 3 2 (称一张底牌)
			return (int)(arrangementor(2, 10)*Math.pow(4, 2)*11*4+
					arrangementor(3,11)*Math.pow(4,3)*2*3);
		}
	}
	
	
	//顺子，最不好做的东西------未完成
	private int get2Flush(Poker[] poker){
		if(is2OnePair(poker)){
			//10 * 4^5 (不称底牌)
			//5 * 4^4 * (12*4+2);
			
		}else{
			if(get2Subtraction(poker) <=4){
				
			}else{
				
			}
		}
		return 0;
	}
	
	
	
	//判断是否是一对
	private boolean is2OnePair(Poker[] poker){
		return poker[0].getPoint() == poker[1].getPoint();
	}
	//得到两张牌之间的点数差
	private int get2Subtraction(Poker[] poker){
		return Math.abs(poker[0].getPoint()-poker[1].getPoint());
	}
	
	
	//得到样本总数
	private int getSum(int[] s){
		int sum = 0;
		for(int i=0;i<s.length;i++){
			sum += s[i];
		}
		return sum;
	}
	
	//组合计算,a是较小者,b是较大者
	private int combinator(int a,int b){
		int res = 0;
		res = arrangementor(a, b)/arrangementor(a, a);
		return res;
	}
	
	//排列计算
	private int arrangementor(int a,int b){
		int res = 1;
		for(int i=b-a+1;i<=b;i++){
			res *= i;
		} 
		return res;
	}
	
	
}

