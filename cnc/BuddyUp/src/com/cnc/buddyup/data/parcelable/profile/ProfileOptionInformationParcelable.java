package com.cnc.buddyup.data.parcelable.profile;

import android.os.Parcel;
import android.os.Parcelable;

public class ProfileOptionInformationParcelable implements Parcelable {

	public ProfileOptionInformationParcelable() {
	}

	private String sex;
	private String description;
	private String comment;

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel out, int flags) {
		out.writeString(sex);
		out.writeString(description);
		out.writeString(comment);

	}

	public static final Parcelable.Creator<ProfileOptionInformationParcelable> CREATOR = new Parcelable.Creator<ProfileOptionInformationParcelable>() {
		public ProfileOptionInformationParcelable createFromParcel(Parcel in) {
			return new ProfileOptionInformationParcelable(in);
		}

		public ProfileOptionInformationParcelable[] newArray(int size) {
			return new ProfileOptionInformationParcelable[size];
		}
	};

	public ProfileOptionInformationParcelable(Parcel in) {
		this.sex = in.readString();
		description = in.readString();
		comment = in.readString();
	}

	@Override
	public String toString() {
		return this.sex + "\n" + this.description + "\n" + this.comment;
	}
}