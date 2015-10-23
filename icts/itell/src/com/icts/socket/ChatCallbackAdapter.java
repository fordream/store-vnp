package com.icts.socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public interface ChatCallbackAdapter {
    public void callback(JSONArray data) throws JSONException;
    public void on(String event, JSONObject data);
    public void on(String event, String data);
    public void onMessage(String message);
    public void onMessage(JSONObject json);
    public void onConnect();
    public void onDisconnect();
    public void onConnectFailure(SocketIOException socketIOException);
}
