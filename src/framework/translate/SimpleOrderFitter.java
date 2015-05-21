package framework.translate;

import java.util.ArrayList;
import java.util.List;

public class SimpleOrderFitter {
	private OrderCallback mCallback;
	private List<String> mContent = new ArrayList<String>();
	
	public interface OrderCallback{
		public void onOrderCallback(String order,String[] content);
	}
	
	public SimpleOrderFitter(OrderCallback callback){
		mCallback = callback;
	}
	
	public void append(String input){
		if(input.equals("reg")){
			mCallback.onOrderCallback("reg",null);
			mContent.clear();
		}else if(input.equals("gameover")){
			mCallback.onOrderCallback("gameover",null);
			mContent.clear();
		}else if(input.startsWith("/")){
			mCallback.onOrderCallback(input.substring(1, input.length()).trim(),mContent.toArray(new String[0]));
			mContent.clear();
		}else if(input.charAt(input.length()-2)=='/'){
			//指令开头
		}else{
			mContent.add(input.substring(0, input.length()-1));
		}
	}
}
