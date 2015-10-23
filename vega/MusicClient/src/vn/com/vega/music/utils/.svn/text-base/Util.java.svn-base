package vn.com.vega.music.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Util {
	
	private Context mContext;
	
	public Util(Context context){
		mContext = context;
	}
	
	
	public void setSharedPre(String name, String value) {

		SharedPreferences mSharedRefs = mContext
				.getSharedPreferences(name,
						Activity.MODE_PRIVATE);
		Editor editor = mSharedRefs.edit();
		editor.putString(name, value);
		editor.commit();

	}
	public String getSharedPre(String name) {

		SharedPreferences mSharedRefs = null;
		mSharedRefs = mContext.getSharedPreferences(
				name, Activity.MODE_PRIVATE);

		return mSharedRefs.getString(name, "");

	}
	
	public static String intToStringTimeFormat(int time) {
		String strTemp = new String();
		int minutes = time / 60;
		int seconds = time % 60;

		if (minutes < 10)
			strTemp = "0" + Integer.toString(minutes) + ":";
		else
			strTemp = Integer.toString(minutes) + ":";

		if (seconds < 10)
			strTemp = strTemp + "0" + Integer.toString(seconds);
		else
			strTemp = strTemp + Integer.toString(seconds);

		return strTemp;
	}
	
	

}
