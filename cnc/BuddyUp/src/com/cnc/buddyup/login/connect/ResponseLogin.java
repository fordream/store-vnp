package com.cnc.buddyup.login.connect;

import android.os.Parcel;
import android.os.Parcelable;

public class ResponseLogin implements Parcelable {
	private boolean status;
	private String user;
	private String password;
	private String message;
	private String error;
	private String userid;

	public ResponseLogin() {
		super();

	}

	public ResponseLogin(boolean status, String user, String password) {
		super();
		this.status = status;
		this.user = user;
		this.password = password;

	}

	public boolean isStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel out, int flags) {
		out.writeInt(status ? 1 : 0);
		out.writeString(user);
		out.writeString(password);
		out.writeString(message);
		out.writeString(error);
		out.writeString(userid);
	}

	public static final Parcelable.Creator<ResponseLogin> CREATOR = new Parcelable.Creator<ResponseLogin>() {
		public ResponseLogin createFromParcel(Parcel in) {
			return new ResponseLogin(in);
		}

		public ResponseLogin[] newArray(int size) {
			return new ResponseLogin[size];
		}
	};

	public ResponseLogin(Parcel in) {
		status = in.readInt() == 1;
		user = in.readString();
		password = in.readString();
		message = in.readString();
		error = in.readString();
		userid = in.readString();
	}
}