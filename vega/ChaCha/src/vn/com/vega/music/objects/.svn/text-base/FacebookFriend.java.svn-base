package vn.com.vega.music.objects;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class FacebookFriend implements Parcelable {
	
	public final String TAG = "Facebook Friend Object";
	public String facebookID;
	public String facebookName;
	private Boolean isChecked;
	
	public String getFacebookID() {
		return facebookID;
	}

	public void setFacebookID(String facebookID) {
		this.facebookID = facebookID;
	}

	public String getFacebookName() {
		return facebookName;
	}

	public void setFacebookName(String facebookName) {
		this.facebookName = facebookName;
	}
	
	public FacebookFriend(){
		isChecked = false;
	}
	
	public FacebookFriend(Parcel p) {
		facebookID = p.readString();
		facebookName = p.readString();
		isChecked = (p.readByte() == 1);
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		Log.d(TAG, "describeContents");
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		Log.d(TAG, "writeToParcel");
		dest.writeString(facebookID);
		dest.writeString(facebookName);
		dest.writeByte((byte)(isChecked ? 1 : 0));
	}
	
	public Boolean getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(Boolean isChecked) {
		this.isChecked = isChecked;
	}

	public static final Parcelable.Creator<FacebookFriend> CREATOR = new Creator<FacebookFriend>() {
		
		@Override
		public FacebookFriend[] newArray(int size) {
			// TODO Auto-generated method stub
			return new FacebookFriend[size];
		}
		
		@Override
		public FacebookFriend createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new FacebookFriend(source);
		}
	};
	
}
