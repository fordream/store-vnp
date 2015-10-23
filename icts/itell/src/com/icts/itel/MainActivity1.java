package com.icts.itel;

import java.net.URI;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.maps.MapActivity;
import com.icts.app.ItelApplication;
import com.icts.control.SwipAdapter;
import com.icts.control.SwipAdapter.OnItemSlideChangedListener;
import com.icts.control.SwipAdapter.OnItemSlideLeftListener;
import com.icts.control.SwipAdapter.OnItemSlideRightListener;
import com.icts.control.SwipListAdapter;
import com.icts.control.SwipListView;
import com.icts.object.FriendObject;
import com.icts.object.MessageObject;
import com.icts.socket.ChatCallbackAdapter;
import com.icts.socket.ChatConnect;
import com.icts.socket.IOAcknowledge;
import com.icts.socket.SocketIOClient;
import com.icts.socket.SocketIOException;
import com.icts.utils.Constant;
import com.icts.utils.CountTime;
import com.icts.utils.NewMessageCache;
import com.icts.utils.Utils;

public class MainActivity1 extends MapActivity implements ChatCallbackAdapter {
	private Button btnFriend;
	private Button btnSetting;
	private Button btnItell;
	private Button btnMap;
	private Button btnChat;
	private LinearLayout llMain;
	CountTime count;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		Constant.SCREEN_WIDTH = displaymetrics.widthPixels;
		test2();
	}
	ChatConnect chatConnect;
	private void test2(){
		setContentView(R.layout.chat_test);
		final TextView tv =(TextView) findViewById(R.id.textView1);
		final Button btn1 =(Button) findViewById(R.id.btn1);
		final EditText ed1 =(EditText) findViewById(R.id.ed1);
		ed1.setText("12");
		final EditText ed2 =(EditText) findViewById(R.id.ed2);
		ed2.setText("hello");
		btn1.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				String receiverID = ed1.getText().toString();
				String message = ed2.getText().toString();
				chatConnect.sendMessage(String.valueOf(ItelApplication.user_id),receiverID,message);
			}
		});
		boolean c = Utils.getServerInfo(ItelApplication.user_id, ItelApplication.uuid);
		String s = ItelApplication.server;
		s = "http://"+s.trim();
		chatConnect = new ChatConnect(this,s);
		chatConnect.start();
	}
	
	Handler mHandler = new Handler(){
		@Override
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
		};
	};
	private void test3(){
		setContentView(R.layout.chat_test);
		final TextView tv =(TextView) findViewById(R.id.textView1);
		final Button btn1 =(Button) findViewById(R.id.btn1);
		final EditText ed1 =(EditText) findViewById(R.id.ed1);
		ed1.setText("12");
		final EditText ed2 =(EditText) findViewById(R.id.ed2);
		ed2.setText("hello");
		btn1.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				String receiverID = ed1.getText().toString();
				String message = ed2.getText().toString();
				chatConnect.sendMessage(String.valueOf(ItelApplication.user_id),receiverID,message);
			}
		});
		boolean c = Utils.getServerInfo(ItelApplication.user_id, ItelApplication.uuid);
		String s = ItelApplication.server;
		Log.i("NDT","address "+s);
		s = "http://"+s.trim();
		try {
			SocketIOClient socketIO = new SocketIOClient(URI.create(s), new SocketIOClient.Handler() {
				
				@Override
				public void onError(Exception error) {
					Log.i("NDT","error "+error.getMessage());
				}
				
				@Override
				public void onDisconnect(int code, String reason) {
					Log.i("NDT","disconnect");
				}
				
				@Override
				public void onConnect() {
					Log.i("NDT","onConnect");
					
				}
				
				@Override
				public void on(String event, JSONArray arguments) {
					Log.i("NDT","on");
				}
			});
			socketIO.connect();
			JSONArray jsonArr = new JSONArray();
			JSONObject json = new JSONObject();
			json.put("user_id", String.valueOf(ItelApplication.user_id));
			json.put("uuid", ItelApplication.uuid);
			jsonArr.put(json);
			socketIO.emit("join_server", jsonArr);
		} catch (Exception e) {
			Log.i("NDT", "error");
			e.printStackTrace();
		}
		//chatConnect = new ChatConnect(this,s);
		//chatConnect.start();
	}
	IOAcknowledge joinAck = new IOAcknowledge() {
		
		@Override
		public void ack(Object... args) {
			Log.i("NDT", "ack join callback");
			
		}
	};
	
	IOAcknowledge sendAck = new IOAcknowledge() {
		
		@Override
		public void ack(Object... args) {
			Log.i("NDT", "ack join callback");
			
		}
	};
	
	
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean onKeyDown(int pKeyCode, KeyEvent pEvent) {
		if (pKeyCode == KeyEvent.KEYCODE_BACK
				&& pEvent.getAction() == KeyEvent.ACTION_DOWN) {
			if (count!=null){
				count.cancel();
			}
			//chatConnect.logout();
			return true;
		}
		return super.onKeyDown(pKeyCode, pEvent);
	}
	
	void ListViewTest(){
		setContentView(R.layout.test);
		final SwipListView lv = null; //= (SwipListView) findViewById(R.id.lv);
        
        /*String[] arrays= new String[50];
        
        for(int i = 0; i < 50; i++){
            arrays[i] = "tien con nha ba su "+i+" "+ i;
        }*/
        
        ArrayList<FriendObject> arr = 
				Utils.getFriendsList(ItelApplication.user_id,
											  ItelApplication.uuid, 0, 0);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, textViewResourceId)
        //SwipAdapter<String> adapter = new SwipAdapter<String>(this, R.layout.test_item, R.id.itemlv, arrays);
        SwipListAdapter adapter = new  SwipListAdapter(this, R.layout.map_list_item, R.id.map_list_tv_name, arr);
        adapter.setOnItemSlideLeftListener(new OnItemSlideLeftListener() {

            public void onItemSlideLeft(View v) {
                // TODO Auto-generated method stub
                Log.d("hieuth", "Di chuyen sang ben trai");
               /* ViewHolder holder = (ViewHolder)v.getTag();
                
                LayoutParams para = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
                para.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                para.addRule(RelativeLayout.CENTER_VERTICAL);
                ((TextView)holder.vText).setText("PROFILE");*/
            }

			public void onItemSlideLeft(View v, float offset) {
				Log.d("hieuth", "Di chuyen sang ben trai1"+offset);
				
				
			}
        });
        
        adapter.setOnItemSlideRightListener(new OnItemSlideRightListener() {

            public void onItemSlideRight(View v) {
                Log.d("hieuth", "Di chuyen sang ben phai");
                /*ViewHolder holder = (ViewHolder)v.getTag();
                LayoutParams para = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
                para.leftMargin = 10;
                para.leftMargin = 10;
                para.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                para.addRule(RelativeLayout.CENTER_VERTICAL);
                
                holder.vText.setLayoutParams(para);
                holder.vText.requestLayout();
                ((TextView)holder.vText).setText("CHAT");*/
            }

			public void onItemSlideRight(View v, float offset) {
				 Log.d("hieuth", "Di chuyen sang ben phai1 "+offset);
				 
			}
        });
        
        adapter.setOnItemSlideChangedListener(new OnItemSlideChangedListener() {

            public void onItemSlideChanged(View v, int direction) {
                if(direction == SwipAdapter.LEFT){
                    Log.d("hieuth", "Chuyen den man hinh chat");
                   
                }
                if(direction == SwipAdapter.RIGHT){
                    Log.d("hieuth", "Chuyen den man hinh profile");
                   
                }
                lv.swip(lv.getPositionForView(v),direction);
            }

			public void onItemSlideChanged(View v, int direction, boolean back) {
				if (direction==SwipAdapter.LEFT){
					
				}
				else if (direction==SwipAdapter.RIGHT){
					
				} 
			}
            
        });
        lv.setAdapter(adapter);
        lv.setClipChildren(false);
        lv.setClipToPadding(false);
        lv.setDividerHeight(0);
        
       
//		initView();
//		setEvent();
//		initData();
	}

	boolean join = false;
	
	@Override
	public void callback(JSONArray data) throws JSONException {
		if (join){
			Log.i("NDT", "callback1 join" + data.toString());
		}
		else {
			Log.i("NDT", "callback1 " + data.toString());
		}
	}
	@Override
	public void on(String event, JSONObject data) {
		Log.i("NDT","on 1"+ event+" "+data.toString());
		 try {
	            if (event.equals("join_server")) {
	               /* JSONArray names = obj.names();
	                String str = "";
	                for (int i=0; i < names.length(); i++) {
	                    if (i != 0)
	                        str += ", ";
	                    str += names.getString(i);
	                }*/
	            }
	            else if (event.equals("logout")){
	            	String user = data.getString("user");
	            	if (user.equalsIgnoreCase(String.valueOf(ItelApplication.user_id))){
	            		join = false;
	            		//onConnect();
	            		chatConnect.getSocket().reconnect();
	            	}
	            }
	            else if (event.equalsIgnoreCase("message")){
	            	MessageObject message = Utils.getMessage(data);
	            	NewMessageCache.addMessageToCache(message, 
	            									  String.valueOf(ItelApplication.user_id));
//	            	if (BuildConfig.DEBUG){
//	            		Log.i("NDT","new message "+NewMessageCache.getCountNewMessage());
//	            	}
	            }
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
		
	}
	@Override
	public void onMessage(String message) {
		Log.i("NDT", "on message 1");
		
	}
	@Override
	public void onMessage(JSONObject json) {
		Log.i("NDT", "on message 2");
		
	}
	
	/*json.put("user_id", "11");
    json.put("uuid", "ccf4766507a1b47bbc321fa995687d6a");*/
   /* json.put("user_id", "12");
    json.put("uuid", "26234213123d6f182a46e615ac0d8a55");*/
	@Override
	public void onConnect() {
		Log.i("NDT", "onConnect1");
		if (!join){
			chatConnect.joinServer(String.valueOf(ItelApplication.user_id),
							   ItelApplication.uuid,joinAck);
		}
		join = true;
	}
	@Override
	public void onDisconnect() {
		join = false;
	}
	@Override
	public void onConnectFailure(SocketIOException socketIOException) {
//		if (BuildConfig.DEBUG){
//			if (join){
//				Log.i("NDT","fail1 join "+socketIOException.getMessage());
//			}
//			else {
//				Log.i("NDT","fail1 "+socketIOException.getMessage());
//			}
//		}
		
	}
	
	private void clear(){
		join = false;
	}
	@Override
	public void on(String event, String data) {
		
	}

}
