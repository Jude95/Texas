package framework.translate;

import java.util.ArrayList;

import util.Log;
import framework.IProgressObserver;
import framework.translate.SimpleOrderFitter.OrderCallback;
import bean.Color;
import bean.Incident;
import bean.Person;
import bean.Poker;
import net.IMessageObserver;

public class Translator implements IMessageObserver , OrderCallback{
	private DispatchProgressObserver dispatcher = new DispatchProgressObserver();
	private SimpleOrderFitter simpleOrderFitter = new SimpleOrderFitter(this);
	
	public void registerObserver(IProgressObserver observer){
		dispatcher.registerObserver(observer);
	}
	
	public void unRegisterObserver(IProgressObserver observer){
		dispatcher.unRegisterObserver(observer);
	}
	
	@Override
	public void onMessageReceive(String msg) {
		simpleOrderFitter.append(msg);
	}
	
	@Override
	public void onOrderCallback(String order, String[] content) {
		if(order.equals("seat")){
			seat(content);
		}else if(order.equals("bind")){
			bind(content);
		}else if(order.equals("hold")){
			hold(content);
		}
	}
	
	public void seat(String[] content){
		ArrayList<Person> persons = new ArrayList<Person>();
		for(String line:content){
			String[] params = line.split(" ");
			persons.add(new Person(params[params.length-3], Integer.parseInt(params[params.length-2]),  Integer.parseInt(params[params.length-1])));
		}
		dispatcher.seat(persons.toArray(new Person[0]));
	}
	
	public void bind(String[] content){
		if(content.length == 1){
			String[] params1 = content[0].split(" ");
			dispatcher.blind(params1[0], Integer.parseInt(params1[1]));
		}else{
			String[] params1 = content[0].split(" ");
			String[] params2 = content[1].split(" ");
			dispatcher.blind(params1[0], Integer.parseInt(params1[1]),params2[0], Integer.parseInt(params2[1]));
		}
	}
	
	public void hold(String[] content){
		ArrayList<Poker> pokers = new ArrayList<Poker>();
		for(String line:content){
			String[] params = line.split(" ");
			pokers.add(new Poker(Color.params(params[0]), Integer.parseInt(params[1])));
		}
		dispatcher.hold(pokers.toArray(new Poker[0]));
	}
	
	
}
