package com.icts.app;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.widget.LinearLayout;

import com.google.android.maps.MapView;
import com.icts.object.FriendObject;
import com.icts.object.MessageObject;
import com.icts.object.User;
import com.icts.socket.ChatCallbackAdapter;
import com.icts.socket.ChatConnect;
import com.icts.utils.Constant;
import com.icts.utils.Constant.ViewMode;
import com.icts.utils.CountDownTime;
import com.icts.utils.Utils;

public class ItelApplication extends Application {
	public static int numMessages;
	public static String server;
	public static String serverInfo;
	public static int port;
	public static String ANDROID_ID; 
	public static int user_id;// = 11;
	public static String uuid;// = "ccf4766507a1b47bbc321fa995687d6a";
	public static long timeStamp;
	public static int err_code;
	public static String err_message = null;
	public static int CACHE_SIZE;
	public static FriendObject friendChat = null;
	public static User user;
	public static ViewMode currentView = ViewMode.ITELL;
	public static ViewMode lastView = ViewMode.ITELL;
	public static Object obj = null;
	private static ChatConnect chatConnect;
	public static CountDownTime count;
	public static boolean isSetTime = false;
	public static LinearLayout holderView;
	public static MapView mapView;
	public static HashMap<String, String> stampCache = new HashMap<String, String>();
	public static ExecutorService executor;
	/**
	 * String chua id
	 * Integer timestamp
	 */
	public static Map<String, Map<Long, MessageObject>> message;// = new HashMap<String, HashMap<Integer,MessageObject>>() ;
	@Override
	public void onCreate() {
		super.onCreate();
		message = new HashMap<String, Map<Long,MessageObject>>();
		final int memClass = ((ActivityManager) getSystemService(
		            Context.ACTIVITY_SERVICE)).getMemoryClass();

		// Use 1/8th of the available memory for this memory cache.
		CACHE_SIZE= 1024 * 1024 * memClass / 8;
		count = new CountDownTime(0, null);
		count.start();
		executor = Executors.newFixedThreadPool(Constant.MAX_EXEC_THREAD);
	}
	
	public static void submit(Runnable task){
		if (executor==null)
			executor = Executors.newFixedThreadPool(Constant.MAX_EXEC_THREAD);
		if (executor!=null){
			executor.submit(task);
		}
	}
	
	
	public static void killTask(ExecutorService pool){
		if (pool == null){
			return;
		}
		 pool.shutdown(); 
		 // Disable new tasks from being submitted   
		 try {     
			 // Wait a while for existing tasks to terminate     
			 if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {       
				 pool.shutdownNow(); 
				 // Cancel currently executing tasks       
				 // Wait a while for tasks to respond to being cancelled       
				 if (!pool.awaitTermination(60, TimeUnit.SECONDS))           
					 System.err.println("Pool did not terminate");     
				 }   
			} 
		 catch (InterruptedException ie) {     
				 // (Re-)Cancel if current thread also interrupted     
				 pool.shutdownNow();     
				 // Preserve interrupt status     
				 Thread.currentThread().interrupt();   
		}
	}
	
	public synchronized static boolean initSocket(ChatCallbackAdapter chatCallBackAdapter){
		boolean c = Utils.getServerInfo(ItelApplication.user_id, ItelApplication.uuid);
		if (!c){
			return false;
		}
		String s = ItelApplication.server;
		if (s!=null){
			s = "http://"+s.trim();
			//if ((chatConnect==null)||
			//	((chatConnect!=null)&&(!chatConnect.getSocket().isConnected()))){
				chatConnect = new ChatConnect(chatCallBackAdapter,s);
				chatConnect.start();
			//}
		}
		return true;
	}
	
	public static void setTime(long userTIme){
		ItelApplication.count.setTimeServer(ItelApplication.timeStamp*1000);
		ItelApplication.count.setTimeUser(userTIme);
		ItelApplication.count.calculate();
		ItelApplication.isSetTime = true;
	}
	public static ChatConnect getChatConnect(){
		return chatConnect;
	}
	public static void setAdapter(ChatCallbackAdapter adapter){
		if (chatConnect!=null){
			chatConnect.setCallback(adapter);
		}
	}
}
