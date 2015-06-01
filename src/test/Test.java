package test;

import algorithms.statistics.HandStatistics;
import bean.Color;
import bean.Poker;

public class Test {
	
	public static void main(String args[]){
		Poker poker[] = {new Poker(Color.HEARTS, 14), new Poker(Color.CLUBS, 14)};
		float win = HandStatistics.getInstance().getProbability(poker, 2);
		System.out.println(win);
		
	}
	
	
}
