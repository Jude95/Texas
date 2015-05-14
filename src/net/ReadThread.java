package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import util.Log;


class ReadThread extends Thread {
	private static final int TIMEOUT = 100000;
	
	private Client mClient;

	private long mDutyTime;
	
	private boolean live = true;
	
	public ReadThread(Client client){
		this.mClient = client;
		mDutyTime = System.currentTimeMillis();
	}
	

	
	public void finish(){
		live = false;
	}
	
	@Override
	public void run() {
		Log.Log("读取进程启动");
		BufferedReader is = null;
		try {
			is = new BufferedReader(new InputStreamReader(mClient.socket.getInputStream()));
			String line;
			while(live){
				
				if((line = is.readLine())!=null){
					mDutyTime = System.currentTimeMillis();
					mClient.dispatchMessage(line);
				}
				
				if(System.currentTimeMillis()-mDutyTime>TIMEOUT){
					mClient.dispatchMessage("Server time out");
					mClient.finish();
				}
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			Log.Log("读取进程结束");
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
		
	}
	
	
}
