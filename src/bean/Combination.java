package bean;

public enum Combination{
	HIGH_CARD,
	ONE_PAIR,
	TWO_PAIR,
	THREE_OF_A_KIND,
	STRAIGHT,
	FLUSH,
	FULL_HOUSE,
	FOUR_OF_A_KIND,
	STRAIGHT_FLUSH;
	
	public static Combination parse(String str){
		if (str.equals(HIGH_CARD.name())){
			return HIGH_CARD;
		}else if (str.equals(ONE_PAIR.name())){
			return ONE_PAIR;
		}else if (str.equals(TWO_PAIR.name())){
			return TWO_PAIR;
		}else if (str.equals(THREE_OF_A_KIND.name())){
			return THREE_OF_A_KIND;
		}else if (str.equals(STRAIGHT.name())){
			return STRAIGHT;
		}else if (str.equals(FLUSH.name())){
			return FLUSH;
		}else if (str.equals(FULL_HOUSE.name())){
			return FULL_HOUSE;
		}else if (str.equals(FOUR_OF_A_KIND.name())){
			return FOUR_OF_A_KIND;
		}else{
			return FULL_HOUSE;
		}
	}
	
}
