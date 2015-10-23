package vn.com.vega.music.database;

import vn.com.vega.music.utils.Const;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedProperties {
	private Context mContext;
	private String mStoreName = Const.PREFERNCE_NAME;

	public SharedProperties(Context context) {
		mContext = context;
	}

	public SharedProperties(Context context, String name) {
		mContext = context;
		mStoreName = name;
	}

	public void setSharedPre(String name, String value) {
		SharedPreferences mSharedRefs = mContext.getSharedPreferences(mStoreName, Activity.MODE_PRIVATE);
		Editor editor = mSharedRefs.edit();
		editor.putString(name, value);
		editor.commit();
	}

	public String getSharedPre(String name) {
		SharedPreferences mSharedRefs = null;
		mSharedRefs = mContext.getSharedPreferences(mStoreName, Activity.MODE_PRIVATE);
		return mSharedRefs.getString(name, "");
	}
}
