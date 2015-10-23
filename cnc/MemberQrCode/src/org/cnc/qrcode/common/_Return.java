package org.cnc.qrcode.common;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class _Return implements Parcelable {
	private String success;
	private String type;
	private String message;
	private String lat;
	private String lng;
	private String name;
	private String address;
	private String question;
	private Answer nextPoint;
	private String url;
	private String time;
	private String errorCode;
	private String timeStart;
	private String timeEnd;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}

	public String getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public _Return() {
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public Answer getNextPoint() {
		return nextPoint;
	}

	public void setNextPoint(Answer nextPoint) {
		this.nextPoint = nextPoint;
	}

	public List<String> lCommon = new ArrayList<String>();
	public List<Answer> lAnswre = new ArrayList<Answer>();

	public static final Parcelable.Creator<_Return> CREATOR = new Parcelable.Creator<_Return>() {
		public _Return createFromParcel(Parcel in) {
			return new _Return(in);
		}

		public _Return[] newArray(int size) {
			return new _Return[size];
		}
	};

	public void writeToParcel(Parcel out, int flags) {
	}

	public _Return(Parcel in) {
	}

	public int describeContents() {
		return 0;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}
