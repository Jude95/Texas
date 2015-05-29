package framework;

import java.util.Random;

import net.Client;
import algorithms.statistics.HandStatistics;
import config.Config;
import framework.deciders.AlgorithmDeciders;
import framework.deciders.AlwaysAllinDeciders;
import framework.deciders.AlwaysCallDeciders;
import framework.deciders.AlwaysFlodDeciders;
import framework.deciders.Deciders;
import framework.deciders.HandAllinDeciders;
import framework.record.SceneRecorder;
import framework.translate.Translator;

public class Manager {
	
	private static Manager instance;
	private Client mClient;
	private Translator mTranslator;
	private SceneRecorder mSceneRecorder;

	public static void init(Client client) {
		instance = new Manager(client);
	}

	private Manager(Client client) {
		this.mClient = client;
		// 初始化翻译机，与client双向绑定
		mTranslator = new Translator(client.obtainMessagePoster());
		mClient.registerObserver(mTranslator);

		
		//初始化统计记录器
		mTranslator.registerObserver(HandStatistics.getInstance());

		// 初始化场景记录器

		mSceneRecorder = new SceneRecorder();
		mTranslator.registerObserver(mSceneRecorder);

		// 初始化决策者，与翻译机双向绑定
		mTranslator.registerObserver(birth());

	}

	private Deciders birth() {

//		switch (Integer.parseInt(Client.ID)) {
//		case 1111:
//			Config.NAME = "CallGod";
//			return new AlwaysCallDeciders(mTranslator.obtainActionPoster(),
//					mSceneRecorder);
//		case 2222:
//			Config.NAME = "FlodFather";
//			return new AlwaysFlodDeciders(mTranslator.obtainActionPoster(),
//					mSceneRecorder);
//		case 3333:
//			Config.NAME = "HandDog";
//			return new HandAllinDeciders(mTranslator.obtainActionPoster(),
//					mSceneRecorder);
//		case 4444:
//			Config.NAME = "CallGod";
//			return new AlwaysCallDeciders(mTranslator.obtainActionPoster(),
//					mSceneRecorder);
//		case 5555:
//			Config.NAME = "FlodFather";
//			return new AlwaysFlodDeciders(mTranslator.obtainActionPoster(),
//					mSceneRecorder);
//		case 6666:
//			Config.NAME = "HandDog";
//			return new HandAllinDeciders(mTranslator.obtainActionPoster(),
//					mSceneRecorder);
//		case 7777:
//			Config.NAME = "AllinBoss";
//			return new AlwaysAllinDeciders(mTranslator.obtainActionPoster(),
//					mSceneRecorder);
//		case 8888:
			Config.NAME = "Algorithm";
			return new AlgorithmDeciders(mTranslator.obtainActionPoster(),
					mSceneRecorder);
//		default:
//			Config.NAME = "AllinBoss";
//			return new AlwaysAllinDeciders(mTranslator.obtainActionPoster(),
//					mSceneRecorder);
//		}
	}
}
