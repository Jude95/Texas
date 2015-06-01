package net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

import config.Config;
import framework.Manager;
import util.FileUtil;
import util.Log;


public class Client {
	Socket socket;
	public static String ID;
	
	private ReadThread mReadThread;
	private WriteThread mWriteThread;
	private NetLogObserver mNetLogObserver;
	private ArrayList<IMessageObserver> mObservers = new ArrayList<IMessageObserver>();
	
	private Client(String serverIP,int serverPort,String localIP,int localPort,String ID){
		try {
			this.ID = ID;
			socket = new Socket(serverIP,serverPort,InetAddress.getByName(localIP),localPort);
			init(ID);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void init(String ID){
		FileUtil.init(ID);
		Log.init();
		//注册基础通信Log
		registerObserver(mNetLogObserver = new NetLogObserver());
		
		//开启读写线程
		mReadThread = new ReadThread(this);
		mReadThread.start();
		mWriteThread = new WriteThread(this);
		mWriteThread.start();
		
		Manager.init(this);
		
		//注册
		mWriteThread.addMessage("reg: "+ID+" "+Config.NAME+" need_notify"+"\n");
		dispatchMessage("reg");
	}
	
	public void registerObserver(IMessageObserver observer){
		mObservers.add(observer);
	}
	
	public void unRegisterObserver(IMessageObserver observer){
		mObservers.remove(observer);
	}
	
	void dispatchMessage(String msg){
		if(interceptMessage(msg)){
			return;
		}
		for(IMessageObserver obs:mObservers){
			obs.onMessageReceive(msg);
		}
	}
	
	boolean interceptMessage(String msg){
		if(msg.trim().equals("game-over")){
			finish();
			return false;
		}
		return false;
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
	}

}
