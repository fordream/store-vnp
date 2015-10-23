package com.icts.itel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

import com.icts.socket.ChatCallbackAdapter;
import com.icts.socket.SocketIOException;

public class SocketService extends Service implements ChatCallbackAdapter {
	public static final String USER_ID = "user_id";
	public static final String UUID = "uuid";
	public static final String DATA = "data";
	public static final String EVENT = "event";
	public static final String ERROR = "error";
	private int result = Activity.RESULT_CANCELED;
	
	
	private Messenger outMessenger; 
	
	public SocketService(){
		super();
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		Bundle extras = intent.getExtras();
		if (extras != null) {
		      outMessenger = (Messenger) extras.get("MESSENGER");
		      result = Activity.RESULT_OK;
			  Message backMsg = Message.obtain(); 
		      backMsg.arg1 = result;
		      Bundle bundle = new Bundle();
		      bundle.putString(DATA, String.format("hehe %s %s","1","a"));
		      backMsg.setData(bundle);
		      try {
		        outMessenger.send(backMsg);
		        Log.i("NDT", "send");
		      } catch (android.os.RemoteException e1) {
		        Log.w(getClass().getName(), "Exception sending message", e1);
		      }
		    }
	}
	@Override
	public IBinder onBind(Intent intent) {
		Bundle extras = intent.getExtras();
		// Get messenger from the Activity
	    if (extras != null) {
	      outMessenger = (Messenger) extras.get("MESSENGER");
	      result = Activity.RESULT_OK;
		  Message backMsg = Message.obtain(); 
	      backMsg.arg1 = result;
	      Bundle bundle = new Bundle();
	      bundle.putString(DATA, String.format("hehe %s %s","1","a"));
	      backMsg.setData(bundle);
	      try {
	        outMessenger.send(backMsg);
	        Log.i("NDT", "send");
	      } catch (android.os.RemoteException e1) {
	        Log.w(getClass().getName(), "Exception sending message", e1);
	      }
	    }
	    // Return our messenger to the Activity to get commands
	    return mBinder;
	}

	 /**
     * This is the object that receives interactions from clients.  See RemoteService
     * for a more complete example.
     */
    private final IBinder mBinder = new Binder() {
        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply,
                        int flags) throws RemoteException {
        return super.onTransact(code, data, reply, flags);
        }
    };
	@Override
	public void callback(JSONArray data) throws JSONException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void on(String event, JSONObject data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void on(String event, String data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMessage(String message) {
		
	}

	@Override
	public void onMessage(JSONObject json) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDisconnect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnectFailure(SocketIOException socketIOException) {
		// TODO Auto-generated method stub
		
	}

}
