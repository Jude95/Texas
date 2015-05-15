package framework.translate;

import java.util.ArrayList;
import java.util.HashMap;

import util.Log;
import framework.IActionPoster;
import framework.IActionObserver;
import framework.translate.SimpleOrderFitter.OrderCallback;
import bean.Action;
import bean.Color;
import bean.Combination;
import bean.Incident;
import bean.Person;
import bean.Poker;
import bean.Result;
import net.IMessageObserver;
import net.IMessagePoster;

public class Translator implements IMessageObserver , OrderCallback{
	private DispatchActionObserver dispatcher = new DispatchActionObserver();
	private IMessagePoster poster;
	private SimpleOrderFitter simpleOrderFitter = new SimpleOrderFitter(this);
	
	public Translator(IMessagePoster poster){
		this.poster = poster;
	}
	
	public void registerObserver(IActionObserver observer){
		dispatcher.registerObserver(observer);
	}
	
	public void unRegisterObserver(IActionObserver observer){
		dispatcher.unRegisterObserver(observer);
	}
	
	@Override
	public void onMessageReceive(String msg) {
		simpleOrderFitter.append(msg);
	}
	
	public IActionPoster obtainActionPoster(){
		return new IActionPoster() {
			
			@Override
			public void raise(int num) {
				poster.send(Action.raise.name()+" "+num);
			}
			
			@Override
			public void fold() {
				poster.send(Action.fold.name());
			}
			
			@Override
			public void check() {
				poster.send(Action.fold.name());
			}
			
			@Override
			public void call() {
				poster.send(Action.call.name());
			}
			
			@Override
			public void all_in() {
				poster.send(Action.all_in.name());
			}
		};
	}
	
	@Override
	public void onOrderCallback(String order, String[] content) {
		if(order.equals("seat")){
			seat(content);
		}else if(order.equals("bind")){
			bind(content);
		}else if(order.equals("hold")){
			hold(content);
		}else if(order.equals("inquire")){
			inquire(content);
		}else if(order.equals("flop")){
			flop(content);
		}else if(order.equals("turn")){
			turn(content);
		}else if(order.equals("river")){
			river(content);
		}else if(order.equals("pot-win")){
			pot_win(content);
		}else if(order.equals("showdown")){
			showdown(content);
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
			pokers.add(new Poker(Color.params(params[0]), params[1]));
		}
		dispatcher.hold(pokers.toArray(new Poker[0]));
	}
	
	public void inquire(String[] content){
		ArrayList<Incident> incidents = new ArrayList<Incident>();
		for(int i = 0;i<content.length-1;i++){
			String[] params = content[i].split(" ");
			Incident incident = new Incident(new Person(params[0], Integer.parseInt(params[1]), Integer.parseInt(params[2])),Integer.parseInt(params[3]),Action.params(params[4]));
			incidents.add(incident);
		}
		String[] params1 = content[content.length-1].split(" ");
		int total = Integer.parseInt(params1[params1.length-1]);
		dispatcher.inquire(incidents.toArray(new Incident[0]),total);
	}
	
	public void flop(String[] content){
		ArrayList<Poker> pokers = new ArrayList<Poker>();
		for(String line:content){
			String[] params = line.split(" ");
			pokers.add(new Poker(Color.params(params[0]), params[1]));
		}
		dispatcher.flop(pokers.toArray(new Poker[0]));
	}
	
	public void turn(String[] content){
		String[] params = content[0].split(" ");
		dispatcher.turn(new Poker(Color.params(params[0]), params[1]));
	}
	
	public void river(String[] content){
		String[] params = content[0].split(" ");
		dispatcher.river(new Poker(Color.params(params[0]), params[1]));
	}
	
	public void pot_win(String[] content){
		HashMap<String, Integer> pots = new HashMap<String, Integer>();
		for(String line:content){
			String[] params = line.split(" ");
			pots.put(params[0], Integer.parseInt(params[1]));
		}
		dispatcher.pot_win(pots);
	}
	
	public void showdown(String[] content){
		ArrayList<Result> result = new ArrayList<Result>();
		for(String line:content){
			String[] params = line.split(" ");
			int index = Integer.parseInt(params[0].charAt(0)+"");
			String id = params[1];
			Poker poker1 = new Poker(Color.params(params[2]), params[3]);
			Poker poker2 = new Poker(Color.params(params[4]), params[5]);
			Combination com = Combination.parse(params[6]);
			result.add(new Result(index, id, poker1, poker2, com));
		}
		dispatcher.showdown(result.toArray(new Result[0]));
	}
}
