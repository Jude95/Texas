package net;

import util.Log;

public class NetLogObserver implements IMessageObserver {

	@Override
	public void onMessageReceive(String msg) {
		Log.Log("NetLog", msg);
	}

}
