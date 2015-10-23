package org.com.vnp.tinhca;

import android.content.Context;
import android.content.SharedPreferences;

public class Acount {
	boolean check;
	String name;
	String pass;

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	private String PREFS_NAME = "PREFS_NAME";
	private String KEY1 = "KEY1"; // check
	private String KEY2 = "KEY2"; // user
	private String KEY3 = "KEY3"; // pass

	public void getData(Context context) {
		SharedPreferences settings = context
				.getSharedPreferences(PREFS_NAME, 0);
		check = settings.getBoolean(KEY1, false);
		name = settings.getString(KEY2, "");
		pass = settings.getString(KEY3, "");
	}

	public void update(Context context) {
		SharedPreferences settings = context
				.getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();

		editor.putBoolean(KEY1, check);
		editor.putString(KEY2, name);
		editor.putString(KEY3, pass);
		editor.commit();

	}
}
