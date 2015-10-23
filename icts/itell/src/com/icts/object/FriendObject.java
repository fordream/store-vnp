package com.icts.object;

import java.util.Date;

import com.icts.utils.Utils;

/**
 * This class contains info of an friend
 * 
 * @author LUONG
 * 
 */
public class FriendObject extends Object {
	private int mFriendID = 0;
	private String mFriendNick = null;
	private String mFriendAva = null;
	// default true: male
	private boolean mFriendGender = true;

	private double mFriendLat = -1;
	private double mFriendLong = -1;
	private String mFriendiTell = null;
	private long mFriendStatTime = -1;
	private String mFriendAddress = null;
	private boolean isFriend = false;
	private boolean restrict_pub = false;
	public double distance;
	public String mMail;
	public String mNumberMobile;
	public String mDesc;
	public String uuid;

	public FriendObject() {
	}

	public boolean isMale() {
		return mFriendGender;
	}

	public void setMale(boolean isMale) {
		this.mFriendGender = isMale;
	}

	public String getmMail() {
		return mMail;
	}

	public void setmMail(String mMail) {
		this.mMail = mMail;
	}

	public String getmNumberMobile() {
		return mNumberMobile;
	}

	public void setmNumberMobile(String mNumberMobile) {
		this.mNumberMobile = mNumberMobile;
	}

	public int getFriendID() {
		return mFriendID;
	}

	public void setFriendID(int mFriendID) {
		this.mFriendID = mFriendID;
	}
	
	public void setFriendID(String mFriendID) {
		try{
			this.mFriendID = Integer.parseInt(mFriendID);
		}
		catch(Exception e){
			this.mFriendID = 0;
		}
	}

	public String getFriendNick() {
		return mFriendNick;
	}

	public void setFriendNick(String mFriendNick) {
		this.mFriendNick = mFriendNick;
	}

	public String getFriendAva() {
		return mFriendAva;
	}

	public void setFriendAva(String mFriendAva) {
		this.mFriendAva = mFriendAva;
	}

	public double getFriendLat() {
		return mFriendLat;
	}

	public void setFriendLat(double mFriendLat) {
		this.mFriendLat = mFriendLat;
	}

	public double getFriendLong() {
		return mFriendLong;
	}

	public void setFriendLong(double mFriendLong) {
		this.mFriendLong = mFriendLong;
	}

	public String getFriendiTell() {
		return mFriendiTell;
	}

	public void setFriendiTell(String mFriendiTell) {
		this.mFriendiTell = mFriendiTell;
	}

	public long getFriendStatTime() {
		return mFriendStatTime;
	}

	public void setFriendStatTime(long mFriendStatTime) {
		this.mFriendStatTime = mFriendStatTime;
	}

	/**
	 * 2012-09-09 01:21:41
	 * 
	 * @param mFriendStatTime
	 */
	public void setFriendStatTime(String mFriendStatTime) {
		// long date = Date.parse(mFriendStatTime);
		Date date = Utils.convertStringToDate(mFriendStatTime,
				"yyyy-MM-dd HH:mm:ss");
		if (date != null) {
			this.mFriendStatTime = date.getTime();// date.getTime();
		} else {
			this.mFriendStatTime = -1;
		}
	}

	public boolean isRestrict_pub() {
		return restrict_pub;
	}

	public void setRestrict_pub(boolean restrict_pub) {
		this.restrict_pub = restrict_pub;
	}

	public String getFriendAddress() {
		return mFriendAddress;
	}

	public void setFriendAddress(String mFriendAddress) {
		this.mFriendAddress = mFriendAddress;
	}

	public boolean isFriend() {
		return isFriend;
	}

	public void setFriend(boolean isFriend) {
		this.isFriend = isFriend;
	}

}
