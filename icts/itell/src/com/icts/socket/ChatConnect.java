package com.icts.socket;

import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;
import android.util.Log;

public class ChatConnect extends Thread {
    private SocketIO socket;
    private ChatCallback callback;
    private String address;
    public ChatConnect(ChatCallbackAdapter callback,String server) {
        this.callback = new ChatCallback(callback);
        this.address = server;
    }
    
    @Override
    public void run() {
        try {
        	socket = new SocketIO(address, callback);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Send message from sender to receiver using emit
     * @param sender: id of sender
     * @param receiver: id of receiver
     * @param message: message will be sent
     */
    public void sendMessage(String sender,String receiver,String message) {
        try {
            JSONObject json = new JSONObject();
            json.put("sender", sender);
            json.put("receiver", receiver);
            json.put("msg", message);
            Log.i("NDT",json.toString());
            socket.emit("message",callback, json.toString());
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Send message from sender to receiver using emit
     * @param sender: id of sender
     * @param receiver: id of receiver
     * @param message: message will be sent
     */
    public void sendMessage(String sender,String receiver,String message,IOAcknowledge io) {
        try {
            JSONObject json = new JSONObject();
            json.put("sender", sender);
            json.put("receiver", receiver);
            json.put("msg", message);
            socket.emit("message",io, json.toString());
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }
    
    
    public void logout(){
    	if (socket!=null){
    		if (socket.isConnected()){
    			socket.disconnect();
    		}
    	}
    	if (callback!=null){
    		callback.setCallBackAdapter(null);
    	}
    }
    /**
     * Send message from sender to receiver using emit
     * @param sender: id of sender
     * @param receiver: id of receiver
     * @param message: message will be sent
     */
    public void sendMessage(int sender,int receiver,String message) {
        try {
            JSONObject json = new JSONObject();
            json.put("sender", sender);
            json.put("receiver", receiver);
            json.put("msg", message);
            Log.i("NDT",json.toString());
            socket.emit("message",callback, json.toString());
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Join server after get server info using emit
     * <p><b>Note</b> only send json.toString()
     * @param userId
     * @param uuid 
     */
    public void joinServer(String userId,String uuid,IOAcknowledge ack) {
        try {
        	Log.i("NDT", "join");
            JSONObject json = new JSONObject();
            json.put("user_id", userId);
            json.put("uuid", uuid);
            System.out.print(json.toString());
            socket.emit("join_server", ack, json.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Join server after get server info using emit
     * <p><b>Note</b> only send json.toString()
     * @param userId
     * @param uuid 
     */
    public void joinServer(String userId,String uuid) {
        try {
        	Log.i("NDT", "join");
            JSONObject json = new JSONObject();
            json.put("user_id", userId);
            json.put("uuid", uuid);
            System.out.print(json.toString());
            socket.emit("join_server", callback, json.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void setCallback(ChatCallbackAdapter callbackAdapter){
    	synchronized (callback) {
    		callback.setCallBackAdapter(callbackAdapter);
		}
    	
    }
    
    public SocketIO getSocket(){
    	return socket;
    }
}
