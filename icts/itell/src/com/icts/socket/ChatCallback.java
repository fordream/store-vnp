package com.icts.socket;

import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class ChatCallback implements IOCallback, IOAcknowledge {
	public boolean isConnect = false;
	public boolean isError = false;
	public String messageError;
    private ChatCallbackAdapter callback;
    //private Handler mHandler;
    public ChatCallback(ChatCallbackAdapter callback) {
        this.callback = callback;
    }

	@Override
	public void ack(Object... data) {
        try {
        	if (callback!=null){
        		callback.callback(new JSONArray(Arrays.asList(data)));
        	}
			/* if (mHandler!=null){
		        	sendMessage("ack", "json array",null, data);
		     }*/
		} catch (JSONException e) {
			e.printStackTrace();
			Log.i("NDT", "error in ack");
			onError(new SocketIOException(
					"Exception was thrown in on(String, JSONObject[]).\n"
							+ e.toString(), e));
		}
    }

    @Override
    public void on(String event, IOAcknowledge ack, Object... data) {
        //callback.on(event, (JSONObject) data[0]);
    	Log.i("NDT","on data "+event+" "+data[0].toString());
    	JSONObject obj;
		try {
			obj = new JSONObject(data[0].toString());
			if (callback!=null){
				callback.on(event, obj);
			}
			/*if (mHandler!=null){
	        	sendMessage("on", "json",event, obj);
	        }*/
		} catch (JSONException e) {
			e.printStackTrace();
			onError(new SocketIOException(
					"Exception was thrown in on(String, JSONObject[]).\n"
							+ e.toString(), e));
		}
    }

    @Override
    public void onMessage(String message, IOAcknowledge ack) {
    	if (callback!=null){
    		callback.onMessage(message);
    	}
        /*if (mHandler!=null){
        	sendMessage("onMessage", "String","", message);
        }*/
    }

    @Override
    public void onMessage(JSONObject json, IOAcknowledge ack) {
    	if (callback!=null){
    		callback.onMessage(json);
    	}
        /*if (mHandler!=null){
        	sendMessage("onMessage", "json",null, json);
        }*/
    }

    @Override
    public void onConnect() {
    	if (callback!=null){
    		callback.onConnect();
    	}
        isConnect = true;
        /*if (mHandler!=null){
        	sendMessage("onConnect", null,null, null);
        }*/
    }

    @Override
    public void onDisconnect() {
    	if (callback!=null){
    		callback.onDisconnect();
    	}
        isConnect = false;
        /*if (mHandler!=null){
        	sendMessage("onDisconnect", null,null, null);
        }*/
    }

	@Override
	public void onError(SocketIOException socketIOException) {
		socketIOException.printStackTrace();
		isError = true;
		messageError = socketIOException.getMessage();
		if (callback!=null){
			callback.onConnectFailure(socketIOException);
		}
        /*if (mHandler!=null){
        	sendMessage("error", null,null, socketIOException.getMessage());
        }*/
	}

	
	public void setCallBackAdapter(ChatCallbackAdapter callback){
		this.callback = callback;
	}
	
	public ChatCallbackAdapter getCallBackAdapter(){
		return callback;
	}
	
	/*public void setHandler(Handler handler){
		this.mHandler = handler;
	}
	
	public Handler getHandler(){
		return mHandler;
	}
	
	public void removeHandler(){
		this.mHandler = null;
	}
	
	private void sendMessage(String func,String type, String event,Object... obj){
		Message backMsg = Message.obtain();
		backMsg.arg1 = Activity.RESULT_OK;
		backMsg.obj = obj;
	    Bundle bundle = new Bundle();
	    bundle.putString(FUNC, func);
	    bundle.putString(EVENT, event);
	    bundle.putString(TYPE, type);
	    backMsg.setData(bundle);
	    mHandler.sendMessage(backMsg);
	}*/
    
}
