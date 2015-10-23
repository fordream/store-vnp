package com.icts.object;

import java.util.Comparator;
import android.os.Parcel;
import android.os.Parcelable;

public class MessageObject implements Comparable<MessageObject>,Comparator<MessageObject>,Parcelable {
	private String receiverID;
	private String senderID;
	private String message;
	private long timestamp;
	private boolean isStamp = false;
	
	
	public MessageObject(){
		
	}
	/**
	 * Get object from bundle
	 * @param in
	 */
	public MessageObject(Parcel in){
		receiverID = in.readString();
		senderID = in.readString();
		message = in.readString();
		timestamp = in.readLong();
		boolean[] b= new boolean[1];
		in.readBooleanArray(b);
		isStamp = b[0];
	}
	public MessageObject(String recever,String sender,String message,long timestamp){
		this.receiverID = recever;
		this.senderID = sender;
		this.message = message;
		this.timestamp = timestamp;
	}
	
	public MessageObject(String recever,String sender,String message){
		this.receiverID = recever;
		this.senderID = sender;
		this.message = message;
		this.timestamp = -1;
	}

	public String getReceiverID() {
		return receiverID;
	}

	public void setReceiverID(String receiverID) {
		this.receiverID = receiverID;
	}

	public String getSenderID() {
		return senderID;
	}

	public void setSenderID(String senderID) {
		this.senderID = senderID;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	public void setStamp(int isStamp) {
		this.isStamp = isStamp==0?false:true;
	}
	
	public boolean isStamp() {
		return isStamp;
	}
	public void setStamp(boolean isStamp) {
		this.isStamp = isStamp;
	}
	@Override
	public int compareTo(MessageObject another) {
		if (this.timestamp>another.getTimestamp()){
			return 1;
		}
		else if (this.timestamp<another.getTimestamp()){
			return -1;
		}
		else {
			return 0;
		}
	}
	@Override
	public int compare(MessageObject object1, MessageObject object2) {
		return object1.compareTo(object2);
	}
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(receiverID);
		dest.writeString(senderID);
		dest.writeString(message);
		dest.writeLong(timestamp);
		boolean[] arr = new boolean[1];
		arr[0] = isStamp;
		dest.writeBooleanArray(arr);
		
	}
	public static final Parcelable.Creator<MessageObject> CREATOR
  														= new Parcelable.Creator<MessageObject>() {
		 public MessageObject createFromParcel(Parcel in) {
			 return new MessageObject(in);
		 }

		 public MessageObject[] newArray(int size) {
			 return new MessageObject[size];
		 }
	};
	
}
