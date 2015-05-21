package framework;


import util.Log;
import algorithm.AlgorithmManager;
import algorithm.IAlgorithm;
import algorithms.statistics.Statistics;
import framework.deciders.AlwaysCallDeciders;
import framework.deciders.Deciders;
import framework.record.SceneRecorder;
import framework.translate.Translator;
import net.Client;

public class Manager{
	private static Manager instance;
	
	private Client mClient;
	private Translator mTranslator;
	private Deciders mDeciders;
	private SceneRecorder mSceneRecorder;

	public static void init(Client client){
		instance = new Manager(client);
	}
	
	private Manager(Client client){
		this.mClient = client;
		//初始化翻译机，与client双向绑定
		mTranslator = new Translator(client.obtainMessagePoster());
		mClient.registerObserver(mTranslator);
		
		mSceneRecorder = new SceneRecorder();
		mTranslator.registerObserver(mSceneRecorder);
		
		//初始化决策者，与翻译机双向绑定
		mDeciders = new AlwaysCallDeciders(mTranslator.obtainActionPoster(),mSceneRecorder);
		mTranslator.registerObserver(mDeciders);
		
		mTranslator.registerObserver(new Statistics());
	}

}
