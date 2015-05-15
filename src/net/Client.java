package net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

import config.Config;
import framework.Manager;
import util.Log;


public class Client {
	Socket socket;
	String ID;
	
	private Manager mManager;
	private ReadThread mReadThread;
	private WriteThread mWriteThread;
	private NetLogObserver mNetLogObserver;
	private ArrayList<IMessageObserver> mObservers = new ArrayList<IMessageObserver>();
	
	private Client(String serverIP,int serverPort,String localIP,int localPort,String ID){
		this.ID = ID;
		try {
			socket = new Socket(serverIP,serverPort,InetAddress.getByName(localIP),localPort);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void init(){
		mNetLogObserver = new NetLogObserver();
		registerObserver(mNetLogObserver);
		Log.setDefaultDir(String.format(Config.LogDir, ID));
		mReadThread = new ReadThread(this);
		mReadThread.start();
		mWriteThread = new WriteThread(this);
		mWriteThread.start();
		mWriteThread.addMessage("reg: "+ID+" "+Config.NAME+"\n");
		mManager = new Manager(this);
	}
	
	public void registerObserver(IMessageObserver observer){
		mObservers.add(observer);
	}
	
	public void unRegisterObserver(IMessageObserver observer){
		mObservers.remove(observer);
	}
	
	void dispatchMessage(String msg){
		for(IMessageObserver obs:mObservers){
			obs.onMessageReceive(msg);
		}
	}
	
	public IMessagePoster obtainMessagePoster(){
		return new IMessagePoster() {
			@Override
			public void send(String msg) {
				mNetLogObserver.onMessageReceive("output/\n"+msg+"\n/output");
				mWriteThread.addMessage(msg);
			}
		};
	}

	public void finish(){
		try {
			socket.close();
			mWriteThread.finish();
			mReadThread.finish();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Client client = new Client(args[0], Integer.parseInt(args[1]), args[2], Integer.parseInt(args[3]), args[4]);
		client.init();
	}

}
