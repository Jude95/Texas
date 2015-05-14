package bean;
public enum Color{
	SPADES,HEARTS,CLUBS,DIAMONDS;
	
	public static Color params(String str){
		if(str.equals(SPADES.name())){
			return SPADES;
		}else if(str.equals(HEARTS.name())){
			return HEARTS;
		}else if(str.equals(CLUBS.name())){
			return CLUBS;
		}else{
			return DIAMONDS;
		}
	}
}