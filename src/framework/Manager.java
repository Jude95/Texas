package framework;

import util.Log;
import bean.Incident;
import bean.Person;
import bean.Poker;
import framework.translate.Translator;
import net.Client;

public class Manager {
	private Client mClient;
	private Translator mTranslator;
	
	public Manager(Client client){
		this.mClient = client;
		mTranslator = new Translator();
		mClient.registerObserver(mTranslator);
		mTranslator.registerObserver(new IProgressObserver() {
			
			@Override
			public void turn(Poker poker) {
				
			}
			
			@Override
			public void seat(Person[] person) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void river(Poker poker) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void inquire(Incident[] action) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void hold(Poker[] poker) {
				Log.Log("hold poker :"+poker[0].getPoint());
			}
			
			@Override
			public void flop(Poker[] poker) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void blind(String smallId, int smallJetton) {
				Log.Log("blind :"+smallId);
			}
			
			@Override
			public void blind(String smallId, int smallJetton, String bigId,
					int bigJetton) {
				// TODO Auto-generated method stub
				
			}
		});
	}

}
