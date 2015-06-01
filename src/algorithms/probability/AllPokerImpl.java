package algorithms.probability;

import java.util.ArrayList;
import java.util.List;

import bean.Color;
import bean.Poker;

public class AllPokerImpl implements IAllPoker {


	@Override
	public List<Poker> getAllPoker() {
		return getAllPoker(null);
	}

	@Override
	public  List<Poker> getAllPoker(Poker[] poker) {
		List<Poker> list = new ArrayList<Poker>();
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 4; j++) {
				list.add(new Poker(getColor(j), i + 2));
			}
		}
		if(poker != null){
			for(int i=0;i<poker.length;i++){
				list.remove(poker[i]);
			}
		}
		return list;
	}

	// 得到color
	private Color getColor(int i) {
		Color color;
		switch (i) {
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
}
