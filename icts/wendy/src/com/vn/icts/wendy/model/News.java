package com.vn.icts.wendy.model;

import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class News extends Item implements Parcelable {
	private String time;
	private String contentCrop = null;

	/**
	 * @return the contentCrop
	 */
	public String getContentCrop() {
		return contentCrop;
	}

	/**
	 * @param contentCrop
	 *            the contentCrop to set
	 */
	public void setContentCrop(String contentCrop) {
		this.contentCrop = contentCrop;
	}

	public News() {
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
		out.writeString(getTime());
		out.writeString(getId());
	}

	public static final Parcelable.Creator<News> CREATOR = new Parcelable.Creator<News>() {
		public News createFromParcel(Parcel in) {
			return new News(in);
		}

		public News[] newArray(int size) {
			return new News[size];
		}
	};

	private News(Parcel in) {
		setName(in.readString());
		setComment(in.readString());
		setAddress(in.readString());
		setWeb(in.readString());
		setPrice(in.readString());
		setUrlImage(in.readString());
		setPhone(in.readString());
		setTime(in.readString());
		setId(in.readString());
	}

	public News(JSONObject object) throws Exception {
		setId(object.getString("id"));
		setName(object.getString("title"));
		setUrlImage(object.getString("link_image"));
		setComment(object.getString("content"));
		setTime(object.getString("create_datetime"));
	}

	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}
}
