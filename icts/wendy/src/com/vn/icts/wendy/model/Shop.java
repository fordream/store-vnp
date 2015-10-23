package com.vn.icts.wendy.model;

import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class Shop extends Item implements Parcelable {
	private String lat, log;

	/**
	 * @return the lat
	 */
	public String getLat() {
		return lat;
	}

	/**
	 * @param lat
	 *            the lat to set
	 */
	public void setLat(String lat) {
		this.lat = lat;
	}

	/**
	 * @return the log
	 */
	public String getLog() {
		return log;
	}

	/**
	 * @param log
	 *            the log to set
	 */
	public void setLog(String log) {
		this.log = log;
	}

	public Shop() {
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
		out.writeString(getId());
		out.writeString(getLat());
		out.writeString(getLog());

	}

	public static final Parcelable.Creator<Shop> CREATOR = new Parcelable.Creator<Shop>() {
		public Shop createFromParcel(Parcel in) {
			return new Shop(in);
		}

		public Shop[] newArray(int size) {
			return new Shop[size];
		}
	};

	private Shop(Parcel in) {
		setName(in.readString());
		setComment(in.readString());
		setAddress(in.readString());
		setWeb(in.readString());
		setPrice(in.readString());
		setUrlImage(in.readString());
		setPhone(in.readString());
		setId(in.readString());
		setLat(in.readString());
		setLog(in.readString());
	}

	public Shop(JSONObject jsonObject) throws Exception {
		setId(jsonObject.getString("id"));
		setName(jsonObject.getString("title"));
		setComment(jsonObject.getString("description"));
		setAddress(jsonObject.getString("address"));
		setLat(jsonObject.getString("lat"));
		setLog(jsonObject.getString("long"));
		String url = jsonObject.getString("website");

		if (url != null && !url.startsWith("http")) {
			url = "http://" + url;
		}
		setWeb(url);
		setUrlImage(jsonObject.getString("image_path"));
	}
}