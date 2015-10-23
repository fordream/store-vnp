package com.icts.object;

import com.icts.utils.Constant;

/**
 * This class contains info of an user
 * ["{\"retval\":true,\"user_info\":{\"id\":11,\"mobile_num\":\"0923456789\",
 * \"uuid\":\"ccf4766507a1b47bbc321fa995687d6a\",\"nick\":\"trungnd\",\"avatar\":\"\",\"gender\":0,
 * \"itell\":\"i need money!!!!!!!!\",\"itell_policy\":1,\"itell_start\":\"2012-08-30T17:29:45.000Z\",
 * \"itell_status\":1,\"longitude\":\"\",\"latitude\":\"\",\"position_time\":null,\"can_search\":1,\"status\":1,
 * \"last_login\":null,\"created\":\"2012-08-24T17:17:51.000Z\",\"modified\":\"2012-08-30T17:29:45.000Z\"}}"]

 * @author LUONG
 * 
 */
public class User {
	// //{"id":"10","nick":"itellnick","avatar":"","gender":false,"itell":"I Love You.\n","itell_policy":"2"
	// ,"itell_start":"2012-08-25 03:49:27","name":"iTell","birth":"1980-08-08 00:00:00","desc":"","badge_good":"0",
	// "badge_normal":"0","badge_bad":"0"}
	private String mStatus = null;
	private String mUserNick = null;
	private String mImageUrl = null;
	private boolean isMale = false;
	private String mAddress = null;
	private long mBirthday = -1;
	private long mTimeCurrent;
	private String mPhone = null;
	private String mFullName = null;
	private long mStatusUpdateTime = -1;
	private String mUserId;
	private boolean isFriend= false;
	
	public int itellPolicy = Constant.POLICY_ALL;
	public double longitude;
	public double latitude;
	public boolean canSearch = true;
	public String uuid;
	public String desc;
	public int badge_good;
	public int badge_normal;
	public int badge_bad;
	public boolean hide_age =false;
	
	public User() {
	}
	

	public long getmTimeCurrent() {
		return mTimeCurrent;
	}


	public void setmTimeCurrent(long mTimeCurrent) {
		this.mTimeCurrent = mTimeCurrent;
	}


	public User(String username, String status) {
		this.mUserNick = username;
		this.mStatus = status;
	}
	

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getUserId() {
		return mUserId;
	}

	public void setUserId(String mUserId) {
		this.mUserId = mUserId;
	}

	public String getStatus() {
		return mStatus;
	}

	public String getName() {
		return mFullName;
	}

	public void setName(String mFullName) {
		this.mFullName = mFullName;
	}

	public void setStatus(String mStatus) {
		this.mStatus = mStatus;
	}

	public String getUserNick() {
		return mUserNick;
	}

	public void setUserNick(String mUserName) {
		this.mUserNick = mUserName;
	}

	public String getImageUrl() {
		return mImageUrl;
	}

	public void setImageUrl(String mImageUrl) {
		this.mImageUrl = mImageUrl;
	}

	public boolean isMale() {
		return isMale;
	}

	public void setMale(boolean isMale) {
		this.isMale = isMale;
	}
	
	public void setMale(int value) {
		if (value==0){
			this.isMale = true;
		}
		else {
			this.isMale = false;
		}
	}

	public long getBirthday() {
		return mBirthday;
	}

	public void setBirthday(long mBirthday) {
		this.mBirthday = mBirthday;
	}

	public String getPhone() {
		return mPhone;
	}

	public void setPhone(String mPhone) {
		this.mPhone = mPhone;
	}

	public long getStatusUpdateTime() {
		return mStatusUpdateTime;
	}

	public void setStatusUpdateTime(long mStatusUpdateTime) {
		this.mStatusUpdateTime = mStatusUpdateTime;
	}

	public String getAddress() {
		return mAddress;
	}

	public void setAddress(String mAddress) {
		this.mAddress = mAddress;
	}

	public boolean isFriend() {
		return isFriend;
	}

	public void setFriend(boolean isFriend) {
		this.isFriend = isFriend;
	}
	
}
