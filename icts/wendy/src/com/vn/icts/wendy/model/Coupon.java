package com.vn.icts.wendy.model;

import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class Coupon extends Item implements Parcelable {
	private String like;
	private String time;
	private String post;
	
	
	/**
	 * @return the post
	 */
	public String getPost() {
		return post;
	}

	/**
	 * @param post the post to set
	 */
	public void setPost(String post) {
		this.post = post;
	}

	/**
	 * @return the like
	 */
	public String getLike() {
		return like;
	}

	/**
	 * @param like the like to set
	 */
	public void setLike(String like) {
		this.like = like;
	}

	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	public Coupon() {
	}

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel out, int flags) {

		out.writeString(getName());
		out.writeString(getComment());
		out.writeString(getAddress());
		out.writeString(getWeb());
		out.writeString(getPrice());
		out.writeString(getUrlImage());
		out.writeString(getPhone());
		
		out.writeString(getLike());
		out.writeString(getTime());
		out.writeString(getPost());
		out.writeString(getId());
	}

	public static final Parcelable.Creator<Coupon> CREATOR = new Parcelable.Creator<Coupon>() {
		public Coupon createFromParcel(Parcel in) {
			return new Coupon(in);
		}

		public Coupon[] newArray(int size) {
			return new Coupon[size];
		}
	};

	private Coupon(Parcel in) {
		setName(in.readString());
		setComment(in.readString());
		setAddress(in.readString());
		setWeb(in.readString());
		setPrice(in.readString());
		setUrlImage(in.readString());
		setPhone(in.readString());
		setLike(in.readString());
		setTime(in.readString());
		setPost(in.readString());
		setId(in.readString());
	}

	public Coupon(JSONObject jsonObject) throws Exception{
		setId(jsonObject.getString("id"));
		setName(jsonObject.getString("title"));
		setComment(jsonObject.getString("description"));
		setAddress(jsonObject.getString("address"));
		String url = jsonObject.getString("website");

		if (url != null && !url.startsWith("http")) {
			url = "http://" + url;
		}
		setWeb(url);
		setUrlImage(jsonObject.getString("image_path"));
		setTime(jsonObject.getString("create_datetime"));
	}

}
