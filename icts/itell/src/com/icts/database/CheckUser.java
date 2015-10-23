package com.icts.database;

import android.R.bool;
import android.content.Context;
import android.content.SharedPreferences;

public class CheckUser {
	private final String USERID_KEY = "userId";
	private final String UUID_KEY = "uuid";
	private final String CONTACT_KEY = "contact";
	private final String SHARED_NAME = "CheckUserGame";
	private final String ALLOW_GET_CONTACT = "contact";
	
	private final String PUSH_ADD = "push_add_friend";
	private final String PUSH_FRIEND = "push_friend";
	private final String PUSH_ITELL = "push_itell";
	
	private SharedPreferences preferences;
	private String uuid;
	private String userId;
	private boolean contact;

	public CheckUser(Context context) {
		preferences = context.getSharedPreferences(SHARED_NAME,
				Context.MODE_PRIVATE);
		uuid = preferences.getString(UUID_KEY, null);
		userId = preferences.getString(USERID_KEY, null);
		contact = preferences.getBoolean(CONTACT_KEY, true);
	}

	public String getUuid() {
		return uuid;
	}

	public boolean getContact() {
		return contact;
	}

	public boolean addUuid(String toke) {
		uuid = toke;
		SharedPreferences.Editor editor2 = preferences.edit();
		editor2.putString(UUID_KEY, uuid);
		editor2.commit();
		return true;
	}
	
	public boolean updateAllowContact(boolean isAllow) {
		SharedPreferences.Editor editor2 = preferences.edit();
		editor2.putBoolean(ALLOW_GET_CONTACT, isAllow);
		editor2.commit();
		return true;
	}
	
	public boolean getAllowContact() {
		return preferences.getBoolean(ALLOW_GET_CONTACT, true);
	}
	
	public boolean setPushAddFriend(boolean isAllow) {
		SharedPreferences.Editor editor2 = preferences.edit();
		editor2.putBoolean(PUSH_ADD, isAllow);
		editor2.commit();
		return true;
	}
	
	public boolean getPushAdd(){
		return preferences.getBoolean(PUSH_ADD, true);
	}
	
	public boolean setPushFriend(boolean isAllow) {
		SharedPreferences.Editor editor2 = preferences.edit();
		editor2.putBoolean(PUSH_FRIEND, isAllow);
		editor2.commit();
		return true;
	}
	
	public boolean getPushFriend(){
		return preferences.getBoolean(PUSH_FRIEND, true);
	}
	
	public boolean setPushItell(boolean isAllow) {
		SharedPreferences.Editor editor2 = preferences.edit();
		editor2.putBoolean(PUSH_ITELL, isAllow);
		editor2.commit();
		return true;
	}
	
	public boolean getPushItell(){
		return preferences.getBoolean(PUSH_ITELL, true);
	}

	public boolean addContact(boolean toke) {
		contact = toke;
		SharedPreferences.Editor editor2 = preferences.edit();
		editor2.putBoolean(CONTACT_KEY, contact);
		editor2.commit();
		return true;
	}

	public String getUserId() {
		return userId;
	}

	public boolean addserId(String toke) {
		userId = toke;
		SharedPreferences.Editor editor2 = preferences.edit();
		editor2.putString(USERID_KEY, userId);
		editor2.commit();
		return true;
	}

	/**
	 * Reset all data that saved
	 * 
	 * @return
	 */
	public boolean clearData() {
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(USERID_KEY, null);
		editor.putString(UUID_KEY, null);
		editor.commit();
		userId = null;
		uuid = null;
		return true;
	}

}