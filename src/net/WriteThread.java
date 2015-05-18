package net;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;

import util.Log;

class WriteThread extends Thread {
	private Queue<String> mMessages;
	private PrintWriter mOutputStream;
	private boolean live = true;
	
	public WriteThread(Client client) {
		mMessages = new LinkedList<String>();
		try {
			mOutputStream = new PrintWriter(client.socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addMessage(String msg){
		mMessages.offer(msg);
		synchronized (this) {
			this.notify();
		}
	}
	
	public void finish(){
		live = false;
		synchronized (this) {
			this.notify();
		}
	}
	
	public void run() {
		Log.Log("写入进程启动");
		while(live){
			
			String msg = null;
			while((msg = mMessages.poll())!=null){
				mOutputStream.print(msg);
				mOutputStream.flush();
			}
			
			synchronized (this) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		mOutputStream.close();
		Log.Log("写入进程结束");
	}

}
