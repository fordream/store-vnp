/***
 * Copyright (c) 2011 readyState Software Ltd
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package com.icts.map;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;
import com.icts.object.CompanyObject;
import com.icts.object.FriendObject;
import com.icts.object.User;

public class CustomOverlayItem extends OverlayItem {

	protected String mImageURL;
	protected String mComName = null;
	protected boolean isMale = true;
	protected boolean isFriend = true;
	protected boolean isCompanyAcc = false;
	private User mUser;
	private FriendObject mFriend;
	private CompanyObject mCom;
	public CustomOverlayItem(GeoPoint point, User user) {
		super(point, user.getUserNick(), user.getStatus());
		mImageURL = user.getImageUrl();
		mUser = user;
	}
	
	public CustomOverlayItem(GeoPoint point, FriendObject friend) {
		super(point, friend.getFriendNick(), friend.getFriendiTell());
		mImageURL = friend.getFriendAva();
		isCompanyAcc = false;
		isFriend = friend.isFriend();
		isMale = friend.isMale();
		mFriend = friend;
	}
	
	public CustomOverlayItem(GeoPoint point, CompanyObject com) {
		super(point, com.getmName(), com.getmName());
		mImageURL = com.getmAvatar();
		this.mComName = com.getmName();
		isCompanyAcc = true;
		mCom = com;
	}
	
	public CustomOverlayItem(GeoPoint point, String title, String snippet, String imageURL) {
		super(point, title, snippet);
		mImageURL = imageURL;
	}

	public FriendObject getFriend(){
		return mFriend;
	}
	
	public User getUser(){
		return mUser;
	}
	
	public String getImageURL() {
		return mImageURL;
	}

	public void setImageURL(String imageURL) {
		this.mImageURL = imageURL;
	}

	public String getComName() {
		return mComName;
	}

	public void setComName(String mComName) {
		this.mComName = mComName;
	}

	public boolean isMale() {
		return isMale;
	}

	public void setMale(boolean isMale) {
		this.isMale = isMale;
	}

	public boolean isFriend() {
		return isFriend;
	}

	public void setFriend(boolean isFriend) {
		this.isFriend = isFriend;
	}

	public boolean isCompanyAcc() {
		return isCompanyAcc;
	}

	public void setCompanyAcc(boolean isCompanyAcc) {
		this.isCompanyAcc = isCompanyAcc;
	}
	
	public CompanyObject getComObject(){
		return mCom;
	}
	
	
}
