package com.icts.object;

import java.util.ArrayList;

/**
 * Stamp object to chat, includes id, code (used to send to server) and image. 
 * With emotion text, image = "  <p>
 * E.g: "stamp_list":[{"id":"1","code":":-==",
 * "image":"http:\/\/icons.iconarchive.com\/icons\/iconscity\/flags\/256\/vietnam-icon.png"}
 * @author Luong
 */
public class StampCatObject{
	private String id;
	private int numberStamp;
	private String imageActive;
	private String imageInactive;
	private boolean isFree = true;
	private boolean fromNative = true;
	private ArrayList<StampObject> arrStamp;
	public StampCatObject(){
		arrStamp = new ArrayList<StampObject>();
	}
	
	public StampCatObject(String id){
		this.id = id;
		arrStamp = new ArrayList<StampObject>();
	}

	public String getId() {
		return id;
	}
	
	public ArrayList<StampObject> getArrStamps(){
		return arrStamp;
	}
	
	public void addStamp(StampObject stamp){
		if (arrStamp!=null){
			arrStamp.add(stamp);
			numberStamp++;
		}
	}
	public void setArrStamps(ArrayList<StampObject> arr){
		arrStamp.clear();
		arrStamp.addAll(arr);
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getNumberStamp() {
		return numberStamp;
	}

	public void setNumberStamp(int numberStamp) {
		this.numberStamp = numberStamp;
	}

	public String getImageActive() {
		return imageActive;
	}

	public void setImageActive(String imageActive) {
		this.imageActive = imageActive;
	}

	public String getImageInactive() {
		return imageInactive;
	}

	public void setImageInactive(String imageInactive) {
		this.imageInactive = imageInactive;
	}

	public boolean isFree() {
		return isFree;
	}

	public void setFree(boolean isFree) {
		this.isFree = isFree;
	}

	public boolean isFromNative() {
		return fromNative;
	}

	public void setFromNative(boolean fromNative) {
		this.fromNative = fromNative;
	}
	

	
}
