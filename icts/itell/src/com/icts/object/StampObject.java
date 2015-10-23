package com.icts.object;

import java.util.Comparator;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Stamp object to chat, includes id, code (used to send to server) and image. 
 * With emotion text, image = "  <p>
 * E.g: "stamp_list":[{"id":"1","code":":-==",
 * "image":"http:\/\/icons.iconarchive.com\/icons\/iconscity\/flags\/256\/vietnam-icon.png"}
 * @author Luong
 */
public class StampObject implements Comparable<StampObject>,Comparator<StampObject>,Parcelable {
	private String id;
	private String code;
	private String imageLink;
	private String catId;
	private boolean isFree = true;
	private boolean fromNative = true;
	public StampObject(){
		
	}
	public StampObject(Parcel in){
		id = in.readString();
		code = in.readString();
		imageLink = in.readString();
		catId = in.readString();
		boolean[] b = new boolean[2];
		in.readBooleanArray(b);
		isFree = b[0];
		fromNative = b[1];
	}
	public StampObject(String id,String code){
		this.id = id;
		this.code = code;
	}
	
	public String getCatId() {
		return catId;
	}
	public void setCatId(String catId) {
		this.catId = catId;
	}
	public boolean isFromNative() {
		return fromNative;
	}
	public void setFromNative(boolean fromNative) {
		this.fromNative = fromNative;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getImageLink() {
		return imageLink;
	}
	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}
	public boolean isFree() {
		return isFree;
	}
	public void setFree(boolean isFree) {
		this.isFree = isFree;
	}
	@Override
	public int compare(StampObject object1, StampObject object2) {
		return object1.compareTo(object2);
	}
	@Override
	public int compareTo(StampObject another) {
		if (this.id==null|another.id==null){
			return 0;
		}
		int mID = Integer.parseInt(this.id);
		int otherID = Integer.parseInt(another.id);
		if (mID>otherID){
			return 1;
		}
		else if (mID<otherID) {
			return -1;
		}
		return 0;
	}
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(code);
		dest.writeString(imageLink);
		dest.writeString(catId);
		boolean[] b = new boolean[] {isFree,fromNative};
		dest.writeBooleanArray(b);
	}
	
	public static final Parcelable.Creator<StampObject> CREATOR
		= new Parcelable.Creator<StampObject>() {
		public StampObject createFromParcel(Parcel in) {
			return new StampObject(in);
		}
		
		public StampObject[] newArray(int size) {
			return new StampObject[size];
		}
	};
	
}
