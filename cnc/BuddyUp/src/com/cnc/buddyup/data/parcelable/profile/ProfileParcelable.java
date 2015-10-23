package com.cnc.buddyup.data.parcelable.profile;

import android.os.Parcel;
import android.os.Parcelable;

public class ProfileParcelable implements Parcelable {
	private String userName;
	private String firstName;
	private String email;
	private String age;
	private String postalCode;
	private String contry;

	public ProfileParcelable() {
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getContry() {
		return contry;
	}

	public void setContry(String contry) {
		this.contry = contry;
	}

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel out, int flags) {
		out.writeString(userName);
		out.writeString(firstName);
		out.writeString(email);
		out.writeString(age);
		out.writeString(postalCode);
		out.writeString(contry);
	}

	public static final Parcelable.Creator<ProfileParcelable> CREATOR = new Parcelable.Creator<ProfileParcelable>() {
		public ProfileParcelable createFromParcel(Parcel in) {
			return new ProfileParcelable(in);
		}

		public ProfileParcelable[] newArray(int size) {
			return new ProfileParcelable[size];
		}
	};

	public ProfileParcelable(Parcel in) {
		userName = in.readString();
		firstName = in.readString();
		email = in.readString();
		age = in.readString();
		postalCode = in.readString();
		contry = in.readString();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(userName).append("\n");
		sb.append(firstName).append("\n");
		sb.append(email).append("\n");
		sb.append(age).append("\n");
		sb.append(postalCode).append("\n");
		sb.append(contry).append("\n");
		return sb.toString();
	}
}