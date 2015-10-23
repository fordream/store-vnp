package com.caferhythm.csn.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.XmlDom;
import com.caferhythm.csn.R;
import com.caferhythm.csn.configure.API;
import com.caferhythm.csn.configure.Configure;
import com.caferhythm.csn.connect.Connection;
import com.caferhythm.csn.json.JsonPaser;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class FlashScreenActivity extends Activity {
	public static String uuidString;
	public static String token;
	// view on screen

	// thread
	Context mContext;
	private Thread splashTread;

	AQuery aq;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		setContentView(R.layout.flash_screen);
		if (!haveNetworkConnection()) {
			Toast.makeText(this, getResources().getString(R.string.ms_no_internet), Toast.LENGTH_LONG).show();
			return;
		}
		
	    
		mContext = this;
		SharedPreferences sp = getSharedPreferences(
				Configure.SHARED_PREFERENCES, MODE_PRIVATE);
		if (sp.getString(Configure.UUID_STRING, "").length() < 1) {
			uuidString = UUID.randomUUID().toString();
			uuidString = uuidString.replace("-", "");
			uuidString = uuidString.substring(0, 20);
			sp.edit().putString(Configure.UUID_STRING, uuidString).commit();
		} else {
			uuidString = sp.getString(Configure.UUID_STRING, "");
		}
		

		if (sp.getString(Configure.TOKEN, "").length() > 1) {
			// FlashScreenActivity.token =sp.getString(Configure.TOKEN, "");

			splashTread = new Thread() {
				@Override
				public void run() {
					try {
						synchronized (this) {

							// wait 1 sec
							wait(2000);
						}

					} catch (InterruptedException e) {
					} finally {

						getToken(uuidString);
						// finish();
						// start a new activity
						// stop();
					}
				}
			};

			splashTread.start();
		}

		checkUUID(uuidString);
	}
	public void picasaCb(String url, XmlDom xml, AjaxStatus status){
	    List<XmlDom> entries = xml.tags("item");        
	    List<String> titles = new ArrayList<String>();
	    List<String> dates = new ArrayList<String>();
	    for(XmlDom entry: entries){
	        titles.add(entry.text("title"));
	        dates.add(entry.text("lastBuildDate"));
	        Log.i("test","title :"+entry.text("title") + " date:"+entry.text("lastBuildDate"));
	    }
	}
	private void checkUUID(String uuid) {
		String url = API.API_CHECK_UUID_UNIQUE + uuid;
		Connection.get(url, null, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String arg0) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0);
				if (JsonPaser.PaserUUIDCheck(arg0) == Configure.UUID_REGISTERED) {

					// go to my page
				} else if (JsonPaser.PaserUUIDCheck(arg0) == Configure.UUID_UNREGISTERED) {
					// show register button
					goToRegister();
					// getToken(uuid);
				} else {
					Toast.makeText(getApplicationContext(),
							getResources().getString(R.string.ms_cannot_get_data) + arg0, Toast.LENGTH_LONG)
							.show();
				}
				Log.i("test", "reslult:----:" + arg0);
			}

			@Override
			public void onFailure(Throwable arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onFailure(arg0, arg1);
				Toast.makeText(getApplicationContext(),
						getResources().getString(R.string.ms_cannot_get_data), Toast.LENGTH_LONG)
						.show();
				Log.i("test", "reslult failure:" + arg1);
			}
		});
	}

	public void goToRegister() {
		Intent i = new Intent(mContext, SPtop01Activity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
	}

	// hoangnn get token cho moi lan dang nhat
	private String getToken(String uuid) {
		String result = "";
		Connection.get(API.API_GET_TOKEN + uuid, null,
				new AsyncHttpResponseHandler() {
					public void onSuccess(String arg0) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0);
						Log.e("E", arg0);
						FlashScreenActivity.token = JsonPaser
								.PaserAccesstoken(arg0);
						Log.e("test", "reslult:----:" + arg0);
						Log.e("test", "reslult:----:"
								+ FlashScreenActivity.token);
						Intent i = new Intent();
						i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						i.setClass(mContext, MyPageActivity.class);
						startActivity(i);
					}

					@Override
					public void onFailure(Throwable arg0, String arg1) {
						// TODO Auto-generated method stub
						super.onFailure(arg0, arg1);
						Log.i("test", "reslult failure:" + arg1);
					}
				});
		return result;
	}

	private boolean haveNetworkConnection() {
		boolean haveConnectedWifi = false;
		boolean haveConnectedMobile = false;

		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] netInfo = cm.getAllNetworkInfo();
		for (NetworkInfo ni : netInfo) {
			if (ni.getTypeName().equalsIgnoreCase("WIFI"))
				if (ni.isConnected())
					haveConnectedWifi = true;
			if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
				if (ni.isConnected())
					haveConnectedMobile = true;
		}
		return haveConnectedWifi || haveConnectedMobile;
	}
}
